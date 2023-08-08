package com.lighthouse.lingoswap.member.entity;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @OneToMany(mappedBy = "member")
    private final List<UsedLanguage> usedLanguages = new ArrayList<>();

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Boolean isValid;
    private LocalDate birthday;
    private String name;
    private String description;
    private String profileImage;
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Country region;

    @Builder
    public Member(final Gender gender,
                  final LocalDate birthday,
                  final String name,
                  final String description,
                  final String profileImage,
                  final String email,
                  final Country region) {
        this.gender = gender;
        this.birthday = birthday;
        this.name = name;
        this.description = description;
        this.profileImage = profileImage;
        this.email = email;
        this.region = region;
    }
}
