package com.lighthouse.lingoswap.chat.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatroomRequest {

    private List<Long> members;
}
