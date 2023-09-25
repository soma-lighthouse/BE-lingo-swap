package com.lighthouse.lingoswap.infra.service;

import com.lighthouse.lingoswap.infra.dto.SendbirdCreateChatRoomRequest;
import com.lighthouse.lingoswap.infra.dto.SendbirdCreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class SendbirdService {

    private final WebClient webClient;

    public void createUser(final SendbirdCreateUserRequest sendbirdCreateUserRequest) {
        webClient.post()
                .uri("/users")
                .bodyValue(sendbirdCreateUserRequest)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public void createChatRoom(final SendbirdCreateChatRoomRequest sendbirdCreateChatRoomRequest) {
        webClient.post()
                .uri("/group_channels")
                .bodyValue(sendbirdCreateChatRoomRequest)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public void deleteUser(final String uuid) {
        webClient.delete()
                .uri("/users/" + uuid)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
