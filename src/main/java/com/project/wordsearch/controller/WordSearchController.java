package com.project.wordsearch.controller;

import com.project.wordsearch.model.WordSearch;
import com.project.wordsearch.service.WordSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wordsearch")
public class WordSearchController {

    private final WordSearchService wordSearchService;

    @Autowired
    public WordSearchController(WordSearchService wordSearchService) {
        this.wordSearchService = wordSearchService;
    }

    @GetMapping
    public List<WordSearch> getAllWordSearches() {
        return wordSearchService.getAllWordSearches();
    }

    @GetMapping("/{id}")
    public Optional<WordSearch> getWordSearchById(@PathVariable Long id) {
        return wordSearchService.getWordSearchById(id);
    }

    @PostMapping
    public WordSearch createWordSearch(@RequestBody WordSearch wordSearch) {
        return wordSearchService.createWordSearch(wordSearch);
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
