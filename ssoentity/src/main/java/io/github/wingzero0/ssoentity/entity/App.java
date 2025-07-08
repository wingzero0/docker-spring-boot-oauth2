package io.github.wingzero0.ssoentity.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Immutable;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.ConfigurationSettingNames;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "oauth2_registered_client")
@Immutable
public class App {
	@Id
	private String id;
	@Column(length = 100)
	private String clientId;
	private Timestamp clientIdIssuedAt;
	@Column(length = 200)
	private String clientSecret;
	private Timestamp clientSecretExpiresAt;
	@Column(length = 200)
	private String clientName;

	@Column(name = "client_authentication_methods", length = 1000)
	private String clientAuthenticationMethodsRaw;
	@Column(name = "authorization_grant_types", length = 1000)
	private String authorizationGrantTypesRaw;
	@Column(name = "redirect_uris", length = 1000)
	private String redirectUrisRaw;
	@Column(name = "post_logout_redirect_uris", length = 1000)
	private String postLogoutRedirectUrisRaw;
	@Column(name = "scopes", length = 1000)
	private String scopesRaw;
	@Column(name = "client_settings", length = 2000)
	private String clientSettingsRaw;
	@Column(name = "token_settings", length = 2000)
	private String tokenSettingsRaw;

	@Transient
	private RegisteredClient registeredClient;

	@PostLoad
	private void PostLoad() {
		Set<String> clientAuthenticationMethods = StringUtils.commaDelimitedListToSet(clientAuthenticationMethodsRaw);
		Set<String> authorizationGrantTypes = StringUtils.commaDelimitedListToSet(authorizationGrantTypesRaw);
		Set<String> redirectUris = StringUtils.commaDelimitedListToSet(redirectUrisRaw);
		Set<String> clientScopes = StringUtils.commaDelimitedListToSet(scopesRaw);

		// @formatter:off
		RegisteredClient.Builder builder = RegisteredClient.withId(id)
				.clientId(clientId)
				.clientIdIssuedAt(clientIdIssuedAt != null ? clientIdIssuedAt.toInstant() : null)
				.clientSecret(clientSecret)
				.clientSecretExpiresAt(clientSecretExpiresAt != null ? clientSecretExpiresAt.toInstant() : null)
				.clientName(clientName)
				.clientAuthenticationMethods((authenticationMethods) ->
						clientAuthenticationMethods.forEach(authenticationMethod ->
								authenticationMethods.add(new ClientAuthenticationMethod(authenticationMethod))))
				.authorizationGrantTypes((grantTypes) ->
						authorizationGrantTypes.forEach(grantType ->
								grantTypes.add(new AuthorizationGrantType(grantType))))
				.redirectUris((uris) -> uris.addAll(redirectUris))
				.scopes((scopes) -> scopes.addAll(clientScopes));
		// @formatter:on

		ObjectMapper objectMapper = new ObjectMapper();
		ClassLoader classLoader = JdbcRegisteredClientRepository.class.getClassLoader();
		List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
		objectMapper.registerModules(securityModules);
		objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());

		Map<String, Object> clientSettingsMap = parseMap(objectMapper, clientSettingsRaw);
		builder.clientSettings(ClientSettings.withSettings(clientSettingsMap).build());

		Map<String, Object> tokenSettingsMap = parseMap(objectMapper, tokenSettingsRaw);
		TokenSettings.Builder tokenSettingsBuilder = TokenSettings.withSettings(tokenSettingsMap);
		if (!tokenSettingsMap.containsKey(ConfigurationSettingNames.Token.ACCESS_TOKEN_FORMAT)) {
			tokenSettingsBuilder.accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED);
		}
		builder.tokenSettings(tokenSettingsBuilder.build());

		this.registeredClient = builder.build();
	}

	private Map<String, Object> parseMap(ObjectMapper objectMapper, String data) {
		try {
			return objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {
			});
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}
	}

	// #region getter setter
	public String getId() {
		return id;
	}

	public RegisteredClient getRegisteredClient() {
		return registeredClient;
	}
	// #endregion getter setter
}
