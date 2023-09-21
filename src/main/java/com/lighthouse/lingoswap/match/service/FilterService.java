package com.lighthouse.lingoswap.match.service;

import com.lighthouse.lingoswap.match.entity.FilteredMember;
import com.lighthouse.lingoswap.match.repository.FilteredMemberRepository;
import com.lighthouse.lingoswap.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FilterService {

    private final FilteredMemberRepository filteredMemberRepository;

    public Slice<FilteredMember> findFilteredMembers(Member member, Pageable pageable) {
        return filteredMemberRepository.findByFromMember(member, pageable);
    }
}
