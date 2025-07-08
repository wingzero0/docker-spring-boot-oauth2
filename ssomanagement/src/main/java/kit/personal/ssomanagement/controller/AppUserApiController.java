package kit.personal.ssomanagement.controller;

import io.github.wingzero0.ssoentity.entity.AppUser;
import io.github.wingzero0.ssoentity.repo.AppUserRepository;
import kit.personal.ssomanagement.controller.exception.ResourceNotFoundException;
import kit.personal.ssomanagement.controller.exception.WrongParameterException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.math.BigInteger;
import java.security.Principal;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
@Tag(name = "AppUserApiController")
public class AppUserApiController {
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(AppUserApiController.class);

	@Autowired
	AppUserRepository appUserRepo;

	@GetMapping(value = "/appUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<AppUser> getAppList(
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") String limit) {
		int pageNum = Integer.valueOf(page);
		int limitNum = Integer.valueOf(limit);
		Sort sort = Sort.by(Sort.Direction.DESC, "id");

		Page<AppUser> appUsers = appUserRepo.findAllBy(PageRequest.of(pageNum, limitNum, sort));
		return appUsers;
	}

	@GetMapping(value = "/appUsers", produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<AppUser> getAllAppUser() {
		return appUserRepo.findAll();
	}

	@PostMapping(value = "/appUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public AppUser create(
			@RequestBody AppUser web,
			Principal principal) {
		AppUser appUser = new AppUser();

		appUser.setUsername(web.getUsername())
				.setDisplayName(web.getDisplayName())
				.setEmail(web.getEmail())
				.setIsActive(web.getIsActive())
				.setLastModifiedDate(new Date());

		if (principal != null && principal.getName() != null) {
			appUser.setLastModifiedBy(principal.getName());
		} else {
			appUser.setLastModifiedBy("test");
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (!StringUtils.hasLength(web.getPassword())) {
			throw new WrongParameterException("password cannot be empty");
		}
		appUser.setPassword(passwordEncoder.encode(web.getPassword()));
		appUserRepo.save(appUser);
		return web;
	}

	@PutMapping(value = "/appUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public AppUser update(
			@RequestBody AppUser web,
			@PathVariable BigInteger id,
			Principal principal) {
		AppUser appUser = appUserRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("app User does not exist"));

		appUser.setUsername(web.getUsername())
				.setDisplayName(web.getDisplayName())
				.setEmail(web.getEmail())
				.setIsActive(web.getIsActive())
				.setLastModifiedDate(new Date());

		if (principal != null && principal.getName() != null) {
			appUser.setLastModifiedBy(principal.getName());
		} else {
			appUser.setLastModifiedBy("test");
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (StringUtils.hasLength(web.getPassword())) {
			appUser.setPassword(passwordEncoder.encode(web.getPassword()));
		} // or else no update password
		appUserRepo.save(appUser);
		return web;
	}

	@GetMapping(value = "/appUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public AppUser read(@PathVariable BigInteger id) {
		Optional<AppUser> optional = appUserRepo.findById(id);
		return optional.orElseThrow(() -> new ResourceNotFoundException("app User does not exist"));
	}

	@GetMapping(value = "/appUserSearch", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<AppUser> searchByUsername(
			@RequestParam(value = "name") String name,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") String limit) {
		int pageNum = Integer.valueOf(page);
		int limitNum = Integer.valueOf(limit);
		Sort sort = Sort.by(Sort.Direction.DESC, "username");
		return appUserRepo.findAllByUsernameContainsOrDisplayNameContains(name, name,
				PageRequest.of(pageNum, limitNum, sort));
	}

	@GetMapping(value = "/appUserByUsername/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AppUser getByUsername(
			@PathVariable(value = "username") String username) {
		return appUserRepo.findOneByUsername(username);
	}
}
