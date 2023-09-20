package com.lighthouse.lingoswap.question.service;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.dto.SliceDto;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.service.MemberService;
import com.lighthouse.lingoswap.question.dto.*;
import com.lighthouse.lingoswap.question.entity.Category;
import com.lighthouse.lingoswap.question.entity.LikeMember;
import com.lighthouse.lingoswap.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class QuestionManager {

    private final MemberService memberService;
    private final CategoryService categoryService;
    private final QuestionService questionService;
    private final LikeMemberService likeMemberService;

    public ResponseDto<Object> create(final QuestionCreateRequest questionCreateRequest) {
        Member member = memberService.findByUuid(questionCreateRequest.userId());
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
        List<QuestionDetail> results = questions.content().stream().map(q -> QuestionDetail.of(q, q.getCreatedMember(), likedQuestions.contains(q))).toList();
        return ResponseDto.success(new QuestionListResponse(questions.nextId(), results));
    }

    @Transactional
    public ResponseDto<Object> updateLike(final Long questionId, final QuestionUpdateLikeRequest questionUpdateLikeRequest) {
        Question question = questionService.findById(questionId);
        Member member = memberService.findByUuid(questionUpdateLikeRequest.userId());

        LikeMember likeMember = LikeMember.of(member, question);
        likeMemberService.save(likeMember);

        question.addOneLike();
        questionService.save(question);
        return ResponseDto.success(null);
    }

    @Transactional
    public ResponseDto<Object> deleteLike(final Long questionId, final QuestionDeleteLikeRequest questionDeleteLikeRequest) {
        Question question = questionService.findById(questionId);
        Member member = memberService.findByUuid(questionDeleteLikeRequest.userId());
        likeMemberService.deleteByQuestionAndMember(question, member);

        question.subtractOneLike();
        questionService.save(question);
        return ResponseDto.success(null);
    }
}
