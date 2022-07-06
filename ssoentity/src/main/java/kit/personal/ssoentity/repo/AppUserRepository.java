package kit.personal.ssoentity.repo;

import kit.personal.ssoentity.entity.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, BigInteger> {
    AppUser findOneByUsername(String username);
    AppUser findOneByUsernameAndIsActive(String username, String isActive);
    AppUser findOneByEmail(String email);
    List<AppUser> findAllByUsernameIn(Collection<String> usernames);
    Page<AppUser> findAllByUsernameContainsOrDisplayNameContains(String username, String displayName, Pageable pageable);
    Page<AppUser> findAllBy(Pageable pageable);
}
