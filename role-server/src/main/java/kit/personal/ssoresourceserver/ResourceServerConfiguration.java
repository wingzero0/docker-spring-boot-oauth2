package kit.personal.ssoresourceserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class ResourceServerConfiguration {
    private static Logger LOG = LoggerFactory.getLogger(ResourceServerConfiguration.class);

    @Value("${application.oauth.checktoken.uri}")
    private String checkTokenUri;
    @Value("${application.oauth.checktoken.clientid}")
    private String clientId;
    @Value("${application.oauth.checktoken.clientsecret}")
    private String clientSecret;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        try {
            // @formatter:off
            http.authorizeHttpRequests(
                    authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/appRole").hasAuthority("SCOPE_app_role")
                        .requestMatchers("/api/appRole/**").hasAuthority("SCOPE_app_role")
                        .requestMatchers("/api/testScopeRead").hasAuthority("SCOPE_message.read")
                        .requestMatchers("/api/testScopeWrite").hasAuthority("SCOPE_message.write")
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
