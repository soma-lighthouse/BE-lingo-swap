package com.lighthouse.lingoswap.member.api;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.member.dto.MemberCreateRequest;
import com.lighthouse.lingoswap.member.dto.MemberResponse;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.service.MemberCRUDService;
import com.lighthouse.lingoswap.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

    private final MemberCRUDService memberCRUDService;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ResponseDto<MemberResponse>> create(@RequestBody @Valid final MemberCreateRequest memberCreateRequest) {
        final MemberResponse memberResponse = memberCRUDService.create(memberCreateRequest);
        return ResponseEntity.ok(ResponseDto.<MemberResponse>builder()
                .code("200")
                .message("Successfully user created")
                .data(memberResponse)
                .build());
    }

    @PostMapping("/temporary/{name}")
    public void createTemporary(@PathVariable String name) {
        for (int i = 0; i < 10; i++) {
            Member member = Member.builder()
                    .gender("M")
                    .name(name + "A")
                    .description("회원" + i)
                    .profileImage("https://img.icons8.com/?size=512&id=63684&format=png")
                    .region("KR")
                    .build();
            memberService.save(member);
        }
    }
}
