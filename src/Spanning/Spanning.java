package Spanning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Spanning {
  public static void main(String[] args) {
    HashMap<HashSet<Character>, HashSet<String>> words = readList();
    Graph wordWeb = new Graph(words.keySet());
    HashSet<HashSet<HashSet<Character>>> res = new HashSet<>();
    System.out.println("Set Up.");
    int a = 0;
    for (HashSet<Character> word : words.keySet()) {
      a++;
      HashSet<HashSet<Character>> intersection = (HashSet<HashSet<Character>>) wordWeb.neighbors(word).clone();
      System.out.println("\nStarting " + a + " / " + words.size() + "\n" + intersection.size() + " Secondary Words\n");
      intersection.forEach(word2 -> {
        HashSet<HashSet<Character>> intersection2 = intersection(wordWeb.neighbors(word), wordWeb.neighbors(word2));
        intersection2.forEach(word3 -> {
          HashSet<HashSet<Character>> intersection3 = intersection(wordWeb.neighbors(word3), intersection2);
          intersection3.forEach(word4 -> {
            HashSet<HashSet<Character>> intersection4 = intersection(wordWeb.neighbors(word4), intersection3);
            intersection4.forEach(word5 -> {
              HashSet<HashSet<Character>> solution = new HashSet<>();
              System.out.println("\nSolution Found!");
              solution.add(word);
              System.out.println("Word 1: " + words.get(word));
              solution.add(word2);
              System.out.println("Word 2: " + words.get(word2));
              solution.add(word3);
              System.out.println("Word 3: " + words.get(word3));
              solution.add(word4);
              System.out.println("Word 4: " + words.get(word4));
              solution.add(word5);
              System.out.println("Word 5: " + words.get(word5));
              res.add(solution);
            });
          });
        });
        wordWeb.removeEdge(word, word2);
      });
      wordWeb.removeWord(word);
      System.out.println("Finished Word: " + words.get(word));
    }
    try{
      PrintWriter writer = new PrintWriter("output.txt", StandardCharsets.UTF_8);
      res.forEach(solution -> {
        writer.println("\nSolution: ");
        System.out.println("\nSolution: ");
        solution.forEach(word -> {
          writer.println(words.get(word));
          System.out.println(words.get(word));
        });
      });
    } catch (Exception e) {
      return;
    }
  }

  public static HashMap<HashSet<Character>, HashSet<String>> readList() {
    File file = new File("valid-wordle-words.txt");
    try {
      BufferedReader input = new BufferedReader(new FileReader(file));
      HashMap<HashSet<Character>, HashSet<String>> words = new HashMap<>();
      input.lines().forEach(line -> {
        HashSet<Character> word = new HashSet<>();
        boolean b = true;
        for (char c : line.toCharArray()) {
          b = word.add(c);
          if (!b) break;
        }
        if (b) {
          if (!words.containsKey(word)) words.put(word, new HashSet<>());
          words.get(word).add(line);
        }
      });
      return words;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> boolean emptyIntersection(Set<T> s1, Set<T> s2) {
    for (T t : s1) {
      if (s2.contains(t)) return false;
    }
    return true;
  }

  public static boolean checkPairs(HashSet<HashSet<Character>> p1, HashSet<HashSet<Character>> p2, Graph g) {
    for (HashSet<Character> w1 : p1) {
      for (HashSet<Character> w2 : p2) {
        if (!g.areNeighbors(w1, w2)) return false;
      }
    }
    return true;
  }

  public static <T> HashSet<T> intersection(HashSet<T> s1, HashSet<T> s2) {
    HashSet<T> intersection = (HashSet<T>) s1.clone();
    intersection.retainAll(s2);
    return intersection;

  }
}