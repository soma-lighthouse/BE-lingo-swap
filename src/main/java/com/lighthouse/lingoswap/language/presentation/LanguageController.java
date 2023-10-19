package com.lighthouse.lingoswap.language.presentation;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.language.application.LanguageManager;
import com.lighthouse.lingoswap.language.dto.LanguageFormResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LanguageController {

    private final LanguageManager languageManager;

    @GetMapping("/api/v1/user/form/language")
    public ResponseEntity<ResponseDto<LanguageFormResponse>> readLanguageForm() {
        return ResponseEntity.ok(ResponseDto.success(languageManager.readLanguageForm()));
    }

}
