package kit.personal.ssomanagementapplicationrunner;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient.Builder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

@SpringBootApplication
@Profile("!test")
public class SsoManagementApplicationRunner implements ApplicationRunner {
	@Autowired
	private RegisteredClientRepository registeredClientRepository;

	public static void main(String[] args) {
		SpringApplication.run(SsoManagementApplicationRunner.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Set<String> optionNames = args.getOptionNames();
		if (optionNames.contains("clientId") && optionNames.contains("clientSecret")) {
			String clientId = args.getOptionValues("clientId").get(0);
			String clientSecret = args.getOptionValues("clientSecret").get(0);
			List<String> redirectUris = args.getOptionValues("redirectUri");
			List<String> scopes = args.getOptionValues("scope");

			RegisteredClient existingClient = registeredClientRepository.findByClientId(clientId);
			if (existingClient != null) {
				throw new RuntimeException("client id existed:" + clientId);
			}
			Builder builder = RegisteredClient.withId(UUID.randomUUID().toString());
			Date now = new Date();
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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
		} else {
			System.out.println("usage: java -jar ssomanagement-application-runner.jar --clientId=xxx --clientSecret=xxx"
					+ " --redirectUri=http://127.0.0.1:8080/ --scope=profile --scope=role");
			System.out.println("or: mvn spring-boot:run -Dspring-boot.run.arguments=\"--clientId=xxx --clientSecret=xxx"
					+ " --redirectUri=http://127.0.0.1:8080/ --scope=profile --scope=role\"");
		}
	}
}
