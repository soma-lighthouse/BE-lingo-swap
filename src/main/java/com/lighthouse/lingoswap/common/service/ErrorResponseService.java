package com.lighthouse.lingoswap.common.service;

import com.lighthouse.lingoswap.common.dto.ErrorMessage;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.error.ErrorCode;
import com.lighthouse.lingoswap.common.message.MessageSourceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ErrorResponseService {

    private final MessageSourceManager messageSourceManager;

    public ResponseEntity<ResponseDto<ErrorMessage>> build(ErrorCode errorCode) {
        HttpStatus status = errorCode.getStatus();
        String code = errorCode.getCode();
        String key = errorCode.getKey();
        return ResponseEntity.status(status)
                .body(ResponseDto.error(code, null, new ErrorMessage(messageSourceManager.translate(key))));
    }

}
