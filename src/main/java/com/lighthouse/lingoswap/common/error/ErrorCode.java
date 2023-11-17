package com.lighthouse.lingoswap.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    VALIDATION_ERROR("40000", BAD_REQUEST),
    DUPLICATE_USER_ERROR("40001", BAD_REQUEST),
    FORBIDDEN_ERROR("40300", FORBIDDEN),
    NOT_FOUND_ERROR("40401", NOT_FOUND),

    SERVER_ERROR("50000", INTERNAL_SERVER_ERROR),
    MATCH_ERROR("50001", INTERNAL_SERVER_ERROR),
    UPLOAD_IMAGE_ERROR("50002", INTERNAL_SERVER_ERROR),
    FORM_ERROR("50003", INTERNAL_SERVER_ERROR),
    QUESTION_RECOMMENDATION_ERROR("50004", INTERNAL_SERVER_ERROR),
    QUESTION_LIST_ERROR("50005", INTERNAL_SERVER_ERROR),
    PROFILE_ERROR("50006", INTERNAL_SERVER_ERROR);

    private static final String ERROR_MESSAGE_CODE_FORMAT = "error.%s.message";

    private final String statusCode;
    private final HttpStatus status;

    public String getMessageCode() {
        return ERROR_MESSAGE_CODE_FORMAT.formatted(statusCode);
    }

}
