package com.lighthouse.lingoswap.batch.processing;

import com.lighthouse.lingoswap.match.entity.MatchedMember;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Component
public class MatchedMemberItemProcessor implements ItemProcessor<Member, List<MatchedMember>> {

    private final MemberRepository memberRepository;
    private final Random random = new Random();

    private List<Member> allMembers;


    @Override
    public List<MatchedMember> process(Member currentMember) throws Exception {
        if (allMembers == null) {
            allMembers = memberRepository.findAll();
        }
        List<Member> selectedMembers = selectRandomMembers(allMembers, currentMember);
        List<MatchedMember> matchedMembers = new ArrayList<>();

        for (Member selectedMember : selectedMembers) {
            MatchedMember matchedMember = new MatchedMember(currentMember, selectedMember);
            matchedMembers.add(matchedMember);
        }
        return matchedMembers;
    }

    private List<Member> selectRandomMembers(List<Member> allMembers, Member currentMember) {
        List<Member> selectedMembers = new ArrayList<>();
        List<Member> tempMembers = new ArrayList<>(allMembers);

        while (selectedMembers.size() < 1000) {
            int index = random.nextInt(tempMembers.size());
            Member selectedMember = tempMembers.get(index);

            if (!selectedMember.equals(currentMember) &&
                    Duration.between(selectedMember.getUpdatedAt(), LocalDateTime.now()).toDays() <= 7) {
                selectedMembers.add(selectedMember);
            }

            tempMembers.remove(index);
        }
        return selectedMembers;
    }

}
