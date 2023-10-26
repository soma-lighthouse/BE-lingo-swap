package com.lighthouse.lingoswap.question.dto;

import lombok.Builder;

public record QuestionDetail(Long questionId,
                             String contents,
                             Long likes,
                             String uuid,
                             String name,
                             String region,
                             String profileImageUri,
                             boolean liked) {

    @Builder
    public static QuestionDetail of(final Long questionId,
                                    final String contents,
                                    final Long likes,
                                    final String uuid,
                                    final String name,
                                    final String region,
                                    final String profileImageUri,
                                    final boolean liked) {
        return new QuestionDetail(
                questionId,
                contents,
                likes,
                uuid,
                name,
                region,
                profileImageUri,
                liked);
    }

}
