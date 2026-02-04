package Spanning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
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
      List<BitSet>[] dict = new ArrayList[22];
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

  public static void recursiveCheck(Pair p, Set<Set<List<String>>> solutions, boolean needToSkip, List<BitSet> words, BitSet sum, int skip) {
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

  /*
  for (int i = 0; i < 2; i++) {
      System.out.println("\nStarting First: " + p.arr[i].size() + " Words To Check.");
      final int skip = i;
      p.arr[i].forEach(word -> {
        p.arr[word.nextClearBit(skip)].forEach(word2 -> {
          BitSet sum = (BitSet) word.clone();
          sum.or(word2);
          if (sum.cardinality() == 10) {
            p.arr[sum.nextClearBit(skip)].forEach(word3 -> {
              BitSet sum2 = (BitSet) sum.clone();
              sum2.or(word3);
              if (sum2.cardinality() == 15) {
                p.arr[sum2.nextClearBit(skip)].forEach(word4 -> {
                  BitSet sum3 = (BitSet) sum2.clone();
                  sum3.or(word4);
                  if (sum3.cardinality() == 20) {
                    p.arr[sum3.nextClearBit(skip)].forEach(word5 -> {
                      BitSet sum4 = (BitSet) sum3.clone();
                      sum4.or(word5);
                      if (sum4.cardinality() == 25) {
                        System.out.println("\nSolution Found.");
                        System.out.println(p.map.get(word));
                        System.out.println(p.map.get(word2));
                        System.out.println(p.map.get(word3));
                        System.out.println(p.map.get(word4));
                        System.out.println(p.map.get(word5));
                        Set<List<String>> solution = new HashSet<>();
                        solution.add(p.map.get(word));
                        solution.add(p.map.get(word2));
                        solution.add(p.map.get(word3));
                        solution.add(p.map.get(word4));
                        solution.add(p.map.get(word5));
                        solutions.add(solution);
                      }
                    });
                  }
                });
              }
            });
          }
        });
        System.out.println("Finished Word: " + p.map.get(word));
      });
    }
   */
}
