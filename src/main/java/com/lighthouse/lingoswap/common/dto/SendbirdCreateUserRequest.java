package com.lighthouse.lingoswap.common.dto;

import lombok.Getter;

@Getter
public class SendbirdCreateUserRequest {

    private String user_id;
    private String nickname;
    private String profile_url;

    public SendbirdCreateUserRequest(String user_id, String nickname, String profile_url) {
        this.user_id = user_id;
        this.nickname = nickname;
        this.profile_url = profile_url;
    }
}
