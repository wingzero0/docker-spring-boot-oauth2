package kit.personal.ssoclient.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@Controller
public class HomeController {
    @Value("${spring.security.oauth2.client.provider.spring.issuer-uri}")
    private String ssoserverBaseURL;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
    @Autowired
    private WebClient webClient;
    private static Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    @ResponseBody
    public String index() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal().getClass().toGenericString() + "--------" + auth.getPrincipal().toString();
    }

    @GetMapping("/loginPage")
    public String login() {
        return "loginPage";
    }

    @GetMapping("/logoutPage")
    public void revokeToken(OAuth2AuthenticationToken authentication, HttpServletRequest logoutRequest,
            HttpServletResponse response) {
        OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        String clientIdAndSecret = authorizedClient.getClientRegistration().getClientId() + ":"
                + authorizedClient.getClientRegistration().getClientSecret();
        String clientIdAndSecretBase64 = Base64.getEncoder().encodeToString(clientIdAndSecret.getBytes());
        LOG.debug("clientIdAndSecret:" + clientIdAndSecret);
        LOG.debug("clientIdAndSecretBase64:" + clientIdAndSecretBase64);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ssoserverBaseURL + "/oauth2/revoke"))
                .timeout(Duration.ofMinutes(2))
                .header("Authorization", "Basic " + clientIdAndSecretBase64)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(BodyPublishers
                        .ofString("token=" + authorizedClient.getAccessToken() + "&token_type_hint=access_token"))
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
            String redirectURL = logoutRequest.getScheme() + "://" + logoutRequest.getServerName() + ":"
                    + logoutRequest.getServerPort() + logoutRequest.getContextPath();
            LOG.debug("redirect URL:" + redirectURL);
            LOG.debug("encode redirect URL:" + URLEncoder.encode(redirectURL, StandardCharsets.UTF_8));

            response.sendRedirect(
                    ssoserverBaseURL + "/exit?callbackURL=" + URLEncoder.encode(redirectURL, StandardCharsets.UTF_8));
            return;
        } catch (IOException e) {
            e.printStackTrace();
            try {
                response.sendRedirect("/");
            } catch (IOException secondE) {
                secondE.printStackTrace();
            }
            return;
        }
    }

    @GetMapping("/userinfo")
    @ResponseBody
    public String userinfo(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);
        if (authorizedClient == null) {
            return "authorizedClient is null, maybe not login with oauth?";
        }
        String ret = "accessToken:" + this.getTokenValue(authorizedClient.getAccessToken());
        ret += ", refreshToken:" + this.getTokenValue(authorizedClient.getRefreshToken());
        ret += ", userName:" + authentication.getName();
        ret += ", clientName:" + authorizedClient.getClientRegistration().getClientName();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority : auth.getAuthorities()) {
            ret += ", authority:" + authority.getAuthority();
        }
        return ret;
    }

    private String getTokenValue(AbstractOAuth2Token token) {
        if (token != null) {
            return token.getTokenValue();
        } else {
            return null;
        }
    }

    @GetMapping("/usercustom")
    @ResponseBody
    public String userCustom(OAuth2AuthenticationToken authentication) {
        String ret = "you have role Custom";
        return ret;
    }

    @GetMapping("/usergoogle")
    @ResponseBody
    public String usergoogle(OAuth2AuthenticationToken authentication) {
        String ret = "you have role google";
        return ret;
    }

    private OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication) {
        return this.authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(), authentication.getName());
    }

    @GetMapping(value = "/testMessageScope")
    @ResponseBody
    public Map<String, String> authorizationCodeGrant(
            @RegisteredOAuth2AuthorizedClient("messaging-client-authorization-code") OAuth2AuthorizedClient authorizedClient) {

        String messages = this.webClient
                .get()
                .uri("http://localhost:8082/res/api/testScopeRead")
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String messages2 = this.webClient
                .get()
                .uri("http://localhost:8082/res/api/testScopeWrite")
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        Map<String, String> ret = Map.of("message.read", messages, "message.write", messages2);
        return ret;
    }
}
