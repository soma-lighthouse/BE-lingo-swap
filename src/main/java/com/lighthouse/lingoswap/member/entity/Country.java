package com.lighthouse.lingoswap.member.entity;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Country extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 2)
    private String code;
}
