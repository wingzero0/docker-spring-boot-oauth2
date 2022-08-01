package kit.personal.ssomanagement.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);

    @Value("${application.disable_api_auth}")
    private boolean isDisableAPIAuth;
    @Value("${spring.security.oauth2.client.registration.my-client-2.client-id}")
    private String ssoClientId;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        if (isDisableAPIAuth){
            http.authorizeRequests().antMatchers("/**").permitAll();
            http.csrf().disable();
            LOG.debug("disable auth");
        }
        http.authorizeRequests()
                    .antMatchers("/api/csrf-token").hasAnyRole("ADMIN", "APP_ADMIN", "USER")
                    .antMatchers("/api/**").hasAnyRole("ADMIN", "APP_ADMIN")
                    .antMatchers("/selfServiceApi/**").hasRole("USER")
                    .antMatchers("/loginPage").permitAll()
                    .antMatchers("/js/about**.js").permitAll()
                    .anyRequest().authenticated()
                .and()
                .oauth2Login()
                    .loginPage("/oauth2/authorization/my-client-2")
                    .userInfoEndpoint()
                    .userService(this.userService())
                    .oidcUserService(this.oidcUserService())
        ;
        http.logout().logoutUrl("/logoutPage").logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
        ;
    }

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> userService() {
        final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

        return (userRequest) -> {
            // Delegate to the default implementation for loading a user
            OAuth2User oAuth2User = delegate.loadUser(userRequest);
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            Map<String, Object> stringObjectMap = oAuth2User.getAttributes();
            List<Object> objList = (List<Object>)stringObjectMap.get("authorities");
            for(Object obj: objList){
                Map<String, String> innerMap = (Map<String, String>) obj;
                String checkedRole =  innerMap.get("authority").toUpperCase();
                String currentAppAdmin = (ssoClientId + "_ADMIN").toUpperCase();
                if (checkedRole.contains(currentAppAdmin)){
                    mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                }
                if (checkedRole.contains("_ADMIN")){
                    mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_APP_ADMIN"));
                }
                mappedAuthorities.add(new SimpleGrantedAuthority(checkedRole));
            }
            oAuth2User = new DefaultOAuth2User(mappedAuthorities, oAuth2User.getAttributes(), "name");

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
            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_GOOGLE"));

            // TODO
            // 1) Fetch the authority information from the protected resource using accessToken
            // 2) Map the authority information to one or more GrantedAuthority's and add it to mappedAuthorities
            // 3) Create a copy of oidcUser but use the mappedAuthorities instead
            oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());

            return oidcUser;
        };
    }
}