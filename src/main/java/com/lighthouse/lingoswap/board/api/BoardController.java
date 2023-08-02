package com.lighthouse.lingoswap.board.api;

import com.lighthouse.lingoswap.board.dto.BoardCreateRequest;
import com.lighthouse.lingoswap.board.dto.BoardLikeResponse;
import com.lighthouse.lingoswap.board.dto.BoardResponse;
import com.lighthouse.lingoswap.board.dto.BoardUpdateLikeRequest;
import com.lighthouse.lingoswap.board.entity.Question;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.board.service.BoardManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board/v1")
@RequiredArgsConstructor
public class BoardController {

    private final BoardManager boardManager;

    @PostMapping("/board")
    public ResponseEntity<ResponseDto> create(@RequestBody @Valid BoardCreateRequest boardCreateRequest) {

        boardManager.create(boardCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.builder()
                .code("20100")
                .message("Successfully created")
                .build()); //보드 정보 내려줌
    }

    @GetMapping("/board/{id}")
    public ResponseEntity<ResponseDto<List<BoardResponse>>> read(@PathVariable Integer category_id, Pageable pageable) {
        Slice<Question> slice = boardManager.read(category_id, pageable);
        Slice<BoardResponse> sliceDto = slice.map(BoardResponse::new);
        List<BoardResponse> results = sliceDto.toList();
        return ResponseEntity.ok(ResponseDto.<List<BoardResponse>>builder()
                .code("20000")
                .message("OK")
                .data(results)
                .build());
    }

    @GetMapping("/board/detail/{question_id}")
    public ResponseEntity<ResponseDto<BoardResponse>> readQuestion(@PathVariable Long question_id) {
        BoardResponse boardSearchDetailResponse = boardManager.readDetail(question_id);
        return ResponseEntity.ok(ResponseDto.<BoardResponse>builder()
                .code("20000")
                .message("OK")
                .data(boardSearchDetailResponse)
                .build());
    }

    @PostMapping("/question/{question_id}/like")
    public ResponseEntity<ResponseDto<BoardLikeResponse>> likeQuestion(@RequestBody @Valid BoardUpdateLikeRequest boardUpdateLikeRequest
            , @PathVariable Long question_id) {
        BoardLikeResponse boardLikeResponse = boardManager.updateLike(boardUpdateLikeRequest, question_id);
        return ResponseEntity.ok(ResponseDto.<BoardLikeResponse>builder()
                .code("20100")
                .message("Successfully created")
                .data(boardLikeResponse) //퀘스쳔 아이디같은 것도 줘야 클라이언트가 편함 //그냥 아이디말고 유효 아이디 추가도 고려
                .build());
    }
}