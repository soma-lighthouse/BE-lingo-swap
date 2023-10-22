package com.lighthouse.lingoswap.member.presentation;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.member.application.MemberManager;
import com.lighthouse.lingoswap.member.dto.MemberPreferenceRequest;
import com.lighthouse.lingoswap.member.dto.MemberPreferenceResponse;
import com.lighthouse.lingoswap.member.dto.MemberProfileResponse;
import com.lighthouse.lingoswap.member.dto.MemberUpdateProfileRequest;
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
        return ResponseEntity.ok(ResponseDto.success(memberManager.readPreference(uuid)));
    }

    @GetMapping("/{uuid}/profile")
    public ResponseEntity<ResponseDto<MemberProfileResponse>> get(@PathVariable final String uuid) {
        return ResponseEntity.ok(ResponseDto.success(memberManager.readProfile(uuid)));
    }

    @PatchMapping("/{uuid}/profile")
    public ResponseEntity<ResponseDto<Void>> patch(@PathVariable final String uuid,
                                                   @RequestBody @Valid final MemberUpdateProfileRequest memberUpdateProfileRequest) {
        memberManager.updateProfile(uuid, memberUpdateProfileRequest);
        return ResponseEntity.ok(ResponseDto.noData());
    }

    @PatchMapping("/{uuid}/preference")
    public ResponseEntity<ResponseDto<Void>> patchPreference(@PathVariable final String uuid,
                                                             @RequestBody final MemberPreferenceRequest memberRequest) {
        memberManager.updatePreference(uuid, memberRequest);
        return ResponseEntity.ok(ResponseDto.noData());
    }

}
