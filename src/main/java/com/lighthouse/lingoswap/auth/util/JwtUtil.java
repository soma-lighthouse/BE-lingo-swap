package com.lighthouse.lingoswap.auth.util;

import com.lighthouse.lingoswap.auth.exception.ExpiredTokenException;
import com.lighthouse.lingoswap.auth.exception.InvalidTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class JwtUtil {

    public static final String AUTH_HEADER = "authorization";
    private static final long ONE_WEEK_IN_MS = 604800000;

    private final JwtParser jwtParser;
    private final String secretKey;

    @Value("${spring.security.jwt.exp.access}")
    private long accessExp;

    @Value("${spring.security.jwt.exp.refresh}")
    private long refreshExp;

    @Value("${spring.security.jwt.auth-scheme}")
    private String authScheme;

    protected JwtUtil(@Value("${spring.security.jwt.secret-key}") final String secretKey) {
        this.secretKey = secretKey;
        jwtParser = Jwts.parser().setSigningKey(secretKey);
    }

    public String generate(final String subject) {
        return buildToken(new HashMap<>(), subject, accessExp);
    }

    public String generate(final Map<String, Object> extraClaims, final String subject) {
        return buildToken(extraClaims, subject, accessExp);
    }

    public String generateRefreshToken(final String subject) {
        return buildToken(new HashMap<>(), subject, refreshExp);
    }

    private String buildToken(final Map<String, Object> extraClaims, final String subject, long exp) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + exp))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String extract(final String value) {
        if (StringUtils.hasText(value) && value.startsWith(authScheme)) {
            return value.substring(authScheme.length());
        }
        return null;
    }

    public String parse(final String token) {
        try {
            return jwtParser.parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException ex) {
            throw new ExpiredTokenException();
        } catch (Exception ex) {
            throw new InvalidTokenException();
        }
    }

    public boolean isExpiredSoon(final String token) {
        try {
            long issuedAt = jwtParser.parseClaimsJws(token).getBody().getIssuedAt().getTime();
            return issuedAt >= System.currentTimeMillis() - ONE_WEEK_IN_MS;
        } catch (ExpiredJwtException ex) {
            throw new ExpiredTokenException();
        } catch (Exception ex) {
            throw new InvalidTokenException();
        }
    }
}
