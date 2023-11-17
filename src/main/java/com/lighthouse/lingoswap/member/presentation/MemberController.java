package com.lighthouse.lingoswap.member.presentation;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.member.application.MemberManager;
import com.lighthouse.lingoswap.member.dto.MemberPreferenceResponse;
import com.lighthouse.lingoswap.member.dto.MemberProfileResponse;
import com.lighthouse.lingoswap.member.dto.MemberUpdatePreferenceRequest;
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

    @GetMapping("/{uuid}/profile")
    public ResponseEntity<ResponseDto<MemberProfileResponse>> getProfile(@PathVariable final String uuid) {
        return ResponseEntity.ok(ResponseDto.success(memberManager.readProfile(uuid)));
    }

    @GetMapping("/{uuid}/preference")
    public ResponseEntity<ResponseDto<MemberPreferenceResponse>> getPreference(@PathVariable final String uuid) {
        return ResponseEntity.ok(ResponseDto.success(memberManager.readPreference(uuid)));
    }

    @PatchMapping("/{uuid}/profile")
    public ResponseEntity<ResponseDto<Void>> patchProfile(@PathVariable final String uuid,
                                                          @Valid @RequestBody final MemberUpdateProfileRequest memberUpdateProfileRequest) {
        memberManager.updateProfile(uuid, memberUpdateProfileRequest);
        return ResponseEntity.ok(ResponseDto.noData());
    }

    @PatchMapping("/{uuid}/preference")
    public ResponseEntity<ResponseDto<Void>> patchPreference(@PathVariable final String uuid,
                                                             @Valid @RequestBody final MemberUpdatePreferenceRequest memberUpdatePreferenceRequest) {
        memberManager.updatePreference(uuid, memberUpdatePreferenceRequest);
        return ResponseEntity.ok(ResponseDto.noData());
    }

}
