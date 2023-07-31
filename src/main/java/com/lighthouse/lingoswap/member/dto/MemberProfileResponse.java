package com.lighthouse.lingoswap.member.dto;

import java.util.List;

public record MemberProfileResponse(String imageUrl,
                                    String name,
                                    String description, String region,
                                    List<LanguageResponse> language) {

}
