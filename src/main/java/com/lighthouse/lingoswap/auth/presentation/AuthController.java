package com.lighthouse.lingoswap.auth.presentation;

import com.lighthouse.lingoswap.auth.application.AuthManager;
import com.lighthouse.lingoswap.auth.dto.LoginResponse;
import com.lighthouse.lingoswap.auth.dto.MemberCreateRequest;
import com.lighthouse.lingoswap.auth.dto.ReissueRequest;
import com.lighthouse.lingoswap.auth.dto.TokenPairInfoResponse;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthManager authManager;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponse>> login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(ResponseDto.success(authManager.login((String) auth.getPrincipal())));
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<LoginResponse>> signup(@RequestBody @Valid final MemberCreateRequest memberCreateRequest) {
        return ResponseEntity.ok(ResponseDto.success(authManager.signup(memberCreateRequest)));
    }

    @PostMapping("/token")
    public ResponseEntity<ResponseDto<TokenPairInfoResponse>> reissue(@RequestBody @Valid final ReissueRequest reissueRequest) {
        return ResponseEntity.ok(ResponseDto.success(authManager.reissue(reissueRequest)));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto<Void>> delete() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        authManager.delete((String) auth.getPrincipal());
        return ResponseEntity.ok(ResponseDto.noData());
    }

}
