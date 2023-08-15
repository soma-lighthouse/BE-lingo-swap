package com.lighthouse.lingoswap.member.api;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.member.dto.MemberPreSignedUrlResponse;
import com.lighthouse.lingoswap.member.dto.MemberProfileResponse;
import com.lighthouse.lingoswap.member.service.MemberManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class MemberController {

    private final MemberManager memberManager;

    @GetMapping("/{userId}/profile")
    public ResponseEntity<ResponseDto<MemberProfileResponse>> get(@PathVariable final Long userId) {
        return ResponseEntity.ok(memberManager.read(userId));
    }

    @GetMapping("/upload/profile")
    public ResponseEntity<ResponseDto<MemberPreSignedUrlResponse>> getPreSignedUrl(final Long userId) {
        return ResponseEntity.ok(memberManager.createPreSignedUrl(userId));
    }
}
