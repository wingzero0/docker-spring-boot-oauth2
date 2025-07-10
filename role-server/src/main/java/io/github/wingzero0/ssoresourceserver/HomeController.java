package io.github.wingzero0.ssoresourceserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {
    @Value("${spring.application.version}")
    private String springApplicationVersion;
    @Value("${spring.profiles.active}")
    private String springProfilesActive;

    @GetMapping("/api/version")
    public Map<String, Object> getVersion() {
        return Map.of("version", springApplicationVersion,
                "profile", springProfilesActive);
    }
}
