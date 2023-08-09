package com.lighthouse.lingoswap.board.service;

import com.lighthouse.lingoswap.board.dto.BoardCreateRequest;
import com.lighthouse.lingoswap.board.dto.BoardCreateResponse;
import com.lighthouse.lingoswap.board.dto.BoardResponse;
import com.lighthouse.lingoswap.board.dto.BoardUpdateLikeRequest;
import com.lighthouse.lingoswap.board.entity.LikeMember;
import com.lighthouse.lingoswap.board.entity.Question;
import com.lighthouse.lingoswap.member.entity.Category;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.service.CategoryService;
import com.lighthouse.lingoswap.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

        Member member = memberService.findById(boardCreateRequest.getMemberId());

        Category category = categoryService.findById(boardCreateRequest.getCategoryId());

        Question question = Question.of(member, category, boardCreateRequest.getContent());
        boardService.save(question);
    }

    @Transactional
    public void updateLike(BoardUpdateLikeRequest boardUpdateLikeRequest, Long questionId) {
        Member member = memberService.findById(boardUpdateLikeRequest.getMemberId());
        Question question = boardService.findById(questionId);

        question.addOneLike();
        boardService.save(question);

        LikeMember likeMember = LikeMember.of(member, question);
        likeMemberService.save(likeMember);
    }

    public BoardCreateResponse read(Long categoryId, Pageable pageable) {
        Category category = categoryService.findById(categoryId);
        Slice<Question> slice = boardService.findQuestionsByCategory(category, pageable);
        List<BoardResponse> results = new ArrayList<>();
        List<Question> list = slice.getContent();
        for (Question question : list) {
            BoardResponse boardResponse = BoardResponse.of(question, question.getCreatedMember());
            results.add(boardResponse);
        }
        return new BoardCreateResponse(results);
    }
}
