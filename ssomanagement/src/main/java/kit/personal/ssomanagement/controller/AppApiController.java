package kit.personal.ssomanagement.controller;

import kit.personal.ssoentity.entity.App;
import kit.personal.ssoentity.entity.AppUserRole;
import kit.personal.ssoentity.repo.AppRepository;
import kit.personal.ssoentity.repo.AppUserRoleRepository;
import kit.personal.ssomanagement.controller.exception.ResourceNotFoundException;
import kit.personal.ssomanagement.controller.exception.WrongParameterException;
import kit.personal.ssomanagement.utility.LoginChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient.Builder;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(value = "/api")
public class AppApiController {
	@Autowired
	private AppRepository appRepository;
	@Autowired
	private AppUserRoleRepository appUserRoleRepository;
	@Autowired
	private LoginChecker loginChecker;
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(AppApiController.class);
	@Autowired
	private RegisteredClientRepository registeredClientRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping(value = "/app", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<App> getAppList(
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer limit,
			Authentication auth) {
		Sort sort = Sort.by(Sort.Direction.DESC, "clientId");

		if (loginChecker.isReachableRole("ROLE_ADMIN", auth)) {
			return appRepository.findAllBy(PageRequest.of(page, limit, sort));
		}

		Set<String> appIds = new HashSet<>();

		List<AppUserRole> appUserRoleList = appUserRoleRepository.findAllByUsernameAndUserRoleIgnoreCase(
				loginChecker.getLoginName(auth), "ADMIN");

		for (AppUserRole appUserRole : appUserRoleList) {
			appIds.add(appUserRole.getAppClientId());
		}
		return appRepository.findAllByClientIdIn(PageRequest.of(page, limit, sort), appIds);
	}

	@GetMapping(value = "/app/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public App getApp(
			@PathVariable(value = "clientId") String clientId) {
		App app = appRepository.findById(clientId)
				.orElseThrow(() -> new ResourceNotFoundException("client id not found"));
		return app;
	}

	@GetMapping(value = "/app/{clientId}/role", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<AppUserRole> getAppRole(@PathVariable(value = "clientId") String clientId) {
		return appUserRoleRepository.findAllByAppClientId(clientId);
	}

	@PostMapping(value = "/app", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RegisteredClient createRegisteredClient(@RequestBody RegisteredClientRequest registeredClientRequest) {
		RegisteredClient existingClient = registeredClientRepository
				.findByClientId(registeredClientRequest.getClientId());
		if (existingClient != null) {
			throw new WrongParameterException("client id existed:" + registeredClientRequest.getClientId());
		}
		Builder builder = RegisteredClient.withId(UUID.randomUUID().toString());
		builder = builder
				.clientId(registeredClientRequest.getClientId())
				.clientSecret(passwordEncoder.encode(registeredClientRequest.getClientSecret()))
				.clientName(registeredClientRequest.getClientName())
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS);
		for (String redirectUri : registeredClientRequest.getRedirectUris()) {
			builder = builder.redirectUri(redirectUri);
		}

		builder = builder.scope(OidcScopes.OPENID); // this one no consent
		for (String scope : registeredClientRequest.getScopes()) {
			builder = builder.scope(scope); // all require consent
		}
		builder = builder.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build());

		RegisteredClient registeredClient = builder.build();
		registeredClientRepository.save(registeredClient);
		return registeredClient;
	}

	@PutMapping(value = "/app/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RegisteredClient updateRegisteredClient(
			@RequestBody RegisteredClientRequest registeredClientRequest,
			@PathVariable(value = "id") String id) {
		RegisteredClient existingClient = registeredClientRepository
				.findByClientId(registeredClientRequest.getClientId());
		if (existingClient == null || existingClient.getId().equalsIgnoreCase(id) == false) {
			throw new WrongParameterException("clientId not found or clientId not match id");
		}

		Builder builder = RegisteredClient.from(existingClient);
		builder.redirectUris((uris) -> uris.clear());
		for (String redirectUri : registeredClientRequest.getRedirectUris()) {
			builder.redirectUri(redirectUri);
		}

		builder.scopes((scopes) -> scopes.clear());
		builder.scope(OidcScopes.OPENID);
		for (String scope : registeredClientRequest.getScopes()) {
			builder.scope(scope);
		}

		builder.clientName(registeredClientRequest.getClientName());

		// In JdbcRegisteredClientRepository: client_id, client_id_issued_at,
		// client_secret, client_secret_expires_at are never updated
		// Also no delete function support. How should I handle client_secret update?

		RegisteredClient replaceClient = builder.build();
		registeredClientRepository.save(replaceClient);
		return replaceClient;
	}
}
