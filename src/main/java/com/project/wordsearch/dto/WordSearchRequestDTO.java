package com.project.wordsearch.dto;

import com.project.wordsearch.model.User;
import lombok.Data;

import java.util.List;

@Data
public class WordSearchRequestDTO {
    private String name;
    private List<String> words;
    private Long teacherId;
}