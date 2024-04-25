package kit.personal.ssomanagementapplicationrunner.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "kit.personal.ssoentity.repo")
@EntityScan(basePackages = "kit.personal.ssoentity.entity")
public class JpaConfig {

}
