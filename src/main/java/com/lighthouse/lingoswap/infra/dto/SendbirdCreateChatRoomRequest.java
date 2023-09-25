package com.lighthouse.lingoswap.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SendbirdCreateChatRoomRequest {

    private boolean is_distinct;
    private List<String> user_ids;


}
