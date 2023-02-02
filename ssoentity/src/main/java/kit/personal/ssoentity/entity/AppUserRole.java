package kit.personal.ssoentity.entity;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigInteger;
import java.util.Date;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppUserRole {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private BigInteger id;
	private String username;
	private String userRole;
	private String appClientId;
	private Date lastModifiedDate;
	private String lastModifiedBy;

	public BigInteger getId() {
		return id;
	}

	public AppUserRole setId(BigInteger id) {
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
