package kit.personal.ssomanagementapplicationrunner.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class RegisteredClientServiceTest {
    @Autowired
    RegisteredClientService registeredClientService;
    @Autowired
    RegisteredClientRepository registeredClientRepository;

    @Test
    void testCreateRegisteredClient() {
        // TODO change jdbc template to h2
        String clientID = "testcase_" + UUID.randomUUID().toString();
        String clientSecret = clientID + "secret";
        List<String> redirectUris = List.of("http://127.0.0.1:8080");
        List<String> scopes = List.of("openid");
        assertNull(registeredClientRepository.findByClientId(clientID));
        registeredClientService.createRegisteredClient(clientID, clientSecret, redirectUris, scopes);
        assertNotNull(registeredClientRepository.findByClientId(clientID));

        assertThrows(RuntimeException.class, ()->{
            registeredClientService.createRegisteredClient(clientID, clientSecret, redirectUris, scopes);
        });
    }
}
