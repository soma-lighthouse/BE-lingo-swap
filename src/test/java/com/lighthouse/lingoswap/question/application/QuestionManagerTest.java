package com.lighthouse.lingoswap.question.application;

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
import com.lighthouse.lingoswap.question.dto.MyQuestionsResponse;
import com.lighthouse.lingoswap.question.dto.QuestionCreateRequest;
import com.lighthouse.lingoswap.question.dto.QuestionListResponse;
import com.lighthouse.lingoswap.question.dto.QuestionRecommendationListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.lighthouse.lingoswap.common.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@Transactional
class QuestionManagerTest extends IntegrationTestSupport {

    @Autowired
    QuestionManager questionManager;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    LikeMemberRepository likeMemberRepository;

    @DisplayName("질문을 생성한다.")
    @Test
    void create() {
        // given
        Member member = memberRepository.save(user());
        Category category = categoryRepository.getByName(CategoryType.FOOD.getName());
        String contents = "Hi";
        Question question = Question.builder()
                .member(member)
                .category(category)
                .like(0L)
                .contents(contents)
                .build();

        // when
        questionManager.create(QuestionCreateRequest.of(USER_UUID, category.getId(), contents));

        // then
        Question actual = questionRepository.findByCreatedMember(member).get(0);
        assertThat(actual.getContents()).isEqualTo(question.getContents());
    }

