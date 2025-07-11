package io.github.wingzero0.ssoresourceserver;

import io.github.wingzero0.ssoentity.entity.AppUserRole;
import io.github.wingzero0.ssoentity.repo.AppUserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RoleController {
    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private AppUserRoleRepository appUserRoleRepo;

    @GetMapping("/testScopeRead")
    @ResponseBody
    public Map<String, Object> user(Principal principal) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("principal", principal);
        ret.put("message", "it works");
        return ret;
    }

    @GetMapping("/testScopeWrite")
    @ResponseBody
    public Map<String, Object> testDB(Principal principal) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("principal", principal);
        ret.put("message", "it works");
        return ret;
    }

    // for user credential access token
    @GetMapping("/appRole")
    public List<String> getRoleByUserToken(Principal principal) {
        LOG.debug("principal class:{}", principal.getClass().getCanonicalName());
        if (principal instanceof BearerTokenAuthentication auth) {
            Map<String, Object> tokenAttributes = auth.getTokenAttributes();
            tokenAttributes.forEach((key, value) -> {
                LOG.debug("key:{}", key);
                LOG.debug("value:{}", value.toString());
                LOG.debug("value class:{}", value.getClass().getCanonicalName());
            });
            String clientId = null;
            if (tokenAttributes.containsKey("client_id")) {
                clientId = (String) tokenAttributes.get("client_id");
            }
            String username = null;
            if (tokenAttributes.containsKey("sub")) {
                username = (String) tokenAttributes.get("sub");
            }
            List<String> roles = appUserRoleRepo.findAllByAppClientIdAndUsername(clientId, username)
                    .stream()
                    .map(AppUserRole::getUserRole)
                    .collect(Collectors.toList());
            for (String role : roles) {
                LOG.debug(role);
            }
            return roles;
        }
        return List.of();
    }

    // for client credential access token
    @GetMapping("/appRole/{username}")
    public List<String> getRoleByClientTokenAndUsername(Principal principal,
                                                        @PathVariable(name = "username") String username) {
        LOG.debug("principal name:{}, username:{}", principal.getName(), username);
        List<String> roles = appUserRoleRepo.findAllByAppClientIdAndUsername(principal.getName(), username)
                .stream()
                .map(AppUserRole::getUserRole)
                .collect(Collectors.toList());
        for (String role : roles) {
            LOG.debug(role);
        }
        return roles;
    }

    // for client credential access token
    @GetMapping("/appRoles")
    public List<String> getRoleByClientToken(Principal principal) {
        return appUserRoleRepo.findAllByAppClientId(principal.getName()).stream().map(AppUserRole::getUserRole).toList();
    }
}