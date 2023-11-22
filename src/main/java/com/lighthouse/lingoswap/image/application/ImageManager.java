package com.lighthouse.lingoswap.image.application;

import com.lighthouse.lingoswap.infra.service.ImageService;
import com.lighthouse.lingoswap.member.dto.MemberPreSignedUrlRequest;
import com.lighthouse.lingoswap.member.dto.MemberPreSignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URL;

@RequiredArgsConstructor
@Service
public class ImageManager {

    private final ImageService imageService;

    public MemberPreSignedUrlResponse createPreSignedUrl(final MemberPreSignedUrlRequest memberPreSignedUrlRequest) {
        URL preSignedUrl = imageService.generatePresignedUrl(memberPreSignedUrlRequest.key());
        return MemberPreSignedUrlResponse.of(preSignedUrl, imageService.getEndpoint());
    }

}
