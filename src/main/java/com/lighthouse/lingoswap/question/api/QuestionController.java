package com.lighthouse.lingoswap.question.api;

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
    public ResponseEntity<Object> post(@RequestBody @Valid final QuestionCreateRequest questionCreateRequest) {
        questionManager.create(questionCreateRequest);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping(path = "/category/{categoryId}")
    public ResponseEntity<QuestionListResponse> get(@RequestHeader("User-Id") Long userId,
                                                    @PathVariable Long categoryId,
                                                    @RequestParam(required = false) Long next,
                                                    @RequestParam(defaultValue = "10") final int pageSize) {
        return ResponseEntity.ok().body(questionManager.read(userId, categoryId, next, pageSize));
    }

    @PostMapping(path = "/{questionId}/like", headers = "User-Id")
    public ResponseEntity<Object> postLike(@RequestHeader("User-Id") String userId,
                                           @PathVariable Long questionId,
                                           @RequestBody QuestionUpdateLikeRequest questionUpdateLikeRequest) {
        questionManager.updateLike(questionId, questionUpdateLikeRequest);
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping(path = "/{questionId}/like", headers = "User-Id")
    public ResponseEntity<Object> deleteLike(@RequestHeader("User-Id") String userId,
                                             @PathVariable Long questionId,
                                             @RequestBody QuestionDeleteLikeRequest questionDeleteLikeRequest) {
        questionManager.deleteLike(questionId, questionDeleteLikeRequest);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping(path = "/recommendation/{categoryId}")
    public ResponseEntity<QuestionRecommendationListResponse> getRecommendation(@PathVariable Long categoryId,
                                                                                @RequestParam(required = false) final Long next,
                                                                                @RequestParam(defaultValue = "10") final int pageSize) {
        return ResponseEntity.ok(questionManager.readRecommendation(categoryId, next, pageSize));
    }
}
