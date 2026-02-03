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
      g.put(word, otherSet);
    });
  }

  public HashSet<HashSet<Character>> neighbors(HashSet<Character> word) {
    return g.get(word);
  }

  public boolean areNeighbors(HashSet<Character> word1, HashSet<Character> word2) {
    return g.get(word1).contains(word2);
  }

  public void removeWord(HashSet<Character> word) {
    g.get(word).forEach(neighbor -> g.get(neighbor).remove(word));
    g.remove(word);
  }

  public void removeEdge(HashSet<Character> word1, HashSet<Character> word2) {
    g.get(word1).remove(word2);
    g.get(word2).remove(word1);
  }
}
