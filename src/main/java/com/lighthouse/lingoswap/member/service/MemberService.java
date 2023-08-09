package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.exception.MemberNotFoundException;
import com.lighthouse.lingoswap.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(final Member member) {
        return memberRepository.save(member);
    }

    public Member findById(final Long id) {  ///왜 퍼블릭이 아니었나? + 위에도
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
    }
}
