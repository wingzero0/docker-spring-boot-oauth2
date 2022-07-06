package kit.personal.ssoentity.entity;


import org.springframework.data.annotation.Immutable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
@Immutable
public class ActingRole {
    @EmbeddedId
    private Pk pk;
    private String appId;
    private String appRole;

    @Embeddable
    public static class Pk implements Serializable {
        private static final long serialVersionUID = 1L;
        private Date fromDate;
        private Date toDate;
        private String username;
        private String actingForUsername;

        public Date getFromDate() {
            return fromDate;
        }

        public void setFromDate(Date fromDate) {
            this.fromDate = fromDate;
        }

        public Date getToDate() {
            return toDate;
        }

        public void setToDate(Date toDate) {
            this.toDate = toDate;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getActingForUsername() {
            return actingForUsername;
        }

        public void setActingForUsername(String actingForUsername) {
            this.actingForUsername = actingForUsername;
        }
    }

    public Pk getPk() {
        return pk;
    }
    public void setPk(Pk pk) {
        this.pk = pk;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppRole() {
        return appRole;
    }

    public ActingRole setAppRole(String appRole) {
        this.appRole = appRole;
        return this;
    }
}
