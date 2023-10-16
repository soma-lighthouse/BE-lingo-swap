package com.lighthouse.lingoswap.infra.dto;

import java.util.List;

public record SendbirdCreateChatRoomRequest(Boolean is_distinct, List<String> user_ids) {

}
