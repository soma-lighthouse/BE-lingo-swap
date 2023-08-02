package com.lighthouse.lingoswap.member.entity;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
public class Member extends BaseEntity {

    @OneToMany(mappedBy = "member")
    private final List<UsedLanguage> usedLanguages = new ArrayList<>();

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthday;
    private String name;
    private String description;
    private String profileImage;
    private String email;
    private String region;
    private Boolean isValid;

    @Builder
    public Member(final Gender gender,
                  final LocalDate birthday,
                  final String name,
                  final String description,
                  final String profileImage,
                  final String email,
                  final String region) {
        this.gender = gender;
        this.birthday = birthday;
        this.name = name;
        this.description = description;
        this.profileImage = profileImage;
        this.email = email;
        this.region = region;
    }
}
