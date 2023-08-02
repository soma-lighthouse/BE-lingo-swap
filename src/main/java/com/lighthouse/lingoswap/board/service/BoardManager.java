package com.lighthouse.lingoswap.board.service;

import com.lighthouse.lingoswap.board.dto.BoardCreateRequest;
import com.lighthouse.lingoswap.board.dto.BoardLikeResponse;
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

@Service
@RequiredArgsConstructor
@Transactional
public class BoardManager {

    private final MemberService memberService;
    private final CategoryService categoryService;
    private final BoardService boardService;
    private final LikeMemberService likeMemberService; // 이걸 주입안하면 아예 서비스 쓸 수가 없음/ 왜인지 알아보자

    public Long create(BoardCreateRequest boardCreateRequest) {

    Member member = memberService.findById(boardCreateRequest.getMemberId());
    Category category = categoryService.findById(boardCreateRequest.getCategoryId());
    Question question = Question.builder()
            .createdMember(member)
            .category(category)
            .contents(boardCreateRequest.getContent())
            .build();
    boardService.save(question);
    return question.getId();
    }


    public BoardResponse readDetail(Long questionId) {
        Question question;
        question = boardService.findById(questionId);
        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setContent(question.getContents());
        boardResponse.setMemberId(question.getCreatedMember().getId());
        boardResponse.setCategoryId(question.getCategory().getId());
        boardResponse.setQuestionId(question.getId());
        return  boardResponse;
    }

    public BoardLikeResponse updateLike(BoardUpdateLikeRequest boardUpdateLikeRequest, Long questionId) {

        Member member = memberService.findById(boardUpdateLikeRequest.getMember_id());
        Question question = boardService.findById(questionId);

        question.addLikes(question.getLikes());

        BoardLikeResponse boardLikeResponse = new BoardLikeResponse();
        boardLikeResponse.setQuestionId(question.getId());
        boardLikeResponse.setMemberId(question.getCreatedMember().getId());
        boardLikeResponse.setLikes(question.getLikes());

        LikeMember likeMember = LikeMember.builder()
                .member(member)
                .question(question)
                .build();
        likeMemberService.save(likeMember);
        return boardLikeResponse;
    }

    public Slice<Question> read(Integer categoryId, Pageable pageable) {
        Category category = categoryService.findById(Long.valueOf(categoryId)); // 데이터가 없을 때 예외처리
        Slice<Question> slice = boardService.findQuestionsByCategory(category, pageable);// 데이터 없을 때 예외처리
                                        // findCategory가 아니라 findQuestionsByCategory라고 해야 됨 - 그냥 Category라고 하면 무엇을 찾는지 잘 모름
        return slice;
    }
}
