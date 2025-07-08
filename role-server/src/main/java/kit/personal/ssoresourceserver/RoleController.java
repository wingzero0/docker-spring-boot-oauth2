package kit.personal.ssoresourceserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.github.wingzero0.ssoentity.entity.AppUserRole;
import io.github.wingzero0.ssoentity.repo.AppUserRoleRepository;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RoleController {
    private static Logger LOG = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private AppUserRoleRepository appUserRoleRepo;

    @GetMapping("/testScopeRead")
    @ResponseBody
    public Map<String, Object> user(Principal principal) {
        Map<String, Object> ret = new HashMap<> ();
        ret.put("principal", principal);
        ret.put("message", "it works");
        return ret;
    }

    @GetMapping("/testScopeWrite")
    @ResponseBody
    public Map<String, Object> testDB(Principal principal) {
        Map<String, Object> ret = new HashMap<> ();
        ret.put("principal", principal);
        ret.put("message", "it works");
        return ret;
    }

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