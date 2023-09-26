package com.lighthouse.lingoswap.common.service;

import com.lighthouse.lingoswap.common.dto.SendbirdCreateChatRoomRequest;
import com.lighthouse.lingoswap.common.dto.SendbirdCreateUserRequest;
import com.lighthouse.lingoswap.common.entity.SendbirdCreateChatroomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class SendbirdService {

    private final WebClient webClient;
    private final RestTemplate restTemplate;

    @Value("${sendbird.api-url}")
    private String apiUrl;

    @Value("${sendbird.api-token}")
    private String apiToken;

    public void createUser(final SendbirdCreateUserRequest sendbirdCreateUserRequest) {
        webClient.post()
                .uri("/users")
                .bodyValue(sendbirdCreateUserRequest)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public SendbirdCreateChatroomResponse createChatRoom(final SendbirdCreateChatRoomRequest sendbirdCreateChatRoomRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Content-Type", "application/json; charset=utf8");
        headers.set("Api-Token", apiToken);

        HttpEntity<SendbirdCreateChatRoomRequest> entity = new HttpEntity<>(sendbirdCreateChatRoomRequest, headers);
        return restTemplate.postForEntity(apiUrl + "/group_channels", entity, SendbirdCreateChatroomResponse.class).getBody();
    }

    public void deleteUser(final String uuid) {
        webClient.delete()
                .uri("/users/" + uuid)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
