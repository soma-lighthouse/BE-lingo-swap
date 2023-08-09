package com.lighthouse.lingoswap.chat.service;

import com.lighthouse.lingoswap.chat.dto.ChatroomRequest;
import com.lighthouse.lingoswap.chat.dto.ChatroomSendbirdCreateRequest;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatroomManager {

    private final RestTemplate restTemplate;
    private final MemberService memberService;

    @Value("${sendbird.api-url}")
    private String apiUrl;

    @Value("${sendbird.api-token}")
    private String apiToken;

    @Autowired
    public ChatroomManager(MemberService memberService, RestTemplate restTemplate) {
        this.memberService = memberService;
        this.restTemplate = restTemplate;
    }

    public void create(ChatroomRequest chatroomRequest) {
        for (Long memberId : chatroomRequest.getMembers()) {
            Member member = memberService.findById(memberId);
            ChatroomSendbirdCreateRequest chatroomSendbirdCreateRequest =
                    new ChatroomSendbirdCreateRequest(String.valueOf(memberId), member.getName(), member.getProfileImage());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Api-Token", apiToken);
            headers.add("Content-Type", "application/json; charset=utf8");
            HttpEntity<ChatroomSendbirdCreateRequest> entity = new HttpEntity<>(chatroomSendbirdCreateRequest, headers);

            ResponseEntity<String> response = restTemplate.exchange(apiUrl + "/users", HttpMethod.POST, entity, String.class);
        }
    }

    public void delete(ChatroomRequest chatroomRequest) {
        for (Long memberId : chatroomRequest.getMembers()) {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Api-Token", apiToken);
                headers.add("Content-Type", "application/json; charset=utf8");
                HttpEntity<String> entity = new HttpEntity<>(headers);

                restTemplate.exchange(apiUrl + "/users/" + String.valueOf(memberId), HttpMethod.DELETE, entity, Void.class);
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                    // 유니크 제약 오류에 대한 처리
                    System.out.println("Error deleting user with ID: " + memberId);
                } else {
                    // 다른 HTTP 오류에 대한 처리
                    e.printStackTrace();
                }
            }
        }
    }
}


/*    public void delete(ChatroomRequest chatroomRequest) {
        for (Long memberId : chatroomRequest.getMembers()) {

            String response = webClient.delete()
                    .uri(apiUrl + "/users/" + String.valueOf(memberId))
                    .header("access_token", apiToken)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }
    }
}*/

/*@Service
public class ChatroomManager {

    private final WebClient webClient;
    private final MemberService memberService;

    @Autowired
    public ChatroomManager(WebClient webClient, MemberService memberService) {
        this.webClient = webClient;
        this.memberService = memberService;
    }

    public void create(ChatroomRequest chatroomRequest) {
        for (Long memberId : chatroomRequest.getMembers()) {
            Member member = memberService.findById(memberId);
            ChatroomSendbirdCreateRequest chatroomSendbirdCreateRequest = new ChatroomSendbirdCreateRequest(String.valueOf(memberId), member.getName(), member.getProfileImage());

            String response = webClient.post()
                    .uri("/users")
                    .body(BodyInserters.fromValue(chatroomSendbirdCreateRequest))  // 여기를 chatroomSendbirdRequest로 변경
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }
    }

    public void delete(ChatroomRequest chatroomRequest) {
        for (Long memberId : chatroomRequest.getMembers()) {

            String response = webClient.delete()
                    .uri("/users/" + String.valueOf(memberId))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }
    }
}*/
