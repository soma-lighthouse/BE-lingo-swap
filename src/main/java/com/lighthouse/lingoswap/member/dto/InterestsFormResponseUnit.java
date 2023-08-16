package com.lighthouse.lingoswap.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class InterestsFormResponseUnit {

    private String category;
    private List<String> interests;
}
