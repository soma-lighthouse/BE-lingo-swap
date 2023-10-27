package com.lighthouse.lingoswap.image.application;

import com.lighthouse.lingoswap.IntegrationTestSupport;
import com.lighthouse.lingoswap.infra.service.S3Service;
import com.lighthouse.lingoswap.member.dto.MemberPreSignedUrlRequest;
import com.lighthouse.lingoswap.member.dto.MemberPreSignedUrlResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class ImageManagerTest extends IntegrationTestSupport {

    private static final String IMAGE_KEY = "/2c1a2c3d-4d8b-46f4-9011-189cd1fc8644/1.jpg";

    @Autowired
    ImageManager imageManager;

    @Autowired
    S3Service s3Service;

    @DisplayName("파일의 key로 pre-signed url을 발급한다.")
    @Test
    void createPreSignedUrl() {
        // given
        MemberPreSignedUrlRequest request = MemberPreSignedUrlRequest.from(IMAGE_KEY);

        // when
        MemberPreSignedUrlResponse actual = imageManager.createPreSignedUrl(request);

        // then
        assertThat(actual.url()).isEqualTo(s3Service.generatePresignedUrl(IMAGE_KEY));
    }

}
