package com.lighthouse.lingoswap.board.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BoardManagerTest {

    @PersistenceContext EntityManager em;

    @Autowired BoardManager boardManager;
    @Autowired BoardService boardService;

    @Test
    @Transactional
    void create() {
/*        //given
        Member member = Member.builder()
                .gender("M")
                .name("A")
                .description("회원1")
                .build();

        em.persist(member);

        Category category = Category.builder()
                .name("요리")
                .build();

        em.persist(category);


        //when
        BoardCreateRequest boardCreateRequest = new BoardCreateRequest();
        boardCreateRequest.setCategoryId(category.getId());
        boardCreateRequest.setMemberId(member.getId());
        boardCreateRequest.setContent("첫 게시물 \n 첫 내용");*/
        /*        Long questionId = boardManager.create(boardCreateRequest);*/

        //then
/*        Question question = boardService.findById((questionId));
        Assertions.assertThat(member.getId()).isEqualTo(question.getCreatedMember().getId());
        Assertions.assertThat("첫 게시물 \n 첫 내용").isEqualTo(question.getContents());
        Assertions.assertThat(Boolean.FALSE).isEqualTo(question.getIsValid());
        Assertions.assertThat(Boolean.FALSE).isEqualTo(question.getIsRecommended());

        em.clear();*/
    }


    @Test
    @Transactional
    void updateLike() {
        //given
/*
        Member member = Member.builder()
                .gender("M")
                .name("A")
                .description("회원1")
                .build();

        em.persist(member);

        Category category = Category.builder()
                .name("요리")
                .build();

        em.persist(category);

        BoardCreateRequest boardCreateRequest = new BoardCreateRequest();
        boardCreateRequest.setCategoryId(category.getId());
        boardCreateRequest.setMemberId(member.getId());
        boardCreateRequest.setContent("첫 게시물 \n 첫 내용");
        Long questionId = boardManager.create(boardCreateRequest);

        Assertions.assertThat(boardService.findById(questionId).getLikes()).isEqualTo(0);

        //when
        BoardUpdateLikeRequest boardUpdateLikeRequest = new BoardUpdateLikeRequest();
        boardUpdateLikeRequest.setMember_id(member.getId());
        BoardLikeResponse boardLikeResponse = boardManager.updateLike(boardUpdateLikeRequest, questionId);
        Assertions.assertThat(boardLikeResponse.getLikes()).isEqualTo(1);
        Assertions.assertThat(boardLikeResponse.getQuestionId()).isEqualTo(questionId);
        Assertions.assertThat(boardLikeResponse.getMemberId()).isEqualTo(member.getId());



*/
/*
    }
    public List<BoardResponse> read(Integer categoryId, Pageable pageable) {
        Category category = categoryService.findById(Long.valueOf(categoryId)); // 데이터가 없을 때 예외처리
        Slice<Question> slice = boardService.findQuestionsByCategory(category, pageable);// 데이터 없을 때 예외처리
        List<BoardResponse> results = new ArrayList<>();
        for (Question question : slice.getContent()) {
            Member member = memberService.findById(question.getCreatedMember().getId());
            String profileImage = member.getProfileImage();
            String name = member.getName();
            String region = countryService.findById(member.getRegionId()).getCode();
            BoardResponse boardResponse = BoardResponse.of(question, profileImage, name, region);
            results.add(boardResponse);
        }
        return results;
    }*/
/*    @Test
    void read() {
        //given
        Member member = Member.builder()
                .gender("M")
                .name("A")
                .description("회원1")
                .build();

        em.persist(member);

        Category category = Category.builder()
                .name("요리")
                .build();

        em.persist(category);

        for(int i=0;i<30;i++){
            Question question = Question.builder()
                    .createdMember(member)
                    .category(category)
                    .contents("ㅋㅋㅋ")
                    .build();
            em.persist(question);
        }*/


/*
        BoardCreateRequest boardCreateRequest = new BoardCreateRequest();
        boardCreateRequest.setCategoryId(category.getId());
        boardCreateRequest.setMemberId(member.getId());
        boardCreateRequest.setContent("첫 게시물 \n 첫 내용");
        Long questionId = boardManager.create(boardCreateRequest);

        Assertions.assertThat(boardService.findById(questionId).getLikes()).isEqualTo(0);

        //when
        BoardUpdateLikeRequest boardUpdateLikeRequest = new BoardUpdateLikeRequest();
        boardUpdateLikeRequest.setMember_id(member.getId());
        BoardLikeResponse boardLikeResponse = boardManager.updateLike(boardUpdateLikeRequest, questionId);
        Assertions.assertThat(boardLikeResponse.getLikes()).isEqualTo(1);
        Assertions.assertThat(boardLikeResponse.getQuestionId()).isEqualTo(questionId);
        Assertions.assertThat(boardLikeResponse.getMemberId()).isEqualTo(member.getId());
*/


    }
}