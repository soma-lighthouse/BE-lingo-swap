package com.lighthouse.lingoswap.question.api;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.question.dto.QuestionCreateRequest;
import com.lighthouse.lingoswap.question.dto.QuestionDeleteLikeRequest;
import com.lighthouse.lingoswap.question.dto.QuestionReadResponse;
import com.lighthouse.lingoswap.question.dto.QuestionUpdateLikeRequest;
import com.lighthouse.lingoswap.question.service.QuestionManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {

    private final QuestionManager questionManager;

    @PostMapping
    public ResponseEntity<ResponseDto> post(@RequestBody @Valid QuestionCreateRequest questionCreateRequest) {
        questionManager.create(questionCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.builder()
                .code("20100")
                .message("Successfully created")
                .data("성공")
                .build());
    }

    @GetMapping(path = "/category/{categoryId}")
    public ResponseEntity<ResponseDto<QuestionReadResponse>> get(@RequestHeader("User-Id") Long userId,
                                                                 @PathVariable Long categoryId,
                                                                 @RequestParam(required = false) Long next,
                                                                 @RequestParam(defaultValue = "10") final int pageSize) {
        QuestionReadResponse question = questionManager.read(userId, categoryId, next, pageSize);
        return ResponseEntity.ok(ResponseDto.<QuestionReadResponse>builder()
                .code("20000")
                .message("OK")
                .data(question)
                .build());
    }

    @PostMapping(path = "/{questionId}/like", headers = "User-Id")
    public ResponseEntity<ResponseDto> postLike(@RequestHeader("User-Id") String userId,
                                                @PathVariable Long questionId,
                                                @RequestBody QuestionUpdateLikeRequest questionUpdateLikeRequest) {
        questionManager.updateLike(questionId, questionUpdateLikeRequest);
        return ResponseEntity.ok(ResponseDto.builder()
                .code("20000")
                .message("Successfully created")
                .data("OK")
                .build());
    }

    @DeleteMapping(path = "/{questionId}/like", headers = "User-Id")
    public ResponseEntity<ResponseDto> deleteLike(@RequestHeader("User-Id") String userId,
                                                  @PathVariable Long questionId,
                                                  @RequestBody QuestionDeleteLikeRequest questionDeleteLikeRequest) {
        questionManager.deleteLike(questionId, questionDeleteLikeRequest);
        return ResponseEntity.ok(ResponseDto.builder()
                .code("20000")
                .message("Successfully deleted")
                .data("OK")
                .build());
    }
}
