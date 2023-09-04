package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.exception.MemberNotFoundException;
import com.lighthouse.lingoswap.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(Member member) {
        memberRepository.save(member);
    }

    public Member findById(final Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
    }

    Member findByIdWithRegionAndUsedLanguage(final Long id) {
        return memberRepository.findByIdWithRegionAndUsedLanguage(id);
    }

    public List<Member> findAllByIdsWithRegionAndUsedLanguage(final List<Long> ids) {
        return memberRepository.findAllByIdsWithRegionAndUsedLanguage(ids);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
