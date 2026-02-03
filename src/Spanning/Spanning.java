package Spanning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;

public class Spanning {
  public static void main(String[] args) {
    System.out.println(new Graph(readList()));
  }

  public static HashSet<HashSet<Character>> readList() {
    File file = new File("valid-wordle-words.txt");
    try {
      BufferedReader input = new BufferedReader(new FileReader(file));
      HashSet<HashSet<Character>> words = new HashSet<>();
      input.lines().forEach(line -> {
        HashSet<Character> word = new HashSet<>();
        boolean b = true;
        for (char c : line.toCharArray()) {
          b = word.add(c);
          if (!b) break;
        }
        if (b) words.add(word);
      });
      return words;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }


}
