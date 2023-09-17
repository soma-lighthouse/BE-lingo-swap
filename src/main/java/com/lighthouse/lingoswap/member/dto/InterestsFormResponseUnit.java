package com.lighthouse.lingoswap.member.dto;

import java.util.List;

public record InterestsFormResponseUnit(String categoryCode, String categoryName, List<InterestsUnit> interests) {

}
