package com.lighthouse.lingoswap.common.dto;


import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class ResponseData {

    private final Map<String, Object> data;

    private ResponseData() {
        this.data = new LinkedHashMap<>();
    }
}
