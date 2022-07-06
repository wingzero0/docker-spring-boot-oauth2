package kit.personal.ssoserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    // @Autowired
    // private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private ADUserDetailsContextMapper adUserDetailsContextMapper;

    @Value("${ad.domain}")
    private String AD_DOMAIN;

    @Value("${ad.url}")
    private String AD_URL;

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests -> {
            authorizeRequests.antMatchers("/vendor/**").permitAll();
            authorizeRequests.antMatchers("/js/**").permitAll();
            authorizeRequests.antMatchers("/css/**").permitAll();
            authorizeRequests.anyRequest().authenticated();
        })
        // .formLogin(withDefaults());
                .formLogin().loginPage("/login").failureUrl("/login-error").permitAll();

        return http.build();
    }

    @Autowired
    public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
        if (this.AD_URL != null && !"".equals(this.AD_URL)) {
            auth.authenticationProvider(activeDirectoryLdapAuthenticationProvider());
        }
        // auth.authenticationProvider(customAuthenticationProvider);
        auth.userDetailsService(userDetailsService);
    }

    protected ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
        ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(AD_DOMAIN,
                AD_URL);
        provider.setConvertSubErrorCodesToExceptions(true);
        provider.setUseAuthenticationRequestCredentials(true);
        provider.setUserDetailsContextMapper(adUserDetailsContextMapper);

        return provider;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
