package kit.personal.ssoentity.repo;


import kit.personal.ssoentity.entity.AppUserActing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AppUserActingRepository extends CrudRepository<AppUserActing, Integer> {
    AppUserActing findOneByFromDateAndToDateAndUsernameAndActingForUsername(Date fromDate, Date toDate, String username, String actingForUsername);
}
