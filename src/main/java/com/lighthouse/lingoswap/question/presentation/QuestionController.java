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
    public ResponseEntity<ResponseDto<QuestionListResponse>> getQuestions(@RequestParam(defaultValue = "1") final Long categoryId,
                                                                          @RequestParam(required = false) final Long next,
                                                                          @RequestParam(defaultValue = "10") final int pageSize) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(ResponseDto.success(questionManager.read((String) auth.getPrincipal(), categoryId, next, pageSize)));
    }

    @PostMapping("/api/v1/question")
    public ResponseEntity<ResponseDto<Void>> postQuestion(@RequestBody @Valid final QuestionCreateRequest questionCreateRequest) {
        questionManager.create(questionCreateRequest);
        return ResponseEntity.ok(ResponseDto.noData());
    }

    @GetMapping("/api/v1/question/recommendation")
    public ResponseEntity<ResponseDto<QuestionRecommendationListResponse>> getRecommendation(@RequestParam(defaultValue = "1") final Long categoryId,
                                                                                             @RequestParam(required = false) final Long next,
                                                                                             @RequestParam(defaultValue = "10") final int pageSize) {
        return ResponseEntity.ok(ResponseDto.success(questionManager.readRecommendation(categoryId, next, pageSize)));
    }

    @GetMapping("/api/v1/user/{uuid}/question")
    public ResponseEntity<ResponseDto<MyQuestionsResponse>> getMyQuestion(@PathVariable final String uuid) {
        return ResponseEntity.ok(ResponseDto.success(questionManager.readByCreatedMember(uuid)));
    }

    @PostMapping("/api/v1/question/{questionId}/like")
    public ResponseEntity<ResponseDto<Void>> postLike(@PathVariable final Long questionId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        questionManager.addOneLike((String) auth.getPrincipal(), questionId);
        return ResponseEntity.ok(ResponseDto.noData());
    }

    @DeleteMapping("/api/v1/question/{questionId}/like")
    public ResponseEntity<ResponseDto<Void>> deleteLike(@PathVariable final Long questionId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        questionManager.subtractOneLike((String) auth.getPrincipal(), questionId);
        return ResponseEntity.ok(ResponseDto.noData());
    }

}
