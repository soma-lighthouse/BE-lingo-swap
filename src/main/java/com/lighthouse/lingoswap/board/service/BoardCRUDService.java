package com.lighthouse.lingoswap.board.service;

import com.lighthouse.lingoswap.board.dto.BoardCreateRequest;
import com.lighthouse.lingoswap.board.dto.BoardReadDetailResponse;
import com.lighthouse.lingoswap.board.dto.BoardUpdateLikeRequest;
import com.lighthouse.lingoswap.board.entity.LikeMember;
import com.lighthouse.lingoswap.board.entity.Question;
import com.lighthouse.lingoswap.member.entity.Category;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.service.CategoryService;
import com.lighthouse.lingoswap.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardCRUDService {

    private final MemberService memberService;
    private final CategoryService categoryService;
    private final BoardService boardService;

    public void create(BoardCreateRequest boardCreateRequest) {

    Member member = memberService.findById(boardCreateRequest.getMemberId());
    Category category = categoryService.findById(boardCreateRequest.getCategoryId());
    Question question = Question.builder()
            .createdMember(member)
            .category(category)
            .contents(boardCreateRequest.getContent())
            .build();



    }

    /*public BoardSearchResponse search(Integer category_id) {

    }*/

    public BoardReadDetailResponse readDetail(Long questionId) {
        Question question;
        question = boardService.findById(questionId);
        BoardReadDetailResponse boardReadDetailResponse = new BoardReadDetailResponse();
        boardReadDetailResponse.setContent(question.getContents());
        boardReadDetailResponse.setMemberId(question.getId());
        boardReadDetailResponse.setCategoryId(boardReadDetailResponse.getCategoryId());
        return  boardReadDetailResponse;
    }

    public Integer updateLike(BoardUpdateLikeRequest boardUpdateLikeRequest) {

        Member member = memberService.findById(boardUpdateLikeRequest.getMember_id());
        Question question = boardService.findById(boardUpdateLikeRequest.getQuestion_id());
        Integer like;
        like = question.getLikes();
        like = like+1;
        LikeMember likeMember = LikeMember.builder()
                .member(member)
                .question(question)
                .build();
        return like;
    }
}
