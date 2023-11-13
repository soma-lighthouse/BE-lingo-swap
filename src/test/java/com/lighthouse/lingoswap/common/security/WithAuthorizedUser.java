package com.lighthouse.lingoswap.common.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.lighthouse.lingoswap.common.fixture.MemberFixture.USER_UUID;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAuthorizedUserSecurityContextFactory.class)
public @interface WithAuthorizedUser {

    String uuid() default USER_UUID;

    String token() default "DUMMY";

    String[] roles() default {"USER"};

}
