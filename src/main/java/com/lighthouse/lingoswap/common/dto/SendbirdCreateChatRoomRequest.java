package com.lighthouse.lingoswap.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SendbirdCreateChatRoomRequest {

    private Boolean is_distinct;
    private List<String> user_ids;
}
