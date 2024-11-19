package com.project.wordsearch.repository;

import com.project.wordsearch.model.WordSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordSearchRepository extends JpaRepository<WordSearch, Long> {
}
