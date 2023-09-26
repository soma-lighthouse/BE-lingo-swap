package com.lighthouse.lingoswap.chat.api;

import com.lighthouse.lingoswap.chat.dto.ChatroomCreateRequest;
import com.lighthouse.lingoswap.chat.service.ChatroomManager;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.entity.SendbirdCreateChatroomResponse;
import com.lighthouse.lingoswap.infra.dto.SendbirdRequestByChatroom;
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
    public ResponseEntity<ResponseDto<Object>> create(@RequestBody @Valid final SendbirdRequestByChatroom sendbirdRequestByChatroom) {
        return ResponseEntity.ok(chatroomManager.create(sendbirdRequestByChatroom));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto<Object>> delete(@RequestBody @Valid final SendbirdRequestByChatroom sendbirdRequestByChatroom) {
        return ResponseEntity.ok(chatroomManager.delete(sendbirdRequestByChatroom));
    }

    @PostMapping(path = "/chatroom")
    public ResponseEntity<ResponseDto<SendbirdCreateChatroomResponse>> createChatroom(@RequestBody final ChatroomCreateRequest chatroomCreateRequest) {
        return ResponseEntity.ok(chatroomManager.createChatroom(chatroomCreateRequest));
    }
}

