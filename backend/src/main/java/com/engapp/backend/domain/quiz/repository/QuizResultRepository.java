package com.engapp.backend.domain.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engapp.backend.domain.quiz.model.QuizResult;

@Repository
public interface QuizResultRepository
        extends JpaRepository<QuizResult, Long> {

                List<QuizResult> findByQuizSessionId(
                Long quizSessionId
        );

}