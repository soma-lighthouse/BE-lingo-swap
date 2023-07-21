package com.lighthouse.lingoswap.member.entity;

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

public class Country {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 2)
    private String code;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
