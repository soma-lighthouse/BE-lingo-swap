package com.lighthouse.lingoswap.question.domain.model;

import com.lighthouse.lingoswap.question.exception.LikeRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.lighthouse.lingoswap.common.fixture.CategoryFixture.category;
import static com.lighthouse.lingoswap.common.fixture.MemberFixture.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QuestionTest {

    @DisplayName("질문의 좋아요 수를 1 증가시키면 좋아요 수가 1 증가한다.")
    @Test
    void addOneLike() {
        // given
        Question question = Question.builder()
                .member(user())
                .like(0L)
                .category(category())
                .contents("Hi")
                .build();

        // when
        question.addOneLike();

        // then
        assertThat(question.getLike()).isEqualTo(1L);
    }

    @DisplayName("질문의 좋아요 수를 1 감소시키면 좋아요 수가 1 감소한다.")
    @Test
    void subtractOneLike() {
        // given
        Question question = Question.builder()
                .member(user())
                .like(0L)
                .category(category())
                .contents("Hi")
                .build();
        question.addOneLike();
        question.addOneLike();

        // when
        question.subtractOneLike();

        // then
        assertThat(question.getLike()).isEqualTo(1L);
    }

    @DisplayName("질문의 좋아요 수가 0일 때 1 감소시키면 예외가 발생한다.")
    @Test
    void subtractOneLikeWhenIsZero() {
        // given
        Question question = Question.builder()
                .member(user())
                .like(0L)
                .category(category())
                .contents("Hi")
                .build();

        // when & then
        assertThatThrownBy(question::subtractOneLike).isInstanceOf(LikeRangeException.class);
    }

}
