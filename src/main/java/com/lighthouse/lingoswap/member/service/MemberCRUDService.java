package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.member.dto.MemberReadRequest;
import com.lighthouse.lingoswap.member.dto.MemberResponse;
import com.lighthouse.lingoswap.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCRUDService {

    private final MemberService memberService;

    public MemberResponse read(final MemberReadRequest memberReadRequest) {
        final Member foundMember = memberService.findById(memberReadRequest.id());
        return new MemberResponse(foundMember.getEmail());
    }
}
