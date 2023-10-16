package com.lighthouse.lingoswap.member.presentation;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.member.application.MemberManager;
import com.lighthouse.lingoswap.member.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class MemberController {

    private final MemberManager memberManager;

    @GetMapping("/{uuid}/preference")
    public ResponseEntity<ResponseDto<MemberPreferenceResponse>> getPreference(@PathVariable final String uuid) {
        return ResponseEntity.ok().body(memberManager.getPreference(uuid));
    }

    @GetMapping("/{uuid}/profile")
    public ResponseEntity<ResponseDto<MemberProfileResponse>> get(@PathVariable final String uuid) {
        return ResponseEntity.ok(memberManager.read(uuid));
    }

    @PatchMapping("/{uuid}/profile")
    public ResponseEntity<ResponseDto<Object>> patch(@PathVariable final String uuid,
                                                     @RequestBody @Valid final MemberUpdateProfileRequest memberUpdateProfileRequest) {
        return ResponseEntity.ok().body(memberManager.updateProfile(uuid, memberUpdateProfileRequest));
    }

    @PatchMapping("/{uuid}/preference")
    public ResponseEntity<ResponseDto<Object>> patchPreference(@PathVariable final String uuid,
                                                               @RequestBody final MemberPreferenceRequest memberRequest) {
        return ResponseEntity.ok().body(memberManager.updatePreference(uuid, memberRequest));
    }

    @GetMapping("/form/country")
    public ResponseEntity<ResponseDto<CountryFormResponse>> readCountryForm() {
        return ResponseEntity.ok(memberManager.readCountryForm());
    }

    @GetMapping("/form/language")
    public ResponseEntity<ResponseDto<LanguageFormResponse>> readLanguageForm() {
        return ResponseEntity.ok(memberManager.readLanguageForm());
    }

    @PostMapping("/upload/profile")
    public ResponseEntity<ResponseDto<MemberPreSignedUrlResponse>> getPreSignedUrl(@RequestBody final MemberPreSignedUrlRequest memberPreSignedUrlRequest) {
        return ResponseEntity.ok(memberManager.createPreSignedUrl(memberPreSignedUrlRequest));
    }

}
