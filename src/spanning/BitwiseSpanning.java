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
import java.util.Map;
import java.util.Set;

public class BitwiseSpanning {
  public static void main(String[] args) {
    Set<Set<List<String>>> solutions = new HashSet<>();
    Pair p = readList();
    recursiveCheck(p, solutions, true, new ArrayList<>(), new BitSet(26), 0);
    recursiveCheck(p, solutions, false, new ArrayList<>(), new BitSet(26), 1);
    System.out.println("\nSolutions: ");
    solutions.forEach(System.out::println);
  }

  public static Pair readList() {
    try {
      BufferedReader input = new BufferedReader(new FileReader(new File("valid-wordle-words.txt")));
      HashMap<BitSet, List<String>> words = new HashMap<>();
      List<BitSet>[] dict = new List[22];
      for (int i = 0; i < dict.length; i++) {
        dict[i] = new ArrayList<>();
      }
      HashMap<Character, Integer> indices = new HashMap<>();
      String revFreqAlph = "qxjzvfwbkgpmhdcytlnuroisea";
      for (int i = 0; i < revFreqAlph.length(); i++) {
        indices.put(revFreqAlph.charAt(i), i);
      }
      input.lines().forEach(line -> {
        BitSet word = new BitSet(26);
        for (char c : line.toCharArray()) {
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
      return new Pair(dict, words);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public static void recursiveCheck(Pair p, Set<Set<List<String>>> solutions, boolean needToSkip,
                                    List<BitSet> words, BitSet sum, int skip) {
    if (words.size() == 5) {
      Set<List<String>> solution = new HashSet<>();
      words.forEach(word -> {
        solution.add(p.map.get(word));
      });
      solutions.add(solution);
    } else {
      p.arr[sum.nextClearBit(skip)].forEach(word -> {
        BitSet sum2 = (BitSet) sum.clone();
        sum2.or(word);
        if (sum2.cardinality() == 5 * (words.size() + 1)) {
          List<BitSet> words2 = new ArrayList<>(words);
          words2.add(word);
          recursiveCheck(p, solutions, needToSkip, words2, sum2, skip);
          if (needToSkip) {
            recursiveCheck(p, solutions, false, words2, sum2, sum2.nextClearBit(skip) + 1);
          }
        }
      });
    }
  }

  public record Pair(List<BitSet>[] arr, Map<BitSet, List<String>> map) {}
}