package com.lighthouse.lingoswap.common.service;

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

    public void deleteUser(Long memberId) {
        String response = webClient.delete()
                .uri("/users/" + String.valueOf(memberId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
