package com.engapp.backend.domain.word.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "words")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "word", nullable = false)
    private String word;

    @Column(name = "meaning", nullable = false)
    private String meaning;

    @Column(name = "example")
    private String example;

    @Column(name = "memorized", nullable = false)
    private Boolean memorized;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;
}