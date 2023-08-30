package com.lighthouse.lingoswap.chat.api;

import com.lighthouse.lingoswap.chat.service.ChatroomManager;
import com.lighthouse.lingoswap.common.dto.SendbirdRequestByChatroom;
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
    public ResponseEntity<Object> create(@RequestBody @Valid final SendbirdRequestByChatroom sendbirdRequestByChatroom) {
        chatroomManager.create(sendbirdRequestByChatroom);
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody @Valid final SendbirdRequestByChatroom sendbirdRequestByChatroom) {
        chatroomManager.delete(sendbirdRequestByChatroom);
        return ResponseEntity.ok().body(null);
    }
}

