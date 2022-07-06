package kit.personal.ssoentity.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity(name = "oauth_client_details")
@JsonIgnoreProperties(ignoreUnknown = true)
public class App {
	@Id
	private String clientId;
	private String resourceIds;
	private String clientSecret;
	private String scope;
	private String authorizedGrantTypes;
	private String webServerRedirectUri;
	private String authorities;
	private Integer accessTokenValidity;
	private Integer refreshTokenValidity;
	private String additionalInformation;
	private String autoapprove;
	private String displayName;
	@Transient
	private boolean updateClientSecret;

	public boolean isUpdateClientSecret() {
		return this.updateClientSecret;
	}

	public App setUpdateClientSecret(boolean updateClientSecret) {
		this.updateClientSecret = updateClientSecret;
		return this;
	}

	public String getClientId() {
		return clientId;
	}

	public App setClientId(String clientId) {
		this.clientId = clientId;
		return this;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public App setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
		return this;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public App setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
		return this;
	}

	public String getScope() {
		return scope;
	}

	public App setScope(String scope) {
		this.scope = scope;
		return this;
	}

	public String getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	public App setAuthorizedGrantTypes(String authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
		return this;
	}

	public String getWebServerRedirectUri() {
		return webServerRedirectUri;
	}

	public App setWebServerRedirectUri(String webServerRedirectUri) {
		this.webServerRedirectUri = webServerRedirectUri;
		return this;
	}

	public String getAuthorities() {
		return authorities;
	}

	public App setAuthorities(String authorities) {
		this.authorities = authorities;
		return this;
	}

	public Integer getAccessTokenValidity() {
		return accessTokenValidity;
	}

	public App setAccessTokenValidity(Integer accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
		return this;
	}

	public Integer getRefreshTokenValidity() {
		return refreshTokenValidity;
	}

	public App setRefreshTokenValidity(Integer refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
		return this;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public App setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
		return this;
	}

	public String getAutoapprove() {
		return autoapprove;
	}

	public App setAutoapprove(String autoapprove) {
		this.autoapprove = autoapprove;
		return this;
	}

	public String getDisplayName() {
		return displayName;
	}

	public App setDisplayName(String displayName) {
		this.displayName = displayName;
		return this;
	}
}
