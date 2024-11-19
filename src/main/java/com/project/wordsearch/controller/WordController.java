package com.project.wordsearch.controller;

import com.project.wordsearch.model.Word;
import com.project.wordsearch.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/words")
public class WordController {

    private final WordService wordService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping
    public List<Word> getAllWords() {
        return wordService.getAllWords();
    }

    @GetMapping("/{id}")
    public Optional<Word> getWordById(@PathVariable Long id) {
        return wordService.getWordById(id);
    }

    @PostMapping
    public Word createWord(@RequestBody Word word) {
        return wordService.createWord(word);
    }

    @PutMapping("/{id}")
    public Word updateWord(@PathVariable Long id, @RequestBody Word word) {
        return wordService.updateWord(id, word);
    }

    @DeleteMapping("/{id}")
    public boolean deleteWord(@PathVariable Long id) {
        return wordService.deleteWord(id);
    }
}
