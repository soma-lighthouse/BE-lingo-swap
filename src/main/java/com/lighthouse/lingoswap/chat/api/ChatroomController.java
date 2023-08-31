package com.lighthouse.lingoswap.chat.api;

import com.lighthouse.lingoswap.chat.service.ChatroomManager;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
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
    public ResponseEntity<ResponseDto<Object>> create(@RequestBody @Valid final SendbirdRequestByChatroom sendbirdRequestByChatroom) {
        return ResponseEntity.ok(chatroomManager.create(sendbirdRequestByChatroom));
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody @Valid final SendbirdRequestByChatroom sendbirdRequestByChatroom) {
        return ResponseEntity.ok(chatroomManager.delete(sendbirdRequestByChatroom));
    }
}

