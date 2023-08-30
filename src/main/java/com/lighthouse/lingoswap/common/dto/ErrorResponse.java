package com.lighthouse.lingoswap.common.dto;

import lombok.Builder;

@Builder
public record ErrorResponse(String code, String message, Object data) {

}
