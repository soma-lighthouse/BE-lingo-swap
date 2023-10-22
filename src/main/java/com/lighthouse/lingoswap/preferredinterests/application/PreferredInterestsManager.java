package com.lighthouse.lingoswap.preferredinterests.application;

import com.lighthouse.lingoswap.interests.domain.model.Interests;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.preferredinterests.domain.model.PreferredInterests;
import com.lighthouse.lingoswap.preferredinterests.domain.repository.PreferredInterestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PreferredInterestsManager {

    private final PreferredInterestsRepository preferredInterestsRepository;

    public void save(PreferredInterests preferredInterests) {
        preferredInterestsRepository.save(preferredInterests);
    }

    public List<PreferredInterests> findAllByMemberWithInterestsAndCategory(final Member member) {
        return preferredInterestsRepository.findAllByMember(member);
    }

    public void deleteByInterestsNameIn(final List<Interests> interests) {
        preferredInterestsRepository.deleteAllByInterestsIn(interests);
    }

    public void saveAll(List<PreferredInterests> preferredInterests) {
        preferredInterestsRepository.saveAll(preferredInterests);
    }

}
