package com.lighthouse.lingoswap.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class InterestsFormResponse {

    List<InterestsFormResponseUnit> interestsForm;
}
