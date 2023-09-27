package com.lighthouse.lingoswap.member.api;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.member.dto.*;
import com.lighthouse.lingoswap.member.service.MemberManager;
import com.lighthouse.lingoswap.question.dto.MyQuestionsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class MemberController {

    private final MemberManager memberManager;

    @GetMapping("/{uuid}/preference")
    public ResponseEntity<ResponseDto<MemberPreferenceResponse>> getPreferred(@PathVariable final String uuid) {
        return ResponseEntity.ok().body(memberManager.getPreference(uuid));
    }

    @GetMapping("/{uuid}/profile")
    public ResponseEntity<ResponseDto<MemberProfileResponse>> get(@PathVariable final String uuid) {
        return ResponseEntity.ok(memberManager.read(uuid));
    }

    @PatchMapping("/{uuid}/profile")
    public ResponseEntity<ResponseDto<Object>> patch(@PathVariable final String uuid,
                                                     @RequestBody final MemberUpdateProfileRequest memberUpdateProfileRequest) {
        return ResponseEntity.ok().body(memberManager.patch(uuid, memberUpdateProfileRequest));
    }

    @PatchMapping("/{uuid}/preference")
    public ResponseEntity<ResponseDto<Object>> patchPreference(@PathVariable final String uuid,
                                                               @RequestBody final MemberPreferenceRequest memberRequest) {
        return ResponseEntity.ok().body(memberManager.patchPreference(uuid, memberRequest));
    }

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

    @PostMapping("/upload/profile")
    public ResponseEntity<ResponseDto<MemberPreSignedUrlResponse>> getPreSignedUrl(@RequestBody final MemberPreSignedUrlRequest memberPreSignedUrlRequest) {
        return ResponseEntity.ok(memberManager.createPreSignedUrl(memberPreSignedUrlRequest));
    }

    @GetMapping(path = "/{uuid}/question")
    public ResponseEntity<ResponseDto<MyQuestionsResponse>> getMyQuestion(@PathVariable final String uuid) {
        return ResponseEntity.ok(memberManager.getMyQuestion(uuid));
    }
}
