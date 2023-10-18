package com.lighthouse.lingoswap.chat.api;

import com.lighthouse.lingoswap.chat.dto.ChatUserRequest;
import com.lighthouse.lingoswap.chat.dto.ChatroomCreateRequest;
import com.lighthouse.lingoswap.chat.service.ChatroomManager;
import com.lighthouse.lingoswap.common.dto.ChatCreateChatroomResponse;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chat")
public class ChatroomController {

    private final ChatroomManager chatroomManager;

    @PostMapping
    public ResponseEntity<ResponseDto<Object>> create(@RequestBody @Valid final ChatUserRequest chatUserRequest) {
        return ResponseEntity.ok(chatroomManager.create(chatUserRequest));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto<Object>> delete(@RequestBody @Valid final ChatUserRequest chatUserRequest) {
        return ResponseEntity.ok(chatroomManager.delete(chatUserRequest));
    }

    @PostMapping(path = "/chatroom")
    public ResponseEntity<ResponseDto<ChatCreateChatroomResponse>> createChatroom(@RequestBody final ChatroomCreateRequest chatroomCreateRequest) {
        return ResponseEntity.ok(chatroomManager.createChatroom(chatroomCreateRequest));
    }

}

