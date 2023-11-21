package com.lighthouse.lingoswap.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class GoogleIdTokenResolver implements TokenResolver {

    private static final String ID_TOKEN = "id_token";

    @Override
    public String resolve(final HttpServletRequest request) {
        return request.getParameter(ID_TOKEN);
    }

}
