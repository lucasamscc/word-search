package com.project.wordsearch.service;

import com.project.wordsearch.model.Word;
import com.project.wordsearch.model.WordSearch;
import com.project.wordsearch.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WordService {

    private final WordRepository wordRepository;
    private final WordSearchService wordSearchService;

    @Autowired
    public WordService(WordRepository wordRepository, WordSearchService wordSearchService) {
        this.wordRepository = wordRepository;
        this.wordSearchService = wordSearchService;
    }

    public List<Word> getAllWords() {
        return wordRepository.findAll();
    }

    public Optional<Word> getWordById(Long id) {
        return wordRepository.findById(id);
    }

    public Word createWord(Word word) {
        return wordRepository.save(word);
    }

    public Word updateWord(Long id, Word updatedWord) {
        if (wordRepository.existsById(id)) {
            updatedWord.setId(id);
            return wordRepository.save(updatedWord);
        }
        return null;
    }

    public boolean deleteWord(Long id) {
        if (wordRepository.existsById(id)) {
            wordRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean validateWord(String word, Long wordSearchId) {
        Optional<WordSearch> wordSearchOpt = wordSearchService.getWordSearchById(wordSearchId);

        if (wordSearchOpt.isEmpty()) {
            return false;
        }

        WordSearch wordSearch = wordSearchOpt.get();

        return wordRepository.existsByWordSearchAndText(wordSearch, word);
    }
}
