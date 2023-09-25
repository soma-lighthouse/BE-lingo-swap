package com.lighthouse.lingoswap.question.dto;

import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.question.entity.Question;

public record QuestionDetail(Long questionId, String uuid, String profileImageUri, String name, String region,
                             String contents, Integer likes, boolean clicked) {

    public static QuestionDetail of(Question question, Member member, String profileImageUri, boolean clicked) {
        return new QuestionDetail(question.getId(), member.getAuthDetails().getUuid(), profileImageUri, member.getName(), member.getRegion().getCode(), question.getContents(), question.getLikes(), clicked);
    }
}
