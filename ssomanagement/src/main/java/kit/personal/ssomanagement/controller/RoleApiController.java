package kit.personal.ssomanagement.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.wingzero0.ssoentity.entity.AppUserRole;
import io.github.wingzero0.ssoentity.repo.AppUserRoleRepository;
import kit.personal.ssomanagement.controller.exception.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/api")
public class RoleApiController {
	@Autowired
	AppUserRoleRepository roleRepository;
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(RoleApiController.class);

	@GetMapping(value = "/role", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<AppUserRole> getRoleList(
			@RequestParam(value = "appClientId") String appClientId,
			@RequestParam(value = "appRole", required = false, defaultValue = "") String appRole,
			@RequestParam(value = "username", required = false, defaultValue = "") String username,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") String limit) {
		int pageNum = Integer.valueOf(page);
		int limitNum = Integer.valueOf(limit);
		Sort sort = Sort.by(Sort.Direction.DESC, "username");

		Page<AppUserRole> roleList = roleRepository.findAllByAppClientIdAndUserRoleContainsAndUsernameContains(
				appClientId, appRole, username, PageRequest.of(pageNum, limitNum, sort));
		return roleList;
	}

	@PostMapping(value = "/role", produces = MediaType.APPLICATION_JSON_VALUE)
	public AppUserRole create(
			@RequestBody AppUserRoleRequest appUserRoleRequest) {
		Date now = new Date();
		String modifier = "test"; // TODO implement auth
		AppUserRole appUserRole = new AppUserRole();
		BeanUtils.copyProperties(appUserRoleRequest, appUserRole);
		appUserRole.setLastModifiedDate(now).setLastModifiedBy(modifier);
		roleRepository.save(appUserRole);
		return appUserRole;
	}

	@PutMapping(value = "/role/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public AppUserRole update(
			@PathVariable(value = "id") String id,
			@RequestBody AppUserRoleRequest appUserRoleRequest) {
		Date now = new Date();
		String modifier = "test"; // TODO implement auth
		AppUserRole appUserRole = roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("role id not found"));
		BeanUtils.copyProperties(appUserRoleRequest, appUserRole);
		appUserRole.setLastModifiedDate(now).setLastModifiedBy(modifier);
		roleRepository.save(appUserRole);
		return appUserRole;
	}

	@GetMapping(value = "/role/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public AppUserRole read(
			@PathVariable(value = "id") String id) {
		Optional<AppUserRole> optional = roleRepository.findById(id);
		return optional.orElse(null);
	}

	@DeleteMapping(value = "/role/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Boolean> delete(
			@PathVariable(value = "id") String id) {
		roleRepository.deleteById(id);

		Map<String, Boolean> ret = new HashMap<>();
		ret.put("ret", true);
		return ret;
	}
}
