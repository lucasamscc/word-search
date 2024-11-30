package com.project.wordsearch.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameDTO {
    private Long id;
    private Long studentId;
    private Long wordSearchId;
    private String foundWords;
    private LocalDateTime gameDate;
}
