package HW6;
/*
 * Name: Aryaman Srivastava
 * Pledge: I pledge my honor that I have abided by the Stevens Honors System
 * CS284 Make-up Assignment
 */
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CountingSortTest {
    /**
     * Tests the Counting Sort Algorithm implementation of CountingSort.java
     * Uses 3 test cases, one given in the documentation, one which consists of all
     * the integers
     * from 0 to 10, and one with integers in the range of [0,9] and repeats of
     * certain integers
     */
    @Test
    public void TestCountingSort() {
        int[] A = { 2, 5, 3, 0, 2, 3, 0, 3 };
        int[] B = { 0, 0, 2, 2, 3, 3, 3, 5 };
        for (int i = 0; i < B.length; i++) {
            org.junit.Assert.assertEquals(B[i], CountingSort.sort(A)[i]);
        }
        int[] C = { 10, 4, 9, 2, 3, 0, 1, 6, 5, 8, 7 };
        int[] D = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        for (int i = 0; i < C.length; i++) {
            org.junit.Assert.assertEquals(D[i], CountingSort.sort(C)[i]);
        }
        int[] E = { 3, 3, 3, 1, 2, 4, 5, 0, 0, 9, 8 };
        int[] F = { 0, 0, 1, 2, 3, 3, 3, 4, 5, 8, 9 };
        for (int i = 0; i < F.length; i++) {
            org.junit.Assert.assertEquals(F[i], CountingSort.sort(E)[i]);
        }
    }
}
