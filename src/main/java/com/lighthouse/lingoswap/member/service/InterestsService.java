package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.member.entity.Interests;
import com.lighthouse.lingoswap.member.repository.InterestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
