package io.github.wingzero0.ssomanagement.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;

@Service
public class LoginChecker {
	@Value("${application.http-auth.enable}")
	private boolean httpAuthEnabled;
	@Value("${application.dummy_login_role}")
	private String dummyLoginRole;
	@Value("${application.dummy_login_name}")
	private String dummyLoginName;

	public String getLoginName(Authentication auth) {
		if (!httpAuthEnabled){
			return dummyLoginName;
		} else {
			return auth.getName();
		}
	}

	public String getLoginName(Principal auth) {
		if (!httpAuthEnabled){
			return dummyLoginName;
		} else {
			return auth.getName();
		}
	}

	public String getModifier(Authentication auth){
		return this.getLoginName(auth);
	}

	public Collection<? extends GrantedAuthority> getGrantedAuthorities(Authentication auth) {
		if (!httpAuthEnabled){
			Collection<GrantedAuthority> dummyAuthority = new HashSet<>();
			dummyAuthority.add(new SimpleGrantedAuthority(dummyLoginRole));
			return dummyAuthority;
		} else {
			return auth.getAuthorities();
		}
	}
	public boolean isReachableRole(String roleName, Authentication auth) {
		Collection<? extends GrantedAuthority> grantedAuthorities = this.getGrantedAuthorities(auth);
		return grantedAuthorities.contains(new SimpleGrantedAuthority(roleName));
	}
}
