package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.member.dto.MemberCreateRequest;
import com.lighthouse.lingoswap.member.dto.MemberResponse;
import com.lighthouse.lingoswap.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCRUDService {

    private final MemberService memberService;

    public MemberResponse create(final MemberCreateRequest memberCreateRequest) {
        Member member = new Member(memberCreateRequest.email());
        Member savedMember = memberService.save(member);
        return new MemberResponse(savedMember.getId());
    }
}
