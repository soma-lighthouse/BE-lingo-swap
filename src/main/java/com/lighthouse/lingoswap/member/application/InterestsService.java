package com.lighthouse.lingoswap.member.application;

import com.lighthouse.lingoswap.interests.domain.model.Interests;
import com.lighthouse.lingoswap.interests.domain.repository.InterestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InterestsService {

    private final InterestsRepository interestsRepository;

    public Interests findByName(String name) {
        return interestsRepository.findByName(name).orElseThrow(() -> new RuntimeException("세부 관심분야가 없습니다"));
    }

    public void save(Interests interests) {
        interestsRepository.save(interests);
    }

    public List<Interests> findAllByNames(List<String> names) {
        return interestsRepository.findAllByNameIn(names);
    }

}
