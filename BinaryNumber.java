/*Name: Aryaman Srivastava
  Pledge: I pledge my honor that I have abided by the Stevens Honors System
  CS 284 HW 1
 */
public class BinaryNumber {
    private int length;
    private int[] data;
    public BinaryNumber(int length) {
        //creates a Binary Number consisting only of 0s and of given length
        this.length = length;
        data = new int[length];
        for (int i = 0; i < length; i++) {
            data[i] = 0;
        }
    }
    public BinaryNumber(String str) {
        //creates a Binary Number given a string representing the Binary Number
        length = str.length();
        data = new int[length];
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '0') {
                data[i] = 0;
            } else if (str.charAt(i) == '1') {
                data[i] = 1;
            }
        }
    }
    public int getLength() {
        //returns the length of the Binary Number
        return length;
    }
    public int getDigit(int index) {
        /*returns the digit of the Binary Number at the given index, gives an error message
            if the index is less than 0 or greater than the length of the Binary Number*/
        if (index < 0 || index >= length) {
            throw new ArrayIndexOutOfBoundsException("Woops! Index is out of bounds!");
        }
        return data[index];
    }
    public int[] getInnerArray() {
        //returns the array representing the Binary Number
        return data;
    }
    public int toDecimal() {
        //returns the decimal representation of the Binary Number
        int num = 0;
        for (int i = 0; i < length; i++) {
            if (data[i] == 1) {
                num += Math.pow(2, length - i - 1);
            }
        }
        return num;
    }
    public String toString() {
        //returns the String representation of the Binary Number
        String num = "";
        for (int i = 0; i < this.length; i++) {
            num += this.data[i];
        }
        return num;
    }
    public static int[] bwor(BinaryNumber b1, BinaryNumber b2) {
        //computes bitwise or of the two given BinaryNumbers
        if (b1.getLength() != b2.getLength()) {
            throw new IllegalArgumentException("Binary Numbers must be of same length!");
        }
        int[] sum = new int[b2.getLength()];
        for (int i = 0; i < sum.length; i++) {
            if (b1.getInnerArray()[i] == 1 || b2.getInnerArray()[i] == 1) {
                sum[i] = 1;
            } else {
                sum[i] = 0;
            }
        }
        return sum;
    }
    public static int[] bwand(BinaryNumber b1, BinaryNumber b2) {
        //computes bitwise and of the two given BinaryNumbers
        if (b1.getLength() != b2.getLength()) {
            throw new IllegalArgumentException("Binary Numbers must be of same length!");
        }
        int[] sum = new int[b2.getLength()];
        for (int i = 0; i < b2.getLength(); i++) {
            if (b1.getInnerArray()[i] == 1 && b2.getInnerArray()[i] == 1) {
                sum[i] = 1;
            } else {
                sum[i] = 0;
            }
        }
        return sum;
    }
    public void bitShift(int direction, int amount) {
        //shifts the Binary number in the given direction(-1 for left and 1 for right) by the given amount of bits
        if (direction != -1 && direction != 1) {
            throw new IllegalArgumentException("That is not a valid argument!");
        } 
        if (amount < 0) {
            throw new IllegalArgumentException("That is not a valid argument!");
        } 
        else {
            int[] clone = data;
            if (direction == 1) {
                if (amount > length) {
                    length = 1;
                    data = new int[length];
                    data[0] = 0;
                } else {
                    length = length - amount;
                    data = new int[length];
                    for (int i = 0; i < length; i++)
                    data[i] = clone[i];
                }
            } else {
                length = length + amount;
                data = new int[length];
                for (int i = clone.length; i < length; i++) {
                    for (int j = 0; j < clone.length; j++) {
                        data[j] = clone[j];
                    }
                    data[i] = 0;
                }
            }
        }
    }
    public void add(BinaryNumber aBinaryNumber) {
        //adds the given BinaryNumber to the Binary Number 
        if (aBinaryNumber.getLength() > length) {
            this.perpend(aBinaryNumber.getLength() - length);
        } else if (length > aBinaryNumber.getLength()) {
            aBinaryNumber.perpend(length - aBinaryNumber.getLength());
        }
        int carry = 0;
        for (int i = length - 1; i >= 0; i--) {
            int sum = aBinaryNumber.getDigit(i) + data[i] + carry;
            if (sum == 3) {
                data[i] = 1;
                carry = 1;
            } if (sum == 2) {
                data[i] = 0;
                carry = 1;
            } else if (sum == 1 || sum == 0) {
                data[i] = sum;
                carry = 0;
            } 
        }
        if (carry == 1) {
            this.perpend(1);
            data[0] = 1;
        }
    }
    public void perpend(int amount) {
        //perpends given amount of 0s to the Binary Number
        int [] clone = data;
        length = length + amount;
        data = new int[length];
        for (int i = 0; i < amount; i++) {
            for (int j = 0; j < clone.length; j++) {
                data[j + amount] = clone[j];
            }
            data[i] = 0;
        }
    }
}
