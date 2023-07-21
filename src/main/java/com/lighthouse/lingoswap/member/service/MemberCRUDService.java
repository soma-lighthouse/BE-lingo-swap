package com.lighthouse.lingoswap.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCRUDService {

    private final MemberService memberService;

//    public SignUpResponse signUp(final SignUpRequest signUpRequest) {
//        final Member savedMember = memberService.save(signUpRequest.email());
//        return SignUpResponse.builder()
//                .email(savedMember.getEmail())
//                .build();
//    }

//    public ReadDetailsResponse showDetails(final ReadDetailsRequest readDetailsRequest) {
//        final Member foundMember = memberService.find(readDetailsRequest.id());
//        ReadDetailsResponse a = new ReadDetailsResponse()
//    }

}
