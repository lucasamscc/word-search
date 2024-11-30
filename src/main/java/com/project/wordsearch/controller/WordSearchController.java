package com.project.wordsearch.controller;

import com.project.wordsearch.dto.WordSearchRequestDTO;
import com.project.wordsearch.model.WordSearch;
import com.project.wordsearch.service.UserService;
import com.project.wordsearch.service.WordSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wordsearches")
public class WordSearchController {

    private final WordSearchService wordSearchService;

    @Autowired
    public WordSearchController(WordSearchService wordSearchService) {
        this.wordSearchService = wordSearchService;
    }

    @Autowired
    public UserService userService;

    @GetMapping
    public List<WordSearch> getAllWordSearches() {
        return wordSearchService.getAllWordSearches();
    }

    @GetMapping("/{id}")
    public WordSearch getWordSearchById(@PathVariable Long id) {
        return wordSearchService.getWordSearchById(id)
                .orElseThrow(() -> new RuntimeException("Caça-palavras não encontrado!"));
    }

    @PostMapping
    public WordSearch createWordSearch(@RequestBody WordSearchRequestDTO request) {
        List<String> words = request.getWords();
        String name = request.getName();
        return wordSearchService.createWordSearch(name, words, request.getTeacherId());
    }

    @PutMapping("/{id}")
    public WordSearch updateWordSearch(@PathVariable Long id, @RequestBody WordSearch wordSearch) {
        return wordSearchService.updateWordSearch(id, wordSearch);
    }

    @DeleteMapping("/{id}")
    public boolean deleteWordSearch(@PathVariable Long id) {
        return wordSearchService.deleteWordSearch(id);
    }
}
