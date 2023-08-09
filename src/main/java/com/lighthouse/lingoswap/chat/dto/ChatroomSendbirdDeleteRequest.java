package com.lighthouse.lingoswap.chat.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatroomSendbirdDeleteRequest {

    private final String user_id;

    static public ChatroomSendbirdDeleteRequest from(Long id) {
        return new ChatroomSendbirdDeleteRequest(String.valueOf(id));
    }


}
