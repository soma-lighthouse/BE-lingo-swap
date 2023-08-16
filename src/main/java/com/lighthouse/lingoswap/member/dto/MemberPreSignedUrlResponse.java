package com.lighthouse.lingoswap.member.dto;

public record MemberPreSignedUrlResponse(String url) {

    public static MemberPreSignedUrlResponse from(String url) {
        return new MemberPreSignedUrlResponse(url);
    }
}
