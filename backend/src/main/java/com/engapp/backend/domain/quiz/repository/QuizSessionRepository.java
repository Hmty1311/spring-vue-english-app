package com.engapp.backend.domain.quiz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.engapp.backend.domain.quiz.model.QuizSession;

public interface QuizSessionRepository
        extends JpaRepository<QuizSession, Long> {
               
        boolean existsByIdAndUserId( Long sessionId, Long userId
        );

}

