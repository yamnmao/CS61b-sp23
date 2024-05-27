package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

import java.util.List;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;

    public RandomChooser(int wordLength, String dictionaryFile) {
        if(wordLength<1){
            throw new IllegalArgumentException("Word length must be at least 1");
        }
        List<String> words = FileUtils.readWordsOfLength(dictionaryFile,wordLength);
        if(words.isEmpty()){
            throw new IllegalStateException("No words found of the specified length");
        }
        int randomlyChosenWordNumber = StdRandom.uniform(words.size());
        chosenWord = words.get(randomlyChosenWordNumber);

        // Initialize the pattern with dashes
        pattern = "-".repeat(wordLength);//repeate - by length
    }

    @Override
    public int makeGuess(char letter) {
        int count = 0;
        StringBuilder updatedPattern = new StringBuilder(pattern);

        for (int i = 0; i < chosenWord.length(); i++) {
            if (chosenWord.charAt(i) == letter) {
                updatedPattern.setCharAt(i, letter);
                count++;
            }
        }
        pattern = updatedPattern.toString();
        return count;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public String getWord() {
        return chosenWord;
    }
}
