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
        List<String> nw = this.keepWords2(pattern); //eg: zebra, media, delta
        //get character frequency map from
        Map<Character, Integer> freq=this.getFrequencyMap(nw); //{a-3,b-1,d-2,e-3...}
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
    //根据提供的模式筛选单词列表，只保留那些与模式匹配的单词。keepWords2是优化版的，下面这个版本要1分钟多
    public List<String> keepWords(String pattern) {
        Map<Character, Integer> map = new TreeMap<>();
        List<String> copy = new ArrayList<>(words);
        int index = 0;
        //iterate through the pattern, collect the character and respective indices
        for (char ch : pattern.toCharArray()) {
            //if the ch not equal to -, then put it to the map
            if (!(ch == '-')) {
                //iterate through the pattern, collect the character and respective indices
                map.put(ch, index);//TreeMap<Key,Value>,这里key是字符，value是字符的index
            }
            index++;
            /*for example pattern is --e-(0,1,2,3), the first two -- will skip if statement
            and index is increased to 2, when it meets "e", it will jump into
            if statement and put the key-char ch, and the value-index 2 into the map
            TreeMap:{e=2}
             */

        }
        //iterate through the words (dictionary),for example, check the word: zebra
        for (String word : words) {
            //iterate through the key(character from pattern) of the map
            for (char ab : map.keySet()) {//for example TreeMap:{e=1，a=4}，keyset=[e,a]
                //当key=e的时候，value=1
                //remove the word if the word doesn't have the char at the specific indice
                //ensure word length must less than key‘s index, otherwise get IndexOutOfBoundsException
                //比如说no这个单词一共长度只有2，然后当key=a的时候value=4，charat（4）就会报错，
                if (word.length()!=pattern.length()||!(word.charAt(map.get(ab)) == ab)) {
                    copy.remove(word);
                }
            }
        }
                return copy;
    }


    public List<String> keepWords2(String pattern) {
        List<String> matchedWords = new ArrayList<>();
        for (String word : words) {
            if (word.length() != pattern.length()) {
                continue;  // Skip words that don't match the pattern's length
            }

            boolean matches = true;
            for (int i = 0; i < pattern.length(); i++) {
                char patternChar = pattern.charAt(i);
                //if patternChar == '-', continue to the loop
                if (patternChar != '-' && word.charAt(i) != patternChar) {
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


    // 计算并返回一个列表中所有单词的字符频率映射。
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