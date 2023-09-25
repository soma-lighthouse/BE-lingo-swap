package com.lighthouse.lingoswap.common.service;

import com.lighthouse.lingoswap.common.dto.ErrorMessage;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ErrorResponseService {

    private final MessageSourceService messageSourceService;

    public ResponseDto<ErrorMessage> build(ExceptionCode exceptionCode) {
        String code = exceptionCode.getCode();
        String key = exceptionCode.getKey();
        return ResponseDto.error(code, null, new ErrorMessage(messageSourceService.translate(key)));
    }
}
