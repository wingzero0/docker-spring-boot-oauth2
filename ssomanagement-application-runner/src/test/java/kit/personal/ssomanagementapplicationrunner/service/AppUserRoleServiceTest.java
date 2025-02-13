package kit.personal.ssomanagementapplicationrunner.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import kit.personal.ssoentity.repo.AppUserRoleRepository;

@SpringBootTest
@ActiveProfiles("test")
public class AppUserRoleServiceTest {
    @Autowired
    AppUserRoleService appUserRoleService;
    @Autowired
    AppUserRoleRepository appUserRoleRepository;

    @Test
    void testCreateAppUserRole() {
        // TODO change jdbc template to h2
        String clientID = "testcase_" + UUID.randomUUID().toString();
        String username = "testcase_user";
        List<String> roleNames = List.of("ADMIN", "GUEST");
        assertTrue(appUserRoleRepository.findAllByAppClientId(clientID).size() == 0);
        appUserRoleService.createAppUserRole(clientID, username, roleNames);
        assertTrue(appUserRoleRepository.findAllByAppClientId(clientID).size() > 0);
    }
}
