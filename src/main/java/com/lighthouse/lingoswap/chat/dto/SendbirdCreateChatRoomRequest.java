package com.lighthouse.lingoswap.chat.dto;

import java.util.List;

public record SendbirdCreateChatRoomRequest(Boolean is_distinct, List<String> user_ids) {

}
