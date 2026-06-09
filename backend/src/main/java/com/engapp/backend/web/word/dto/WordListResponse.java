package com.engapp.backend.web.word.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WordListResponse {

    private List<WordResponse> content;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

}
