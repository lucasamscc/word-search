package com.project.wordsearch.service;

import com.project.wordsearch.model.Game;
import com.project.wordsearch.model.User;
import com.project.wordsearch.model.WordSearch;
import com.project.wordsearch.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Retrieves all games from the database.
     */
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    /**
     * Retrieves a game by its ID.
     */
    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }

    /**
     * Creates a new game entry for a student and a word search puzzle.
     * The game date is automatically set to the current time.
     */
    public Game createGame(Game game) {
        game.setGameDate(LocalDateTime.now()); // Automatically set the current time
        return gameRepository.save(game);
    }

    /**
     * Updates an existing game by its ID.
     */
    public Game updateGame(Long id, Game updatedGame) {
        if (gameRepository.existsById(id)) {
            updatedGame.setId(id);
            return gameRepository.save(updatedGame);
        }
        return null;
    }

    /**
     * Deletes a game entry by its ID.
     */
    public boolean deleteGame(Long id) {
        if (gameRepository.existsById(id)) {
            gameRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Records a word as found in the game and updates the list of found words.
     */
    public Game recordFoundWord(Long gameId, String word) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            String foundWords = game.getFoundWords();
            if (foundWords == null || foundWords.isEmpty()) {
                foundWords = word;
            } else {
                foundWords += "," + word; // Append the new found word
            }
            game.setFoundWords(foundWords);
            return gameRepository.save(game);
        }
        return null; // Game not found
    }

    /**
     * Checks if a student has completed the game by finding all words.
     * This would compare the list of found words with the word list in the word search puzzle.
     */
    public boolean isGameCompleted(Long gameId) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            WordSearch wordSearch = game.getWordSearch();
            List<String> wordList = wordSearch.getWords();
            String[] foundWordsArray = game.getFoundWords().split(",");
            return wordList.size() == foundWordsArray.length; // Check if all words are found
        }
        return false; // Game not found
    }
}
