package kit.personal.ssoentity.repo;

import kit.personal.ssoentity.entity.AppUserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

@Repository
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
