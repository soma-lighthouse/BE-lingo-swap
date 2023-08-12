package com.lighthouse.lingoswap.question.dto;

import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.question.entity.Question;

public record QuestionResponse(Long questionId, Long userId, String profileImage, String name, String region,
                               String contents, Integer likes, boolean clicked) {

    public static QuestionResponse of(Question question, Member member, boolean clicked) {
        return new QuestionResponse(question.getId(), member.getId(), member.getProfileImage(), member.getName(), member.getRegion().getCode(), question.getContents(), question.getLikes(), clicked);
    }
}
