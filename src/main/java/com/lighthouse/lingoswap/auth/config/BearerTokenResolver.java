package com.lighthouse.lingoswap.auth.config;

import com.lighthouse.lingoswap.auth.exception.InvalidTokenException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BearerTokenResolver implements TokenResolver {

    private static final String AUTHENTICATION_SCHEME_BEARER = "Bearer";
    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$",
            Pattern.CASE_INSENSITIVE);

    @Override
    public String resolve(final HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.startsWithIgnoreCase(authorization, AUTHENTICATION_SCHEME_BEARER)) {
            return null;
        }
        Matcher matcher = authorizationPattern.matcher(authorization);
        if (!matcher.matches()) {
            throw new InvalidTokenException();
        }
        return matcher.group("token");
    }

}
