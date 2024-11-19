package com.project.wordsearch.dto;

import lombok.Data;

@Data
public class WordValidationRequestDTO {
    private String word;
    private Long wordSearchId;
}
