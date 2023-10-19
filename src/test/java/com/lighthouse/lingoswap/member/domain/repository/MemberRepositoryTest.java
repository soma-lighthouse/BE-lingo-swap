package com.lighthouse.lingoswap.member.domain.repository;

import com.lighthouse.lingoswap.common.support.IntegrationTestSupport;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.exception.MemberNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static com.lighthouse.lingoswap.member.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
class MemberRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("UUID로 유저를 조회한다.")
    @Test
    void getByUuid() {
        // given
        Member member = memberRepository.save(user());

        // when
        Member actual = memberRepository.getByUuid(USER_UUID);

        // then
        assertThat(actual.getId()).isEqualTo(member.getId());
    }

    @DisplayName("UUID로 유저를 조회 시 존재하지 않으면 예외가 발생한다.")
    @Test
    void failedToGetByUuid() {
        // given & when & then
        assertThatThrownBy(() -> memberRepository.getByUuid("1"))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("아이디로 유저를 조회한다.")
    @Test
    void getByUsername() {
        // given
        Member member = memberRepository.save(user());

        // when
        Member actual = memberRepository.getByUsername(USER_USERNAME);

        // then
        assertThat(actual.getId()).isEqualTo(member.getId());
    }

    @DisplayName("아이디로 유저를 조회 시 존재하지 않으면 예외가 발생한다.")
    @Test
    void failedToGetByUsername() {
        String username = "test";

        assertThatThrownBy(() -> memberRepository.getByUsername(username))
                .isInstanceOf(MemberNotFoundException.class);
    }

}
