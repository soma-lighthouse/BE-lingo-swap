package com.lighthouse.lingoswap.question.application;

import com.lighthouse.lingoswap.category.domain.model.Category;
import com.lighthouse.lingoswap.category.domain.repository.CategoryRepository;
import com.lighthouse.lingoswap.common.dto.SliceDto;
import com.lighthouse.lingoswap.likemember.domian.repository.LikeMemberRepository;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import com.lighthouse.lingoswap.question.domain.model.Question;
import com.lighthouse.lingoswap.question.domain.repository.QuestionQueryRepository;
import com.lighthouse.lingoswap.question.domain.repository.QuestionRepository;
import com.lighthouse.lingoswap.question.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class QuestionManager {

    private final QuestionRepository questionRepository;
    private final QuestionQueryRepository questionQueryRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final LikeMemberRepository likeMemberRepository;

    @Transactional
    public void create(final QuestionCreateRequest questionCreateRequest) {
        Member member = memberRepository.getByUuid(questionCreateRequest.uuid());
        Category category = categoryRepository.getByCategoryId(questionCreateRequest.categoryId());
        Question question = Question.builder()
                .member(member)
                .category(category)
                .like(0L)
                .contents(questionCreateRequest.content())
                .build();
        questionRepository.save(question);
    }

    public QuestionListResponse read(final String memberUuid, final Long categoryId, final Long nextId, final int pageSize) {
        SliceDto<Question> questions = questionQueryRepository.findQuestionsByCategoryId(categoryId, nextId, pageSize);
        Member member = memberRepository.getByUuid(memberUuid);
        List<Question> likeMembers = likeMemberRepository.findAllQuestionsByMember(member);

        List<QuestionDetail> results = questions.content().stream().map(q ->
                new QuestionDetail(q.getId(), q.getContents(), q.getLike(),
                        q.getCreatedMemberUuid(), q.getCreatedMemberName(), q.getCreatedMemberRegion(), q.getCreatedMemberProfileImageUrl(),
                        likeMembers.contains(q))).toList();
        return new QuestionListResponse(questions.nextId(), results);
    }

    public QuestionRecommendationListResponse readRecommendation(final Long categoryId, final Long nextId, final int pageSize) {
        SliceDto<Question> questionRecommendations = questionQueryRepository.findRecommendedQuestionsByCategoryId(categoryId, nextId, pageSize);
        List<String> results = questionRecommendations.content().stream().map(Question::getContents).toList();
        return new QuestionRecommendationListResponse(questionRecommendations.nextId(), results);
    }

    public MyQuestionsResponse readByCreatedMember(final String memberUuid) {
        Member member = memberRepository.getByUuid(memberUuid);
        return new MyQuestionsResponse(questionRepository.findByCreatedMember(member).stream().map(MyQuestionDetail::from).toList());
    }

}
