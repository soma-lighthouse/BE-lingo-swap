package com.lighthouse.lingoswap.question.entity;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import com.lighthouse.lingoswap.member.entity.Interests;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Category extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Interests> interests = new ArrayList<>();
}
