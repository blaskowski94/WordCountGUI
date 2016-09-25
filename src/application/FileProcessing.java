package application;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The FileProcessing class can be used with the GUI class or independently. An instance of FileProcessing takes in a
 * text file and allows you to determine information about the file. There are methods to count the number of words,
 * count the number of different words and the count of each individual word.
 *
 * @author Bob Laskowski
 */
public class FileProcessing {

    private File file; // File object to hold the text file
    private HashMap<String, Integer> wordMap; // HashMap to determine count by word
    private boolean fileProcessed; // True if processFile() method has been called on instance, false otherwise
    private boolean fileLoaded; // True if file is set to a valid .txt file in constructor, false otherwise
    private int totalWordCount; // Total number of words in the text document

    /**
     * Creates a FileProcessing object. A new object should be created for every file you wish to process. Constructor
     * checks file passed in to make sure it exists, is a file and not a directory and that it has a .txt extension at
     * the end. If all these conditions are not met, an error message is printed and fileLoaded is set to false. If the
     * conditions are met, file is set to the file passed in. Also declares the HashMap and fileProcessed boolean.
     *
     * @param f A .txt file with words
     */
    public FileProcessing(File f) {
        if (!f.exists() || !f.isFile()
                || !f.getName().substring(f.getName().lastIndexOf('.'), f.getName().length()).equals(".txt")) {
            fileLoaded = false;
        } else {
            file = f;
            fileLoaded = true;
        }
        wordMap = new HashMap<>();
        totalWordCount = 0;
        fileProcessed = false; // processFile method has not been called yet
    }

    /**
     * This method parses the text file using a scanner. It removes all punctuation (except apostrophes), spaces and
     * converts everything to lower case. It populates the object's hash map with the words as keys and the count of the
     * words as the values.
     *
     * @return A HashMap with all words as keys and number of occurrence as values
     */
    private HashMap<String, Integer> processFile() {
        // Checks to see if file successfully loaded
        if (fileLoaded) {
            // try/catch statement required for scanner
            try {
                Scanner scan = new Scanner(file);
                while (scan.hasNext()) {
                    // uses regular expressions to remove all characters that
                    // are not lower case letters, upper case letters, spaces or
                    // apostrophes
                    String word = scan.next().replaceAll("[^a-zA-Z ']", "").toLowerCase();

                    // counts the total number of words
                    totalWordCount++;

                    // ignore all empty strings
                    if (word.equals("")) {
                        continue;
                    }
                    // increment value if word is already found
                    if (wordMap.containsKey(word)) {
                        int count = wordMap.get(word);
                        wordMap.put(word, ++count);
                    }
                    // add word to hash map with value 1 if not already found
                    else
                        wordMap.put(word, 1);
                }
                fileProcessed = true;
                scan.close();
                return wordMap;
            } catch (FileNotFoundException e) {
                System.out.println("Error loading file");
                System.out.println(e.getMessage());
            }
        }
        // return null if method is called on object whose file is not loaded
        return null;
    }

    /**
     * Returns the total number of words in the text file. The total is counted during the processFile method. Method
     * returns 0 by default if a file has not been loaded. If the processFile method has not yet been called,
     * processFile is called.
     *
     * @return total number of words in the text document
     */
    public int countWords() {
        if (fileLoaded) {
            if (!fileProcessed) {
                processFile();
            }
            return totalWordCount;
        }
        return 0;
    }

    /**
     * Counts the total number of different words in the text document. This is determined by the number of keys in the
     * hash map. The method first checks to see if the file was loaded successfully and if processFile has been called
     * and calls it if it has not.
     *
     * @return the size of the hash map, which is the number of different words
     */
    public int countDiffWords() {
        if (fileLoaded) {
            if (!fileProcessed) {
                processFile();
            }

            return wordMap.size();
        }
        return 0;
    }

    /**
     * This method first checks to see if the file was loaded correctly and if processFile has been called and calls
     * process file if it has not. It returns the hash map populated in processFile. This contains the words as keys and
     * the values as the number of occurrences.
     *
     * @return A hash map of all words and number of occurrences in the .txt document
     */
    public HashMap<String, Integer> countByWord() {
        if (fileLoaded) {
            if (!fileProcessed) {
                processFile();
            }
            return wordMap;
        }
        return null;
    }

}
