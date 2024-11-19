package com.project.wordsearch.repository;

import com.project.wordsearch.model.Word;
import com.project.wordsearch.model.WordSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {
    boolean existsByWordSearchAndText(WordSearch wordSearch, String word);
}
