package com.lighthouse.lingoswap.member.domain.model;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AuthDetails authDetails;

    @Column(name = "region")
    private String region;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthday;
    private String description;
    private String name;
    private String profileImageUri;

    @Builder
    public Member(final LocalDate birthday, final String name, final String description, final String profileImageUri, final Gender gender, final String username, final String uuid, final Role role, final String region) {
        this.birthday = birthday;
        this.name = name;
        this.description = description;
        this.profileImageUri = profileImageUri;
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

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUri() {
        return profileImageUri;
    }

    public Gender getGender() {
        return gender;
    }

    public int calculateAge(final LocalDate currentDate) {
        return Period.between(birthday, currentDate).getYears();
    }

    public void changeDescription(final String description) {
        this.description = description;
    }

    public void changeProfileImageUri(final String profileImageUri) {
        this.profileImageUri = profileImageUri;
    }

}
