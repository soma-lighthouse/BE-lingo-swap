package com.lighthouse.lingoswap.common.dto;


import lombok.Getter;
import org.springframework.util.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class ResponseData {

    private final Map<String, Object> data;

    private ResponseData() {
        data = new LinkedHashMap<>();
    }

    public static ResponseData builder() {
        return new ResponseData();
    }

    public ResponseData insert(String key, Object value) {
        Assert.notNull(key, "The key must not be null");
        Assert.notNull(value, "The value must not be null");
        data.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return data;
    }
}
