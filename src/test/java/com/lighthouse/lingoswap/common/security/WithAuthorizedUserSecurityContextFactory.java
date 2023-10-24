package com.lighthouse.lingoswap.common.security;

import com.lighthouse.lingoswap.auth.config.JwtAuthenticationToken;
import com.lighthouse.lingoswap.member.domain.model.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.lang.annotation.Annotation;
import java.util.Collections;

import static com.lighthouse.lingoswap.common.fixture.MemberFixture.USER_UUID;

public class WithAuthorizedUserSecurityContextFactory implements WithSecurityContextFactory<Annotation> {

    @Override
    public SecurityContext createSecurityContext(final Annotation annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication auth = new JwtAuthenticationToken(USER_UUID, "dummyAccessToken", Collections.singletonList(Role.USER));
        context.setAuthentication(auth);
        return context;
    }

}
