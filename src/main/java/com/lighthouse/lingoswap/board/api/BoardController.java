package com.lighthouse.lingoswap.board.api;

import com.lighthouse.lingoswap.board.dto.BoardCreateRequest;
import com.lighthouse.lingoswap.board.dto.BoardSearchDetailResponse;
import com.lighthouse.lingoswap.board.dto.BoardSearchReponse;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.board.service.BoardCRUDService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/board/v1") //버전을 명시해주자 - api/board/v1 이런식으로 작성함
@RequiredArgsConstructor
public class BoardController {

    private final BoardCRUDService boardCRUDService;

    @PostMapping
    public ResponseEntity<ResponseDto> create(@RequestBody @Valid BoardCreateRequest boardCreateRequest) {

        boardCRUDService.create(boardCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.builder()
                .code("20100")
                .message("Successfully created")
                .data("")
                .build());
    }

    @GetMapping("/category/{category_id}") //수정
    public ResponseEntity<ResponseDto<BoardSearchReponse>> get(@PathVariable int category_id) {
        BoardSearchReponse boardSearchReponse = boardCRUDService.search(category_id);
        return ResponseEntity.ok(ResponseDto.<BoardSearchReponse>builder()
                .code("20000")
                .message("OK")
                .data(boardSearchReponse)
                .build());
    }

    @GetMapping("/question/{question_id}")
    public ResponseEntity<ResponseDto<BoardSearchDetailResponse>> getQuestion(@PathVariable Long question_id) {
        BoardSearchDetailResponse boardSearchDetailResponse = boardCRUDService.searchDetail(question_id);
        return ResponseEntity.ok(ResponseDto.<BoardSearchDetailResponse>builder()
                .code("20000")
                .message("OK")
                .data(boardSearchDetailResponse)
                .build());
    }
}