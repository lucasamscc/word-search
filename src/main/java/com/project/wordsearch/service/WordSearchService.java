package com.project.wordsearch.service;

import com.project.wordsearch.model.User;
import com.project.wordsearch.model.WordSearch;
import com.project.wordsearch.repository.WordSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class WordSearchService {

    private final WordSearchRepository wordSearchRepository;

    @Autowired
    public WordSearchService(WordSearchRepository wordSearchRepository) {
        this.wordSearchRepository = wordSearchRepository;
    }

    @Autowired
    public UserService userService;

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

    /**
     * Creates a new word search puzzle with the specified name, words, and teacher ID.
     */
    public WordSearch createWordSearch(String name, List<String> words, Long teacherId) {
        User teacher = userService.getUserById(teacherId);
        WordSearch wordSearch = new WordSearch();
        wordSearch.setName(name);
        wordSearch.setTeacher(teacher);
        wordSearch.setWords(words);

        String grid = generateGrid(words);
        wordSearch.setGrid(grid);
        wordSearch.setCreatedAt(LocalDateTime.now());
        wordSearchRepository.save(wordSearch);

        return wordSearch;
    }

    /**
     * Generates a word search grid by placing the given words randomly within a 10x10 grid.
     */
    public String generateGrid(List<String> words) {
        int size = 10; // Grid size
        char[][] grid = new char[size][size];
        Random random = new Random();

        // Initialize the grid with '0' to represent empty spaces
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = '0';
            }
        }

        // Insert each word into the grid
        for (String word : words) {
            boolean inserted = false;
            int attempts = 0;

            // Try to place the word until successful or attempts exceed the limit
            while (!inserted && attempts < 100) {
                int row = random.nextInt(size);
                int col = random.nextInt(size);
                String direction = random.nextBoolean() ? "horizontal" : "vertical";

                if (direction.equals("horizontal") && col + word.length() <= size) {
                    if (isSpaceAvailable(grid, word, row, col, "horizontal")) {
                        placeWord(grid, word, row, col, "horizontal");
                        inserted = true;
                    }
                } else if (direction.equals("vertical") && row + word.length() <= size) {
                    if (isSpaceAvailable(grid, word, row, col, "vertical")) {
                        placeWord(grid, word, row, col, "vertical");
                        inserted = true;
                    }
                }
                attempts++;
            }
            if (!inserted) {
                System.out.println("Failed to place word: " + word);
            }
        }

        // Fill the remaining empty spaces with random letters
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == '0') {
                    grid[i][j] = (char) ('A' + random.nextInt(26));
                }
            }
        }

        // Convert the final grid to a string and return it
        String finalGrid = gridToString(grid);
        System.out.println(finalGrid);
        return finalGrid;
    }

    /**
     * Checks if there is enough space to place a word in the grid.
     */
    private boolean isSpaceAvailable(char[][] grid, String word, int row, int col, String direction) {
        if (direction.equals("horizontal")) {
            for (int i = 0; i < word.length(); i++) {
                if (grid[row][col + i] != '0' && grid[row][col + i] != word.charAt(i)) {
                    return false;
                }
            }
        } else { // Vertical
            for (int i = 0; i < word.length(); i++) {
                if (grid[row + i][col] != '0' && grid[row + i][col] != word.charAt(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Places a word into the grid at the specified starting position and direction.
     */
    private void placeWord(char[][] grid, String word, int row, int col, String direction) {
        if (direction.equals("horizontal")) {
            for (int i = 0; i < word.length(); i++) {
                grid[row][col + i] = word.charAt(i);
            }
        } else { // Vertical
            for (int i = 0; i < word.length(); i++) {
                grid[row + i][col] = word.charAt(i);
            }
        }
    }

    /**
     * Converts the grid array into a string representation.
     */
    private String gridToString(char[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : grid) {
            for (char cell : row) {
                sb.append(cell);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
