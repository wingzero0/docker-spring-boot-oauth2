package kit.personal.ssomanagementapplicationrunner.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.wingzero0.ssoentity.entity.AppUser;
import io.github.wingzero0.ssoentity.repo.AppUserRepository;

@Service
public class AppUserService {
    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser createAppUser(String username, String displayName,
            String password, String email) {
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setDisplayName(displayName);
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setEmail(email);
        appUser.setIsActive(true);
        appUserRepository.save(appUser);
        return appUser;
    }
}
