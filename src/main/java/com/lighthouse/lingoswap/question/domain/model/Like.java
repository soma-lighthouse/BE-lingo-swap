package com.lighthouse.lingoswap.question.domain.model;

import com.lighthouse.lingoswap.question.exception.LikeRangeException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Like {

    @Column(name = "likes")
    private Long value;

    Long getValue() {
        return value;
    }

    public void addOneLike() {
        value++;
    }

    public void subtractOneLike() {
        validateRange();
        value--;
    }

    private void validateRange() {
        if (this.value == 0) {
            throw new LikeRangeException();
        }
    }

}
