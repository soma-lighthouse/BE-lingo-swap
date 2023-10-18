package com.lighthouse.lingoswap.member.domain.model;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AuthDetails authDetails;

    @Column(length = 2)
    private String region;

    @Embedded
    private MemberUsedLanguages memberUsedLanguages;

    @Embedded
    private MemberPreferredInterests memberPreferredInterests;

    @Embedded
    private MemberPreferredCountries memberPreferredCountries;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthday;
    private String description;
    private String name;
    private String profileImageUrl;

    @Builder
    public Member(final LocalDate birthday, final String name, final String description, final String profileImageUrl, final Gender gender, final String username, final String uuid, final Role role, final String region) {
        this.birthday = birthday;
        this.name = name;
        this.description = description;
        this.profileImageUrl = profileImageUrl;
        this.gender = gender;
        this.authDetails = AuthDetails.builder()
                .username(username)
                .uuid(uuid)
                .role(role)
                .build();
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    public AuthDetails getAuthDetails() {
        return authDetails;
    }

    public String getUuid() {
        return authDetails.getUuid();
    }

    public String getUsername() {
        return authDetails.getUsername();
    }

    public String getRegion() {
        return region;
    }

    public List<UsedLanguage> getMemberUsedLanguages() {
        return memberUsedLanguages.getUsedLanguages();
    }

    public MemberPreferredInterests getMemberPreferredInterests() {
        return memberPreferredInterests;
    }

    public MemberPreferredCountries getMemberPreferredCountries() {
        return memberPreferredCountries;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public int calculateAge(final LocalDate currentDateTime) {
        return Period.between(birthday, currentDateTime).getYears();
    }

    public void changeDescription(final String description) {
        this.description = description;
    }

    public void changeProfileImageUrl(final String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

}
