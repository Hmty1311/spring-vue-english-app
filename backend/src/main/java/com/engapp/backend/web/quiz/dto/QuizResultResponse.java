package com.engapp.backend.web.quiz.dto;

public record QuizResultResponse(

    boolean correct,

    String correctMeaning

) {
}
