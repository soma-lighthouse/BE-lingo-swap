package com.lighthouse.lingoswap.auth.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.lighthouse.lingoswap.auth.exception.InvalidIdTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class GoogleIdTokenAuthenticationProvider implements AuthenticationProvider {

    private final GoogleIdTokenVerifier verifier;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        try {
            String idTokenString = (String) authentication.getPrincipal();
            GoogleIdToken idToken = verifier.verify(idTokenString);
            GoogleIdToken.Payload payload = Objects.requireNonNull(idToken.getPayload());
            String email = Objects.requireNonNull(payload.getEmail());
            return new GoogleIdTokenAuthenticationToken(email, idTokenString, Collections.emptyList());
        } catch (GeneralSecurityException | IllegalArgumentException | NullPointerException | IOException ex) {
            throw new InvalidIdTokenException(ex);
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return IdTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
