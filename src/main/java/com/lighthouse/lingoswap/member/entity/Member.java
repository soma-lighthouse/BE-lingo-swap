package com.lighthouse.lingoswap.member.entity;

import com.lighthouse.lingoswap.common.entity.DateBasicEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends DateBasicEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    public Member(String email) {
        this.email = email;
    }

    @Column(length = 1)
    private String gender;

    private String description;

    private String profileImage;
}
