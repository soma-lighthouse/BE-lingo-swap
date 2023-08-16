package com.lighthouse.lingoswap.member.api;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.member.dto.*;
import com.lighthouse.lingoswap.member.service.MemberManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class MemberController {

    private final MemberManager memberManager;

    @PostMapping
    public ResponseEntity<ResponseDto> create(@RequestBody @Valid MemberCreateRequest memberCreateRequest) {
        memberManager.create(memberCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.builder()
                .code("20100")
                .message("Successfully created")
                .data("성공")
                .build());
    }

    @GetMapping("/form/interests")
    public ResponseEntity<ResponseDto<InterestsFormResponse>> readInterestsForm() {
        InterestsFormResponse interestsFormResponse = memberManager.readInterestsForm();
        return ResponseEntity.ok(ResponseDto.<InterestsFormResponse>builder()
                .code("20000")
                .message("OK")
                .data(interestsFormResponse)
                .build());
    }


    @GetMapping("/form/country")
    public ResponseEntity<ResponseDto<CountryFormResponse>> readCountryForm() {
        CountryFormResponse countryFormResponse = memberManager.readCountryForm();
        return ResponseEntity.ok(ResponseDto.<CountryFormResponse>builder()
                .code("20000")
                .message("OK")
                .data(countryFormResponse)
                .build());
    }

    @GetMapping("/form/language")
    public ResponseEntity<ResponseDto<LanguageFormResponse>> readLanguageForm() {
        LanguageFormResponse languageFormResponse = memberManager.readLanguageForm();
        return ResponseEntity.ok(ResponseDto.<LanguageFormResponse>builder()
                .code("20000")
                .message("OK")
                .data(languageFormResponse)
                .build());
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<ResponseDto<MemberProfileResponse>> get(@PathVariable final Long userId) {
        return ResponseEntity.ok(memberManager.read(userId));
    }

    @GetMapping("/upload/profile")
    public ResponseEntity<ResponseDto<MemberPreSignedUrlResponse>> getPreSignedUrl(final Long userId) {
        return ResponseEntity.ok(memberManager.createPreSignedUrl(userId));
    }
}
