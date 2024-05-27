package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

import aoa.guessers.GetFrequencyMap;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        List<String>nw = this.keepWords(pattern,guesses);
        Map<Character,Integer>freq = GetFrequencyMap.getFrequencyMap(nw);
        char ch = '?';
        int max=0;
        for (char an: freq.keySet()) {
            //内部的 if 语句检查当前字符 an 是否未被猜过，筛选中没有猜过的最高频率的字符
            if (!guesses.contains(an)) {
                //如果该字符未被猜过，再检查该字符的频率是否是目前遇到的最高频率
                // 比对max和字符的freq大小，更新 max 为当前字符的频率，并更新 ch 为当前字符
                if (max < freq.get(an)||(max == freq.get(an) && ch > an)) {
                    max = freq.get(an);
                    ch = an;
                }
            }
        }
        return ch;
    }
    public List<String> keepWords(String pattern,List<Character> guesses) {
        List<String> matchedWords = new ArrayList<>();
        Set<Character> incorrectGuesses = new HashSet<>(guesses);
        //remove correctly guessed letters from the set
        for(int i = 0;i<pattern.length();i++){
            char patternChar = pattern.charAt(i);
            if (patternChar!='-'){
                incorrectGuesses.remove(patternChar);
            }
        }
        // Filter words based on the pattern and absence of incorrect guesses
        for (String word : words) {
            if (word.length() != pattern.length()) {
                continue;  // Skip words that don't match the pattern's length
            }

            boolean matches = true;
            for (int i = 0; i < pattern.length(); i++) {
                char patternChar = pattern.charAt(i);
                //if patternChar == '-', continue to the loop
                /**
                 * Pattern: "-a-e"
                 * Guesses made: ['t', 'r', 's']
                 * incorrectGuesses: Any character guessed that is not 'a' or 'e', so here it would be {'t', 'r', 's'}.
                 * Now, if the word being considered is "rate", it would be checked as follows:
                 *
                 * For character 'r' at index 0 (patternChar is '-'), since 'r' is in incorrectGuesses, this word would be excluded.
                 */
                if ((patternChar != '-' && word.charAt(i) != patternChar)
                ||(patternChar== '-' &&incorrectGuesses.contains(word.charAt(i)))) {
                    matches = false;
                    break;  // Stop checking this word as it does not match the pattern
                }
            }
            if (matches) {
                matchedWords.add(word);
            }
        }
        return matchedWords;
    }


    public static void main(String[] args) {
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("data/example.txt");
        System.out.println(pagalfg.getGuess("----", List.of('e')));
    }
}
