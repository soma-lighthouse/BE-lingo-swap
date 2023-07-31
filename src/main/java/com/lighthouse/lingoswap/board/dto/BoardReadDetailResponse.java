package com.lighthouse.lingoswap.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardReadDetailResponse {
    private Long memberId;
    private Long categoryId;
    private String content;
    private LocalDateTime created_date;


}
