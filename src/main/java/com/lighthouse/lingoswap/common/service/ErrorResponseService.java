package com.lighthouse.lingoswap.common.service;

import com.lighthouse.lingoswap.common.dto.ErrorMessage;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ErrorResponseService {

    private final MessageService messageService;

    public ResponseEntity<ResponseDto<ErrorMessage>> buildResponse(final String message, final ErrorCode errorCode) {
        HttpStatus status = errorCode.getStatus();
        String statusCode = errorCode.getStatusCode();
        String messageCode = errorCode.getMessageCode();
        return ResponseEntity.status(status)
                .body(ResponseDto.error(statusCode, message, ErrorMessage.of(messageService.translate(messageCode))));
    }

}
