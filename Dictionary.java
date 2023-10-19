/* Name: Aryaman Srivastava
 * Pledge: I pledge my honor that I have abided by the Stevens Honors System
 * CS284 HW 2
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {
    private ArrayList<String> wordList;
    private ArrayList<DictionaryItem> dictArrayList;
    public Dictionary() {
        /*constructor for a Dictionary: initialize wordList and dictArrayList with a capacity of 1300 and 
        using the readFile() method to fill each list*/
        wordList = new ArrayList<String>(1300);
        dictArrayList = new ArrayList<DictionaryItem>(1300);
        readFile("ionDictionary.txt");
    }
    public Dictionary(String filename) {
        /*constructor for a Dictionary: initialize wordList and dictArrayList with a capacity of 1300 and using the readFile()
        with the given filename to fill each list*/
        wordList = new ArrayList<String>(1300);
        dictArrayList = new ArrayList<DictionaryItem>(1300);
        readFile(filename);
    }
    public void readFile(String filename) {
        /*reads the file with the given filename if it exists, otherwise throws an exception */
        try {
            File file = new File(filename);
            Scanner scan = new Scanner(file);
            splitStoreLine(scan);
            scan.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found!"); 
        }
    }
    private void splitStoreLine(Scanner scan) {
        /*function to read dictionary and separate words with their counts and then store in 
         * dictArrayList and wordList
        */
        for (int i = 0; i < 4; i++) { //skip over first 4 lines as they don't contain words to store.
            scan.nextLine();
        }
        while(scan.hasNextLine()) {
            String line = scan.nextLine();
            String word = line.split("\\|")[0].split(" ")[0];
            int count = Integer.parseInt(line.split("\\| ")[1]);
            wordList.add(word);
            dictArrayList.add(new DictionaryItem(word, count));
        }
    } 
    public void printMenu() {
        /*Print the menu and prompt the user to input a number from 1 to 3 */
        System.out.println("Welcome to the Ion Dictionary! This dictionary is created from the book Ion by Plato!");
        System.out.println("Please choose one of the following menu items indicated with 1-3");
        System.out.println("1: To print all the words in the dictionary, choose 1");
        System.out.println("2: To search a word in the dictionary, choose 2");
        System.out.println("3: To quit the program, choose 3");
        Scanner scan = new Scanner(System.in);
        int input = scan.nextInt();
        while (processMenuItem(input, scan)) {
            System.out.println("Please choose one of the following menu items indicated with 1-3");
            System.out.println("1: To print all the words in the dictionary, choose 1");
            System.out.println("2: To search a word in the dictionary, choose 2");
            System.out.println("3: To quit the program, choose 3");
            input = scan.nextInt();
        }
    }
    private boolean processMenuItem(int menuItem, Scanner scan) {
        if (menuItem > 3 || menuItem < 1) {
            System.out.println("ERROR! Please enter a number from 1 to 3");
            return true;
        }
        if (menuItem == 1) {
            System.out.println("All the words mentioned in the Ion book!");
            System.out.println("Words");
            System.out.println("------------");
            printDictionary();
            return true;
        } else if (menuItem == 2) {
            System.out.println("Please enter the word you would like to search:");
            String word = scan.next();
            int count = searchDictionary(word);
            if (count == 0) {
                System.out.println("The word '" + word + "' does not exist in the Ion dictionary!");
            } else {
                System.out.println("The word '" + word + "' occurred " + count + " times!");
            }
            return true;
        } else if (menuItem == 3) {
            System.out.println("Thanks for using the Ion Dictionary! Bye!");
            return false;
        } else {
            return true;
        }
    }
    public void printDictionary() {
        //prints all the words in the dictionary
        for (String word : wordList) {
            System.out.println(word);
        }
    }
    public int searchDictionary(String word) {
        int index = binarySearch(word, 0, wordList.size() - 1);
        if (index == -1) {
            return 0;
        }
        return dictArrayList.get(index).getCount();
    }
    private int binarySearch(String word, int low, int high) {
        int index = -1;
         while (low <= high) {
            int mid = low + ((high - low)/2);
            if (wordList.get(mid).equals(word)) {
                index = mid;
                break;
            }  else if (wordList.get(mid).compareTo(word) > 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return index; 
    }
    public static void main(String[] args) {
        Dictionary dict = new Dictionary();
        dict.printMenu();
    }
}
