package com.lighthouse.lingoswap.user.api;

import com.lighthouse.lingoswap.user.service.UserCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserCRUDService userCRUDService;

//    @GetMapping
//    public ResponseEntity<ReadDetailsCommonResponseBody> showUserProfile(@RequestBody final ReadDetailsRequest readDetailsRequest) {
//        return ResponseEntity.ok(userCRUDService.showDetails(readDetailsRequest));
//    }
//
//    @PostMapping
//    public ResponseEntity<SignUpResponse> signUp(@RequestBody final SignUpRequest signUpRequest) {
//        return ResponseEntity.ok(userCRUDService.signUp(signUpRequest));
//    }
}
