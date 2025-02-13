package kit.personal.ssomanagementapplicationrunner.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient.Builder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;

@Service
public class RegisteredClientService {
    @Autowired
    private RegisteredClientRepository registeredClientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createRegisteredClient(
            String clientId,
            String clientSecret,
            List<String> redirectUris,
            List<String> scopes) {
        RegisteredClient existingClient = registeredClientRepository.findByClientId(clientId);
        if (existingClient != null) {
            throw new RuntimeException("client id existed:" + clientId);
        }
        Builder builder = RegisteredClient.withId(UUID.randomUUID().toString());
        Date now = new Date();
        builder = builder
                .clientId(clientId)
                .clientIdIssuedAt(now.toInstant())
                .clientSecret(passwordEncoder.encode(clientSecret))
                // .clientSecretExpiresAt(registeredClientRequest.getClientSecretExpiresAt())
                .clientName(clientId);

        builder.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN);
        builder.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS);

        for (String redirectUri : redirectUris) {
            builder = builder.redirectUri(redirectUri);
        }

        builder = builder.scope(OidcScopes.OPENID); // this one no consent
        for (String scope : scopes) {
            builder = builder.scope(scope); // all require consent
        }
        builder = builder.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build());

        RegisteredClient registeredClient = builder.build();
        registeredClientRepository.save(registeredClient);
    }
}
