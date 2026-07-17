package com.engapp.backend.domain.quiz.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quiz_sessions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    public static QuizSession create(Long userId) {

        QuizSession session = new QuizSession();

        LocalDateTime now = LocalDateTime.now();

        session.userId = userId;
        session.startedAt = now;
        session.createdDate = now;
        session.updatedDate = now;

        return session;
    }

    public void finish() {
        LocalDateTime now = LocalDateTime.now();

        this.finishedAt = now;
        this.updatedDate = now;
    }
}