package com.lighthouse.lingoswap.image.application;

import com.lighthouse.lingoswap.infra.service.S3Service;
import com.lighthouse.lingoswap.member.dto.MemberPreSignedUrlRequest;
import com.lighthouse.lingoswap.member.dto.MemberPreSignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URL;

@RequiredArgsConstructor
@Service
public class ImageManager {

    private final S3Service s3Service;

    public MemberPreSignedUrlResponse createPreSignedUrl(final MemberPreSignedUrlRequest memberPreSignedUrlRequest) {
        URL preSignedUrl = s3Service.generatePresignedUrl(memberPreSignedUrlRequest.key());
        return MemberPreSignedUrlResponse.from(preSignedUrl);
    }

}
