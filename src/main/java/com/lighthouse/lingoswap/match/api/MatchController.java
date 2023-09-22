package com.lighthouse.lingoswap.match.api;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.match.dto.MatchedMemberProfilesResponse;
import com.lighthouse.lingoswap.match.service.MatchManager;
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
                                                                          @RequestParam(required = false) final Long next,
                                                                          @RequestParam(defaultValue = "10") final int pageSize) {
        return ResponseEntity.ok(matchManager.read(uuid, next, pageSize));
    }
}
