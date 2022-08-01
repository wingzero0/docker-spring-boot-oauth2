package kit.personal.ssomanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "kit.personal.ssoentity.repo")
@EntityScan(basePackages = "kit.personal.ssoentity.entity")
public class SsoManagementApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SsoManagementApplication.class, args);
	}
}
