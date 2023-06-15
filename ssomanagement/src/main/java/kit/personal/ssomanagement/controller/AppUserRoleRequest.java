package kit.personal.ssomanagement.controller;

public class AppUserRoleRequest {
    private String username;
    private String userRole;
    private String appClientId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getAppClientId() {
        return appClientId;
    }

    public void setAppClientId(String appClientId) {
        this.appClientId = appClientId;
    }

}
