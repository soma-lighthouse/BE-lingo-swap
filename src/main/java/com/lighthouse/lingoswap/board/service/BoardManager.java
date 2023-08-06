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

@Service
@RequiredArgsConstructor
@Transactional
public class BoardManager {

    private final MemberService memberService;
    private final CategoryService categoryService;
    private final BoardService boardService;
    private final LikeMemberService likeMemberService; // 이걸 주입안하면 아예 서비스 쓸 수가 없음/ 왜인지 알아보자

    public void create(BoardCreateRequest boardCreateRequest) {

        Member member = memberService.findById(boardCreateRequest.getMemberId());

        Category category = categoryService.findById(boardCreateRequest.getCategoryId());

        Question question = Question.builder()
                .createdMember(member)
                .category(category)
                .contents(boardCreateRequest.getContent())
                .build();
        boardService.save(question);
    }


/*    public BoardResponse readDetail(Long questionId) {
        Question question;
        question = boardService.findById(questionId);
        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setContent(question.getContents());
        boardResponse.setMemberId(question.getCreatedMember().getId());
        boardResponse.setCategoryId(question.getCategory().getId());
        boardResponse.setQuestionId(question.getId());
        return  boardResponse;
    }*/

    public void updateLike(BoardUpdateLikeRequest boardUpdateLikeRequest, Long questionId) {

        Member member = memberService.findById(boardUpdateLikeRequest.getMember_id());
        Question question = boardService.findById(questionId);

        question.addLikes(question.getLikes()); //수정 필요
        boardService.save(question);
/*
        BoardLikeResponse boardLikeResponse = new BoardLikeResponse(); //한줄로 수저
        boardLikeResponse.setQuestionId(question.getId());
        boardLikeResponse.setMemberId(question.getCreatedMember().getId());
        boardLikeResponse.setLikes(question.getLikes());
*/

        LikeMember likeMember = LikeMember.builder()
                .member(member)
                .question(question)
                .build();
        likeMemberService.save(likeMember);
    }

    public BoardCreateResponse read(Long categoryId, Pageable pageable) {
        Category category = categoryService.findById(categoryId); // 데이터가 없을 때 예외처리 ////여기 valueof 바꾸자
        Slice<Question> slice = boardService.findQuestionsByCategory(category, pageable);// 데이터 없을 때 예외처리
        BoardCreateResponse results = new BoardCreateResponse();
        for (Question question : slice.getContent()) {
            Member member = memberService.findById(question.getCreatedMember().getId());
            String profileImage = member.getProfileImage();
            String name = member.getName();
            String region = member.getRegion();
            BoardResponse boardResponse = BoardResponse.of(question, profileImage, name, region);
            results.add(boardResponse);
        }
        return results;
    }
    // findCategory가 아니라 findQuestionsByCategory라고 해야 됨 - 그냥 Category라고 하면 무엇을 찾는지 잘 모름
}
