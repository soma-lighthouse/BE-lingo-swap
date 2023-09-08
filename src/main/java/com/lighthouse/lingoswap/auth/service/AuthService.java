package com.lighthouse.lingoswap.auth.service;

import com.lighthouse.lingoswap.auth.exception.AuthNotFoundException;
import com.lighthouse.lingoswap.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) {
        return memberRepository.findByAuthUsername(username)
                .orElseThrow(() -> new AuthNotFoundException(username))
                .getAuth();
    }
}
