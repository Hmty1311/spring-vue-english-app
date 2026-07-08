package com.engapp.backend.web.quiz;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.engapp.backend.common.util.SecurityUtil;
import com.engapp.backend.domain.quiz.facade.QuizFacade;
import com.engapp.backend.web.quiz.dto.QuizResponse;
import com.engapp.backend.web.quiz.dto.QuizResultRequest;
import com.engapp.backend.web.quiz.dto.QuizResultResponse;

import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizFacade quizFacade;

    @GetMapping
    public List<QuizResponse> getQuizzes(
        @RequestParam(defaultValue = "10")
        Integer count
    ){

        Long userId =
            SecurityUtil.getLoginUserId();

        return quizFacade.getQuizzes(
            userId,
            count
        );
    }

    @PostMapping("/result")
    public QuizResultResponse registerQuizResult(
        @Valid @RequestBody QuizResultRequest request
    ){
        Long userId = SecurityUtil.getLoginUserId();

        return quizFacade.registerQuizResult(userId, request);
    }

}
