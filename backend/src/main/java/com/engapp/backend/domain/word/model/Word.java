package com.engapp.backend.domain.word.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "words")
@Data
public class Word {

    @Id
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
