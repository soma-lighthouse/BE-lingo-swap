package com.lighthouse.lingoswap.question.service;

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

    public void create(QuestionCreateRequest questionCreateRequest) {
        Member member = memberService.findById(questionCreateRequest.userId());
        Category category = categoryService.findById(questionCreateRequest.categoryId());
        Question question = Question.of(member, category, questionCreateRequest.content());
        questionService.save(question);
    }

    public QuestionListResponse read(Long userId, Long categoryId, Long nextId, int pageSize) {
        SliceDto<Question> questions = questionService.search(categoryId, nextId, pageSize);
        List<LikeMember> likeMembers = likeMemberService.findAllByMemberId(userId);

        List<Question> likedQuestions = likeMembers.stream().map(LikeMember::getQuestion).toList();
        List<QuestionDetail> results = questions.content().stream().map(q -> QuestionDetail.of(q, q.getCreatedMember(), likedQuestions.contains(q))).toList();
        return new QuestionListResponse(questions.nextId(), results);
    }

    @Transactional
    public void updateLike(Long questionId, QuestionUpdateLikeRequest questionUpdateLikeRequest) {
        Question question = questionService.findById(questionId);
        Member member = memberService.findById(questionUpdateLikeRequest.userId());

        LikeMember likeMember = LikeMember.of(member, question);
        likeMemberService.save(likeMember);

        question.addOneLike();
        questionService.save(question);
    }

    @Transactional
    public void deleteLike(Long questionId, QuestionDeleteLikeRequest questionDeleteLikeRequest) {
        Question question = questionService.findById(questionId);
        Member member = memberService.findById(questionDeleteLikeRequest.userId());
        likeMemberService.deleteByQuestionAndMember(question, member);

        question.subtractOneLike();
        questionService.save(question);
    }
}
