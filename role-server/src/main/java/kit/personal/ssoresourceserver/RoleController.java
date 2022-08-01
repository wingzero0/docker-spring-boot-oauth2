package kit.personal.ssoresourceserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kit.personal.ssoentity.entity.AppUserRole;
import kit.personal.ssoentity.repo.AppUserRoleRepository;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RoleController {
    private static Logger LOG = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private AppUserRoleRepository appUserRoleRepo;

    @GetMapping("/appRole")
    public List<AppUserRole> getAllRoleInApp(Principal principal) {
        return appUserRoleRepo.findAllByAppClientId(principal.getName());
    }

    @GetMapping("/appRole/{username}")
    public List<String> getAllRoleInAppByUsername(Principal principal,
            @PathVariable(name = "username") String username) {
        LOG.debug("principal name:" + principal.getName() + " username:" + username);
        List<String> roles = appUserRoleRepo.findAllByAppClientIdAndUsername(principal.getName(), username)
                .stream()
                .map(appUserRole -> appUserRole.getUserRole())
                .collect(Collectors.toList());
        for (String role : roles){
            LOG.debug(role);
        }
        return roles;
    }
}