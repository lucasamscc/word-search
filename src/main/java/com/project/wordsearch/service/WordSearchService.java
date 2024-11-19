package com.project.wordsearch.service;

import com.project.wordsearch.model.Direction;
import com.project.wordsearch.model.Word;
import com.project.wordsearch.model.WordSearch;
import com.project.wordsearch.repository.WordSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.project.wordsearch.model.Direction.*;

@Service
public class WordSearchService {

    private final WordSearchRepository wordSearchRepository;

    @Autowired
    public WordSearchService(WordSearchRepository wordSearchRepository) {
        this.wordSearchRepository = wordSearchRepository;
    }

    public List<WordSearch> getAllWordSearches() {
        return wordSearchRepository.findAll();
    }

    public Optional<WordSearch> getWordSearchById(Long id) {
        return wordSearchRepository.findById(id);
    }

    public WordSearch updateWordSearch(Long id, WordSearch updatedWordSearch) {
        if (wordSearchRepository.existsById(id)) {
            updatedWordSearch.setId(id);
            return wordSearchRepository.save(updatedWordSearch);
        }
        return null;
    }

    public boolean deleteWordSearch(Long id) {
        if (wordSearchRepository.existsById(id)) {
            wordSearchRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public WordSearch createWordSearch(WordSearch wordSearch) {
        // Geração da grade do caça-palavras
        String grid = generateGrid(wordSearch.getWords());
        wordSearch.setGrid(grid);

        return wordSearchRepository.save(wordSearch);
    }

    private String generateGrid(List<Word> words) {
        int size = 10; // Tamanho fixo da grade, pode ser parametrizado
        char[][] grid = new char[size][size];

        // Inicializa a grade com espaços em branco
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = ' ';
            }
        }

        Random random = new Random();

        for (Word word : words) {
            boolean placed = false;
            while (!placed) {
                Direction direction = Direction.values()[random.nextInt(Direction.values().length)];
                int row = random.nextInt(size);
                int col = random.nextInt(size);

                if (canPlaceWord(grid, word.getText(), row, col, direction)) {
                    placeWord(grid, word.getText(), row, col, direction);
                    word.setDirection(Direction.valueOf(direction.name())); // Armazena a direção
                    placed = true;
                }
            }
        }

        return gridToString(grid);
    }

    private boolean canPlaceWord(char[][] grid, String word, int row, int col, Direction direction) {
        int size = grid.length;
        int wordLength = word.length();

        switch (direction) {
            case HORIZONTAL:
                if (col + wordLength > size) return false;
                for (int i = 0; i < wordLength; i++) {
                    if (grid[row][col + i] != ' ') return false;
                }
                break;
            case VERTICAL:
                if (row + wordLength > size) return false;
                for (int i = 0; i < wordLength; i++) {
                    if (grid[row + i][col] != ' ') return false;
                }
                break;
            case DIAGONAL:
                if (row + wordLength > size || col + wordLength > size) return false;
                for (int i = 0; i < wordLength; i++) {
                    if (grid[row + i][col + i] != ' ') return false;
                }
                break;
        }

        return true;
    }

    private void placeWord(char[][] grid, String word, int row, int col, Direction direction) {
        int size = grid.length;

        switch (direction) {
            case HORIZONTAL:
                for (int i = 0; i < word.length(); i++) {
                    grid[row][col + i] = word.charAt(i);
                }
                break;
            case VERTICAL:
                for (int i = 0; i < word.length(); i++) {
                    grid[row + i][col] = word.charAt(i);
                }
                break;
            case DIAGONAL:
                for (int i = 0; i < word.length(); i++) {
                    grid[row + i][col + i] = word.charAt(i);
                }
                break;
        }
    }

    private String gridToString(char[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                sb.append(grid[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
