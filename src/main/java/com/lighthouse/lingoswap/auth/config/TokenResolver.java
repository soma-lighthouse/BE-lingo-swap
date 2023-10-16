package com.lighthouse.lingoswap.auth.config;

import jakarta.servlet.http.HttpServletRequest;

public interface TokenResolver {

    String resolve(HttpServletRequest request);

}
