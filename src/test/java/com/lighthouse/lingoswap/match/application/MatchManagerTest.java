package com.lighthouse.lingoswap.match.application;

import com.lighthouse.lingoswap.IntegrationTestSupport;
import com.lighthouse.lingoswap.interests.domain.model.Interests;
import com.lighthouse.lingoswap.interests.domain.repository.InterestsRepository;
import com.lighthouse.lingoswap.match.domain.model.MatchedMember;
import com.lighthouse.lingoswap.match.domain.repository.MatchedMemberRepository;
import com.lighthouse.lingoswap.match.dto.MatchedMemberProfilesResponse;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import com.lighthouse.lingoswap.member.dto.MemberSimpleProfile;
import com.lighthouse.lingoswap.preferredinterests.domain.model.PreferredInterests;
import com.lighthouse.lingoswap.preferredinterests.domain.repository.PreferredInterestsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.lighthouse.lingoswap.common.fixture.InterestsType.*;
import static com.lighthouse.lingoswap.common.fixture.MemberFixture.USER_UUID;
import static com.lighthouse.lingoswap.common.fixture.MemberFixture.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@Transactional
class MatchManagerTest extends IntegrationTestSupport {

    @Autowired
    MatchManager matchManager;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MatchedMemberRepository matchedMemberRepository;

    @Autowired
    PreferredInterestsRepository preferredInterestsRepository;

    @Autowired
    InterestsRepository interestsRepository;

    @DisplayName("매칭 리스트를 조회한다.")
    @Test
    void read() {
        // given
        Member fromMember = memberRepository.save(user());
        saveMatchedMembers(fromMember);

        // when
        Long nextId = matchedMemberRepository.findAllByFromMember(fromMember).get(2).getId();
        MatchedMemberProfilesResponse actual = matchManager.read(USER_UUID, nextId, 2);

        // then
        Member toMember1 = memberRepository.getByUuid("1");
        Member toMember2 = memberRepository.getByUuid("2");
        assertSoftly(softly -> {
            softly.assertThat(actual.nextId()).isEqualTo(-1);
            softly.assertThat(actual.profiles()).hasSize(2)
                    .containsExactlyInAnyOrder(
                            MemberSimpleProfile.of(toMember1, List.of(JAPANESE_FOOD.getName(), RPG_GAME.getName())),
                            MemberSimpleProfile.of(toMember2, List.of(CHINESE_FOOD.getName(), RPG_GAME.getName(), FPS_GAME.getName()))
                    );
        });
    }

    private void saveMatchedMembers(Member fromMember) {
        Member toMember1 = Member.builder().uuid("1").build();
        Member toMember2 = Member.builder().uuid("2").build();
        Member toMember3 = Member.builder().uuid("3").build();
        List<Member> members = List.of(toMember1, toMember2, toMember3);
        memberRepository.saveAll(members);

        Interests japaneseFood = interestsRepository.getByName(JAPANESE_FOOD.getName());
        Interests chineseFood = interestsRepository.getByName(CHINESE_FOOD.getName());
        Interests rpgGame = interestsRepository.getByName(RPG_GAME.getName());
        Interests fpsGame = interestsRepository.getByName(FPS_GAME.getName());
        preferredInterestsRepository.saveAll(List.of(
                PreferredInterests.builder().member(fromMember).interests(japaneseFood).build(),
                PreferredInterests.builder().member(fromMember).interests(chineseFood).build(),
                PreferredInterests.builder().member(fromMember).interests(rpgGame).build(),
                PreferredInterests.builder().member(fromMember).interests(fpsGame).build(),
                PreferredInterests.builder().member(toMember1).interests(japaneseFood).build(),
                PreferredInterests.builder().member(toMember1).interests(rpgGame).build(),
                PreferredInterests.builder().member(toMember2).interests(chineseFood).build(),
                PreferredInterests.builder().member(toMember2).interests(rpgGame).build(),
                PreferredInterests.builder().member(toMember2).interests(fpsGame).build(),
                PreferredInterests.builder().member(toMember3).interests(japaneseFood).build(),
                PreferredInterests.builder().member(toMember3).interests(chineseFood).build()));

        matchedMemberRepository.saveAll(members.stream()
                .map(toMember -> MatchedMember.builder().fromMember(fromMember).toMember(toMember).build())
                .toList());
    }

    @DisplayName("페이지에 더이상 매칭된 유저가 없으면 nextId에 -1을 반환한다.")
    @Test
    void readByEmptyPage() {
        // given
        Member fromMember = memberRepository.save(user());
        saveMatchedMembers(fromMember);

        // when
        Long nextId = matchedMemberRepository.findAllByFromMember(fromMember).get(2).getId();
        MatchedMemberProfilesResponse actual = matchManager.read(USER_UUID, nextId, 3);

        // then
        assertThat(actual.nextId()).isEqualTo(-1);
    }

    @DisplayName("매칭된 유저가 남아 있으면 마지막 row의 id를 반환한다.")
    @Test
    void readByPage() {
        // given
        Member fromMember = memberRepository.save(user());
        saveMatchedMembers(fromMember);

        // when
        List<MatchedMember> matchedMembers = matchedMemberRepository.findAllByFromMember(fromMember);
        MatchedMemberProfilesResponse actual = matchManager.read(USER_UUID, matchedMembers.get(2).getId() + 1, 2);

        // then
        assertThat(actual.nextId()).isEqualTo(matchedMembers.get(1).getId());
    }

}
