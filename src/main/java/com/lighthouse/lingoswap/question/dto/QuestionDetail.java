package com.lighthouse.lingoswap.question.dto;

import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.question.entity.Question;

public record QuestionDetail(Long questionId, Long userId, String profileImageUri, String name, String region,
                             String contents, Integer likes, boolean clicked) {

    public static QuestionDetail of(Question question, Member member, boolean clicked) {
        return new QuestionDetail(question.getId(), member.getId(), member.getProfileImageUri(), member.getName(), member.getRegion().getCode(), question.getContents(), question.getLikes(), clicked);
    }
}
