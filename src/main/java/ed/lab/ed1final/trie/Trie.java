package ed.lab.ed1final.trie;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Trie {

    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        int wordCount = 0;
        int prefixCount = 0;
    }

    private final TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.children.computeIfAbsent(c, k -> new TrieNode());
            node.prefixCount++;
        }
        node.wordCount++; // <--- ¡CLAVE! Incrementa solo en el nodo final
    }

    public int countWordsEqualTo(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.children.get(c);
            if (node == null) return 0;
        }
        return node.wordCount; // <--- Devuelve correctamente la cantidad exacta
    }

    public int countWordsStartingWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            node = node.children.get(c);
            if (node == null) return 0;
        }
        return node.prefixCount + node.wordCount;
    }

    public void erase(String word) {
        if (countWordsEqualTo(word) == 0) return;

        TrieNode node = root;
        for (char c : word.toCharArray()) {
            TrieNode next = node.children.get(c);
            next.prefixCount--;
            node = next;
        }
        node.wordCount--;
    }
}
