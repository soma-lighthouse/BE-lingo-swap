package com.lighthouse.lingoswap.question.service;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.dto.SliceDto;
import com.lighthouse.lingoswap.infra.service.DistributionService;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.service.MemberService;
import com.lighthouse.lingoswap.question.dto.QuestionCreateRequest;
import com.lighthouse.lingoswap.question.dto.QuestionDetail;
import com.lighthouse.lingoswap.question.dto.QuestionListResponse;
import com.lighthouse.lingoswap.question.dto.QuestionRecommendationListResponse;
import com.lighthouse.lingoswap.question.entity.Category;
import com.lighthouse.lingoswap.question.entity.LikeMember;
import com.lighthouse.lingoswap.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class QuestionManager {

    private final MemberService memberService;
    private final CategoryService categoryService;
    private final QuestionService questionService;
    private final LikeMemberService likeMemberService;
    private final DistributionService distributionService;

    public ResponseDto<Object> create(final QuestionCreateRequest questionCreateRequest) {
        Member member = memberService.findByUuid(questionCreateRequest.uuid());
        Category category = categoryService.findById(questionCreateRequest.categoryId());
        Question question = Question.of(member, category, questionCreateRequest.content());
        questionService.save(question);
        return ResponseDto.success(null);
    }

    public ResponseDto<QuestionListResponse> read(final String uuid, final Long categoryId, final Long nextId, final int pageSize) {
        SliceDto<Question> questions = questionService.search(categoryId, nextId, pageSize);
        Member member = memberService.findByUuid(uuid);
        List<LikeMember> likeMembers = likeMemberService.findAllByMember(member);

        List<Question> likedQuestions = likeMembers.stream().map(LikeMember::getQuestion).toList();
        List<QuestionDetail> results = questions.content().stream().map(q -> QuestionDetail.of(q, q.getCreatedMember(), distributionService.generateUri(q.getCreatedMember().getProfileImageUri()), likedQuestions.contains(q))).toList();
        return ResponseDto.success(new QuestionListResponse(questions.nextId(), results));
    }

    @Transactional
    public ResponseDto<Object> updateLike(final String uuid, final Long questionId) {
        Question question = questionService.findById(questionId);
        Member member = memberService.findByUuid(uuid);

        LikeMember likeMember = LikeMember.of(member, question);
        likeMemberService.save(likeMember);

        question.addOneLike();
        questionService.save(question);
        return ResponseDto.success(null);
    }

    @Transactional
    public ResponseDto<Object> deleteLike(final String uuid, final Long questionId) {
        Question question = questionService.findById(questionId);
        Member member = memberService.findByUuid(uuid);
        LikeMember likeMember = likeMemberService.findByMemberAndQuestion(member, question);
        likeMemberService.delete(likeMember);

        question.subtractOneLike();
        questionService.save(question);
        return ResponseDto.success(null);
    }

    public ResponseDto<QuestionRecommendationListResponse> readRecommendation(final Long categoryId, final Long nextId, final int pageSize) {
        SliceDto<Question> questionRecommendations = questionService.searchRecommendation(categoryId, nextId, pageSize);

        List<String> results = new ArrayList<>();
        questionRecommendations.content().forEach(q -> results.add(q.getContents()));
        return ResponseDto.success(new QuestionRecommendationListResponse(questionRecommendations.nextId(), results));
    }
}
