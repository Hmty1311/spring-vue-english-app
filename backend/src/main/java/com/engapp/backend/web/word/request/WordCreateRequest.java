package com.engapp.backend.web.word.request;

import jakarta.validation.constraints.NotBlank;

public record WordCreateRequest(

    @NotBlank(message = "Word is required")
    String word,

    @NotBlank(message = "Meaning is required")
    String meaning,

    String example,

    boolean memorized

){}