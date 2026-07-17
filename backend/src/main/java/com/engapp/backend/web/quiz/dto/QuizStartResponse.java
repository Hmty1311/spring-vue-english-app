package com.engapp.backend.web.quiz.dto;

import java.util.List;

public record QuizStartResponse(
    Long sessionId,
    List<QuizQuestionResponse> questions
) {

}
