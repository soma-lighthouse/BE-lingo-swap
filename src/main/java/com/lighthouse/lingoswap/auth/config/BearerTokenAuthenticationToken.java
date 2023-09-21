package com.lighthouse.lingoswap.auth.config;

import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

@EqualsAndHashCode(callSuper = false)
public class BearerTokenAuthenticationToken extends AbstractAuthenticationToken {

    private final String token;

    public BearerTokenAuthenticationToken(final String token) {
        super(Collections.emptyList());
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }
}
