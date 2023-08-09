package com.lighthouse.lingoswap.member.dto;


import com.lighthouse.lingoswap.member.entity.Member;

public record MemberResponse(Long id) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId());
    }
}
