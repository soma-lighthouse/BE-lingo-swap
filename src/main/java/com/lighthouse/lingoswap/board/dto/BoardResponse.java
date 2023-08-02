package com.lighthouse.lingoswap.board.dto;

import com.lighthouse.lingoswap.board.entity.Question;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BoardResponse {

    private Long questionId;
    private Long memberId;
    private Long categoryId;
    private String content;
    private Integer likes;

    public BoardResponse(Question question) { //이걸 만들면 @Data가 자동으로 기본생성자 생성안해주는 듯?
        this.questionId = question.getId();
        this.memberId = question.getCreatedMember().getId();
        this.categoryId = question.getCategory().getId();
        this.content = question.getContents();
        this.likes = question.getLikes();
    }

}
