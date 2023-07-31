package com.lighthouse.lingoswap.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardUpdateLikeRequest {

    private Long member_id;
    private Long question_id;
    private String content;
    private LocalDateTime created_date;
}
