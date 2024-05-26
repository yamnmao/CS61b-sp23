package aoa.guessers;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GetFrequencyMap {
    public static Map<Character, Integer> getFrequencyMap(List<String> lst) {
        Map<Character, Integer> map = new TreeMap<>();
        for (String word : lst) {
            for (char c : word.toCharArray()) {
                char lowerCase = Character.toLowerCase(c);
                map.put(lowerCase,map.getOrDefault(lowerCase,0)+1);
                //if lowerCase doesn't exist then defaultvalue = 0 and then +1
                //if exist, map.get(lowerCase)+1
            }
        }
        return map;
    }
}
