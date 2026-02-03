package Spanning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;

public class Spanning {
  public static void main(String[] args) {
    System.out.println(new Graph(readList().keySet()));
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


}
