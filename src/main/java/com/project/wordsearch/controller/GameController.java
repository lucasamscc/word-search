package com.project.wordsearch.controller;

import com.project.wordsearch.dto.GameDTO;
import com.project.wordsearch.model.Game;
import com.project.wordsearch.model.User;
import com.project.wordsearch.model.WordSearch;
import com.project.wordsearch.service.GameService;
import com.project.wordsearch.service.UserService;
import com.project.wordsearch.service.WordSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @Autowired
    private WordSearchService wordSearchService;

    /**
     * Retrieves all games.
     */
    @GetMapping
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    /**
     * Retrieves a game by its ID.
     */
    @GetMapping("/{id}")
    public Optional<Game> getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    /**
     * Creates a new game for a student and a word search puzzle.
     */
    @PostMapping
    public Game createGame(@RequestBody GameDTO gameDTO) {
        // Carregar o User e WordSearch com base no id recebido no DTO
        User user = userService.getUserById(gameDTO.getStudentId());
        if (user == null) {
            throw new RuntimeException("User with ID " + gameDTO.getStudentId() + " not found");
        }

        Optional<WordSearch> wordSearch = wordSearchService.getWordSearchById(gameDTO.getWordSearchId());
        if (!wordSearch.isPresent()) {
            throw new RuntimeException("WordSearch with ID " + gameDTO.getWordSearchId() + " not found");
        }

        // Criar o Game com os objetos completos
        Game game = new Game();
        game.setStudent(user);  // Associa o objeto User
        game.setWordSearch(wordSearch.get());  // Associa o objeto WordSearch
        game.setFoundWords(gameDTO.getFoundWords());
        game.setGameDate(gameDTO.getGameDate());

        return gameService.createGame(game); // Salva o Game
    }


    /**
     * Updates an existing game.
     */
    @PutMapping("/{id}")
    public Game updateGame(@PathVariable Long id, @RequestBody Game game) {
        return gameService.updateGame(id, game);
    }

    /**
     * Deletes a game by its ID.
     */
    @DeleteMapping("/{id}")
    public boolean deleteGame(@PathVariable Long id) {
        return gameService.deleteGame(id);
    }

    /**
     * Registers a found word in the game.
     * The word will be added to the list of found words.
     */
    @PostMapping("/{gameId}/found-word")
    public Game recordFoundWord(@PathVariable Long gameId, @RequestParam String word) {
        return gameService.recordFoundWord(gameId, word);
    }

    /**
     * Checks if the game has been completed by the student.
     * Returns true if all words have been found.
     */
    @GetMapping("/{gameId}/completed")
    public boolean checkIfGameCompleted(@PathVariable Long gameId) {
        return gameService.isGameCompleted(gameId);
    }
}
