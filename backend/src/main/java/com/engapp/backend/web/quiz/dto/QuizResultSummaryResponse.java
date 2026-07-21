package com.engapp.backend.web.quiz.dto;

import java.util.List;

public record QuizResultSummaryResponse(
    int total,
    int correct,
    int accuracy,
    List<QuizWrongAnswerResponse> wrongAnswers
) {
}