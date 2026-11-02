package io.github.wingzero0.ssoresourceserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {
    @Value("${spring.application.version}")
    private String springApplicationVersion;

    private final Environment environment;

    public HomeController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/api/version")
    public Map<String, Object> getVersion() {
        return Map.of("version", springApplicationVersion,
                "profile", String.join(",", environment.getActiveProfiles()));
    }
}
