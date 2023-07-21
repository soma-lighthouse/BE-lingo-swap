package com.lighthouse.lingoswap.member.api;

import com.lighthouse.lingoswap.member.service.MemberCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberCRUDService memberCRUDService;

//    @GetMapping
//    public ResponseEntity<ReadDetailsCommonResponseBody> showMemberProfile(@RequestBody final ReadDetailsRequest readDetailsRequest) {
//        return ResponseEntity.ok(memberCRUDService.showDetails(readDetailsRequest));
//    }
//
//    @PostMapping
//    public ResponseEntity<SignUpResponse> signUp(@RequestBody final SignUpRequest signUpRequest) {
//        return ResponseEntity.ok(memberCRUDService.signUp(signUpRequest));
//    }
}
