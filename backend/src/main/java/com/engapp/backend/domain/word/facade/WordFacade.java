package com.engapp.backend.domain.word.facade;

import java.util.List;

import org.springframework.stereotype.Component;

import com.engapp.backend.domain.word.model.Word;
import com.engapp.backend.domain.word.service.WordService;
import com.engapp.backend.web.word.dto.WordResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WordFacade {

    private final WordService wordService;

    public List<WordResponse> getWords(Long userId){

        List<Word> words = wordService.getWords(userId);

        return words.stream()
            .map(word -> new WordResponse(
                word.getId(),
                word.getWord(),
                word.getMeaning(),
                word.getExample(),
                word.getMemorized(),
                word.getCreatedDate(),
                word.getUpdatedDate()
            ))
            .toList();
    }


}
