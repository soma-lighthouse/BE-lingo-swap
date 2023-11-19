package com.lighthouse.lingoswap.match.presentation;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.match.application.MatchManager;
import com.lighthouse.lingoswap.match.dto.MatchedMemberProfilesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class MatchController {

    private final MatchManager matchManager;

    @GetMapping("/{uuid}/matches")
    public ResponseEntity<ResponseDto<MatchedMemberProfilesResponse>> get(@PathVariable final String uuid,
                                                                          @RequestParam(required = false) final Long nextId,
                                                                          @RequestParam(defaultValue = "10") final int pageSize) {
        if (nextId == null) {
            matchManager.replaceWithNewMatchedMember(uuid);
        }
        return ResponseEntity.ok(ResponseDto.success(matchManager.read(uuid, nextId, pageSize)));
    }

}
