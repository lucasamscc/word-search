package com.project.wordsearch.repository;

import com.project.wordsearch.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
