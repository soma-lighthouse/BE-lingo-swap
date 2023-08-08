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

@RestController
@RequestMapping("/api/v1/board/question")
@RequiredArgsConstructor
public class BoardController {

    private final BoardManager boardManager;

    @PostMapping
    public ResponseEntity<ResponseDto> create(@RequestBody @Valid BoardCreateRequest boardCreateRequest) {
        boardManager.create(boardCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.builder()
                .code("20100")
                .message("Successfully created")
                .data("성공")
                .build());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ResponseDto<BoardCreateResponse>> read(@PathVariable Long categoryId, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        BoardCreateResponse question = boardManager.read(categoryId, pageable);
        return ResponseEntity.ok(ResponseDto.<BoardCreateResponse>builder()
                .code("20000")
                .message("OK")
                .data(question)
                .build());
    }

    @PostMapping("/{questionId}/like")
    public ResponseEntity<ResponseDto> likeQuestion(@PathVariable Long questionId,
                                                    @RequestBody @Valid BoardUpdateLikeRequest boardUpdateLikeRequest) {
        boardManager.updateLike(boardUpdateLikeRequest, questionId);
        return ResponseEntity.ok(ResponseDto.builder()
                .code("20000")
                .message("Successfully created")
                .data("성공")
                .build());
    }
}