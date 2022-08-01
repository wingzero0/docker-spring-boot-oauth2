package kit.personal.ssomanagement.controller;

import kit.personal.ssoentity.entity.AppUser;
import kit.personal.ssoentity.repo.AppUserRepository;
import kit.personal.ssomanagement.controller.exception.ResourceNotFoundException;
import kit.personal.ssomanagement.utility.LoginChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping(value = "/selfServiceApi")
public class AppUserSelfServiceApiController {
	@Autowired
	AppUserRepository appUserRepo;
	@Autowired
	LoginChecker loginChecker;

	@RequestMapping( value = "/getAppUser", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public AppUser getAppUser(
			Principal principal
	){
		AppUser appUser = appUserRepo.findOneByUsernameAndIsActive(loginChecker.getLoginName(principal), "Y");
		if (appUser == null) {
			throw new ResourceNotFoundException(loginChecker.getLoginName(principal) + " doesn't not exists");
		}
		return appUser;
	}

	@RequestMapping( value = "/updateAppUser", method = {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public AppUser updateAppUser(
			@RequestBody AppUser web,
			Principal principal
	){
		String loginName = loginChecker.getLoginName(principal);
		AppUser appUser = appUserRepo.findOneByUsernameAndIsActive(loginName, "Y");
		if (appUser == null) {
			throw new ResourceNotFoundException(loginName + " doesn't not exists");
		}

		appUser.setDisplayName(web.getDisplayName())
				.setEmail(web.getEmail())
				.setLastModifiedDate(new Date())
				.setLastModifiedBy(loginName);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (web.getPassword() != null && !"".equalsIgnoreCase(web.getPassword())){
			appUser.setPassword(passwordEncoder.encode(web.getPassword()));
		}
		appUserRepo.save(appUser);
		return web;
	}
}
