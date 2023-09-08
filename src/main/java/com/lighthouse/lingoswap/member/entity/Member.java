package com.lighthouse.lingoswap.member.entity;

import com.lighthouse.lingoswap.auth.entity.Auth;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate birthday;
    private String name;
    private String description;
    private String profileImageUri;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private Auth auth;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Country region;

    @OneToMany(mappedBy = "member")
    private List<UsedLanguage> usedLanguages = new ArrayList<>();

    public Member(final LocalDate birthday, final String name, final String description, final String profileImageUri, final Gender gender, final Auth auth, final Country region) {
        this.birthday = birthday;
        this.name = name;
        this.description = description;
        this.profileImageUri = profileImageUri;
        this.gender = gender;
        this.auth = auth;
        this.region = region;
    }

    public int calculateAge() {
        return Period.between(birthday, LocalDate.now()).getYears();
    }
}
