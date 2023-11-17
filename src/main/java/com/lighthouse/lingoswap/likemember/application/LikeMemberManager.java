package com.lighthouse.lingoswap.likemember.application;

import com.lighthouse.lingoswap.likemember.domian.model.LikeMember;
import com.lighthouse.lingoswap.likemember.domian.repository.LikeMemberRepository;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import com.lighthouse.lingoswap.question.domain.model.Question;
import com.lighthouse.lingoswap.question.domain.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LikeMemberManager {

    private final LikeMemberRepository likeMemberRepository;
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createLikeMember(final String memberUuid, final Long questionId) {
        Question question = questionRepository.getByQuestionId(questionId);
        Member member = memberRepository.getByUuid(memberUuid);
        likeMemberRepository.validateAlreadyLiked(member, question);
        LikeMember likeMember = LikeMember.builder()
                .member(member)
                .question(question)
                .build();
        likeMemberRepository.save(likeMember);

        question.addOneLike();
        questionRepository.save(question);
    }

    @Transactional
    public void deleteLikeMember(final String memberUuid, final Long questionId) {
        Question question = questionRepository.getByQuestionId(questionId);
        Member member = memberRepository.getByUuid(memberUuid);
        LikeMember likeMember = likeMemberRepository.getByMemberAndQuestion(member, question);
        likeMemberRepository.delete(likeMember);

        question.subtractOneLike();
        questionRepository.save(question);
    }

}
