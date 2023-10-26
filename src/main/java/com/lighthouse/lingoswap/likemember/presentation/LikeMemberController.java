package com.lighthouse.lingoswap.likemember.presentation;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.likemember.application.LikeMemberManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikeMemberController {

    private final LikeMemberManager likeMemberManager;

    @PostMapping("/api/v1/{questionId}/like")
    public ResponseEntity<ResponseDto<Void>> postLike(@PathVariable final Long questionId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        likeMemberManager.createLikeMember((String) auth.getPrincipal(), questionId);
        return ResponseEntity.ok(ResponseDto.noData());
    }

    @DeleteMapping("/api/v1/{questionId}/like")
    public ResponseEntity<ResponseDto<Void>> deleteLike(@PathVariable final Long questionId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        likeMemberManager.deleteLikeMember((String) auth.getPrincipal(), questionId);
        return ResponseEntity.ok(ResponseDto.noData());
    }

}
