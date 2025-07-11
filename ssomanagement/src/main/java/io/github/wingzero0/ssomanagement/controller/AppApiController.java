package io.github.wingzero0.ssomanagement.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient.Builder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.github.wingzero0.ssoentity.entity.App;
import io.github.wingzero0.ssoentity.entity.AppUserRole;
import io.github.wingzero0.ssoentity.repo.AppRepository;
import io.github.wingzero0.ssoentity.repo.AppUserRoleRepository;
import io.github.wingzero0.ssomanagement.controller.exception.ResourceNotFoundException;
import io.github.wingzero0.ssomanagement.controller.exception.WrongParameterException;
import io.github.wingzero0.ssomanagement.utility.LoginChecker;

@Controller
@RequestMapping(value = "/api")
@Tag(name = "AppApiController")
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
	public Page<RegisteredClientResponse> getAppList(
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer limit,
			Authentication auth) {
		Sort sort = Sort.by(Sort.Direction.DESC, "clientId");

		if (loginChecker.isReachableRole("ROLE_ADMIN", auth)) {
			Page<App> appResult = appRepository.findAllBy(PageRequest.of(page, limit, sort));
			return appResult.map((app) -> {
				return this.convertRegisteredClient(app.getRegisteredClient());
			});
		}

		Set<String> appIds = new HashSet<>();

		List<AppUserRole> appUserRoleList = appUserRoleRepository.findAllByUsernameAndUserRoleIgnoreCase(
				loginChecker.getLoginName(auth), "ADMIN");

		for (AppUserRole appUserRole : appUserRoleList) {
			appIds.add(appUserRole.getAppClientId());
		}

		Page<App> filteredAppResult = appRepository.findAllByClientIdIn(PageRequest.of(page, limit, sort), appIds);
		return filteredAppResult.map((app) -> {
			return this.convertRegisteredClient(app.getRegisteredClient());
		});
	}

	private RegisteredClientResponse convertRegisteredClient(RegisteredClient client) {
		RegisteredClientResponse response = new RegisteredClientResponse();
		BeanUtils.copyProperties(client, response);
		response.getAuthorizationGrantTypes().addAll(
				client.getAuthorizationGrantTypes().stream().map((grantType) -> grantType.getValue()).toList());
		response.getClientAuthenticationMethods().addAll(
				client.getClientAuthenticationMethods().stream().map((method) -> method.getValue()).toList());
		return response;
	}

	@PostMapping(value = "/app", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RegisteredClientResponse createRegisteredClient(
			@RequestBody RegisteredClientRequest registeredClientRequest) {
		RegisteredClient existingClient = registeredClientRepository
				.findByClientId(registeredClientRequest.getClientId());
		if (existingClient != null) {
			throw new WrongParameterException("client id existed:" + registeredClientRequest.getClientId());
		}
		Builder builder = RegisteredClient.withId(UUID.randomUUID().toString());
		Date now = new Date();
		builder = builder
				.clientId(registeredClientRequest.getClientId())
				.clientIdIssuedAt(now.toInstant())
				.clientSecret(passwordEncoder.encode(registeredClientRequest.getClientSecret()))
				.clientSecretExpiresAt(registeredClientRequest.getClientSecretExpiresAt())
				.clientName(registeredClientRequest.getClientName());

		for (String method : registeredClientRequest.getClientAuthenticationMethods()) {
			builder.clientAuthenticationMethod(
					new ClientAuthenticationMethod(method));
		}

		for (String grantType : registeredClientRequest.getAuthorizationGrantTypes()) {
			builder.authorizationGrantType(
					new AuthorizationGrantType(grantType));
		}

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
		return this.convertRegisteredClient(registeredClient);
	}

	@GetMapping(value = "/app/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RegisteredClientResponse readRegisteredClient(
			@PathVariable(value = "id") String id) {
		return this.convertRegisteredClient(registeredClientRepository.findById(id));
	}

	@PutMapping(value = "/app/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RegisteredClientResponse updateRegisteredClient(
			@RequestBody RegisteredClientRequest registeredClientRequest,
			@PathVariable(value = "id") String id) {
		RegisteredClient existingClient = registeredClientRepository
				.findByClientId(registeredClientRequest.getClientId());
		if (existingClient == null || existingClient.getId().equalsIgnoreCase(id) == false) {
			throw new WrongParameterException("clientId not found or clientId not match id");
		}

		Builder builder = RegisteredClient.from(existingClient);
		builder.clientName(registeredClientRequest.getClientName());
		builder.clientAuthenticationMethods((methods) -> methods.clear());
		for (String method : registeredClientRequest.getClientAuthenticationMethods()) {
			builder.clientAuthenticationMethod(
					new ClientAuthenticationMethod(method));
		}
		builder.authorizationGrantTypes((grantTypes) -> grantTypes.clear());
		for (String grantType : registeredClientRequest.getAuthorizationGrantTypes()) {
			builder.authorizationGrantType(
					new AuthorizationGrantType(grantType));
		}

		builder.redirectUris((uris) -> uris.clear());
		for (String redirectUri : registeredClientRequest.getRedirectUris()) {
			builder.redirectUri(redirectUri);
		}

		builder.scopes((scopes) -> scopes.clear());
		builder.scope(OidcScopes.OPENID);
		for (String scope : registeredClientRequest.getScopes()) {
			builder.scope(scope);
		}

		// In JdbcRegisteredClientRepository: client_id, client_id_issued_at,
		// client_secret, client_secret_expires_at are never updated
		// Also no delete function support. How should I handle client_secret update?

		RegisteredClient replaceClient = builder.build();
		registeredClientRepository.save(replaceClient);
		return this.convertRegisteredClient(replaceClient);
	}

	@GetMapping(value = "/app/{id}/role", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<AppUserRole> getAppRole(@PathVariable(value = "id") String id) {
		RegisteredClient registeredClient = registeredClientRepository.findById(id);
		if (registeredClient == null) {
			throw new ResourceNotFoundException("RegisteredClient id not found" + id);
		}
		return appUserRoleRepository.findAllByAppClientId(registeredClient.getClientId());
	}
}
