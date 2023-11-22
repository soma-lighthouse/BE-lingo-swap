package com.lighthouse.lingoswap.auth.config;

import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@EqualsAndHashCode(callSuper = false)
public class GoogleIdTokenAuthenticationToken extends AbstractAuthenticationToken {

    private final String email;
    private final String token;

    public GoogleIdTokenAuthenticationToken(final String email, final String token, final Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.email = email;
        this.token = token;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.email;
    }

}
