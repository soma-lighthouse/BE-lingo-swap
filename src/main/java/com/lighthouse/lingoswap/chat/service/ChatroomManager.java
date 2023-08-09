package com.lighthouse.lingoswap.chat.service;

import com.lighthouse.lingoswap.chat.dto.ChatroomRequest;
import com.lighthouse.lingoswap.chat.dto.ChatroomSendbirdCreateRequest;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class ChatroomManager {

    private final WebClient webClient;
    private final MemberService memberService;

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
}
