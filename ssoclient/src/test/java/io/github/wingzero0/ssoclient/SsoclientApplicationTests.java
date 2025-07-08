package io.github.wingzero0.ssoclient;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class SsoclientApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Configuration
	static class ClientConfig {
		@Bean
		ClientRegistrationRepository clients() {
			ClientRegistration.Builder builder = ClientRegistration.withRegistrationId("dummy");
			builder.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
					.clientId("dummy")
					.tokenUri("dummy");
			return new InMemoryClientRegistrationRepository(builder.build());
		}

		@Bean
		OAuth2AuthorizedClientRepository authorizedClients() {
			return new HttpSessionOAuth2AuthorizedClientRepository();
		}
	}
}
