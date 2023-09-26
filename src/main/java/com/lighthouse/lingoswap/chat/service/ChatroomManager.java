package com.lighthouse.lingoswap.chat.service;

import com.lighthouse.lingoswap.chat.dto.ChatroomCreateRequest;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.dto.SendbirdCreateChatRoomRequest;
import com.lighthouse.lingoswap.common.dto.SendbirdCreateUserRequest;
import com.lighthouse.lingoswap.common.dto.SendbirdRequestByChatroom;
import com.lighthouse.lingoswap.common.entity.SendbirdCreateChatroomResponse;
import com.lighthouse.lingoswap.common.service.SendbirdService;
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

    public ResponseDto<SendbirdCreateChatroomResponse> createChatroom(ChatroomCreateRequest chatroomCreateRequest) {
        SendbirdCreateChatRoomRequest sendbirdCreateChatRoomRequest = new SendbirdCreateChatRoomRequest(true, chatroomCreateRequest.users());
        return ResponseDto.success(sendbirdService.createChatRoom(sendbirdCreateChatRoomRequest));
    }
}
