package com.lighthouse.lingoswap.likemember.domian.model;

import com.lighthouse.lingoswap.question.domain.model.Question;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
class LikedQuestion {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    LikedQuestion(final Question question) {
        this.question = question;
    }

    Long getQuestionId() {
        return question.getId();
    }

}
