package com.lighthouse.lingoswap.member.dto;

public record MemberPreSignedUrlRequest(String key) {

    public static MemberPreSignedUrlRequest from(final String key) {
        return new MemberPreSignedUrlRequest(key);
    }

}
