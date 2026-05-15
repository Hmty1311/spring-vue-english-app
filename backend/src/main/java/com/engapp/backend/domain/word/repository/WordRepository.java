package com.engapp.backend.domain.word.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engapp.backend.domain.word.model.Word;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    List<Word> findByUserId(Long userId);

}