package com.engapp.backend.web.word;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.engapp.backend.common.util.SecurityUtil;
import com.engapp.backend.domain.word.facade.WordFacade;
import com.engapp.backend.web.word.dto.WordListResponse;
import com.engapp.backend.web.word.dto.WordResponse;
import com.engapp.backend.web.word.request.WordCreateRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/words")
public class WordController {

    private final WordFacade wordFacade;

    @GetMapping
    public WordListResponse getWords(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) Boolean memorized,
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size
    ){

        Long userId = SecurityUtil.getLoginUserId();

        return wordFacade.getWords(userId, keyword, memorized, page, size);
    }

    @PostMapping
    public void createdWord(@Valid @RequestBody WordCreateRequest request){
        Long userId = SecurityUtil.getLoginUserId();
        wordFacade.createWord(userId,request);
    }
    
    @GetMapping("/{id}")
    public WordResponse getWord(@PathVariable Long id){
        Long userId =
            SecurityUtil.getLoginUserId();

        return wordFacade.getWord(id, userId);
    }

    @PutMapping("/{id}")
    public void updateWord(
            @PathVariable Long id, 
            @Valid @RequestBody WordCreateRequest request){
                Long userId =
                    SecurityUtil.getLoginUserId();

            wordFacade.updateWord(
                id,
                userId,
                request
            );
    }

    @DeleteMapping("/{id}")
    public void deleteWord(@PathVariable Long id){
        Long userId = SecurityUtil.getLoginUserId();
        wordFacade.deleteWord(id, userId);
    }

}
