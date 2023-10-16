package com.lighthouse.lingoswap.member.application;

import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import com.lighthouse.lingoswap.member.exception.DuplicateMemberException;
import com.lighthouse.lingoswap.member.exception.MemberNotFoundException;
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
            throw new DuplicateMemberException(member.getUsername(), ex);
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

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

}
