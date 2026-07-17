package com.engapp.backend.web.quiz.dto;

import java.util.List;

public record QuizQuestionResponse (
    long wordId,
    String word,
    List<String> choices
){
}
