package com.lighthouse.lingoswap.image.presentation;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.image.application.ImageManager;
import com.lighthouse.lingoswap.member.dto.MemberPreSignedUrlRequest;
import com.lighthouse.lingoswap.member.dto.MemberPreSignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageManager imageManager;

    @PostMapping("/api/v1/user/upload/profile")
    public ResponseEntity<ResponseDto<MemberPreSignedUrlResponse>> getPreSignedUrl(@RequestBody final MemberPreSignedUrlRequest memberPreSignedUrlRequest) {
        return ResponseEntity.ok(ResponseDto.success(imageManager.createPreSignedUrl(memberPreSignedUrlRequest)));
    }

}
