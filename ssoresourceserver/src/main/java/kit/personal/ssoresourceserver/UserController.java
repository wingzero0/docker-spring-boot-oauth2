package kit.personal.ssoresourceserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {
    @SuppressWarnings("unused")
    private static Logger LOG = LoggerFactory.getLogger(UserController.class);


    @GetMapping("/user/read")
    @ResponseBody
    public Principal user(Principal principal) {
        // TODO how to update info if token is a long term token?
        return principal;
    }

    @GetMapping("/user/write")
    @ResponseBody
    public String testDB(Principal principal) {
        return "it works";
    }

    // temp copy for ssoserver
    // @GetMapping("/user/role")
    // @ResponseBody
    // public Set<String> role(Principal principal) {
    //     OAuth2Authentication authen = (OAuth2Authentication) principal;
    //     String appId = authen.getOAuth2Request().getClientId();
    //     String username = principal.getName();
    //     Set<String> roles = new HashSet<>();

    //     roles.add("ROLE_USER");

    //     //List<ActingRole> extendRoleList = actingRoleRepository.findAllByAppIdAndPkUsername(appId, username);
    //     Date today = new Date();
    //     List<ActingRole> extendRoleList = actingRoleRepository.findAllByAppIdAndPkUsernameAndDate(appId, username, today);


    //     for (ActingRole role : extendRoleList){
    //         roles.add("ROLE_" + role.getAppId() + "_" + role.getAppRole());
    //     }

    //     List<AppUserRole> originalRoleList = roleRepository.findAllByAppIdAndUsername(appId, username);
    //     for (AppUserRole role : originalRoleList){
    //         roles.add("ROLE_" + role.getAppId() + "_" + role.getAppRole());
    //     }
    //     return roles;
    // }

}