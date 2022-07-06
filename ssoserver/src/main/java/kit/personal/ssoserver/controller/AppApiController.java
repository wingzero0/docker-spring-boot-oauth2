package kit.personal.ssoserver.controller;

// import com.fasterxml.jackson.annotation.JsonView;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import kit.personal.ssoserver.controller.exception.WrongParameterException;
// import kit.personal.ssoentity.entity.*;
// import kit.personal.ssoentity.repo.ActingRoleRepository;
// import kit.personal.ssoentity.repo.AppUserActingRepository;
// import kit.personal.ssoentity.repo.AppUserRepository;
// import kit.personal.ssoentity.repo.AppUserRoleRepository;
// import kit.personal.ssoserver.utils.UpsertUser;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Sort;
// import org.springframework.http.MediaType;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.*;

// import javax.transaction.Transactional;
// import java.io.IOException;
// import java.security.Principal;
// import java.text.SimpleDateFormat;
// import java.util.*;

// @Controller
public class AppApiController {
    // @Autowired
    // AppUserRoleRepository roleRepository;
    // @Autowired
    // ActingRoleRepository actingRoleRepository;
    // @Autowired
    // AppUserRepository appUserRepository;
    // @Autowired
    // AppUserActingRepository appUserActingRepository;
    // @Autowired
    // PasswordEncoder passwordEncoder;

    // private static Logger LOG = LoggerFactory.getLogger(AppApiController.class);

    // @PostMapping(value = "/app/upsertUserList", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // @PreAuthorize("#oauth2.hasScope('user_management')")
    // @ResponseBody
    // public String upsertUserList(Principal principal, @RequestBody String jsonString) {
    //     //TODO make username as unique,
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     UpsertUser[] upsertUsers = null;
    //     try {
    //         upsertUsers = objectMapper.readValue(jsonString, UpsertUser[].class);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         throw new WrongParameterException("json parser fail");
    //     }
    //     List<AppUser> appUserList = new ArrayList<>();
    //     for (UpsertUser user: upsertUsers){
    //         AppUser appUser = appUserRepository.findOneByUsername(user.username);
    //         if (appUser == null){
    //             appUser = new AppUser();
    //             appUser.setUsername(user.username);
    //         }

    //         appUser.setDisplayName(user.displayName)
    //             .setPassword(passwordEncoder.encode(user.password))
    //             .setEmail(user.email)
    //             .setIsActive(user.isActive)
    //             .setLastModifiedDate(new Date())
    //             .setLastModifiedBy(principal.getName());
    //         appUserList.add(appUser);
    //     }
    //     if (!appUserList.isEmpty()){
    //         appUserRepository.saveAll(appUserList);
    //         return "{\"ret\":true}";
    //     } else {
    //         return "{\"ret\":false}";
    //     }
    // }

    // @PostMapping(value = "/app/upsertUserActing", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // @PreAuthorize("#oauth2.hasScope('user_management')")
    // @ResponseBody
    // public String upsertUserActing(Principal principal, @RequestBody String jsonString) {
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
    //     AppUserActing[] actingRecords = null;
    //     try {
    //         actingRecords = objectMapper.readValue(jsonString, AppUserActing[].class);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         throw new WrongParameterException("json parser fail");
    //     }
    //     List<AppUserActing> newActingList = new ArrayList<>();
    //     for (AppUserActing acting: actingRecords){
    //         AppUserActing exitingActing = appUserActingRepository.findOneByFromDateAndToDateAndUsernameAndActingForUsername(
    //                 acting.getFromDate(), acting.getToDate(), acting.getUsername(), acting.getActingForUsername());
    //         if (exitingActing != null){
    //             continue;
    //         }
    //         acting.setLastModifiedBy(principal.getName())
    //                 .setLastModifiedDate(new Date());
    //         newActingList.add(acting);
    //     }
    //     if (!newActingList.isEmpty()){
    //         appUserActingRepository.saveAll(newActingList);
    //         return "{\"ret\":true}";
    //     } else {
    //         return "{\"ret\":false}";
    //     }
    // }

    // @GetMapping("/app/allUsersEmail")
    // @PreAuthorize("#oauth2.hasScope('user_management')")
    // @ResponseBody
    // @JsonView(EntityJsonView.PUBLIC_VIEW.class)
    // public List<AppUser> offlineAllUsersEmail(Principal principal,
    //                                           @RequestParam(value = "username[]") String[] usernameList
    // ) {
    //     List<String> funcNos = Arrays.asList(usernameList);
    //     List<AppUser> appUserList = this.appUserRepository.findAllByUsernameIn(funcNos);
    //     return appUserList;
    // }

    // @GetMapping("/app/fullUserList")
    // @PreAuthorize("#oauth2.hasScope('full_user_list')")
    // @ResponseBody
    // public Iterable<AppUserRole> offlineRole(Principal principal) {
    //     Iterable<AppUserRole> roleList = roleRepository.findAllByAppId(principal.getName());
    //     return roleList;
    // }

