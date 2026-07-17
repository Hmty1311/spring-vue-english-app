package com.engapp.backend.domain.quiz.service;

import com.engapp.backend.common.exception.ResourceNotFoundException;
import com.engapp.backend.domain.quiz.model.QuizResult;
import com.engapp.backend.domain.quiz.model.QuizSession;
import com.engapp.backend.domain.quiz.repository.QuizResultRepository;
import com.engapp.backend.domain.quiz.repository.QuizSessionRepository;
import com.engapp.backend.domain.word.model.Word;
import com.engapp.backend.domain.word.repository.WordRepository;
import com.engapp.backend.web.quiz.dto.QuizQuestionResponse;
import com.engapp.backend.web.quiz.dto.QuizResultRequest;
import com.engapp.backend.web.quiz.dto.QuizResultResponse;
import com.engapp.backend.web.quiz.dto.QuizStartResponse;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizService {

    private final WordRepository wordRepository;

    private final QuizResultRepository quizResultRepository;

    private final QuizSessionRepository quizSessionRepository;


    // public List<QuizQuestionResponse> getQuizzes(
    //     Long userId,
    //     Integer count
    // ){

    //     List<Word> quizWords = 
    //         wordRepository.findRandomWords(
    //             userId,
    //             PageRequest.of(0, count)
    //         );
        
    //     List<Word> allWords =
    //         wordRepository.findByUserIdAndDeletedDateIsNull(
    //             userId
    //         );
        
    //    return quizWords.stream()
    //         .map(word -> 
    //                     new QuizQuestionResponse(
    //                                 word.getId(),
    //                                 word.getWord(),
    //                                 generateChoices(
    //                                     word,
    //                                     allWords
    //                                 )
    //                     )
    //         )
    //         .toList();
    // }

    private List<String> generateChoices(
        Word answerWord,
        List<Word> allWords
    ){
        List<String> choices = 
            new ArrayList<>();
        
        // 正解
        choices.add(answerWord.getMeaning());

        // ダミー選択肢
        allWords.stream()
            .filter(word -> !word.getId().equals(answerWord.getId()))
            .map(Word::getMeaning)
            .distinct()
            .limit(3)
            .forEach(choices::add);
        
            Collections.shuffle(choices);
            return choices;
    }

    @Transactional
    public QuizResultResponse registerQuizResult(
            Long userId,
            QuizResultRequest request
    ) {

        if(!quizSessionRepository.existsByIdAndUserId(
            request.sessionId(),
            userId
        )) {
            throw new ResourceNotFoundException("QUIZ-01");
        }

        Word word = wordRepository
                .findByIdAndUserId(
                        request.wordId(),
                        userId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException("WORD-001")
                );

        boolean correct =
                word.getMeaning()
                        .equals(request.selectedMeaning());

        QuizResult result =
                QuizResult.create(
                        request.sessionId(),
                        word.getId(),
                        correct
                );

        quizResultRepository.save(result);

        return new QuizResultResponse(correct, word.getMeaning());
    }

    @Transactional
    public QuizStartResponse startQuiz(
        Long userId,
        Integer count
    ){
        // セッション作成
        QuizSession session =
            QuizSession.create(userId);

        quizSessionRepository.save(session);

        // 問題生成
        List<QuizQuestionResponse> questions =
            createQuestions(
                userId,
                count
            );

        return new QuizStartResponse(
            session.getId(),
            questions
        );
        
    }

    private List<QuizQuestionResponse> createQuestions(
        Long userId,
        Integer count
    ) {

        List<Word> quizWords = 
            wordRepository.findRandomWords(
                userId,
                PageRequest.of(0, count)
            );
        
        List<Word> allWords =
            wordRepository.findByUserIdAndDeletedDateIsNull(
                userId
            );
        
       return quizWords.stream()
            .map(word -> 
                        new QuizQuestionResponse(
                                    word.getId(),
                                    word.getWord(),
                                    generateChoices(
                                        word,
                                        allWords
                                    )
                        )
            )
            .toList();
    }
}
