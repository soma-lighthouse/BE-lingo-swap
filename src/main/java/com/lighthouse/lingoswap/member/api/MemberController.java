package com.lighthouse.lingoswap.member.api;

import com.lighthouse.lingoswap.member.dto.*;
import com.lighthouse.lingoswap.member.service.MemberManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class MemberController {

    private final MemberManager memberManager;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid final MemberCreateRequest memberCreateRequest) {
        memberManager.create(memberCreateRequest);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/form/interests")
    public ResponseEntity<InterestsFormResponse> readInterestsForm() {
        return ResponseEntity.ok().body(memberManager.readInterestsForm());
    }

    @GetMapping("/form/country")
    public ResponseEntity<CountryFormResponse> readCountryForm() {
        return ResponseEntity.ok().body(memberManager.readCountryForm());
    }

    @GetMapping("/form/language")
    public ResponseEntity<LanguageFormResponse> readLanguageForm() {
        return ResponseEntity.ok().body(memberManager.readLanguageForm());
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<MemberProfileResponse> get(@PathVariable final Long userId) {
        return ResponseEntity.ok().body(memberManager.read(userId));
    }

    @PostMapping("/upload/profile")
    public ResponseEntity<MemberPreSignedUrlResponse> getPreSignedUrl(@RequestBody final MemberPreSignedUrlRequest memberPreSignedUrlRequest) {
        return ResponseEntity.ok(memberManager.createPreSignedUrl(memberPreSignedUrlRequest));
    }
}
