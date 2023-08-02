package com.lighthouse.lingoswap.member.entity;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1)
    private String gender;

    private Boolean isValid;
    private LocalDate birthday;
    private String name;
    private String description;
    private String profileImage;
    private String email;

    @Builder
    public Member(Long id, String gender, Boolean isValid, LocalDate birthday, String name, String description, String profileImage, String email) {
        this.id = id;
        this.gender = gender;
        this.isValid = isValid;
        this.birthday = birthday;
        this.name = name;
        this.description = description;
        this.profileImage = profileImage;
        this.email = email;
    }

    public Member(String email) {
        this.email = email;
    }

}
