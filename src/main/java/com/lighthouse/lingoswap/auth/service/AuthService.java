package com.lighthouse.lingoswap.auth.service;

import com.lighthouse.lingoswap.member.domain.model.AuthDetails;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public AuthDetails loadUserByUsername(final String username) {
        return memberRepository.getByUsername(username).getAuthDetails();
    }

}
