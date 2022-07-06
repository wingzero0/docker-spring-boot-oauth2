package kit.personal.ssoserver;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Service;

import kit.personal.ssoentity.entity.AppUserRole;
import kit.personal.ssoentity.repo.AppUserRoleRepository;

@Service
public class ADUserDetailsContextMapper implements UserDetailsContextMapper {
    @Autowired
    private AppUserRoleRepository roleRepository;

    private static Logger LOG = LoggerFactory.getLogger(ADUserDetailsContextMapper.class);

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
            Collection<? extends GrantedAuthority> authorities) {
        List<AppUserRole> roleList = roleRepository.findAllByUsername(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        for (AppUserRole role : roleList) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getAppId() + "_" + role.getAppRole()));
        }

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        LOG.debug("password will be set as [AD MASKED]");

        return new User(username, "[AD MASKED]", grantedAuthorities);
    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {

    }
}
