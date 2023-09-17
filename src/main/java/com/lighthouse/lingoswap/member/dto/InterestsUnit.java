package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.member.entity.Interests;

public record InterestsUnit(String InterestsCode, String InterestsName) {

    public static InterestsUnit of(Interests interests, String interestsName) {
        return new InterestsUnit(interests.getName(), interestsName);
    }
}