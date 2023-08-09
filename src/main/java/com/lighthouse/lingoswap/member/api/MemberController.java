package com.lighthouse.lingoswap.member.api;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.service.MemberCRUDService;
import com.lighthouse.lingoswap.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class MemberController {

    private final MemberCRUDService memberCRUDService;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ResponseDto> create() {

        for (int i = 0; i < 10; i++) {
            Member member = new Member("ahj" + i, "ㅋㅋ", "https://img.icons8.com/?size=512&id=63684&format=png");
            memberService.save(member);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.builder()
                .code("20100")
                .message("Successfully created")
                .data("성공")
                .build()); //보드 정보 내려줌
    }
}