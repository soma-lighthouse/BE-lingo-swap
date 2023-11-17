package com.lighthouse.lingoswap.chat.api;

import com.lighthouse.lingoswap.chat.dto.ChannelUrlResponse;
import com.lighthouse.lingoswap.chat.dto.ChatUserRequest;
import com.lighthouse.lingoswap.chat.dto.ChatroomCreateRequest;
import com.lighthouse.lingoswap.chat.service.ChatroomManager;
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
    public ResponseEntity<ResponseDto<Void>> create(@RequestBody @Valid final ChatUserRequest chatUserRequest) {
        chatroomManager.create(chatUserRequest);
        return ResponseEntity.ok(ResponseDto.noData());
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto<Void>> delete(@RequestBody @Valid final ChatUserRequest chatUserRequest) {
        chatroomManager.delete(chatUserRequest);
        return ResponseEntity.ok(ResponseDto.noData());
    }

    @PostMapping(path = "/chatroom")
    public ResponseEntity<ResponseDto<ChannelUrlResponse>> createChatroom(@RequestBody final ChatroomCreateRequest chatroomCreateRequest) {
        return ResponseEntity.ok(chatroomManager.createChatroom(chatroomCreateRequest));
    }

}

