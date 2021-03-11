package olddervation;

import java.math.BigInteger;

public class Item {
    private char sign1;
    private char sign2;
    private BigInteger constant;
    private BigInteger index;

    public Item(char a, String b, char c, String d) {
        this.sign1 = a;
        this.constant = new BigInteger(b, 10);
        if (a == '-') {
            this.constant = this.constant.negate();
        }
        this.sign2 = c;
        this.index = new BigInteger(d, 10);
        if (c == '-') {
            this.index = this.index.negate();
        }
    }

    public boolean compare(Item a) {
        return this.index == a.index;
    }

    public BigInteger getConstant() {
        return this.constant;
    }

    public BigInteger getIndex() {
        return this.index;
    }

    public char getSign() {
        return sign1;
    }

    public void merge(Item newitem) {
        BigInteger zero = new BigInteger("0", 10);
        this.constant = this.constant.add(newitem.getConstant());
        if (this.constant.compareTo(zero) >= 0) {
            this.sign1 = '+';
        } else {
            this.sign1 = '-';
        }
    }

    public String getstring() {
        BigInteger zero = new BigInteger("0", 10);
        BigInteger one = new BigInteger("1", 10);
        BigInteger deone = new BigInteger("-1", 10);
        String ret = "";
        if (constant.equals(zero)) {
            return "0";
        } else if (constant.equals(one)) {
            ret = "x";
        } else if (constant.equals(deone)) {
            ret = "-x";
        } else {
            ret = String.valueOf(constant);
            ret += "*x";
        }
        if (index.equals(zero)) {
            if (constant.equals(one)) {
                return "1";
            }
            ret = String.valueOf(constant);
            return ret;
        } else if (index.equals(one)) {
            return ret;
        }
        return ret + "**" + index;
    }

    public void dervate() {
        constant = constant.multiply(index);
        BigInteger ax = new BigInteger("1", 10);
        index = index.subtract(ax);
        BigInteger zero = new BigInteger("0", 10);
        if (this.constant.compareTo(zero) >= 0) {
            this.sign1 = '+';
        } else {
            this.sign1 = '-';
        }
    }
}
