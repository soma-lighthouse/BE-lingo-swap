package com.lighthouse.lingoswap.chat.api;

import com.lighthouse.lingoswap.chat.dto.ChatroomRequest;
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
    public ResponseEntity<ResponseDto> create(@RequestBody @Valid final ChatroomRequest chatroomRequest) {

        chatroomManager.create(chatroomRequest);

        return ResponseEntity.ok(ResponseDto.builder()
                .code("200")
                .message("Successfully user created")
                .data("성공")
                .build());
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> delete(@RequestBody @Valid final ChatroomRequest chatroomRequest) {

        chatroomManager.delete(chatroomRequest);

        return ResponseEntity.ok(ResponseDto.builder()
                .code("200")
                .message("Successfully user created")
                .data("성공")
                .build());
    }
}