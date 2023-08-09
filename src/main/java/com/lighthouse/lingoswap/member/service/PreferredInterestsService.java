package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.member.entity.PreferredInterests;
import com.lighthouse.lingoswap.member.repository.PreferredInterestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PreferredInterestsService {

    private final PreferredInterestsRepository preferredInterestsRepository;

    List<PreferredInterests> findAllByMemberIdWithInterestsAndCategory(Long id) {
        return preferredInterestsRepository.findAllByMemberIdWithInterestsAndCategory(id);
    }
}
