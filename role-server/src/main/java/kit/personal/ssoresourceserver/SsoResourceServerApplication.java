package kit.personal.ssoresourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "kit.personal.ssoentity.repo")
@EntityScan(basePackages = "kit.personal.ssoentity.entity")
public class SsoResourceServerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SsoResourceServerApplication.class, args);
	}
}
