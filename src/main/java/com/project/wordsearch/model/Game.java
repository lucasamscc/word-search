package com.project.wordsearch.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "wordsearch_id", nullable = false)
    private WordSearch wordSearch;

    @Column(name = "found_words", columnDefinition = "TEXT")
    private String foundWords;

    @Column(name = "game_date", nullable = false)
    private LocalDateTime gameDate;
}
