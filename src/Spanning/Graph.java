package Spanning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Graph {
  private HashMap<HashSet<Character>, HashSet<HashSet<Character>>> g = new HashMap<>();

  public Graph(HashSet<HashSet<Character>> words) {
    words.forEach((word) -> {
      HashSet<HashSet<Character>> otherSet = new HashSet<>();
      g.keySet().forEach(other -> {
        HashSet<Character> word1 = (HashSet<Character>) word.clone();
        word1.retainAll(other);
        if (word1.isEmpty()) {
          otherSet.add(other);
          g.get(other).add(word);
        }
      });
    });
  }
}
