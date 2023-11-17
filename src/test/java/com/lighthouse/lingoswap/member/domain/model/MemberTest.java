package com.lighthouse.lingoswap.member.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.lighthouse.lingoswap.common.fixture.MemberFixture.user;
import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @DisplayName("유저의 생일로 나이를 계산할 수 있다.")
    @Test
    void calculateAge() {
        // given
        Member member = user();

        // when
        int age = member.calculateAge(LocalDate.of(2023, 3, 27));

        // then
        assertThat(age).isEqualTo(25);
    }

    @DisplayName("유저의 자기소개를 변경할 수 있다.")
    @Test
    void changeDescription() {
        // given
        Member member = user();

        // when
        String description = "Hello";
        member.changeDescription(description);

        // then
        assertThat(member.getDescription()).isEqualTo(description);
    }

    @DisplayName("유저의 프로필 이미지를 변경할 수 있다.")
    @Test
    void changeProfileImageUri() {
        // given
        Member member = user();

        // when
        String profileImageUri = "/profile.png";
        member.changeProfileImageUri(profileImageUri);

        // then
        assertThat(member.getProfileImageUri()).isEqualTo(profileImageUri);
    }

}
