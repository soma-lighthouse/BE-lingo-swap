package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.exception.DuplicateMemberException;
import com.lighthouse.lingoswap.member.exception.MemberNotFoundException;
import com.lighthouse.lingoswap.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(Member member) {
        try {
            memberRepository.save(member);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateMemberException(member.getAuthDetails().getUsername(), ex);
        }
    }

    public Member findByUuid(final String uuid) {
        return memberRepository.findByAuthDetailsUuid(uuid)
                .orElseThrow(() -> new MemberNotFoundException(uuid));
    }

    Member findByUuidWithRegionAndUsedLanguage(final String uuid) {
        return memberRepository.findByUuidWithRegionAndUsedLanguage(uuid)
                .orElseThrow((() -> new MemberNotFoundException(uuid)));
    }

    public List<Member> findAllByIdsWithRegionAndUsedLanguage(final List<Long> ids) {
        return memberRepository.findAllByIdsWithRegionAndUsedLanguage(ids);
    }
}
