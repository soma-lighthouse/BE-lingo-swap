package com.lighthouse.lingoswap.member.dto;

import java.net.URL;

public record MemberPreSignedUrlResponse(URL url) {

    public static MemberPreSignedUrlResponse from(URL url) {
        return new MemberPreSignedUrlResponse(url);
    }

}
