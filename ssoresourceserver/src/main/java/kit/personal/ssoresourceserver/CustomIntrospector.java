package kit.personal.ssoresourceserver;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

public class CustomIntrospector implements OpaqueTokenIntrospector {
    private static Logger LOG = LoggerFactory.getLogger(CustomIntrospector.class);
    private String checkTokenUri;
    public CustomIntrospector(String uri){
        this.checkTokenUri = uri;
    }
    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        Set<GrantedAuthority> mappedAuthorities = new HashSet<GrantedAuthority>();
        CheckTokenResponse attributes = this.checkToken(token);
        Set<String> scopeSet = this.castToString(attributes.getMap().get("scope"));
        if (scopeSet == null){
            throw new IntrospectException("unable to verify token");
        }
        for (String scope: scopeSet){
            mappedAuthorities.add(new SimpleGrantedAuthority("SCOPE_" + scope));
        }
        return new DefaultOAuth2User(mappedAuthorities, attributes.getMap(), "user_name");
    }

    private Set<String> castToString(Object objCollection){
        if (objCollection == null){
            return null;
        }
        Set<String> ret = new HashSet<>();
        if (objCollection instanceof Collection<?>){
            Collection<?> rowCollection = (Collection<?>) objCollection;
            for(Object obj : rowCollection){
                if (obj instanceof String){
                    ret.add(String.class.cast(obj));
                } else {
                    LOG.error("wrong type. expect type String, but was " + obj.getClass().getCanonicalName());
                }
            }
        } else {
            LOG.error("wrong type. expect type Collection<String>, but was " + objCollection.getClass().getCanonicalName());
        }
        return ret;
    }
    
    private CheckTokenResponse checkToken(String token){
        HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();
        Map<Object, Object> data = new HashMap<>();
        data.put("token", token);
        HttpRequest checkTokenRequest = HttpRequest.newBuilder()
            .uri(URI.create(this.checkTokenUri))
            .timeout(Duration.ofMinutes(2))
            .POST(this.buildFormDataFromMap(data))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build();
        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = client.send(checkTokenRequest, HttpResponse.BodyHandlers.ofString());
            String jsonData = httpResponse.body();
            LOG.debug(jsonData);
            ObjectMapper objectMapper = new ObjectMapper();
            CheckTokenResponse checkTokenResponse = objectMapper.readValue(jsonData, CheckTokenResponse.class);
            return checkTokenResponse;
        } catch (JsonProcessingException e){
            LOG.error(e.getMessage(), e);
            throw new RuntimeException("auth server parser fail");
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException("auth server connection fail");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        LOG.debug(builder.toString());
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }
}
