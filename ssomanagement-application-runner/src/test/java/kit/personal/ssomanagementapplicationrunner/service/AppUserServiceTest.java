package kit.personal.ssomanagementapplicationrunner.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import kit.personal.ssoentity.entity.AppUser;

@SpringBootTest
@ActiveProfiles("test")
public class AppUserServiceTest {
    @Autowired
    private AppUserService appUserService;

    @Test
    void testCreateAppUser() {
        appUserService.createAppUser("user1", "user1", "password1", "user1@localhost");
        assertThrows(Exception.class, () -> {
            appUserService.createAppUser("user1", "user1", "password1", "user1@localhost");
        });
        AppUser appUser2 = appUserService.createAppUser("user2", "user2", "password2", "user2@localhost");
        assertNotNull(appUser2.getPassword());
        assertNotEquals("password2", appUser2.getPassword());
        assertEquals(true, appUser2.getIsActive());

    }
}
