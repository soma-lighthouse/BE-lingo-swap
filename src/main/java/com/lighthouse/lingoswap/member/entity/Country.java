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

public class Country extends DateBasicEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 2)
    private String code;
}
