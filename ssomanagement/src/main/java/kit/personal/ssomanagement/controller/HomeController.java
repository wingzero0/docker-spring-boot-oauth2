package kit.personal.ssomanagement.controller;

import kit.personal.ssoentity.entity.AppUserRole;
import kit.personal.ssoentity.repo.AppUserRoleRepository;
import kit.personal.ssomanagement.config.LoginInfo;
import kit.personal.ssomanagement.utility.LoginChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

@Controller
public class HomeController{
    @Value("${spring.security.oauth2.client.provider.spring.issuer-uri}")
    private String ssoserverBaseURL;

    @Value("${application.disable_api_auth}")
    private boolean isDisableAPIAuth;

    @Autowired
    LoginChecker loginChecker;

    @Autowired
    AppUserRoleRepository roleRepo;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
    private static Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @GetMapping(value = "/api/appUserRole/{appId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AppUserRole> appUserRole(
            @PathVariable String appId
    ) {
        //"spring-security-oauth2-read-write-client"
        return roleRepo.findAllByAppClientId(appId);
    }

    @GetMapping("/loginPage")
    public String login() {
        return "loginPage";
    }

    @GetMapping("/logoutPage")
    public void revokeToken(OAuth2AuthenticationToken authentication, HttpServletRequest logoutRequest, HttpServletResponse response) {
        OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);
        // String ret = "token:" + authorizedClient.getAccessToken().getTokenValue();
        HttpClient client = HttpClient.newBuilder()
            .followRedirects(Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(ssoserverBaseURL + "/user/revoke"))
            .timeout(Duration.ofMinutes(2))
            .header("Authorization", "Bearer " + authorizedClient.getAccessToken().getTokenValue())
            .POST(BodyPublishers.ofString(""))
            .build();
        client.sendAsync(request, BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(System.out::println);

        try {
            logoutRequest.logout();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        try {
            String redirectURL = logoutRequest.getScheme() + "://" + logoutRequest.getServerName() + ":" + logoutRequest.getServerPort() + logoutRequest.getContextPath();
            LOG.debug("redirect URL:" + redirectURL);
            LOG.debug("encode redirect URL:" + URLEncoder.encode(redirectURL, StandardCharsets.UTF_8));

            response.sendRedirect(ssoserverBaseURL + "/exit?callbackURL="+ URLEncoder.encode(redirectURL, StandardCharsets.UTF_8));
            return;
        } catch (IOException e){
            e.printStackTrace();
            try{
                response.sendRedirect("/");
            } catch (IOException secondE ){
                secondE.printStackTrace();
            }
            return;
        }
    }

    @GetMapping(value = "/api/csrf-token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> getCsrfToken(HttpServletRequest request){
        Enumeration<String> attributes = request.getAttributeNames();
        while (attributes.hasMoreElements()){
            LOG.debug(attributes.nextElement());
        }
        Map<String, String> map = new HashMap<>();
        map.put("csrf_header","X-CSRF-TOKEN");
        if (isDisableAPIAuth){
            LOG.debug("dummy csrf_token");
            map.put("csrf_token", "dummy");
        } else {
            CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            LOG.debug("CsrfToken.class.getName():" + CsrfToken.class.getName());
            map.put("csrf_token", token.getToken());
        }

        return map;
    }

    @GetMapping("/userinfo")
    @ResponseBody
    public String userinfo(OAuth2AuthenticationToken authentication) {

        OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);
        String ret = "token:" + authorizedClient.getAccessToken().getTokenValue();
        ret += ", userName:" + authentication.getName();
        ret += ", clientName:" + authorizedClient.getClientRegistration().getClientName();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority : auth.getAuthorities()){
            ret += ", authority:" + authority.getAuthority();
        }
        //DefaultOidcUser oidcUser = (DefaultOidcUser) auth.getPrincipal();
        //ret += ", oidcUser" + oidcUser.getFullName();
        return ret;
    }

    private OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication) {
        return this.authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(), authentication.getName());
    }

    @GetMapping(value = "/api/loginInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoginInfo getLoginInfo(
            Authentication auth
    ){
        Collection<? extends GrantedAuthority> authorities = loginChecker.getGrantedAuthorities(auth);
        String loginName = loginChecker.getLoginName(auth);
        return (new LoginInfo()).setGrantedAuthorities(authorities).setName(loginName);
    }
}
