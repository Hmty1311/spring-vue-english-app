package com.engapp.backend.web.word;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engapp.backend.common.util.SecurityUtil;
import com.engapp.backend.domain.word.facade.WordFacade;
import com.engapp.backend.web.word.dto.WordResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/words")
public class WordController {

    private final WordFacade wordFacade;

    @GetMapping
    public List<WordResponse> getWords(){

        Long userId = SecurityUtil.getLoginUserId();

        return wordFacade.getWords(userId);
    }

}
