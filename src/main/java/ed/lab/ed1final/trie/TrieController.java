package ed.lab.ed1final.trie;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/trie")
public class TrieController {

    private final Trie trie;

    public TrieController(Trie trie) {
        this.trie = trie;
    }

    // POST /trie/{word}
    @PostMapping("/{word}")
    public ResponseEntity<?> insertWord(@PathVariable String word) {
        if (!isValidWord(word)) {
            return ResponseEntity.badRequest().body("Palabra inválida");
        }

        trie.insert(word);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 Created
    }

    // GET /trie/{word}/count
    @GetMapping("/{word}/count")
    public ResponseEntity<Map<String, Object>> countWordsEqualTo(@PathVariable String word) {
        int count = trie.countWordsEqualTo(word);
        return ResponseEntity.ok(Map.of(
                "word", word,
                "wordsEqualTo", count
        ));
    }

    // GET /trie/{prefix}/prefix
    @GetMapping("/{prefix}/prefix")
    public ResponseEntity<Map<String, Object>> countWordsWithPrefix(@PathVariable String prefix) {
        int count = trie.countWordsStartingWith(prefix);
        return ResponseEntity.ok(Map.of(
                "word", prefix,
                "wordsStartingWith", count
        ));
    }

    // DELETE /trie/{word}
    @DeleteMapping("/{word}")
    public ResponseEntity<?> deleteWord(@PathVariable String word) {
        trie.erase(word);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Validación: solo minúsculas, no vacías
    private boolean isValidWord(String word) {
        return word != null && !word.isBlank() && word.matches("[a-z]+");
    }
}
