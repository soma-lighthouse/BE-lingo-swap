package com.lighthouse.lingoswap.auth.api;

import com.lighthouse.lingoswap.auth.dto.LoginRequest;
import com.lighthouse.lingoswap.auth.dto.LoginResponse;
import com.lighthouse.lingoswap.auth.dto.ReissueRequest;
import com.lighthouse.lingoswap.auth.dto.ReissueResponse;
import com.lighthouse.lingoswap.auth.service.AuthManager;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthManager authManager;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponse>> login(@RequestBody @Valid final LoginRequest loginRequest) {
        return ResponseEntity.ok(authManager.login(loginRequest));
    }

    @PostMapping("/token")
    public ResponseEntity<ResponseDto<ReissueResponse>> reissue(@RequestBody @Valid final ReissueRequest reissueRequest) {
        return ResponseEntity.ok(authManager.reissue(reissueRequest));
    }
}
