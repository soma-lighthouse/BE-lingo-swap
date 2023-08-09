package com.lighthouse.lingoswap.member.api;

import com.lighthouse.lingoswap.member.service.MemberCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class MemberController {

    private final MemberCRUDService memberCRUDService;
}