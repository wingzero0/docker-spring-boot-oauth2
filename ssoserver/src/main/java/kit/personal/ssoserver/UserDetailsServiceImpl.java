package kit.personal.ssoserver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kit.personal.ssoentity.entity.AppUser;
import kit.personal.ssoentity.entity.AppUserRole;
import kit.personal.ssoentity.repo.AppUserRepository;
import kit.personal.ssoentity.repo.AppUserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AppUserRepository userRepository;
    @Autowired
    private AppUserRoleRepository roleRepository;
    private static Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findOneByUsernameAndIsActive(username, "Y");

        if (appUser == null) {
            throw new UsernameNotFoundException("Staff no not found");
        }

        List<AppUserRole> roleList = roleRepository.findAllByUsername(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        for (AppUserRole role : roleList) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getAppClientId() + "_" + role.getUserRole()));
        }

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_SERVER"));
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_NEW_SERVER_USER"));
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        LOG.debug("original password:" + appUser.getPassword());

        return new User(appUser.getUsername(), appUser.getPassword(), grantedAuthorities);
    }
}
