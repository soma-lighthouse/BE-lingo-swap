package com.lighthouse.lingoswap.board.dto;

import com.lighthouse.lingoswap.board.entity.Question;
import com.lighthouse.lingoswap.member.entity.Member;

public record BoardResponse(Long questionId, Long userId, String profileImage, String name, String region,
                            String contents, Integer likes, boolean clicked) {

    public static BoardResponse of(Question question, Member member, boolean clicked) {
        return new BoardResponse(question.getId(), member.getId(), member.getProfileImage(), member.getName(), member.getRegion().getCode(), question.getContents(), question.getLikes(), clicked);
    }
}
