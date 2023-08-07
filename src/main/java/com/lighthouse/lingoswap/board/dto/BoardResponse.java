package com.lighthouse.lingoswap.board.dto;

import com.lighthouse.lingoswap.board.entity.Question;
import com.lighthouse.lingoswap.member.entity.Member;

public record BoardResponse(Long questionId, String profileImage, String name, String region, String content,
                            Integer likes) {

    public static BoardResponse of(Question question, Member member) {
        return new BoardResponse(question.getId(), member.getProfileImage(), member.getName(), member.getRegion(), question.getContents(), question.getLikes());
    }

}
