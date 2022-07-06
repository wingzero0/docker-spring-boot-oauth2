package kit.personal.ssoentity.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigInteger;
import java.util.Date;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppUserRole {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;
	// TODO consider add foreign key to AppUser.username, or change reference to AppUser.id?
	private String username;
	private String appId;
	private String appRole;
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

	public String getAppId() {
		return appId;
	}

	public AppUserRole setAppId(String appId) {
		this.appId = appId;
		return this;
	}

	public String getAppRole() {
		return appRole;
	}

	public AppUserRole setAppRole(String role) {
		this.appRole = role;
		return this;
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
