package com.engapp.backend.web.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuizResultRequest(

    @NotNull
    Long sessionId,

    @NotNull
    Long wordId,

    @NotBlank
    String selectedMeaning

) {
}
