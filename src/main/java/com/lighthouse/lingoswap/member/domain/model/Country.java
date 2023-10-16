package com.lighthouse.lingoswap.member.domain.model;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Country extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 2)
    private String code;

    public Country(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
