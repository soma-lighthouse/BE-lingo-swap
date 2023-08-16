package com.lighthouse.lingoswap.chat.service;

import com.lighthouse.lingoswap.common.dto.SendbirdCreateUserRequest;
import com.lighthouse.lingoswap.common.dto.SendbirdRequestByChatroom;
import com.lighthouse.lingoswap.common.service.SandbirdService;
import com.lighthouse.lingoswap.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatroomManager {

    private final MemberService memberService;
    private final SandbirdService sandbirdService;

    public void create(SendbirdRequestByChatroom sendbirdRequestByChatroom) {
        sendbirdRequestByChatroom.getMembers().stream()
                .map(memberService::findById)
                .map(member -> new SendbirdCreateUserRequest(String.valueOf(member.getId()), member.getName(), member.getProfileImage()))
                .forEach(sandbirdService::createUser);
    }


    public void delete(SendbirdRequestByChatroom sendbirdRequestByChatroom) {
        sendbirdRequestByChatroom.getMembers().stream()
                .forEach(sandbirdService::deleteUser);
    }
}