    // @GetMapping("/app/fullUserListActing")
    // @PreAuthorize("#oauth2.hasScope('full_user_list')")
    // @ResponseBody
    // public Iterable<ActingRole> offlineActingRole(Principal principal) {
    //     Date today = new Date();
    //     Iterable<ActingRole> roleList = actingRoleRepository.findAllByAppIdAndDate(
    //             principal.getName(),
    //             today
    //     );
    //     return roleList;
    // }

    // @GetMapping("/app/fullUserListPaging")
    // @PreAuthorize("#oauth2.hasScope('full_user_list')")
    // @ResponseBody
    // public Page<AppUserRole> offlineRolePaging(Principal principal,
    //                                            @RequestParam(value = "page", required = false, defaultValue = "0") String page,
    //                                            @RequestParam(value = "limit", required = false, defaultValue = "10") String limit
    // ) {
    //     int pageNum = Integer.valueOf(page);
    //     int limitNum = Integer.valueOf(limit);
    //     Sort sort = Sort.by(Sort.Direction.DESC, "username");

    //     Page<AppUserRole> roleList = roleRepository.findAllByAppId(principal.getName(), PageRequest.of(pageNum, limitNum, sort));
    //     return roleList;
    // }

    // @PostMapping("/app/addUserRole")
    // @PreAuthorize("#oauth2.hasScope('full_user_list')")
    // @ResponseBody
    // public Map<String, Boolean> offlineAddRole(
    //         Principal principal,
    //         @RequestParam(value = "username") String username,
    //         @RequestParam(value = "appRole") String appRole
    // ) {
    //     String appName = principal.getName();
    //     long recordCount = roleRepository.countByAppIdAndUsernameAndAppRole(appName, username, appRole);
    //     HashMap<String, Boolean> ret = new HashMap<>();
    //     if (recordCount <= 0.0){
    //         AppUserRole role = new AppUserRole();
    //         role.setAppId(appName)
    //             .setAppRole(appRole)
    //             .setUsername(username)
    //             .setLastModifiedBy(principal.getName())
    //             .setLastModifiedDate(new Date())
    //         ;
    //         roleRepository.save(role);
    //         ret.put("ret", true);
    //     } else {
    //         ret.put("ret", false);
    //     }
    //     return ret;
    // }

    // @PostMapping("/app/deleteUserRole")
    // @PreAuthorize("#oauth2.hasScope('full_user_list')")
    // @ResponseBody
    // @Transactional
    // public Map<String, Boolean> deleteUserRole(
    //         Principal principal,
    //         @RequestParam(value = "username") String username,
    //         @RequestParam(value = "appRole") String appRole
    // ) {
    //     String appName = principal.getName();
    //     long recordCount = roleRepository.deleteByAppIdAndUsernameAndAppRole(appName, username, appRole);
    //     HashMap<String, Boolean> ret = new HashMap<>();
    //     if (recordCount > 0.0){
    //         ret.put("ret", true);
    //     } else {
    //         ret.put("ret", false);
    //     }
    //     return ret;
    // }


    // @GetMapping("/app/usersWithRole/{appRole}")
    // @PreAuthorize("#oauth2.hasScope('full_user_list')")
    // @ResponseBody
    // @JsonView(EntityJsonView.PUBLIC_VIEW.class)
    // public Iterable<AppUser> offlineUsersOfRole(Principal principal, @PathVariable String appRole) {
    //     List<AppUserRole> roleList = roleRepository.findAllByAppIdAndAppRole(principal.getName(), appRole);
    //     Set<String> usernames = new HashSet<>();
    //     for(AppUserRole role:roleList){
    //         usernames.add(role.getUsername());
    //     }
    //     List<ActingRole> actingRoles = actingRoleRepository.findAllByAppIdAndAppRoleAndDate(principal.getName(), appRole, new Date());
    //     for (ActingRole actingRole:actingRoles){
    //         usernames.add(actingRole.getPk().getUsername());
    //     }
    //     List<AppUser> gpsList = this.appUserRepository.findAllByUsernameIn(usernames);
    //     return gpsList;
    // }

    // @GetMapping("/app/usersEmail")
    // @PreAuthorize("#oauth2.hasScope('full_user_list')")
    // @ResponseBody
    // @JsonView(EntityJsonView.PUBLIC_VIEW.class)
    // public List<AppUser> offlineUsersEmail(Principal principal,
    //         @RequestParam(value = "username[]") String[] usernameArray
    // ) {
    //     List<String> usernameList = Arrays.asList(usernameArray);
    //     List<AppUserRole> roleList = this.roleRepository.findAllByAppIdAndUsernameIn(principal.getName(), usernameList);
    //     Set<String> usernameFiltered = new HashSet<>();
    //     for(AppUserRole role: roleList){
    //         usernameFiltered.add(role.getUsername());
    //     }

    //     List<ActingRole> actingRoleList = this.actingRoleRepository.findAllByAppIdAndPkUsernameInAndDate(principal.getName(), usernameList, new Date());
    //     for(ActingRole actingRole: actingRoleList){
    //         usernameFiltered.add(actingRole.getPk().getUsername());
    //     }
    //     List<AppUser> appUserList = this.appUserRepository.findAllByUsernameIn(usernameFiltered);

    //     return appUserList;
    // }
}
