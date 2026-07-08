package com.engapp.backend.domain.word.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.engapp.backend.domain.word.model.Word;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    Page<Word> findByUserId(Long userId, Pageable pageable);

    Page<Word> findByUserIdAndWordContaining(Long userId, String keyword, Pageable pageable);

    Page<Word> findByUserIdAndMemorized(Long userId, Boolean memorized, Pageable pageable);

    Page<Word> findByUserIdAndWordContainingAndMemorized(Long userId, String keyword, Boolean memorized, Pageable pageable);

    Optional<Word> findByIdAndUserId(Long id, Long userId);    

    @Query("""
        SELECT w
        FROM Word w
        WHERE w.userId = :userId
        AND w.deletedDate IS NULL
        ORDER BY function('RANDOM')
    """)
    List<Word> findRandomWords(
            Long userId,
            Pageable pageable
    );

    List<Word> findByUserIdAndDeletedDateIsNull(Long userId);
}