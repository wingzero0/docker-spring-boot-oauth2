package io.github.wingzero0.ssoresourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "io.github.wingzero0.ssoentity.repo")
@EntityScan(basePackages = "io.github.wingzero0.ssoentity.entity")
public class RoleServerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RoleServerApplication.class, args);
	}
}
