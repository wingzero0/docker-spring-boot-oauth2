package kit.personal.ssoclient.config;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;


@EnableWebSecurity
public class SecurityConfig {
    Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);

    @Value("${resource.server.role.uri}")
    private String roleUri;
    @Autowired
    private WebClient webClient;

    // @Bean
	// WebSecurityCustomizer webSecurityCustomizer() {
	// 	return (web) -> web.ignoring().antMatchers("/webjars/**");
	// }

    // @formatter:off
    @Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authorizeRequests ->{
                authorizeRequests
                    .antMatchers("/usercustom").hasRole("CUSTOM")
                    .antMatchers("/usergoogle").hasRole("GOOGLE")
                    .antMatchers("/login/**").permitAll()
                    .antMatchers("/loginPage").permitAll();
                authorizeRequests.anyRequest().authenticated();
            })
            .oauth2Login(oauth2Login ->{
				oauth2Login
					.loginPage("/loginPage")
					.userInfoEndpoint()
					.userService(this.userService())
					.oidcUserService(this.oidcUserService());
            })
			.oauth2Client(withDefaults());



        http.logout()
            .logoutUrl("/logoutPage")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID");
		return http.build();
	}
    // @formatter:on

	private OAuth2UserService<OAuth2UserRequest, OAuth2User> userService() {
        final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

        return (userRequest) -> {
            // Delegate to the default implementation for loading a user
            OAuth2User oAuth2User = delegate.loadUser(userRequest);
            return oAuth2User;
        };
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();

        return (userRequest) -> {
            // Delegate to the default implementation for loading a user
            OidcUser oidcUser = delegate.loadUser(userRequest);

            OAuth2AccessToken accessToken = userRequest.getAccessToken();
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_OIDC"));

            LOG.debug("oidcUserName:" + oidcUser.getName());

            String[] messages = this.webClient
				.get()
				.uri(this.roleUri + oidcUser.getName())
				.attributes(clientRegistrationId("messaging-client-client-credentials"))
				.retrieve()
				.bodyToMono(String[].class)
				.block();
            for (String message: messages) {
                LOG.debug("ROLE:" + message);
                mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + message.toUpperCase()));
            }
            
            // TODO
            // 1) Fetch the authority information from the protected resource using accessToken
            // 2) Map the authority information to one or more GrantedAuthority's and add it to mappedAuthorities
            // 3) Create a copy of oidcUser but use the mappedAuthorities instead
            oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());

            return oidcUser;
        };
    }
}