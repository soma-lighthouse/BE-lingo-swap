package com.lighthouse.lingoswap.member.api;

import com.lighthouse.lingoswap.auth.dto.TokenPairResponse;
import com.lighthouse.lingoswap.auth.service.AuthManager;
import com.lighthouse.lingoswap.auth.util.JwtUtil;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.member.dto.*;
import com.lighthouse.lingoswap.member.service.MemberManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class MemberController {

    private final AuthManager authManager;
    private final MemberManager memberManager;

    @PostMapping
    public ResponseEntity<ResponseDto<TokenPairResponse>> create(@RequestHeader(JwtUtil.AUTH_HEADER) final String idTokenValue, @RequestBody @Valid final MemberRequest memberRequest) {
        ResponseDto<TokenPairResponse> responseDto = authManager.login(idTokenValue);
        memberManager.create(memberRequest);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<ResponseDto<Object>> patch(@PathVariable Long userId,
                                                     @RequestBody MemberRequest memberRequest) {
        return ResponseEntity.ok().body(memberManager.patch(userId, memberRequest));
    }

    @GetMapping("/form/interests")
    public ResponseEntity<ResponseDto<InterestsFormResponse>> readInterestsForm() {
        return ResponseEntity.ok(memberManager.readInterestsForm());
    }

    @GetMapping("/form/country")
    public ResponseEntity<ResponseDto<CountryFormResponse>> readCountryForm(Locale locale) {
        return ResponseEntity.ok(memberManager.readCountryForm(locale));
    }

    @GetMapping("/form/language")
    public ResponseEntity<ResponseDto<LanguageFormResponse>> readLanguageForm() {
        return ResponseEntity.ok(memberManager.readLanguageForm());
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<ResponseDto<MemberProfileResponse>> get(@PathVariable final Long userId) {
        return ResponseEntity.ok(memberManager.read(userId));
    }

    @PostMapping("/upload/profile")
    public ResponseEntity<ResponseDto<MemberPreSignedUrlResponse>> getPreSignedUrl(@RequestBody final MemberPreSignedUrlRequest memberPreSignedUrlRequest) {
        return ResponseEntity.ok(memberManager.createPreSignedUrl(memberPreSignedUrlRequest));
    }
}
