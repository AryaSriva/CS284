package HW5;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
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
public class AnagramsTest{
    /**
     * Tester method for the addWord method of the Anagrams Table
     */
    @Test
    public void testAddWord() {
        Anagrams a = new Anagrams();
        org.junit.Assert.assertThrows(IllegalArgumentException.class, () -> a.addWord(""));
        a.addWord("a");
        Map<Long, ArrayList<String>> expected = new HashMap<Long, ArrayList<String>>();
        ArrayList<String> expectedList = new ArrayList<String>();
        expectedList.add("a");
        expected.put((long)2, expectedList);
        org.junit.Assert.assertEquals(expected, a.anagramTable);
        a.addWord("ab");
        a.addWord("ba");
        ArrayList<String> expectedList2 = new ArrayList<String>();
        expectedList2.add("ab");
        expectedList2.add("ba");
        expected.put((long)6, expectedList2);
        org.junit.Assert.assertEquals(expected, a.anagramTable);
    }
    /**
     * Tester method for the myHashCode method of the Anagrams class
     */
    @Test
    public void testHashCode() {
        Anagrams a = new Anagrams();
        org.junit.Assert.assertThrows(IllegalArgumentException.class, () -> a.myHashCode(""));
        org.junit.Assert.assertEquals(236204078, a.myHashCode("alerts"));
        org.junit.Assert.assertEquals(236204078, a.myHashCode("resalt"));
    }
    /**
     * Tester method for the getMaxEntries method of the Anagrams class
     */
    @Test
    public void testMaxEntries() {
        Anagrams a = new Anagrams();
        org.junit.Assert.assertEquals(null, a.getMaxEntries());
        Map<Long, ArrayList<String>> expected = new HashMap<Long, ArrayList<String>>();
        ArrayList<String> expectedList = new ArrayList<String>();
        expectedList.add("alerts");
        expectedList.add("resalt");
        expected.put((long)236204078, expectedList);
        a.addWord("alerts");
        a.addWord("resalt");
        org.junit.Assert.assertEquals(expected.entrySet().toString(), a.getMaxEntries().toString());
        ArrayList<String> expectedList2 = new ArrayList<String>();
        expectedList2.add("ab");
        expectedList2.add("ba");
        a.addWord("ab");
        a.addWord("ba");
        expected.put((long)6, expectedList2);
        org.junit.Assert.assertEquals(expected.entrySet().toString(), a.getMaxEntries().toString());
    }
}