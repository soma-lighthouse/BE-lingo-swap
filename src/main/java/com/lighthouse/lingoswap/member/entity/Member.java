package com.lighthouse.lingoswap.member.entity;

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
    private List<UsedLanguage> usedLanguages = new ArrayList<>();

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

    private Member(final Gender gender,
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

    public static Member of(Gender gender, LocalDate birthday, String name, String description,
                            String profileImage, String email, Country region) {
        return new Member(gender, birthday, name, description, profileImage, email, region);
    }

    public int calculateAge() {
        return Period.between(birthday, LocalDate.now()).getYears();
    }

    public void addUsedLanguage(List<UsedLanguage> usedLanguages) {
        this.usedLanguages = usedLanguages;
    }
}
