package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */
    public char getGuess(String pattern, List<Character> guesses) {
        //keepWords select words matching the pattern
        List<String> nw = this.keepWords(pattern);
        //get character frequency map from
        Map<Character, Integer> freq=this.getFrequencyMap(nw);
        char ch = '?';
        int max=0;
        for (char an: freq.keySet()) {
            if (!guesses.contains(an)) {
                if (max < freq.get(an)) {
                    max = freq.get(an);
                    ch = an;
                }
            }
        }
        return ch;

    }
    public List<String> keepWords(String pattern){
        Map<Character,Integer> map = new TreeMap<>();
        List<String> copy = new ArrayList<>(words);
        int index=0;
        //iterate through the pattern, collect the character and respective indices
        for (char ch: pattern.toCharArray()){
            //if the ch not equal to -, then put it to the map
            if (!(ch=='-')){
                map.put(ch,index);
            }
            index++;
            /*for example pattern is --e-(0,1,2,3), the first two -- will skip if statement
            and index is increased to 2, when it meets "e", it will jump into
            if statement and put the key-char ch, and the value-index 2 into the map
             */
        }
        //iterate through the words (dictionary)
        for (String word: words){
            //iterate through the key(character from pattern) of the map
            for (char ab: map.keySet()){
                //remove the word if the word doesn't have the char at the specific indice
                /*map.get(ab)=ab's indice, this map<key=char, value=indice>
                whether word.charAt(ab's indice) == ab(the char in the map)
                remove the word if it doesn't contain the char with indice.
                 */
                if (!(word.charAt(map.get(ab))==ab)){
                    copy.remove(word);
                }
            }
        }
        return copy;
    }

    public Map<Character, Integer> getFrequencyMap(List<String> lst) {
        Map<Character, Integer> map = new TreeMap<>();
        for (String word : lst) {
            for (char c : word.toCharArray()) {
                if (map.containsKey(c)) {
                    int oc = map.get(c);
                    map.put(c, oc + 1);
                } else {
                    map.put(c, 1);
                }
               /* char lowerCase = Character.toLowerCase(c);
                map.put(lowerCase,map.getOrDefault(lowerCase,0)+1);*/
            }
        }
        return map;
    }


    public static void main(String[] args) {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));
    }
}