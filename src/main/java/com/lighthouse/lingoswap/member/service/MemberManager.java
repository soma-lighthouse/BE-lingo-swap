package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.member.dto.MemberCreateRequest;
import com.lighthouse.lingoswap.member.dto.MemberResponse;
import com.lighthouse.lingoswap.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberManager {

    private final MemberService memberService;

    public ResponseDto<MemberResponse> create(final MemberCreateRequest memberCreateRequest) {
        Member savedMember = memberService.save(memberCreateRequest.email());
        return ResponseDto.<MemberResponse>builder()
                .code(20000)
                .message("Successfully member saved")
                .data(MemberResponse.from(savedMember))
                .build();
    }
}
