package com.project.wordsearch.controller;

import com.project.wordsearch.dto.WordValidationRequestDTO;
import com.project.wordsearch.model.Game;
import com.project.wordsearch.service.GameService;
import com.project.wordsearch.service.WordSearchService;
import com.project.wordsearch.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private WordSearchService wordSearchService;

    @Autowired
    private WordService wordService;

    @GetMapping
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/{id}")
    public Optional<Game> getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    @PostMapping
    public Game createGame(@RequestBody Game game) {
        return gameService.createGame(game);
    }

    @PutMapping("/{id}")
    public Game updateGame(@PathVariable Long id, @RequestBody Game game) {
        return gameService.updateGame(id, game);
    }

    @DeleteMapping("/{id}")
    public boolean deleteGame(@PathVariable Long id) {
        return gameService.deleteGame(id);
    }

    @PostMapping("/validateWord")
    public ResponseEntity<?> validateWord(@RequestBody WordValidationRequestDTO request) {
        boolean isValid = wordService.validateWord(request.getWord(), request.getWordSearchId());

        return isValid
                ? ResponseEntity.ok().body("Palavra válida!")
                : ResponseEntity.badRequest().body("Palavra inválida.");
    }

}
