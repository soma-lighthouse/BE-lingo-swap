package com.lighthouse.lingoswap.member.api;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.member.dto.MemberReadRequest;
import com.lighthouse.lingoswap.member.dto.MemberResponse;
import com.lighthouse.lingoswap.member.service.MemberCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberCRUDService memberCRUDService;

    @GetMapping
    public ResponseEntity<ResponseDto<MemberResponse>> read(@RequestBody final MemberReadRequest memberReadRequest) {
        final MemberResponse memberResponse = memberCRUDService.read(memberReadRequest);
        return ResponseEntity.ok(ResponseDto.<MemberResponse>builder()
                .code("200")
                .message("OK")
                .data(memberResponse)
                .build());
    }
}
