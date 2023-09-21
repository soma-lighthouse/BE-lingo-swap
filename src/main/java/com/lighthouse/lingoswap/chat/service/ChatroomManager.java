package com.lighthouse.lingoswap.chat.service;

import com.lighthouse.lingoswap.chat.dto.ChatRoomUrlResponse;
import com.lighthouse.lingoswap.chat.dto.ChatroomCreateRequest;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.dto.SendbirdCreateChatRoomRequest;
import com.lighthouse.lingoswap.common.dto.SendbirdCreateUserRequest;
import com.lighthouse.lingoswap.common.dto.SendbirdRequestByChatroom;
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
        sendbirdRequestByChatroom.members().stream()
                .map(memberService::findById)
                .map(member -> new SendbirdCreateUserRequest(String.valueOf(member.getId()), member.getName(), member.getProfileImageUri()))
                .forEach(sendbirdService::createUser);
        return ResponseDto.success(null);
    }


    public ResponseDto<Object> delete(SendbirdRequestByChatroom sendbirdRequestByChatroom) {
        sendbirdRequestByChatroom.members().forEach(sendbirdService::deleteUser);
        return ResponseDto.success(null);
    }

    public ResponseDto<ChatRoomUrlResponse> createChatroom(ChatroomCreateRequest chatroomCreateRequest) {
        SendbirdCreateChatRoomRequest sendbirdCreateChatRoomRequest = new SendbirdCreateChatRoomRequest(true, chatroomCreateRequest.uuids());
        String chatroomUrl = sendbirdService.createChatRoom(sendbirdCreateChatRoomRequest);
        return ResponseDto.success(new ChatRoomUrlResponse(chatroomUrl));
    }
}
