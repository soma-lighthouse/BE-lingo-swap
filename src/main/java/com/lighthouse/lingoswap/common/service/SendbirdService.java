package com.lighthouse.lingoswap.common.service;

import com.lighthouse.lingoswap.chat.dto.ChatRoomUrlResponse;
import com.lighthouse.lingoswap.common.dto.SendbirdCreateChatRoomRequest;
import com.lighthouse.lingoswap.common.dto.SendbirdCreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class SendbirdService {

    private final WebClient webClient;

    public void createUser(SendbirdCreateUserRequest sendbirdCreateUserRequest) {
        String response = webClient.post()
                .uri("/users")
                .bodyValue(sendbirdCreateUserRequest)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String createChatRoom(SendbirdCreateChatRoomRequest sendbirdCreateChatRoomRequest) {
        return webClient.post()
                .uri("/group_channels")
                .bodyValue(sendbirdCreateChatRoomRequest)
                .retrieve()
                .bodyToMono(ChatRoomUrlResponse.class)
                .map(ChatRoomUrlResponse::channel_url)
                .block();
    }

    public void deleteUser(Long memberId) {
        String chatroomUrl = webClient.delete()
                .uri("/users/" + String.valueOf(memberId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
