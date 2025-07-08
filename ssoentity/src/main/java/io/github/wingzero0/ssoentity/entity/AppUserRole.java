package io.github.wingzero0.ssoentity.entity;

import java.util.Date;
import java.util.UUID;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppUserRole {
	@Id
	private String id;
	private String username;
	private String userRole;
	private String appClientId;
	private Date lastModifiedDate;
	private String lastModifiedBy;

	@PrePersist
	@JsonIgnore
	public void genUUID() {
		Date now = new Date();
		if (!StringUtils.hasLength(this.getId())) {
			this.setId(now.getTime() + "_" + UUID.randomUUID().toString());
		}
	}

	public String getId() {
		return id;
	}

	public AppUserRole setId(String id) {
		this.id = id;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public AppUserRole setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getAppClientId() {
		return appClientId;
	}

	public void setAppClientId(String appClientId) {
		this.appClientId = appClientId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public AppUserRole setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
		return this;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public AppUserRole setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
		return this;
	}
}
