package com.engapp.backend.web.word;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engapp.backend.domain.word.facade.WordFacade;
import com.engapp.backend.web.word.dto.WordResponse;

@RestController
public class WordController {

    private final WordFacade wordFacade;

    public WordController(WordFacade wordFacade){
        this.wordFacade = wordFacade;
    }

    @GetMapping("/api/words")
    public List<WordResponse> getWords(){

        //TODO: 本来はJWTから取得
        Long userId = 1l;

        return wordFacade.getWords(userId);
    }

}
