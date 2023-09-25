package com.lighthouse.lingoswap.chat.service;

import com.lighthouse.lingoswap.chat.dto.ChatroomCreateRequest;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.infra.dto.SendbirdCreateChatRoomRequest;
import com.lighthouse.lingoswap.infra.dto.SendbirdCreateUserRequest;
import com.lighthouse.lingoswap.infra.dto.SendbirdRequestByChatroom;
import com.lighthouse.lingoswap.infra.service.SendbirdService;
import com.lighthouse.lingoswap.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatroomManager {

    private final MemberService memberService;
    private final SendbirdService sendbirdService;

    public ResponseDto<Object> create(SendbirdRequestByChatroom sendbirdRequestByChatroom) {
        sendbirdRequestByChatroom.memberUuids().stream()
                .map(memberService::findByUuid)
                .map(member -> new SendbirdCreateUserRequest(member.getAuthDetails().getUuid(), member.getName(), member.getProfileImageUri()))
                .forEach(sendbirdService::createUser);
        return ResponseDto.success(null);
    }


    public ResponseDto<Object> delete(SendbirdRequestByChatroom sendbirdRequestByChatroom) {
        sendbirdRequestByChatroom.memberUuids().forEach(sendbirdService::deleteUser);
        return ResponseDto.success(null);
    }

    public ResponseDto<Object> createChatroom(ChatroomCreateRequest chatroomCreateRequest) {
        SendbirdCreateChatRoomRequest sendbirdCreateChatRoomRequest = new SendbirdCreateChatRoomRequest(true, chatroomCreateRequest.uuids());
        sendbirdService.createChatRoom(sendbirdCreateChatRoomRequest);
        return ResponseDto.success(null);
    }
}
