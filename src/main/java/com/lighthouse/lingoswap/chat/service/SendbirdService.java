package com.lighthouse.lingoswap.chat.service;

import com.lighthouse.lingoswap.chat.dto.SendbirdCreateChatRoomRequest;
import com.lighthouse.lingoswap.chat.dto.SendbirdCreateUserRequest;
import com.lighthouse.lingoswap.common.dto.ChatCreateChatroomResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Profile({"local", "dev"})
@Service
public class SendbirdService {

    private final WebClient webClient;
    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String apiToken;

    public SendbirdService(final RestTemplateBuilder restTemplateBuilder,
                           @Value("${sendbird.api-url}") final String apiUrl,
                           @Value("${sendbird.api-token}") final String apiToken) {
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Content-Type", "application/json; charset=utf8")
                .defaultHeader("Api-Token", apiToken)
                .build();
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(5))
                .build();
        this.apiUrl = apiUrl;
        this.apiToken = apiToken;
    }

    public void createUser(final String userId, final String nickname, final String profileUrl) {
        webClient.post()
                .uri("/users")
                .bodyValue(new SendbirdCreateUserRequest(userId, nickname, profileUrl))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String createChatroom(final List<String> userIds) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Content-Type", "application/json; charset=utf8");
        headers.set("Api-Token", apiToken);

        HttpEntity<SendbirdCreateChatRoomRequest> entity = new HttpEntity<>(new SendbirdCreateChatRoomRequest(true, userIds), headers);
        ChatCreateChatroomResponse body = restTemplate.postForEntity(
                        apiUrl + "/group_channels",
                        entity,
                        ChatCreateChatroomResponse.class)
                .getBody();
        return Objects.requireNonNull(body).channel_url();
    }

    public void deleteUser(final String uuid) {
        webClient.delete()
                .uri("/users/" + uuid)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
