package com.lighthouse.lingoswap.chat.dto;

import lombok.Getter;

@Getter
public class ChatroomSendbirdCreateRequest {

    private String user_id;
    private String nickname;
    private String profile_url;

    public ChatroomSendbirdCreateRequest(String user_id, String nickname, String profile_url) {
        this.user_id = user_id;
        this.nickname = nickname;
        this.profile_url = profile_url;
    }
}
