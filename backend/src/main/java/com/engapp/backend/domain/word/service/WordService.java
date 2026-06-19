package com.engapp.backend.domain.word.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.engapp.backend.domain.user.model.User;
import com.engapp.backend.domain.user.service.LoginService;
import com.engapp.backend.domain.user.service.UserService;
import com.engapp.backend.domain.word.model.Word;
import com.engapp.backend.domain.word.repository.WordRepository;
import com.engapp.backend.web.word.request.WordCreateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WordService {

    private final WordRepository wordRepository;
    private final UserService userService;

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

        LocalDateTime now = LocalDateTime.now();

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

        System.out.println("update start");
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
