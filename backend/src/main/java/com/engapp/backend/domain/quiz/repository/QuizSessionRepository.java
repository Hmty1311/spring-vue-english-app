package com.engapp.backend.domain.quiz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.engapp.backend.domain.quiz.model.QuizSession;
import com.engapp.backend.domain.word.model.Word;

public interface QuizSessionRepository
        extends JpaRepository<QuizSession, Long> {
        
        Optional<QuizSession> findByIdAndUserId(Long id, Long userId);    
               
        boolean existsByIdAndUserId( Long sessionId, Long userId
        );

}

