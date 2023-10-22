package com.lighthouse.lingoswap.question.application;

import com.lighthouse.lingoswap.category.domain.model.Category;
import com.lighthouse.lingoswap.category.domain.repository.CategoryRepository;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.dto.SliceDto;
import com.lighthouse.lingoswap.infra.service.CloudFrontService;
import com.lighthouse.lingoswap.likemember.domian.model.LikeMember;
import com.lighthouse.lingoswap.likemember.domian.repository.LikeMemberRepository;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import com.lighthouse.lingoswap.question.domain.model.Question;
import com.lighthouse.lingoswap.question.domain.repository.QuestionQueryRepository;
import com.lighthouse.lingoswap.question.domain.repository.QuestionRepository;
import com.lighthouse.lingoswap.question.dto.*;
import com.lighthouse.lingoswap.question.exception.LikeMemberNotFoundException;
import com.lighthouse.lingoswap.question.exception.QuestionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class QuestionManager {

    private final QuestionRepository questionRepository;
    private final QuestionQueryRepository questionQueryRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final LikeMemberRepository likeMemberRepository;
    private final CloudFrontService cloudFrontService;

    public void create(final QuestionCreateRequest questionCreateRequest) {
        Member member = memberRepository.getByUuid(questionCreateRequest.uuid());
        Category category = categoryRepository.getByCategoryId(questionCreateRequest.categoryId());
        Question question = Question.builder()
                .member(member)
                .category(category)
                .contents(questionCreateRequest.content())
                .build();
        questionRepository.save(question);
    }

    public ResponseDto<QuestionListResponse> read(final String memberUuid, final Long categoryId, final Long nextId, final int pageSize) {
        SliceDto<Question> questions = questionQueryRepository.findQuestionsByCategoryId(categoryId, nextId, pageSize);
        Member member = memberRepository.getByUuid(memberUuid);
        List<LikeMember> likeMembers = likeMemberRepository.findAllByMember(member);

        List<Question> likedQuestions = likeMembers.stream().map(LikeMember::getQuestion).toList();
        List<QuestionDetail> results = questions.content().stream().map(q ->
                new QuestionDetail(q.getId(), q.getContents(), q.getLike(),
                        q.getCreatedMemberUuid(), q.getCreatedMemberName(), q.getCreatedMemberRegion(), cloudFrontService.addEndpoint(q.getCreatedMemberProfileImageUrl()),
                        likedQuestions.contains(q))).toList();
        return ResponseDto.success(new QuestionListResponse(questions.nextId(), results));
    }

    public void updateLike(final String memberUuid, final Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new QuestionNotFoundException(questionId));
        Member member = memberRepository.getByUuid(memberUuid);

        LikeMember likeMember = LikeMember.of(member, question);
        likeMemberRepository.save(likeMember);

        question.addOneLike();
        questionRepository.save(question);
    }

    public void deleteLike(final String memberUuid, final Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));
        Member member = memberRepository.getByUuid(memberUuid);
        LikeMember likeMember = likeMemberRepository.findByMemberAndQuestion(member, question)
                .orElseThrow(() -> new LikeMemberNotFoundException(member.getUsername(), question.getId()));
        likeMemberRepository.delete(likeMember);

        question.subtractOneLike();
        questionRepository.save(question);
    }

    public ResponseDto<QuestionRecommendationListResponse> readRecommendation(final Long categoryId, final Long nextId, final int pageSize) {
        SliceDto<Question> questionRecommendations = questionQueryRepository.findRecommendedQuestionsByCategoryId(categoryId, nextId, pageSize);
        List<String> results = questionRecommendations.content().stream().map(Question::getContents).toList();
        return ResponseDto.success(new QuestionRecommendationListResponse(questionRecommendations.nextId(), results));
    }

    public ResponseDto<MyQuestionsResponse> readByCreatedMember(final String memberUuid) {
        Member member = memberRepository.getByUuid(memberUuid);
        return ResponseDto.success(new MyQuestionsResponse(questionRepository.findByCreatedMember(member).stream().map(MyQuestionDetail::from).toList()));
    }

}
