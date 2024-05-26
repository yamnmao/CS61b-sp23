package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        Map<Character, Integer> result = new HashMap<>();
        int count =0;
        //get word from words list
        for(String word:words){
            //The Java string toCharArray() method converts the given string into a sequence of characters.
            //get character from the word
            for(char c:word.toCharArray()){
                char lowercaseLetter = Character.toLowerCase(c);
                result.put(lowercaseLetter,result.getOrDefault(lowercaseLetter,0)+1);

            }
        }
        return result;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        char common = '?';
        int max = 0;
        Map<Character, Integer> frequencyMap = getFrequencyMap();
        for(char guess:guesses){
            frequencyMap.remove(guess);
        }
        for(Map.Entry<Character,Integer>entry: frequencyMap.entrySet()){
            char letter = entry.getKey();
            int frequency = entry.getValue();

            if (frequency > max) {
                common = letter;
                max = frequency;
            }
        }

        return common;
    }

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
