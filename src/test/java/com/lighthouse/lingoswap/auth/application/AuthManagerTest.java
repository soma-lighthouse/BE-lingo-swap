package com.lighthouse.lingoswap.auth.application;

import com.lighthouse.lingoswap.IntegrationTestSupport;
import com.lighthouse.lingoswap.auth.dto.LoginResponse;
import com.lighthouse.lingoswap.auth.dto.MemberCreateRequest;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.lighthouse.lingoswap.common.fixture.CountryType.KOREA;
import static com.lighthouse.lingoswap.common.fixture.CountryType.US;
import static com.lighthouse.lingoswap.common.fixture.InterestsType.*;
import static com.lighthouse.lingoswap.common.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class AuthManagerTest extends IntegrationTestSupport {

    @Autowired
    private AuthManager authManager;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("idToken과 가입 정보로 회원 가입할 수 있다.")
    @Test
    void signup() {
        // given
        MemberCreateRequest request = MemberCreateRequest.builder()
                .profileImageUri(USER_PROFILE_IMAGE_URI)
                .birthday(USER_BIRTHDAY)
                .name(USER_NAME)
                .gender(USER_GENDER)
                .description(USER_DESCRIPTION)
                .region(USER_REGION)
                .preferredCountries(List.of(KOREA.getCode(), US.getCode()))
                .preferredInterests(List.of(JAPANESE_FOOD.getName(), KOREAN_FOOD.getName(), SPORTS_GAME.getName()))
                .build();

        // when
        LoginResponse response = authManager.signup(request);
        Member actual = memberRepository.getByUuid(response.uuid());

        // then
        assertThat(actual.getUuid()).isEqualTo(USER_UUID);
    }

}
