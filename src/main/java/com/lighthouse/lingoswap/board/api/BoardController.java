package com.lighthouse.lingoswap.board.api;

import com.lighthouse.lingoswap.board.dto.BoardCreateRequest;
import com.lighthouse.lingoswap.board.dto.BoardCreateResponse;
import com.lighthouse.lingoswap.board.dto.BoardUpdateLikeRequest;
import com.lighthouse.lingoswap.board.service.BoardManager;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/board/v1")
@RequiredArgsConstructor
public class BoardController {

    private final BoardManager boardManager;

    @PostMapping("/question")
    public ResponseEntity<ResponseDto> create(@RequestBody @Valid BoardCreateRequest boardCreateRequest) {

        boardManager.create(boardCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.builder()
                .code("20100")
                .message("Successfully created")
                .data("성공")
                .build()); //보드 정보 내려줌
    }

    @GetMapping("/question/{categoryId}") /////////// 기본 디폴트 값 넣어주기
    public ResponseEntity<ResponseDto<BoardCreateResponse>> read(@PathVariable Long categoryId, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        BoardCreateResponse question = boardManager.read(categoryId, pageable);
        return ResponseEntity.ok(ResponseDto.<BoardCreateResponse>builder()
                .code("20000")
                .message("OK")
                .data(question)
                .build());
    }

    @PostMapping("/question/{question_id}/addLike")
    public ResponseEntity<ResponseDto> likeQuestion(@RequestBody @Valid BoardUpdateLikeRequest boardUpdateLikeRequest
            , @PathVariable Long question_id) {
        boardManager.updateLike(boardUpdateLikeRequest, question_id);
        return ResponseEntity.ok(ResponseDto.builder()
                .code("20000")
                .message("Successfully created")
                .data("성공") //퀘스쳔 아이디같은 것도 줘야 클라이언트가 편함 //그냥 아이디말고 유효 아이디 추가도 고려
                .build());
    }
}