    @DisplayName("카테고리로 질문 리스트를 조회하면 최신순으로 출력한다.")
    @Test
    void read() {
        // given
        Member member = memberRepository.save(user());
        Category category = categoryRepository.getByName(CategoryType.FOOD.getName());
        Question question1 = Question.builder()
                .member(member)
                .like(0L)
                .category(category)
                .contents("Hi")
                .build();
        Question question2 = Question.builder()
                .member(member)
                .like(0L)
                .category(category)
                .contents("Hello")
                .build();
        Question question3 = Question.builder()
                .member(member)
                .like(0L)
                .category(category)
                .contents("Good")
                .build();
        questionRepository.saveAll(List.of(question1, question2, question3));

        // when
        QuestionListResponse actual = questionManager.read(USER_UUID, category.getId(), null, 10);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.nextId()).isEqualTo(-1L);
            softly.assertThat(actual.questions()).hasSize(3);
            softly.assertThat(actual.questions())
                    .extracting("questionId", "contents", "likes", "uuid", "name", "region", "profileImageUri", "liked")
                    .containsExactly(
                            tuple(question3.getId(), "Good", 0L, USER_UUID, USER_NAME, USER_REGION, USER_PROFILE_ENDPOINT, false),
                            tuple(question2.getId(), "Hello", 0L, USER_UUID, USER_NAME, USER_REGION, USER_PROFILE_ENDPOINT, false),
                            tuple(question1.getId(), "Hi", 0L, USER_UUID, USER_NAME, USER_REGION, USER_PROFILE_ENDPOINT, false)
                    );
        });
    }

    @DisplayName("다음 질문의 id로 질문 리스트를 조회하면 해당 질문 다음부터 최신순으로 출력한다.")
    @Test
    void readNextPage() {
        // given
        Member member = memberRepository.save(user());
        Category category = categoryRepository.getByName(CategoryType.FOOD.getName());
        Question question1 = Question.builder()
                .member(member)
                .like(0L)
                .category(category)
                .contents("Hi")
                .build();
        Question question2 = Question.builder()
                .member(member)
                .like(0L)
                .category(category)
                .contents("Hello")
                .build();
        Question question3 = Question.builder()
                .member(member)
                .like(0L)
                .category(category)
                .contents("Good")
                .build();
        questionRepository.saveAll(List.of(question1, question2, question3));

        // when
        QuestionListResponse actual = questionManager.read(USER_UUID, category.getId(), question2.getId(), 2);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.nextId()).isEqualTo(-1L);
            softly.assertThat(actual.questions()).hasSize(1);
            softly.assertThat(actual.questions())
                    .extracting("questionId", "contents", "likes", "uuid", "name", "region", "profileImageUri", "liked")
                    .containsExactly(
                            tuple(question1.getId(), "Hi", 0L, USER_UUID, USER_NAME, USER_REGION, USER_PROFILE_ENDPOINT, false)
                    );
        });
    }

    @DisplayName("페이지 사이즈로 질문 리스트를 조회하면 페이지 사이즈만큼 최신순으로 출력한다.")
    @Test
    void readByPage() {
        // given
        Member member = memberRepository.save(user());
        Category category = categoryRepository.getByName(CategoryType.FOOD.getName());
        Question question1 = Question.builder()
                .member(member)
                .like(0L)
                .category(category)
                .contents("Hi")
                .build();
        Question question2 = Question.builder()
                .member(member)
                .like(0L)
                .category(category)
                .contents("Hello")
                .build();
        Question question3 = Question.builder()
                .member(member)
                .like(0L)
                .category(category)
                .contents("Good")
                .build();
        questionRepository.saveAll(List.of(question1, question2, question3));

        // when
        QuestionListResponse actual = questionManager.read(USER_UUID, category.getId(), null, 2);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.nextId()).isEqualTo(question2.getId());
            softly.assertThat(actual.questions()).hasSize(2);
            softly.assertThat(actual.questions())
                    .extracting("questionId", "contents", "likes", "uuid", "name", "region", "profileImageUri", "liked")
                    .containsExactly(
                            tuple(question3.getId(), "Good", 0L, USER_UUID, USER_NAME, USER_REGION, USER_PROFILE_ENDPOINT, false),
                            tuple(question2.getId(), "Hello", 0L, USER_UUID, USER_NAME, USER_REGION, USER_PROFILE_ENDPOINT, false)
                    );
        });
    }

    @DisplayName("질문 리스트 조회 시 좋아요를 누른 항목이 표시된다.")
    @Test
    void readLiked() {
        // given
        Member member = memberRepository.save(user());
        Category category = categoryRepository.getByName(CategoryType.FOOD.getName());
        Question question1 = Question.builder()
                .member(member)
                .like(0L)
                .category(category)
                .contents("Hi")
                .build();
        Question question2 = Question.builder()
                .member(member)
                .like(0L)
                .category(category)
                .contents("Hello")
                .build();
        Question question3 = Question.builder()
                .member(member)
                .like(0L)
                .category(category)
                .contents("Good")
                .build();
        questionRepository.saveAll(List.of(question1, question2, question3));

        LikeMember likeMember = LikeMember.builder()
                .member(member)
                .question(question3)
                .build();
        likeMemberRepository.save(likeMember);

        question3.addOneLike();

        // when
        QuestionListResponse actual = questionManager.read(USER_UUID, category.getId(), null, 10);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.nextId()).isEqualTo(-1L);
            softly.assertThat(actual.questions()).hasSize(3);
            softly.assertThat(actual.questions())
                    .extracting("questionId", "contents", "likes", "uuid", "name", "region", "profileImageUri", "liked")
                    .containsExactly(
                            tuple(question3.getId(), "Good", 1L, USER_UUID, USER_NAME, USER_REGION, USER_PROFILE_ENDPOINT, true),
                            tuple(question2.getId(), "Hello", 0L, USER_UUID, USER_NAME, USER_REGION, USER_PROFILE_ENDPOINT, false),
                            tuple(question1.getId(), "Hi", 0L, USER_UUID, USER_NAME, USER_REGION, USER_PROFILE_ENDPOINT, false)
                    );
        });
    }

    @DisplayName("카테고리로 좋아요 수가 50 이상인 질문 리스트를 조회하면 최신순으로 출력한다.")
    @Test
    void readRecommendation() {
        // given
        Member member = memberRepository.save(user());
        Category category = categoryRepository.getByName(CategoryType.FOOD.getName());
        Question question1 = Question.builder()
                .member(member)
                .like(49L)
                .category(category)
                .contents("Hi")
                .build();
        Question question2 = Question.builder()
                .member(member)
                .like(50L)
                .category(category)
                .contents("Hello")
                .build();
        Question question3 = Question.builder()
                .member(member)
                .like(51L)
                .category(category)
                .contents("Good")
                .build();
        questionRepository.saveAll(List.of(question1, question2, question3));

        // when
        QuestionRecommendationListResponse actual = questionManager.readRecommendation(category.getId(), null, 10);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.nextId()).isEqualTo(-1L);
            softly.assertThat(actual.questions()).hasSize(2);
            softly.assertThat(actual.questions())
                    .containsExactly("Good", "Hello");
        });
    }

    @DisplayName("다음 질문의 id로 좋아요 수가 50 이상인 질문 리스트를 조회하면 해당 질문 다음부터 최신순으로 출력한다.")
    @Test
    void readRecommendationNextPage() {
        // given
        Member member = memberRepository.save(user());
        Category category = categoryRepository.getByName(CategoryType.FOOD.getName());
        Question question1 = Question.builder()
                .member(member)
                .like(50L)
                .category(category)
                .contents("Hi")
                .build();
        Question question2 = Question.builder()
                .member(member)
                .like(51L)
                .category(category)
                .contents("Hello")
                .build();
        Question question3 = Question.builder()
                .member(member)
                .like(52L)
                .category(category)
                .contents("Good")
                .build();
        questionRepository.saveAll(List.of(question1, question2, question3));

        // when
        QuestionRecommendationListResponse actual = questionManager.readRecommendation(category.getId(), question3.getId(), 2);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.nextId()).isEqualTo(-1L);
            softly.assertThat(actual.questions()).hasSize(2);
            softly.assertThat(actual.questions())
                    .containsExactly("Hello", "Hi");
        });
    }

    @DisplayName("페이지 사이즈로 좋아요 수가 50 이상인 질문 리스트를 조회하면 페이지 사이즈만큼 최신순으로 출력한다.")
    @Test
    void readRecommendationByPage() {
        // given
        Member member = memberRepository.save(user());
        Category category = categoryRepository.getByName(CategoryType.FOOD.getName());
        Question question1 = Question.builder()
                .member(member)
                .like(50L)
                .category(category)
                .contents("Hi")
                .build();
        Question question2 = Question.builder()
                .member(member)
                .like(51L)
                .category(category)
                .contents("Hello")
                .build();
        Question question3 = Question.builder()
                .member(member)
                .like(52L)
                .category(category)
                .contents("Good")
                .build();
        questionRepository.saveAll(List.of(question1, question2, question3));

        // when
        QuestionRecommendationListResponse actual = questionManager.readRecommendation(category.getId(), null, 3);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.nextId()).isEqualTo(-1L);
            softly.assertThat(actual.questions()).hasSize(3);
            softly.assertThat(actual.questions())
                    .containsExactly("Good", "Hello", "Hi");
        });
    }

    @DisplayName("유저의 UUID로 해당 유저가 작성한 질문 리스트를 최신순으로 조회한다.")
    @Test
    void readByCreatedMember() {
        // given
        Member member = memberRepository.save(user());
        Category category = categoryRepository.getByName(CategoryType.FOOD.getName());
        Question question1 = Question.builder()
                .member(member)
                .like(0L)
                .category(category)
                .contents("Hi")
                .build();
        Question question2 = Question.builder()
                .member(member)
                .like(0L)
                .category(category)
                .contents("Hello")
                .build();
        questionRepository.saveAll(List.of(question1, question2));

        // when
        MyQuestionsResponse actual = questionManager.readByCreatedMember(USER_UUID);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.questions()).hasSize(2);
            softly.assertThat(actual.questions())
                    .extracting("questionId", "contents", "likes", "category")
                    .containsExactly(
                            tuple(question2.getId(), "Hello", 0L, category.getId()),
                            tuple(question1.getId(), "Hi", 0L, category.getId())
                    );
        });
    }

}
