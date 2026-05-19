package com.engapp.backend.domain.word.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.engapp.backend.domain.word.model.Word;
import com.engapp.backend.domain.word.repository.WordRepository;

@Service
public class WordService {

    private final WordRepository wordRepository;

    public WordService(WordRepository wordRepository){
        this.wordRepository = wordRepository;
    }

    @Transactional(readOnly = true)
    public List<Word> getWords(Long userId){
        
        return wordRepository.findByUserId(userId);
    }
}
