package com.lighthouse.lingoswap.board.service;

import com.lighthouse.lingoswap.board.dto.BoardCreateRequest;
import com.lighthouse.lingoswap.board.dto.BoardCreateResponse;
import com.lighthouse.lingoswap.board.dto.BoardResponse;
import com.lighthouse.lingoswap.board.dto.BoardUpdateLikeRequest;
import com.lighthouse.lingoswap.board.entity.Category;
import com.lighthouse.lingoswap.board.entity.LikeMember;
import com.lighthouse.lingoswap.board.entity.Question;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardManager {

    private final MemberService memberService;
    private final CategoryService categoryService;
    private final BoardService boardService;
    private final LikeMemberService likeMemberService;

    public void create(BoardCreateRequest boardCreateRequest) {
        Member member = memberService.findById(boardCreateRequest.userId());
        Category category = categoryService.findById(boardCreateRequest.categoryId());
        Question question = Question.of(member, category, boardCreateRequest.content());
        boardService.save(question);
    }

    @Transactional
    public void updateLike(Long questionId, BoardUpdateLikeRequest boardUpdateLikeRequest) {
        Question question = boardService.findById(questionId);
        Member member = memberService.findById(boardUpdateLikeRequest.userId());

        LikeMember likeMember = LikeMember.of(member, question);
        likeMemberService.save(likeMember);

        question.addOneLike();
        boardService.save(question);
    }

    public BoardCreateResponse read(Long userId, Long categoryId, Pageable pageable) {
        List<Question> questions = boardService.findQuestionsByCategory(categoryId, pageable).getContent();
        List<LikeMember> likeMembers = likeMemberService.findAllByMemberId(userId);

        List<Question> likedQuestions = likeMembers.stream().map(LikeMember::getQuestion).toList();
        List<BoardResponse> results = questions.stream().map(q -> BoardResponse.of(q, q.getCreatedMember(), likedQuestions.contains(q))).toList();
        return new BoardCreateResponse(results);
    }
}
