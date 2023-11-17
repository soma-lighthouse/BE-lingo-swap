package com.lighthouse.lingoswap.common.security;

import com.lighthouse.lingoswap.auth.config.JwtAuthenticationToken;
import com.lighthouse.lingoswap.member.domain.model.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;

public class WithAuthorizedUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthorizedUser> {

    @Override
    public SecurityContext createSecurityContext(final WithAuthorizedUser user) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication auth = new JwtAuthenticationToken(user.uuid(), user.token(), Collections.singletonList(Role.ADMIN));
        context.setAuthentication(auth);
        return context;
    }

}
