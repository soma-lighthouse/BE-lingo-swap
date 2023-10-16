package com.lighthouse.lingoswap.question.dto;

public record QuestionDetail(Long questionId, String contents, Long likes,
                             String uuid, String name, String region, String profileImageUri,
                             boolean liked) {

}
