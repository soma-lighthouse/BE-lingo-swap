package com.lighthouse.lingoswap.chat.service;

import com.lighthouse.lingoswap.chat.dto.ChatUserRequest;
import com.lighthouse.lingoswap.chat.dto.ChatroomCreateRequest;
import com.lighthouse.lingoswap.common.dto.ChatCreateChatroomResponse;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatroomManager {

    private final MemberRepository memberRepository;
    private final SendbirdService sendbirdService;

    public ResponseDto<Object> create(ChatUserRequest chatUserRequest) {
        chatUserRequest.memberUuids().stream()
                .map(memberRepository::getByUuid)
                .forEach(m -> sendbirdService.createUser(m.getUuid(), m.getName(), m.getProfileImageUrl()));
        return ResponseDto.success(null);
    }

    public ResponseDto<Object> delete(ChatUserRequest chatUserRequest) {
        chatUserRequest.memberUuids().forEach(sendbirdService::deleteUser);
        return ResponseDto.success(null);
    }

    public ResponseDto<ChatCreateChatroomResponse> createChatroom(ChatroomCreateRequest chatroomCreateRequest) {
        String channelUrl = sendbirdService.createChatroom(chatroomCreateRequest.users());
        return ResponseDto.success(new ChatCreateChatroomResponse(channelUrl));
    }

}
