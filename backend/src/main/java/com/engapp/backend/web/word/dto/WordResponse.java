package com.engapp.backend.web.word.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WordResponse {

    private Long wordId;

    private String word;

    private String meaning;

    private String example;

    private Boolean memorized;

    private LocalDateTime createdDate;
    
    private LocalDateTime modifiedDate;

}
