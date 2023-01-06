package kit.personal.ssomanagement.controller;

import kit.personal.ssoentity.entity.App;
import kit.personal.ssoentity.entity.AppRequest;
import kit.personal.ssoentity.entity.AppUserRole;
import kit.personal.ssoentity.repo.AppRepository;
import kit.personal.ssoentity.repo.AppUserRoleRepository;
import kit.personal.ssomanagement.controller.exception.ResourceNotFoundException;
import kit.personal.ssomanagement.utility.LoginChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(value = "/api")
public class AppApiController {
	@Autowired
	AppRepository appRepository;
	@Autowired
	AppUserRoleRepository appUserRoleRepository;
	@Autowired
	LoginChecker loginChecker;
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(AppApiController.class);
	@Autowired
	RegisteredClientRepository registeredClientRepository;

	@GetMapping( value = "/app", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<App> getAppList(
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer limit,
			Authentication auth
	){
		Sort sort = Sort.by(Sort.Direction.DESC, "clientId");

		if (loginChecker.isReachableRole("ROLE_ADMIN", auth)){
			return appRepository.findAllBy(PageRequest.of(page, limit, sort));
		}

		Set<String> appIds = new HashSet<>();

		List<AppUserRole> appUserRoleList = appUserRoleRepository.findAllByUsernameAndUserRoleIgnoreCase(
				loginChecker.getLoginName(auth), "ADMIN");

		for (AppUserRole appUserRole : appUserRoleList){
			appIds.add(appUserRole.getAppClientId());
		}
		return appRepository.findAllByClientIdIn(PageRequest.of(page, limit, sort), appIds);
	}

	// private Date getDateTruncateTime(Date dateObject){
	// 	Calendar cal = Calendar.getInstance();
	// 	cal.setTime(dateObject);
	// 	cal.set(Calendar.HOUR_OF_DAY, 0);
	// 	cal.set(Calendar.MINUTE, 0);
	// 	cal.set(Calendar.SECOND, 0);
	// 	cal.set(Calendar.MILLISECOND, 0);
	// 	return new Date(cal.getTimeInMillis());
	// }

	@GetMapping( value = "/app/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public App getApp(
			@PathVariable(value = "clientId") String clientId
	){
		App app = appRepository.findById(clientId).orElseThrow(()-> new ResourceNotFoundException("client id not found"));
		return app;
	}

	@GetMapping(value = "/app/{clientId}/role", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<AppUserRole> getAppRole(@PathVariable(value = "clientId") String clientId) {
		return appUserRoleRepository.findAllByAppClientId(clientId);
	}

	@PostMapping( value = "/app", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public App createApp(
			@RequestBody AppRequest appRequest
	){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		App app = new App();
		BeanUtils.copyProperties(appRequest, app);
		app.setClientSecret(encoder.encode(app.getClientSecret()));
		appRepository.save(app);
		return app;
	}

	@PutMapping( value = "/app/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public App updateApp(
			@RequestBody AppRequest appRequest,
			@PathVariable(value = "id") String id
	){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		App app = appRepository.findById(id).orElseThrow(()-> new RuntimeException("app id not found"));
		
		BeanUtils.copyProperties(appRequest, app, "clientSecret");
		if (appRequest.isUpdateClientSecret()){
			app.setClientSecret(encoder.encode(appRequest.getClientSecret()));
		}
		appRepository.save(app);
		return app;
	}

	@GetMapping( value = "/appr", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RegisteredClient getRegisteredClient(){
		return registeredClientRepository.findByClientId("messaging-client2");
	}
}
