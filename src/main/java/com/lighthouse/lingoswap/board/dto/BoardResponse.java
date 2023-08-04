package com.lighthouse.lingoswap.board.dto;

import com.lighthouse.lingoswap.board.entity.Question;
import com.lighthouse.lingoswap.member.entity.Country;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.repository.MemberRepository;
import com.lighthouse.lingoswap.member.service.MemberService;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BoardResponse {

    private static MemberService memberService;

    private final Long questionId;
    private final String profileImage;
    private final String name;
    private final String region;
    private final String content;
    private final Integer likes;

//    private BoardResponse(Long questionId, String profileImage, String name, String region, String content, Integer likes) {
//        this.questionId = questionId;
//        this.profileImage = profileImage;
//        this.name = name;
//        this.region = region;
//        this.content = content;
//        this.likes = likes;
//    }

    public static BoardResponse of(Question question, String profileImage, String name, String region) {
        return new BoardResponse(question.getId(), profileImage, name, region, question.getContents(), question.getLikes());
    }

}
