package com.lighthouse.lingoswap.interests.domain.repository;

import com.lighthouse.lingoswap.interests.domain.model.Interests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestsRepository extends JpaRepository<Interests, Long> {

    Optional<Interests> findByName(String name);

    List<Interests> findAllByNameIn(List<String> names);

}
