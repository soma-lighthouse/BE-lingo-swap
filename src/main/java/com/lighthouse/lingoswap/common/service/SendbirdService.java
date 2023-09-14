package com.lighthouse.lingoswap.common.service;

import com.lighthouse.lingoswap.common.dto.SendbirdCreateUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RequiredArgsConstructor
@Service
public class SendbirdService {

    private final WebClient webClient;

    public void createUser(SendbirdCreateUserRequest sendbirdCreateUserRequest) {
        webClient.post()
                .uri("/users")
                .bodyValue(sendbirdCreateUserRequest)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public void deleteUser(String memberUuid) {
        webClient.delete()
                .uri("/users/" + memberUuid)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
