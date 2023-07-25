package com.lighthouse.lingoswap.common.entity;

import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class DateBasicEntity {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
