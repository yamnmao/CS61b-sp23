import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        int totalSum =0;
        for(int num:L){
            totalSum += num;
        }
        return totalSum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> evens = new ArrayList<Integer>();
        for(int num:L){
            if (num%2==0){
                evens.add(num);
            }
        }
        return evens;

    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> commonList = new ArrayList<>();
        for(int num:L1){
            if (L2.contains(num)) {
                commonList.add(num);
            }
        }
        return commonList;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int count = 0;
        //The outer loop (for (String word : words))iterates through each word in the list words.
        for(String word:words){
            //For each word, the inner loop (for (char ch : word.toCharArray()))iterates through each character
            //The Java string toCharArray() method converts the given string into a sequence of characters.
            for(char check : word.toCharArray()){
                if(check == c){
                    count++;
                }
            }
        }
        return count;
    }
}
