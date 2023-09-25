package com.lighthouse.lingoswap.member.entity;

import com.lighthouse.lingoswap.auth.entity.AuthDetails;
import com.lighthouse.lingoswap.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {

    @OneToMany(mappedBy = "member")
    private final List<UsedLanguage> usedLanguages = new ArrayList<>();

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate birthday;

    private String name;

    private String description;

    private String profileImageUri;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private AuthDetails authDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Country region;

    public Member(final LocalDate birthday, final String name, final String description, final String profileImageUri, final Gender gender, final AuthDetails authDetails, final Country region) {
        this.birthday = birthday;
        this.name = name;
        this.description = description;
        this.profileImageUri = profileImageUri;
        this.gender = gender;
        this.authDetails = authDetails;
        this.region = region;
    }

    public int calculateAge() {
        return Period.between(birthday, LocalDate.now()).getYears();
    }

    public void updateMember(LocalDate birthday, String name, String description, String profileImageUri, Gender gender, Country region) {
        if (birthday != null) this.birthday = birthday;
        if (name != null) this.name = name;
        if (description != null) this.description = description;
        if (profileImageUri != null) this.profileImageUri = profileImageUri;
        if (gender != null) this.gender = gender;
        if (region != null) this.region = region;
    }

    public void patchDescription(String description) {
        this.description = description;
    }
}
