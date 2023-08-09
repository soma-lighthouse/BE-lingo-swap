package com.lighthouse.lingoswap.member.entity;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1)
    private String gender;

    private boolean isValid;
    private LocalDate birthday;
    private String name;
    private String description;
    private String profileImage;
    private String email;

    public Member(String email) {
        this.email = email;
    }

    public Member(String name, String description, String profileImage) {
        this.name = name;
        this.description = description;
        this.profileImage = profileImage;
    }

}
