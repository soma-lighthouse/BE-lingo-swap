package com.lighthouse.lingoswap.match.api;

import com.lighthouse.lingoswap.match.service.MatchManager;
import com.lighthouse.lingoswap.member.dto.MemberSimpleProfile;
import com.lighthouse.lingoswap.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class MatchController {

    private final MatchManager matchManager;

    @Transactional
    @GetMapping("/{user_id}/matches")
    public ResponseEntity<Slice<MemberSimpleProfile>> get(@PathVariable final String uuid
            , @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Slice<Member> slice = matchManager.read(uuid, pageable);
        Slice<MemberSimpleProfile> sliceDto = slice.map(MemberSimpleProfile::from);

        return ResponseEntity.ok(sliceDto);
    }
}
