package com.engapp.backend.web.quiz.dto;

import java.util.List;

public record QuizResponse (
    long wordId,
    String word,
    List<String> choices
){
}
