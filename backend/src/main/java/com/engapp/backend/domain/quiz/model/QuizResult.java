package com.engapp.backend.domain.quiz.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quiz_results")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quiz_session_id", nullable = false)
    private Long quizSessionId;

    @Column(name = "word_id", nullable = false)
    private Long wordId;

    @Column(name = "correct", nullable = false)
    private Boolean correct;

    @Column(name = "answered_at", nullable = false)
    private LocalDateTime answeredAt;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    public static QuizResult create(
            Long quizSessionId,
            Long wordId,
            Boolean correct
    ) {

        QuizResult result = new QuizResult();

        result.quizSessionId = quizSessionId;
        result.wordId = wordId;
        result.correct = correct;

        LocalDateTime now = LocalDateTime.now();

        result.answeredAt = now;
        result.createdDate = now;
        result.updatedDate = now;

        return result;
    }
}