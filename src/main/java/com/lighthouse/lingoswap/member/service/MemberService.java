package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.entity.UsedLanguage;
import com.lighthouse.lingoswap.member.exception.MemberNotFoundException;
import com.lighthouse.lingoswap.member.repository.MemberQueryRepository;
import com.lighthouse.lingoswap.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;

    public Member findById(final Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
    }

    public List<Member> findAllById(final List<Long> ids) {
        return memberQueryRepository.findAllById(ids);
    }

//    public Map<Long, List<UsedLanguage>> findUsedLanguagesByMembers(final List<Member> members) {
//        List<Long> memberIds = extractIds(members);
//        List<UsedLanguage> usedLanguages = memberQueryRepository.findUsedLanguagesWithJoinIn(memberIds);
//        return groupLanguagesById(usedLanguages, memberIds);
//    }

    private Map<Long, List<UsedLanguage>> groupLanguagesById(final List<UsedLanguage> usedLanguages, final List<Long> memberIds) {
        Map<Long, List<UsedLanguage>> usedLanguagesMap = generateEmptyListById(memberIds);
        usedLanguages.forEach(u -> usedLanguagesMap.get(u.getMember().getId()).add(u));
        return usedLanguagesMap;
    }

    private Map<Long, List<UsedLanguage>> generateEmptyListById(final List<Long> memberIds) {
        return memberIds.stream().collect(toMap(Function.identity(), memberId -> new ArrayList<>(), (first, second) -> first));
    }

    private List<Long> extractIds(final List<Member> members) {
        return members.stream().map(Member::getId).toList();
    }
}
