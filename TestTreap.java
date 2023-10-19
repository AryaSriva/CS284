import org.junit.Test;
import static org.junit.Assert.assertEquals;
/*
 * Name: Aryaman Srivastava
 * Pledge: I pledge my honor that I have abided by the Stevens Honors System
 * CS284 HW4
 */
public class TestTreap {
    @Test
    public void testAdd() {
        Treap<Integer> testTree = new Treap<Integer>();
        testTree.add(4,19);
        testTree.add(2,31);
        testTree.add(6,70);
        testTree.add(1,84);
        testTree.add(3,12);
        testTree.add(5,83);
        testTree.add(7,26);
        String expected = "";
        expected += "(key = 1, priority = 84)";
        expected += "\n null";
        expected += "\n (key = 5, priority = 83)";
        expected += "\n  (key = 2, priority = 31)";
        expected += "\n   null";
        expected += "\n   (key = 4, priority = 19)";
        expected += "\n    (key = 3, priority = 12)";
        expected += "\n     null";
        expected += "\n     null";
        expected += "\n    null";
        expected += "\n  (key = 6, priority = 70)";
        expected += "\n   null";
        expected += "\n   (key = 7, priority = 26)";
        expected += "\n    null";
        expected += "\n    null";
        expected += "\n";
        org.junit.Assert.assertEquals(expected, testTree.toString());
        testTree.add(1,34);
        testTree.add(10, 19);
        org.junit.Assert.assertEquals(expected, testTree.toString());
        testTree.add(11);
        org.junit.Assert.assertTrue(testTree.toString().contains("11"));
    }
    @Test
    public void testDelete() {
        Treap<Integer> testTree = new Treap<Integer>(0);
        testTree.add(4,19);
        testTree.add(2,31);
        testTree.add(6,70);
        testTree.add(1,84);
        testTree.add(3,12);
        testTree.add(5,83);
        testTree.add(7,26);
        testTree.delete(1);
        String expected = "(key = 5, priority = 83)";
        expected += "\n (key = 2, priority = 31)";
        expected += "\n  null";
        expected += "\n  (key = 4, priority = 19)";
        expected += "\n   (key = 3, priority = 12)";
        expected += "\n    null";
        expected += "\n    null";
        expected += "\n   null";
        expected += "\n (key = 6, priority = 70)";
        expected += "\n  null";
        expected += "\n  (key = 7, priority = 26)";
        expected += "\n   null";
        expected += "\n   null";
        expected += "\n";
        org.junit.Assert.assertEquals(expected, testTree.toString());
        testTree.delete(10);
        org.junit.Assert.assertEquals(expected, testTree.toString());
        testTree.delete(3);
        String expected2 = "(key = 5, priority = 83)";
        expected2 += "\n (key = 2, priority = 31)";
        expected2 += "\n  null";
        expected2 += "\n  (key = 4, priority = 19)";
        expected2 += "\n   null";
        expected2 += "\n   null";
        expected2 += "\n (key = 6, priority = 70)";
        expected2 += "\n  null";
        expected2 += "\n  (key = 7, priority = 26)";
        expected2 += "\n   null";
        expected2 += "\n   null";
        expected2 += "\n";
        org.junit.Assert.assertEquals(expected2, testTree.toString());
    }
    @Test
    public void testFind() {
        Treap<Integer> testTree = new Treap<Integer>();
        testTree.add(4,19);
        testTree.add(2,31);
        testTree.add(6,70);
        testTree.add(1,84);
        testTree.add(3,12);
        testTree.add(5,83);
        testTree.add(7,26);
        org.junit.Assert.assertTrue(testTree.find(4));
        org.junit.Assert.assertFalse(testTree.find(11));
        testTree.delete(4);
        org.junit.Assert.assertFalse(testTree.find(4));
    }
    public static void main(String[] args) {
        Treap<Integer> testTree = new Treap<Integer>();
        testTree.add(4,19);
        testTree.add(2,31);
        testTree.add(6,70);
        testTree.add(1,84);
        testTree.add(3,12);
        testTree.add(5,83);
        testTree.add(7,26);
        System.out.println(testTree);
    }
}
