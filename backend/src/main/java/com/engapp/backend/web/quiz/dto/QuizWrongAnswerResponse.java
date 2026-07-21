package com.engapp.backend.web.quiz.dto;

public record QuizWrongAnswerResponse(
    String word,
    String meaning
) {
}