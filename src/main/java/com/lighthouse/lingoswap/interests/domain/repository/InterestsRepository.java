package com.lighthouse.lingoswap.interests.domain.repository;

import com.lighthouse.lingoswap.interests.domain.model.Interests;
import com.lighthouse.lingoswap.interests.exception.InterestsNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestsRepository extends JpaRepository<Interests, Long> {

    Optional<Interests> findByName(final String name);

    default Interests getByName(final String name) {
        return findByName(name)
                .orElseThrow(InterestsNotFoundException::new);
    }

    List<Interests> findAllByNameIn(final List<String> names);

}
