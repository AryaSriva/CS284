package HW6;
/*
 * Name: Aryaman Srivastava
 * Pledge: I pledge my honor that I have abided by the Stevens Honors System
 * CS284 Make-up Assignment
 */
public class CountingSort {
    /**
     * @param A
     * @return
     *         returns an array of integers in sorted order from the given integer
     *         array using the Counting Sort Algorithm
     */
    public static int[] sort(int[] A) {
        int n = A.length;
        int k = maxValue(A) + 1;
        int[] B = new int[n];
        int[] C = new int[k];
        for (int i = 0; i < k; i++) {
            C[i] = 0;
        }
        for (int j = 0; j < n; j++) {
            C[A[j]] = C[A[j]] + 1;
        }
        for (int i = 1; i < k; i++) {
            C[i] = C[i] + C[i - 1];
        }
        for (int j = n - 1; j >= 0; j--) {
            B[C[A[j]] - 1] = A[j];
            C[A[j]] = C[A[j]] - 1;
        }
        return B;
    }

    /**
     * @param A
     * @return
     *         returns the maximum value in an given array of integers
     */
    public static int maxValue(int[] A) {
        int max = A[0];
        for (int i = 0; i < A.length; i++) {
            if (A[i] > max) {
                max = A[i];
            }
        }
        return max;
    }
}
