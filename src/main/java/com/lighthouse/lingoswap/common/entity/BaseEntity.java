package com.lighthouse.lingoswap.common.entity;

import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
