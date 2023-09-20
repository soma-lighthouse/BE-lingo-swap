package com.lighthouse.lingoswap.auth.config;

import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@EqualsAndHashCode(callSuper = false)
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String uuid;
    private final String token;

    public JwtAuthenticationToken(final String uuid, final String token, final Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.uuid = uuid;
        this.token = token;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.uuid;
    }
}
