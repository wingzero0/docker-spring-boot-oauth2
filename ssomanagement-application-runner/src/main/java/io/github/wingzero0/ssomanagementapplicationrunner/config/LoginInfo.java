package io.github.wingzero0.ssomanagementapplicationrunner.config;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class LoginInfo {
    private String name;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public String getName() {
        return name;
    }

    public LoginInfo setName(String name) {
        this.name = name;
        return this;
    }

    public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public LoginInfo setGrantedAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
        return this;
    }
}