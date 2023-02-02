package kit.personal.ssoentity.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class AppUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;
    @JsonView(EntityJsonView.PUBLIC_VIEW.class)
    private String username;
    @JsonView(EntityJsonView.PUBLIC_VIEW.class)
    private String displayName;
    private String password;
    @JsonView(EntityJsonView.PUBLIC_VIEW.class)
    private String email;
    private String isActive;
    private Date lastModifiedDate;
    private String lastModifiedBy;

    public BigInteger getId() {
        return id;
    }

    public AppUser setId(BigInteger id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public AppUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public AppUser setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AppUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AppUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getIsActive() {
        return isActive;
    }

    public AppUser setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public AppUser setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public AppUser setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }
}
