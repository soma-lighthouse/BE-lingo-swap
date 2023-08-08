package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.exception.MemberNotFoundException;
import com.lighthouse.lingoswap.member.repository.MemberQueryRepository;
import com.lighthouse.lingoswap.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;

    public Member findById(final Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
    }

    public List<Member> findAllById(final List<Long> ids) {
        return memberQueryRepository.findAllById(ids);
    }
}
