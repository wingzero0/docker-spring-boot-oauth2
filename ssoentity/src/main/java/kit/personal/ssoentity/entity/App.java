package kit.personal.ssoentity.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "oauth2_registered_client")
@JsonIgnoreProperties(ignoreUnknown = true)
public class App {
	@Id
	private String id;
	private String clientId;
	private LocalDateTime clientIdIssuedAt;
	private String clientSecret;
	private LocalDateTime clientSecretExpiresAt;
	private String clientName;
	private String clientAuthenticationMethods;
	private String authorizationGrantTypes;
	private String redirectUris;
	private String scopes;
	private String clientSettings;
	private String tokenSettings;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public LocalDateTime getClientIdIssuedAt() {
		return clientIdIssuedAt;
	}
	public void setClientIdIssuedAt(LocalDateTime clientIdIssuedAt) {
		this.clientIdIssuedAt = clientIdIssuedAt;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public LocalDateTime getClientSecretExpiresAt() {
		return clientSecretExpiresAt;
	}
	public void setClientSecretExpiresAt(LocalDateTime clientSecretExpiresAt) {
		this.clientSecretExpiresAt = clientSecretExpiresAt;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientAuthenticationMethods() {
		return clientAuthenticationMethods;
	}
	public void setClientAuthenticationMethods(String clientAuthenticationMethods) {
		this.clientAuthenticationMethods = clientAuthenticationMethods;
	}
	public String getAuthorizationGrantTypes() {
		return authorizationGrantTypes;
	}
	public void setAuthorizationGrantTypes(String authorizationGrantTypes) {
		this.authorizationGrantTypes = authorizationGrantTypes;
	}
	public String getRedirectUris() {
		return redirectUris;
	}
	public void setRedirectUris(String redirectUris) {
		this.redirectUris = redirectUris;
	}
	public String getScopes() {
		return scopes;
	}
	public void setScopes(String scopes) {
		this.scopes = scopes;
	}
	public String getClientSettings() {
		return clientSettings;
	}
	public void setClientSettings(String clientSettings) {
		this.clientSettings = clientSettings;
	}
	public String getTokenSettings() {
		return tokenSettings;
	}
	public void setTokenSettings(String tokenSettings) {
		this.tokenSettings = tokenSettings;
	}

	
}
