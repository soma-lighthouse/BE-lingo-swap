package com.lighthouse.lingoswap.question.presentation;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.question.application.QuestionManager;
import com.lighthouse.lingoswap.question.dto.MyQuestionsResponse;
import com.lighthouse.lingoswap.question.dto.QuestionCreateRequest;
import com.lighthouse.lingoswap.question.dto.QuestionListResponse;
import com.lighthouse.lingoswap.question.dto.QuestionRecommendationListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final QuestionManager questionManager;

    @GetMapping("/api/v1/question")
    public ResponseEntity<ResponseDto<QuestionListResponse>> get(@RequestParam(defaultValue = "1") final Long categoryId,
                                                                 @RequestParam(required = false) Long next,
                                                                 @RequestParam(defaultValue = "10") final int pageSize) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(questionManager.read((String) auth.getPrincipal(), categoryId, next, pageSize));
    }

    @PostMapping("/api/v1/question")
    public ResponseEntity<ResponseDto<Object>> post(@RequestBody @Valid final QuestionCreateRequest questionCreateRequest) {
        return ResponseEntity.ok(questionManager.create(questionCreateRequest));
    }

    @PostMapping("/api/v1/{questionId}/like")
    public ResponseEntity<ResponseDto<Object>> postLike(@PathVariable final Long questionId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(questionManager.updateLike((String) auth.getPrincipal(), questionId));
    }

    @DeleteMapping("/api/v1/{questionId}/like")
    public ResponseEntity<ResponseDto<Object>> deleteLike(@PathVariable final Long questionId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(questionManager.deleteLike((String) auth.getPrincipal(), questionId));
    }

    @GetMapping("/api/v1/question/recommendation")
    public ResponseEntity<ResponseDto<QuestionRecommendationListResponse>> getRecommendation(@RequestParam(defaultValue = "1") final Long categoryId,
                                                                                             @RequestParam(required = false) final Long next,
                                                                                             @RequestParam(defaultValue = "10") final int pageSize) {
        return ResponseEntity.ok(questionManager.readRecommendation(categoryId, next, pageSize));
    }

    @GetMapping("/api/v1/member/{uuid}/question")
    public ResponseEntity<ResponseDto<MyQuestionsResponse>> getMyQuestion(@PathVariable final String uuid) {
        return ResponseEntity.ok(questionManager.readByCreatedMember(uuid));
    }

}
