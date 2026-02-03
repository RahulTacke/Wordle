package Spanning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Graph {
  private HashMap<HashSet<Character>, HashSet<HashSet<Character>>> g = new HashMap<>();

  public Graph(Set<HashSet<Character>> words) {
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
