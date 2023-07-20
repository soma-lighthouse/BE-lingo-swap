package com.lighthouse.lingoswap.user.service;

import com.lighthouse.lingoswap.user.entity.User;
import com.lighthouse.lingoswap.user.exception.UserNotFoundException;
import com.lighthouse.lingoswap.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public User find(final Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User save(final String email) {
        final User user = new User(email);
        return memberRepository.save(user);
    }
}
