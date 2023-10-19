package com.lighthouse.lingoswap.interests.presentation;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.interests.application.InterestsManager;
import com.lighthouse.lingoswap.interests.dto.InterestsFormResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class InterestsController {

    private final InterestsManager interestsManager;

    @GetMapping("/form/interests")
    public ResponseEntity<ResponseDto<InterestsFormResponse>> getInterestsForm() {
        return ResponseEntity.ok(interestsManager.readInterestsForm());
    }

}
