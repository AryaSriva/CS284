/*Name: Aryaman Srivastava
 * Pledge: I pledge my honor that I have abided by the Stevens Honors System.
 * CS284 HW 2
 */

public class DictionaryItem{
    private String word;
    private int count;
    public DictionaryItem(String word, int count) {
        //constructor for a Dictionary Item: initialize the word and count parameter
        this.word = word;
        this.count = count;
    }
    public void setWord(String word) {
        //setter method for word parameter
        this.word = word;
    }
    public void setCount(int count) {
        //setter method for count parameter
        this.count = count;
    }
    public int getCount() {
        //getter method for count parameter
        return count;
    }
    public String getWord() {
        //getter method for word parameter
        return word;
    }
    
}