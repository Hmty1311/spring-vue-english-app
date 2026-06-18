package com.engapp.backend.domain.word.facade;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.engapp.backend.domain.word.model.Word;
import com.engapp.backend.domain.word.service.WordService;
import com.engapp.backend.web.word.dto.WordListResponse;
import com.engapp.backend.web.word.dto.WordResponse;
import com.engapp.backend.web.word.request.WordCreateRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WordFacade {

    private final WordService wordService;

    public WordListResponse getWords(Long userId, String keyword, Boolean memorized, int page, int size){

        Page<Word> words = wordService.getWords(userId, keyword, memorized, page, size);

        List<WordResponse> content = 
            words.getContent()
                    .stream()
                    .map(word ->  new WordResponse(
                        word.getId(),
                        word.getWord(),
                        word.getMeaning(),
                        word.getExample(),
                        word.getMemorized(),
                        word.getCreatedDate(),
                        word.getUpdatedDate()
                    ))
                    .toList();
        
        return new WordListResponse(
            content,
            words.getNumber(),
            words.getSize(),
            words.getTotalElements(),
            words.getTotalPages()
        );
    }

    public void createWord(Long userId, WordCreateRequest request){
        wordService.createWord(userId,request);
    }

    public WordResponse getWord(
        Long id, Long userId
    ){
        Word word =
            wordService.getWord(id, userId);

        return new WordResponse(
            word.getId(),
            word.getWord(),
            word.getMeaning(),
            word.getExample(),
            word.getMemorized(),
            word.getCreatedDate(),
            word.getUpdatedDate()
        );
    }

    public void updateWord(
        Long id,
        Long userId,
        WordCreateRequest request
    ){
        wordService.updateWord(id, userId, request);
    }

}
