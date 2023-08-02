package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.match.repository.MatchQueryRepository;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.exception.MemberNotFoundException;
import com.lighthouse.lingoswap.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MatchQueryRepository matchQueryRepository;

    Member save(final String email) {
        Member member = Member.builder().email(email).build();
        return memberRepository.save(member);
    }

    Member findById(final Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
    }
}
