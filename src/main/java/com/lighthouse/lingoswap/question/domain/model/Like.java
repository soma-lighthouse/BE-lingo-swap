package com.lighthouse.lingoswap.question.domain.model;

import com.lighthouse.lingoswap.question.exception.LikeRangeException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
class Like {

    @Column(name = "likes")
    private Long value = 0L;

    Like(final Long value) {
        this.value = value;
    }

    Long getValue() {
        return value;
    }

    Like addOneLike() {
        return new Like(value + 1);
    }

    Like subtractOneLike() {
        validateRange();
        return new Like(value - 1);
    }

    private void validateRange() {
        if (this.value == 0) {
            throw new LikeRangeException();
        }
    }

}
