package com.lighthouse.lingoswap.auth.config;

import com.lighthouse.lingoswap.auth.entity.TokenPair;
import com.lighthouse.lingoswap.auth.service.AuthService;
import com.lighthouse.lingoswap.auth.service.TokenPairService;
import com.lighthouse.lingoswap.member.domain.model.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final AuthService authService;
    private final TokenPairService tokenPairService;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        String accessToken = (String) authentication.getPrincipal();
        String email = tokenPairService.parseToken(accessToken);
        return createSuccessAuthentication(email);
    }

    private Authentication createSuccessAuthentication(final String username) {
        AuthDetails user = authService.loadUserByUsername(username);
        TokenPair tokenPair = tokenPairService.findNotExpiredByUsername(username);
        return new JwtAuthenticationToken(user.getUuid(), tokenPair.getAccessToken(), user.getAuthorities());
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return BearerTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
