package kit.personal.ssomanagementapplicationrunner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kit.personal.ssoentity.entity.AppUserRole;
import kit.personal.ssoentity.repo.AppUserRoleRepository;

@Service
public class AppUserRoleService {
    @Autowired
    AppUserRoleRepository appUserRoleRepository;

    public List<AppUserRole> createAppUserRole(
            String appClientId, String username, List<String> userRoles) {
        List<AppUserRole> appUserRoles = userRoles.stream().map(userRole -> {
            AppUserRole appUserRole = new AppUserRole();
            appUserRole.setAppClientId(appClientId);
            appUserRole.setUsername(username);
            appUserRole.setUserRole(userRole);
            return appUserRole;
        }).toList();

        appUserRoleRepository.saveAll(appUserRoles);
        return appUserRoles;
    }
}
