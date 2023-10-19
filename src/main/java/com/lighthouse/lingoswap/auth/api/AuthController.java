package com.lighthouse.lingoswap.auth.api;

import com.lighthouse.lingoswap.auth.dto.LoginResponse;
import com.lighthouse.lingoswap.auth.dto.MemberCreateRequest;
import com.lighthouse.lingoswap.auth.dto.ReissueRequest;
import com.lighthouse.lingoswap.auth.dto.TokenPairInfoResponse;
import com.lighthouse.lingoswap.auth.service.AuthManager;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final String ID_TOKEN_PARAMETER_NAME = "id_token";

    private final AuthManager authManager;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponse>> login(@RequestParam(ID_TOKEN_PARAMETER_NAME) String idToken) {
        return ResponseEntity.ok(authManager.login(idToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<LoginResponse>> signup(@RequestParam(ID_TOKEN_PARAMETER_NAME) String idToken,
                                                             @RequestBody @Valid final MemberCreateRequest memberCreateRequest) {
        return ResponseEntity.ok(authManager.signup(idToken, memberCreateRequest));
    }

    @PostMapping("/token")
    public ResponseEntity<ResponseDto<TokenPairInfoResponse>> reissue(@RequestBody @Valid final ReissueRequest reissueRequest) {
        return ResponseEntity.ok(authManager.reissue(reissueRequest));
    }

}
