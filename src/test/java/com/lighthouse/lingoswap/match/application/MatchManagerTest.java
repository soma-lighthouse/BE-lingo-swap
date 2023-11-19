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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Stream;

import static com.lighthouse.lingoswap.common.fixture.InterestsType.*;
import static com.lighthouse.lingoswap.common.fixture.MemberFixture.USER_UUID;
import static com.lighthouse.lingoswap.common.fixture.MemberFixture.user;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

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

    @AfterEach
    void tearDown() {
        matchedMemberRepository.deleteAllInBatch();
        preferredInterestsRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("매칭 리스트를 조회한다.")
    @Test
    void read() {
        // given
        Member fromMember = memberRepository.save(user());
        savePreferredInterests(List.of(fromMember));

        Member toMember1 = Member.builder().uuid("1").build();
        Member toMember2 = Member.builder().uuid("2").build();
        saveWithPreferences(toMember1, toMember2);

        List<MatchedMember> matchedMembers = Stream.of(toMember1, toMember2)
                .map(toMember -> MatchedMember.builder().fromMember(fromMember).toMember(toMember).build())
                .toList();
        matchedMemberRepository.saveAll(matchedMembers);

        // when
        MatchedMemberProfilesResponse actual = matchManager.read(USER_UUID, toMember2.getId() + 1, 2);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.nextId()).isEqualTo(-1);
            softly.assertThat(actual.profiles()).hasSize(2)
                    .containsExactlyInAnyOrder(
                            MemberSimpleProfile.of(toMember1, List.of(JAPANESE_FOOD.getName(), RPG_GAME.getName())),
                            MemberSimpleProfile.of(toMember2, List.of(CHINESE_FOOD.getName(), RPG_GAME.getName(), FPS_GAME.getName()))
                    );
        });
    }

    private void saveWithPreferences(final Member toMember1, final Member toMember2) {
        memberRepository.saveAll(List.of(toMember1, toMember2));

        Interests japaneseFood = interestsRepository.getByName(JAPANESE_FOOD.getName());
        Interests chineseFood = interestsRepository.getByName(CHINESE_FOOD.getName());
        Interests rpgGame = interestsRepository.getByName(RPG_GAME.getName());
        Interests fpsGame = interestsRepository.getByName(FPS_GAME.getName());
        preferredInterestsRepository.saveAll(List.of(
                PreferredInterests.builder().member(toMember1).interests(japaneseFood).build(),
                PreferredInterests.builder().member(toMember1).interests(rpgGame).build(),
                PreferredInterests.builder().member(toMember2).interests(chineseFood).build(),
                PreferredInterests.builder().member(toMember2).interests(rpgGame).build(),
                PreferredInterests.builder().member(toMember2).interests(fpsGame).build()));
    }

    private void savePreferredInterests(final List<Member> members) {
        Interests japaneseFood = interestsRepository.getByName(JAPANESE_FOOD.getName());
        Interests chineseFood = interestsRepository.getByName(CHINESE_FOOD.getName());
        Interests rpgGame = interestsRepository.getByName(RPG_GAME.getName());
        Interests fpsGame = interestsRepository.getByName(FPS_GAME.getName());
        for (Member member : members) {
            preferredInterestsRepository.saveAll(List.of(
                    PreferredInterests.builder().member(member).interests(japaneseFood).build(),
                    PreferredInterests.builder().member(member).interests(chineseFood).build(),
                    PreferredInterests.builder().member(member).interests(rpgGame).build(),
                    PreferredInterests.builder().member(member).interests(fpsGame).build()));
        }
    }
    
    @DisplayName("페이지에 더이상 매칭된 유저가 없으면 nextId에 -1을 반환한다.")
    @Test
    void readByEmptyPage() {
        // given
        Member fromMember = memberRepository.save(user());
        savePreferredInterests(List.of(fromMember));

        List<Member> toMembers = List.of(Member.builder().uuid("1").build(), Member.builder().uuid("2").build(), Member.builder().uuid("3").build());
        memberRepository.saveAll(toMembers);
        savePreferredInterests(toMembers);

        List<MatchedMember> matchedMembers = toMembers.stream()
                .map(toMember -> MatchedMember.builder().fromMember(fromMember).toMember(toMember).build())
                .toList();
        matchedMemberRepository.saveAll(matchedMembers);

        // when
        MatchedMemberProfilesResponse actual = matchManager.read(USER_UUID, toMembers.get(2).getId() + 1, 3);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.nextId()).isEqualTo(-1);
            softly.assertThat(actual.profiles()).hasSize(3);
        });
    }

    @DisplayName("")
    @Test
    void replaceWithNewMatchedMember() {
        // given


        // when


        // then

    }

}
