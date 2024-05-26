import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
      /*letterToNum: This method returns a map from every lower case letter
    to the number corresponding to that letter starting with ‘a’ is 1.
     */
    public static Map<Character, Integer> letterToNum() {

        Map<Character, Integer> result = new HashMap<>();
        for (char c = 'a'; c <= 'z'; c++) {
            int num = c - 'a' + 1;
            result.put(c, num);
        }
        return result;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        Map<Integer,Integer> result = new HashMap<>();
        int squares = 0;
        if(nums.isEmpty()==true){
            System.out.print("empty");
            return result;
        }else {
            for (int num : nums) {
                result.put(num, num*num);
            }
            return result;
        }
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        Map<String,Integer>result = new HashMap<>();
        for (String word : words){
            result.put(word,result.getOrDefault(word,0)+1);
        }
        return result;
    }
}
