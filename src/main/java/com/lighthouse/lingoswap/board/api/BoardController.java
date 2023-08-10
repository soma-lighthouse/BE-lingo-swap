package com.lighthouse.lingoswap.board.api;

import com.lighthouse.lingoswap.board.dto.BoardCreateRequest;
import com.lighthouse.lingoswap.board.dto.BoardCreateResponse;
import com.lighthouse.lingoswap.board.dto.BoardUpdateLikeRequest;
import com.lighthouse.lingoswap.board.service.BoardManager;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/board/question")
public class BoardController {

    private final BoardManager boardManager;

    @PostMapping
    public ResponseEntity<ResponseDto> post(@RequestBody @Valid BoardCreateRequest boardCreateRequest) {
        boardManager.create(boardCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.builder()
                .code("20100")
                .message("Successfully created")
                .data("성공")
                .build());
    }

    @GetMapping(path = "/{categoryId}", headers = "User-Id")
    public ResponseEntity<ResponseDto<BoardCreateResponse>> get(@RequestHeader("User-Id") Long userId,
                                                                @PathVariable Long categoryId,
                                                                @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        BoardCreateResponse question = boardManager.read(userId, categoryId, pageable);
        return ResponseEntity.ok(ResponseDto.<BoardCreateResponse>builder()
                .code("20000")
                .message("OK")
                .data(question)
                .build());
    }

    @PostMapping(value = "/{questionId}/like", headers = "User-Id")
    public ResponseEntity<ResponseDto> likeQuestion(@RequestHeader("User-Id") String userId,
                                                    @PathVariable Long questionId,
                                                    @RequestBody @Valid BoardUpdateLikeRequest boardUpdateLikeRequest) {
        boardManager.updateLike(questionId, boardUpdateLikeRequest);
        return ResponseEntity.ok(ResponseDto.builder()
                .code("20000")
                .message("Successfully updated")
                .data("OK")
                .build());
    }
}
