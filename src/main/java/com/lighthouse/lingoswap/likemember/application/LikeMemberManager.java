package com.lighthouse.lingoswap.likemember.application;

import com.lighthouse.lingoswap.likemember.domian.model.LikeMember;
import com.lighthouse.lingoswap.likemember.domian.repository.LikeMemberRepository;
import com.lighthouse.lingoswap.question.exception.DuplicateLikeException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeMemberManager {

    private final LikeMemberRepository likeMemberRepository;

    public void save(final LikeMember likeMember) {
        try {
            likeMemberRepository.save(likeMember);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateLikeException();
        }
    }

    public void delete(final LikeMember likeMember) {
        likeMemberRepository.delete(likeMember);
    }

}
