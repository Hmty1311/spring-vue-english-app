package com.engapp.backend.domain.word.service;

import static java.time.LocalDateTime.now;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.engapp.backend.domain.word.model.Word;
import com.engapp.backend.domain.word.repository.WordRepository;
import com.engapp.backend.web.word.request.WordCreateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class WordService {

    private final WordRepository wordRepository;

    @Transactional(readOnly = true)
    public Page<Word> getWords(Long userId, String keyword, Boolean memorized, int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        boolean hasKeyword = keyword != null && !keyword.isBlank();

        boolean hasMemorized = memorized != null;

        if(hasKeyword && hasMemorized){
            
            return wordRepository.findByUserIdAndWordContainingAndMemorized(
                    userId, 
                    keyword, 
                    memorized,
                    pageable
            );
        }

        if(hasKeyword){
            return wordRepository.findByUserIdAndWordContaining(
                    userId,
                    keyword,
                    pageable
            );
        }

        if(hasMemorized){
            return wordRepository.findByUserIdAndMemorized(
                    userId, 
                    memorized, 
                    pageable
            );
        }

        return wordRepository.findByUserId(userId, pageable);
        
    }

    @Transactional
    public void createWord(
            Long userId,
            WordCreateRequest request
    ) {

        LocalDateTime now = now();

        Word word = Word.builder()
                .userId(userId)
                .word(request.word())
                .meaning(request.meaning())
                .example(request.example())
                .memorized(
                        Boolean.TRUE.equals(request.memorized())
                )
                .createdDate(now)
                .updatedDate(now)
                .build();

        wordRepository.save(word);
    }

    @Transactional(readOnly = true)
    public Word getWord(
        Long id,
        Long userId
    ){
        return wordRepository
            .findByIdAndUserId(
                id,
                 userId
            )
            .orElseThrow(() ->
                new RuntimeException("Word not found"));
    }

    @Transactional
    public void updateWord(
            Long id,
            Long userId,
            WordCreateRequest request
    ){

        log.debug("update start");
        Word word =
            wordRepository
                .findByIdAndUserId(
                    id, 
                    userId
                )
                .orElseThrow(() ->
                    new RuntimeException("Word not found"));

        word.setWord(request.word());
        word.setMeaning(request.meaning());
        word.setExample(request.example());
        word.setMemorized(
            Boolean.TRUE.equals(
                request.memorized()
            )
        );
        word.setUpdatedDate(LocalDateTime.now());
        wordRepository.save(word);
    }

    @Transactional
    public  void deleteWord(Long id, Long userId){

        Word word = wordRepository.findByIdAndUserId(
            id, 
            userId)
            .orElseThrow(() -> new RuntimeException("Word not found"));
    
        wordRepository.delete(word);

    }

}
