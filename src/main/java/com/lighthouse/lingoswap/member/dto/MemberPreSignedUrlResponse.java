package com.lighthouse.lingoswap.member.dto;

import java.net.URL;

public record MemberPreSignedUrlResponse(URL url, URL endPoint) {

    public static MemberPreSignedUrlResponse of(URL url, URL endPoint) {
        return new MemberPreSignedUrlResponse(url, endPoint);
    }

}
