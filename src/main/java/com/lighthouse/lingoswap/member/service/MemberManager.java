package com.lighthouse.lingoswap.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberManager {

    private final MemberService memberService;
}
