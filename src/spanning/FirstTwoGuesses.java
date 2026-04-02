package spanning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FirstTwoGuesses {
  public static void main(String[] args) {
    BitwiseSpanning.Pair pair = readList();
    Set<List<List<String>>> solutions = new HashSet<>();
    pair.arr()[0].forEach(word -> {
      pair.arr()[word.nextClearBit(0)].forEach(word2 -> {
        BitSet check = (BitSet) word2.clone();
        check.or(word);
        if (check.cardinality() == 10) {
          List<List<String>> solution = new ArrayList<>();
          solution.add(pair.map().get(word));
          solution.add(pair.map().get(word2));
          solutions.add(solution);
        }
      });
    });
    System.out.println("Solutions: ");
    solutions.forEach(System.out::println);
  }

  public static BitwiseSpanning.Pair readList() {
    try {
      BufferedReader input = new BufferedReader(new FileReader(new File("valid-wordle-words.txt")));
      HashMap<BitSet, List<String>> words = new HashMap<>();
      List<BitSet>[] dict = new List[9];
      for (int i = 0; i < dict.length; i++) {
        dict[i] = new ArrayList<>();
      }
      HashMap<Character, Integer> indices = new HashMap<>();
      String revFreqAlph = "tlnuroisea";
      for (int i = 0; i < revFreqAlph.length(); i++) {
        indices.put(revFreqAlph.charAt(i), i);
      }
      input.lines().forEach(line -> {
        BitSet word = new BitSet(10);
        for (char c : line.toCharArray()) {
          if (indices.get(c) == null) return;
          word.set(indices.get(c));
        }
        if (word.cardinality() == 5) {
          if (!words.containsKey(word)) {
            words.put(word, new ArrayList<>());
            dict[word.nextSetBit(0)].add(word);
          }
          words.get(word).add(line);
        }
      });
      return new BitwiseSpanning.Pair(dict, words);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
