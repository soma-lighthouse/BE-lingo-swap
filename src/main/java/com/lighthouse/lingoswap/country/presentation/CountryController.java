package com.lighthouse.lingoswap.country.presentation;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.country.application.CountryManager;
import com.lighthouse.lingoswap.country.dto.CountryFormResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CountryController {

    private final CountryManager countryManager;

    @GetMapping("/api/v1/form/country")
    public ResponseEntity<ResponseDto<CountryFormResponse>> getForm() {
        return ResponseEntity.ok(ResponseDto.success(countryManager.readForm()));
    }

}
