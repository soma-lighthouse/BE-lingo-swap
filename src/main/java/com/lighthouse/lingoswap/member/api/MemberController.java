package com.lighthouse.lingoswap.member.api;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.member.dto.*;
import com.lighthouse.lingoswap.member.service.MemberManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class MemberController {

    private final MemberManager memberManager;

    @GetMapping("/form/interests")
    public ResponseEntity<ResponseDto<InterestsFormResponse>> readInterestsForm() {
        return ResponseEntity.ok(memberManager.readInterestsForm());
    }

    @GetMapping("/form/country")
    public ResponseEntity<ResponseDto<CountryFormResponse>> readCountryForm() {
        return ResponseEntity.ok(memberManager.readCountryForm());
    }

    @GetMapping("/form/language")
    public ResponseEntity<ResponseDto<LanguageFormResponse>> readLanguageForm() {
        return ResponseEntity.ok(memberManager.readLanguageForm());
    }

    @GetMapping("/{uuid}/profile")
    public ResponseEntity<ResponseDto<MemberProfileResponse>> get(@PathVariable final String uuid) {
        return ResponseEntity.ok(memberManager.read(uuid));
    }

    @PostMapping("/upload/profile")
    public ResponseEntity<ResponseDto<MemberPreSignedUrlResponse>> getPreSignedUrl(@RequestBody final MemberPreSignedUrlRequest memberPreSignedUrlRequest) {
        return ResponseEntity.ok(memberManager.createPreSignedUrl(memberPreSignedUrlRequest));
    }
}
