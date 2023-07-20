package com.lighthouse.lingoswap.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCRUDService {

    private final MemberService memberService;

//    public SignUpResponse signUp(final SignUpRequest signUpRequest) {
//        final User savedUser = memberService.save(signUpRequest.email());
//        return SignUpResponse.builder()
//                .email(savedUser.getEmail())
//                .build();
//    }

//    public ReadDetailsResponse showDetails(final ReadDetailsRequest readDetailsRequest) {
//        final Member foundMember = memberService.find(readDetailsRequest.id());
//        ReadDetailsResponse a = new ReadDetailsResponse()
//    }

}
