package kit.personal.ssoentity.repo;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import kit.personal.ssoentity.entity.AppUserRole;

public interface AppUserRoleRepository extends CrudRepository<AppUserRole, BigInteger> {
	List<AppUserRole> findAllByUsername(String username);
	List<AppUserRole> findAllByAppClientId(String app);
	Page<AppUserRole> findAllByAppClientId(String app, Pageable pageable);
	List<AppUserRole> findAllByAppClientIdAndUserRole(String appClientId, String role);
	Page<AppUserRole> findAllByAppClientIdAndUserRoleContainsAndUsernameContains(String appClientId, String role, String username, Pageable pageable);
	List<AppUserRole> findAllByUsernameAndUserRoleIgnoreCase(String username, String role);
	List<AppUserRole> findAllByAppClientIdAndUsername(String appClientId, String username);
	List<AppUserRole> findAllByAppClientIdAndUsernameIn(String appClientId, Collection<String> usernames);
	long countByAppClientIdAndUsernameAndUserRole(String appClientId, String username, String role);
	Long deleteByAppClientIdAndUsernameAndUserRole(String appClientId, String username, String role);
}
