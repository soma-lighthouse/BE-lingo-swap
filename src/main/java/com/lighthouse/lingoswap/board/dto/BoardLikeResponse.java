package com.lighthouse.lingoswap.board.dto;

import lombok.Data;

@Data
public class BoardLikeResponse {

    private Long questionId;
    private Long memberId;
    private Integer likes;
}
