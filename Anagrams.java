package HW5;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
/*
 * Name: Aryaman Srivastava
 * Pledge: I pledge my honor that I have abided by the Stevens Honors System
 * CS284 HW 5
 */
public class Anagrams {
	final Integer[] primes =  
			{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 
			31, 37, 41, 43, 47, 53, 59, 61, 67, 
			71, 73, 79, 83, 89, 97, 101};
	Map<Character,Integer> letterTable;
	Map<Long,ArrayList<String>> anagramTable;

	/**
	 * Initializes the letterTable hashmap to be used later, where the keys are letters, and the values are the prime numbers 
	 * in the integer array primes.
	 */
	public void buildLetterTable() {
		letterTable = new HashMap<Character, Integer>();
		Character[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
								'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
								'w', 'x', 'y', 'z'};
		for (int i = 0; i < letters.length; i++) {
			letterTable.put(letters[i], primes[i]);
		}		
	}

	/**
	 * Constructor for anagrams object
	 * Calls buildLetterTable to initialize letterTable hashmap
	 * Initializes anagramTable to a new HashMap where the keys are Long values
	 * and the values are ArrayList<String> values.
	 */
	public Anagrams() {
		buildLetterTable();
		anagramTable = new HashMap<Long,ArrayList<String>>();
	}

	/**
	 * @param s
	 * Adds the given word into the anagram table by first calculating the hash code of the string
	 * and then checking if the hashcode already exists in the anagram table
	 * If it does, then simply add the word to the arrayList value mapped to that specific hashcode key
	 * If not, add a new entry into the anagram table with a key of the hashcode of the word and 
	 * a value of an arrayList containing only the word we want to add
	 */
	public void addWord(String s) {
		long hashCode = myHashCode(s);
		if (anagramTable.containsKey(hashCode)) {
			anagramTable.get(hashCode).add(s);
		} else {
			ArrayList<String> list = new ArrayList<String>();
			list.add(s);
			anagramTable.put(hashCode, list);
		}
	}
	
	/**
	 * @param s
	 * @return
	 * Calculates the hashcode of the given string using the values mapped to each letter in the letterTable hashMap
	 * if the string is empty or is null, this method will throw an IllegalArgumentException
	 */
	public long myHashCode(String s) {
		if (s.equals("") || s == null) {
			throw new IllegalArgumentException("String is empty!");
		}
		long hashCode = letterTable.get(s.charAt(0));
		for (int i = 1; i < s.length(); i++) {
			hashCode *= letterTable.get(s.charAt(i));
		}
		return hashCode;
	}
	
	/**
	 * @param s
	 * @throws IOException
	 * Given method that reads a given file(if it exists) and inputs all the words in that file
	 * into the hashmap anagramTable. 
	 */
	public void processFile(String s) throws IOException {
		FileInputStream fstream = new FileInputStream(s);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		while ((strLine = br.readLine()) != null)   {
		  this.addWord(strLine);
		}
		br.close();
	}
	
	/**
	 * @return
	 * returns an Array List consisting of the entries in the anagramTable hash map with the most number of anagrams
	 * The most number of anagrams is determined by the size of the ArrayList value for each entry
	 * If there are multiple entries with the most number of anagrams, they will all be printed.
	 * If the anagramTable is empty, this method will return nothing
	 */
	public ArrayList<Map.Entry<Long,ArrayList<String>>> getMaxEntries() {
		ArrayList<Map.Entry<Long, ArrayList<String>>> list = new ArrayList<Map.Entry<Long, ArrayList<String>>>();
		if (anagramTable.isEmpty()) {
			return null;
		}
		int max = 0;
		for (Map.Entry<Long, ArrayList<String>> x: anagramTable.entrySet()) {
			if (x.getValue().size() > max) {
				max = x.getValue().size();
			}
		}
		for (Map.Entry<Long, ArrayList<String>> x: anagramTable.entrySet()) {
			if (x.getValue().size() == max) {
				list.add(x);
			}
		}
		return list; 
	}
	
	/**
	 * @param args
	 * main method for Anagrams class, records the run time of the program and gives sample output for the given file
	 * "words_alpha.txt"
	 */
	public static void main(String[] args) {
		Anagrams a = new Anagrams();

		final long startTime = System.nanoTime();    
		try {
			a.processFile("words_alpha.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<Map.Entry<Long,ArrayList<String>>> maxEntries = a.getMaxEntries();
		final long estimatedTime = System.nanoTime() - startTime;
		final double seconds = ((double) estimatedTime/1000000000);
		System.out.println("Time: "+ seconds);
		System.out.println("List of max anagrams: "+ maxEntries);
	}
}
