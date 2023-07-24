package com.lighthouse.lingoswap.member.entity;

import com.lighthouse.lingoswap.common.entity.DateBasicEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Language extends DateBasicEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 2)
    private String code;

    private String description;
}
