package com.lighthouse.lingoswap.board.dto;

import lombok.Data;

@Data
public class BoardCreateRequest {

    private Long memberId;
    private Long categoryId;
    private String content;
}
