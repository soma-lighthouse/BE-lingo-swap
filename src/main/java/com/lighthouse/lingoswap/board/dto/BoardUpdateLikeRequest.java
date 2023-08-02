package com.lighthouse.lingoswap.board.dto;

import com.lighthouse.lingoswap.board.entity.Question;
import lombok.Data;
import org.aspectj.weaver.patterns.TypePatternQuestions;

import java.time.LocalDateTime;

@Data
public class BoardUpdateLikeRequest {

    private Long member_id;
}
