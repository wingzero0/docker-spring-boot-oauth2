package kit.personal.ssoresourceserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ResourceServerConfiguration {
    private static Logger LOG = LoggerFactory.getLogger(ResourceServerConfiguration.class);

    @Value("${oauth.checkToken.uri}")
    private String checkTokenUri;
    @Value("${oauth.checkToken.clientId}")
    private String clientId;
    @Value("${oauth.checkToken.clientSecret}")
    private String clientSecret;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        try {
            // @formatter:off
            http.authorizeHttpRequests(
                    authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/appRole").hasAuthority("SCOPE_app_role")
                        .requestMatchers("/api/appRole/**").hasAuthority("SCOPE_app_role")
                        .anyRequest().authenticated()
                ).oauth2ResourceServer(oauth2 -> oauth2
                    .opaqueToken(token -> token.introspectionUri(checkTokenUri)
                      .introspectionClientCredentials(this.clientId, this.clientSecret)));
                    ;
                return http.build();
            // @formatter:on
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
