package com.engapp.backend.domain.quiz.facade;

import java.util.List;

import org.springframework.stereotype.Component;

import com.engapp.backend.domain.quiz.service.QuizService;
import com.engapp.backend.web.quiz.dto.QuizResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuizFacade {

    private final QuizService quizService;

    public List<QuizResponse> getQuizzes(
            Long userId,
            Integer count
    ){
        return quizService.getQuizzes(
            userId,
            count
        );
    }
}
