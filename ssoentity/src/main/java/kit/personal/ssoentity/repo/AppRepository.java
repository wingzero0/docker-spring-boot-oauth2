package kit.personal.ssoentity.repo;

import kit.personal.ssoentity.entity.App;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface AppRepository extends CrudRepository<App, String> {
	Page<App> findAllBy(Pageable pageable);
	Page<App> findAllByClientIdIn(Pageable pageable, Set<String> clientId);
}
