package com.lighthouse.lingoswap.common.fixture;

import com.lighthouse.lingoswap.auth.domain.model.TokenPair;

import static com.lighthouse.lingoswap.common.fixture.MemberFixture.USER_UUID;

public class TokenPairFixture {

    public static final String ACCESS_TOKEN = "abcd";
    public static final String REFRESH_TOKEN = "abcd";
    public static final String ID_TOKEN = "abcd";

    public static TokenPair tokenPair() {
        return TokenPair.builder()
                .username(USER_UUID)
                .accessToken(ACCESS_TOKEN)
                .refreshToken(REFRESH_TOKEN)
                .build();
    }

}
