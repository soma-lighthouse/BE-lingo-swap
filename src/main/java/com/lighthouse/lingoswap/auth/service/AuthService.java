package com.lighthouse.lingoswap.auth.service;

import com.lighthouse.lingoswap.auth.entity.Auth;
import com.lighthouse.lingoswap.auth.exception.AuthNotFoundException;
import com.lighthouse.lingoswap.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public Auth loadUserByUsername(final String username) {
        return memberRepository.findByAuthUsername(username)
                .orElseThrow(() -> new AuthNotFoundException(username))
                .getAuth();
    }

    public String generateAuthentication(final String username) {
        Auth userDetails = loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return userDetails.getUuid();
    }
}
