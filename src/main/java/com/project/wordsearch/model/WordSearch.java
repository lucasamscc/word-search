package com.project.wordsearch.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "wordsearch")
public class WordSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_teacher", nullable = false)
    private User teacher;

    @Column(name = "grid", columnDefinition = "TEXT", nullable = false)
    private String grid;

    @OneToMany(mappedBy = "wordSearch", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Word> words;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
