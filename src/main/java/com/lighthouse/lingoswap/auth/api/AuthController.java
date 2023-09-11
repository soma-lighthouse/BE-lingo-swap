package com.lighthouse.lingoswap.auth.api;

import com.lighthouse.lingoswap.auth.dto.ReissueRequest;
import com.lighthouse.lingoswap.auth.dto.TokenPairResponse;
import com.lighthouse.lingoswap.auth.service.AuthManager;
import com.lighthouse.lingoswap.auth.util.JwtUtil;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthManager authManager;

    @PostMapping("/login/google")
    public ResponseEntity<ResponseDto<TokenPairResponse>> login(@RequestHeader(JwtUtil.AUTH_HEADER) final String idTokenValue) {
        return ResponseEntity.ok(authManager.login(idTokenValue));
    }

    @PostMapping("/token")
    public ResponseEntity<ResponseDto<TokenPairResponse>> reissue(@RequestBody @Valid final ReissueRequest reissueRequest) {
        return ResponseEntity.ok(authManager.reissue(reissueRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<Object>> logout(@RequestHeader(JwtUtil.AUTH_HEADER) final String accessTokenValue) {
        return ResponseEntity.ok(authManager.logout(accessTokenValue));
    }
}
