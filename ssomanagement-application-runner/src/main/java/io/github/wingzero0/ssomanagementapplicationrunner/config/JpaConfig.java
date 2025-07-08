package io.github.wingzero0.ssomanagementapplicationrunner.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "io.github.wingzero0.ssoentity.repo")
@EntityScan(basePackages = "io.github.wingzero0.ssoentity.entity")
public class JpaConfig {

}
