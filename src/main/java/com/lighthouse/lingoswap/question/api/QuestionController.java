package com.lighthouse.lingoswap.question.api;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.question.dto.QuestionCreateRequest;
import com.lighthouse.lingoswap.question.dto.QuestionListResponse;
import com.lighthouse.lingoswap.question.service.QuestionManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<ResponseDto<QuestionListResponse>> get(@PathVariable Long categoryId,
                                                                 @RequestParam(required = false) Long next,
                                                                 @RequestParam(defaultValue = "10") final int pageSize) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(questionManager.read((String) auth.getPrincipal(), categoryId, next, pageSize));
    }

    @PostMapping(path = "/{questionId}/like")
    public ResponseEntity<ResponseDto<Object>> postLike(@PathVariable Long questionId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(questionManager.updateLike((String) auth.getPrincipal(), questionId));
    }

    @DeleteMapping(path = "/{questionId}/like")
    public ResponseEntity<ResponseDto<Object>> deleteLike(@PathVariable Long questionId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(questionManager.deleteLike((String) auth.getPrincipal(), questionId));
    }
}
