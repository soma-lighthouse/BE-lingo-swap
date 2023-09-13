package com.lighthouse.lingoswap.question.api;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.question.dto.*;
import com.lighthouse.lingoswap.question.service.QuestionManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {

    private final QuestionManager questionManager;

    @PostMapping
    public ResponseEntity<ResponseDto<Object>> post(@RequestBody @Valid final QuestionCreateRequest questionCreateRequest) {
        return ResponseEntity.ok(questionManager.create(questionCreateRequest));
    }

    @GetMapping(path = "/category/{categoryId}")
    public ResponseEntity<ResponseDto<QuestionListResponse>> get(@RequestHeader("User-Id") Long userId,
                                                                 @PathVariable Long categoryId,
                                                                 @RequestParam(required = false) Long next,
                                                                 @RequestParam(defaultValue = "10") final int pageSize) {
        return ResponseEntity.ok(questionManager.read(userId, categoryId, next, pageSize));
    }

    @PostMapping(path = "/{questionId}/like", headers = "User-Id")
    public ResponseEntity<ResponseDto<Object>> postLike(@RequestHeader("User-Id") String userId,
                                                        @PathVariable Long questionId,
                                                        @RequestBody QuestionUpdateLikeRequest questionUpdateLikeRequest) {
        return ResponseEntity.ok().body(questionManager.updateLike(questionId, questionUpdateLikeRequest));
    }

    @DeleteMapping(path = "/{questionId}/like", headers = "User-Id")
    public ResponseEntity<ResponseDto<Object>> deleteLike(@RequestHeader("User-Id") String userId,
                                                          @PathVariable Long questionId,
                                                          @RequestBody QuestionDeleteLikeRequest questionDeleteLikeRequest) {
        return ResponseEntity.ok().body(questionManager.deleteLike(questionId, questionDeleteLikeRequest));
    }

    @GetMapping(path = "/myQuestion/{userId}")
    public ResponseEntity<ResponseDto<MyQuestionListResponse>> getMyQuestion(@PathVariable Long userId) {
        return ResponseEntity.ok(questionManager.getMyQuestion(userId));
    }
}
