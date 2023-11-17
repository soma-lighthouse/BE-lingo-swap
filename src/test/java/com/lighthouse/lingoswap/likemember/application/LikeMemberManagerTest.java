package com.lighthouse.lingoswap.likemember.application;

import com.lighthouse.lingoswap.IntegrationTestSupport;
import com.lighthouse.lingoswap.category.domain.model.Category;
import com.lighthouse.lingoswap.category.domain.repository.CategoryRepository;
import com.lighthouse.lingoswap.common.fixture.CategoryType;
import com.lighthouse.lingoswap.likemember.domian.model.LikeMember;
import com.lighthouse.lingoswap.likemember.domian.repository.LikeMemberRepository;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import com.lighthouse.lingoswap.question.domain.model.Question;
import com.lighthouse.lingoswap.question.domain.repository.QuestionRepository;
import com.lighthouse.lingoswap.question.exception.DuplicateLikeMemberException;
import com.lighthouse.lingoswap.question.exception.LikeMemberNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static com.lighthouse.lingoswap.common.fixture.MemberFixture.USER_UUID;
import static com.lighthouse.lingoswap.common.fixture.MemberFixture.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Transactional
class LikeMemberManagerTest extends IntegrationTestSupport {

    @Autowired
    LikeMemberManager likeMemberManager;

    @Autowired
    LikeMemberRepository likeMemberRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @DisplayName("질문에 좋아요를 누르면 좋아요 목록에 유저와 질문이 추가된다.")
    @Test
    void createLikeMember() {
        // given
        Member member = memberRepository.save(user());
        Category category = categoryRepository.getByName(CategoryType.FOOD.getName());
        Question question = questionRepository.save(Question.builder()
                .member(member)
                .like(0L)
                .category(category)
                .contents("Hi")
                .build());

        // when
        likeMemberManager.createLikeMember(USER_UUID, question.getId());

        // then
        LikeMember actual = likeMemberRepository.getByMemberAndQuestion(member, question);
        assertThat(actual.getMemberId()).isEqualTo(member.getId());
        assertThat(actual.getQuestionId()).isEqualTo(question.getId());
    }

    @DisplayName("질문에 이미 좋아요를 눌렀던 유저의 UUID로 목록에 추가하면 예외가 발생한다.")
    @Test
    void createLikeMemberAlreadyLiked() {
        // given
        Member member = memberRepository.save(user());
        Category category = categoryRepository.getByName(CategoryType.FOOD.getName());
        Question question = questionRepository.save(Question.builder()
                .member(member)
                .like(0L)
                .category(category)
                .contents("Hi")
                .build());
        LikeMember likeMember = LikeMember.builder()
                .member(member)
                .question(question)
                .build();
        likeMemberRepository.save(likeMember);

        // when & then
        assertThatThrownBy(() -> likeMemberManager.createLikeMember(USER_UUID, question.getId()))
                .isInstanceOf(DuplicateLikeMemberException.class);
    }

    @DisplayName("질문에 좋아요를 눌렀던 유저의 UUID로 목록에서 삭제한다.")
    @Test
    void deleteLikeMember() {
        // given
        Member member = memberRepository.save(user());
        Category category = categoryRepository.getByName(CategoryType.FOOD.getName());
        Question question = questionRepository.save(Question.builder()
                .member(member)
                .like(0L)
                .category(category)
                .contents("Hi")
                .build());
        likeMemberManager.createLikeMember(USER_UUID, question.getId());

        // when

        // then
        LikeMember actual = likeMemberRepository.getByMemberAndQuestion(member, question);
        assertThat(actual.getMemberId()).isEqualTo(member.getId());
        assertThat(actual.getQuestionId()).isEqualTo(question.getId());
    }

    @DisplayName("질문에 좋아요를 누른 적 없는 유저의 UUID로 목록에서 삭제하면 예외가 발생한다.")
    @Test
    void deleteLikeMemberNotLiked() {
        // given
        Member member = memberRepository.save(user());
        Category category = categoryRepository.getByName(CategoryType.FOOD.getName());
        Question question = questionRepository.save(Question.builder()
                .member(member)
                .like(0L)
                .category(category)
                .contents("Hi")
                .build());

        // when & then
        assertThatThrownBy(() -> likeMemberManager.deleteLikeMember(USER_UUID, question.getId()))
                .isInstanceOf(LikeMemberNotFoundException.class);
    }

}
