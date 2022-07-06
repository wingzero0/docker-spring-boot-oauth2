package kit.personal.ssoentity.repo;

import kit.personal.ssoentity.entity.ActingRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface ActingRoleRepository extends CrudRepository<ActingRole, Integer> {
    @Query(value = "select r from ActingRole r where r.pk.username = ?1 and r.pk.fromDate <= ?2 and r.pk.toDate >= ?2")
    List<ActingRole> findAllByPkUsernameAndDate(String username, Date testDate);

    @Query(value = "select r from ActingRole r where r.pk.username = ?1 and r.pk.fromDate <= ?2 and r.pk.toDate >= ?2 and upper(r.appRole) like upper(?3)")
    List<ActingRole> findAllByPkUsernameAndDateAndAppRoleIgnoreCase(String username, Date testDate, String role);

    @Query(value = "select r from ActingRole r where r.appId = ?1 and r.pk.fromDate <= ?2 and r.pk.toDate >= ?2")
    List<ActingRole> findAllByAppIdAndDate(String appId, Date testDate);

    @Query(value = "select r from ActingRole r where r.appId = ?1 and r.pk.username = ?2 and r.pk.fromDate <= ?3 and r.pk.toDate >= ?3")
    List<ActingRole> findAllByAppIdAndPkUsernameAndDate(String appId, String username, Date testDate);

    @Query(value = "select r from ActingRole r where r.appId = ?1 and r.pk.username in ?2 and r.pk.fromDate <= ?3 and r.pk.toDate >= ?3")
    List<ActingRole> findAllByAppIdAndPkUsernameInAndDate(String appId, Collection<String> usernames, Date testDate);

    @Query(value = "select r from ActingRole r where r.appId = ?1 and r.appRole = ?2 and r.pk.fromDate <= ?3 and r.pk.toDate >= ?3")
    List<ActingRole> findAllByAppIdAndAppRoleAndDate(String appId, String AppRole, Date testDate);
}
