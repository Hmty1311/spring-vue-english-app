package com.engapp.backend.domain.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engapp.backend.domain.quiz.model.QuizResult;

@Repository
public interface QuizResultRepository
        extends JpaRepository<QuizResult, Long> {

}