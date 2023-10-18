package com.lighthouse.lingoswap.member.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @DisplayName("유저의 생일로 나이를 계산할 수 있다.")
    @Test
    void calculateAge() {
        LocalDate birthDay = LocalDate.of(1997, 3, 28);
        Member member = Member.builder()
                .birthday(birthDay)
                .build();

        int age = member.calculateAge(LocalDate.of(2023, 3, 27));

        assertThat(age).isEqualTo(25);
    }

    @DisplayName("유저의 자기소개를 변경할 수 있다.")
    @Test
    void changeDescription() {
        String description = "Hello";
        Member member = Member.builder()
                .description(description)
                .build();

        member.changeDescription(description);

        assertThat(member.getDescription()).isEqualTo(description);
    }

    @DisplayName("유저의 프로필 이미지를 변경할 수 있다.")
    @Test
    void changeProfileImageUri() {
        String profileImageUrl = "/profile.png";
        Member member = Member.builder()
                .profileImageUrl(profileImageUrl)
                .build();

        member.changeProfileImageUrl(profileImageUrl);

        assertThat(member.getProfileImageUrl()).isEqualTo(profileImageUrl);
    }

}
