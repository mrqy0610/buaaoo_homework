package olddervation;

import java.math.BigInteger;

public class Itemgroup {
    private Item[] itemsgroup;
    private int count;

    public Itemgroup(int count) {
        this.count = count;
        itemsgroup = new Item[2000];
    }

    public void additem(Item newitem) {
        for (int i = 1; i <= count; i++) {
            if (itemsgroup[i].getIndex().equals(newitem.getIndex())) {
                itemsgroup[i].merge(newitem);
                return;
            }
        }
        itemsgroup[++count] = new Item('+', "0", '+', "0");
        itemsgroup[count] = newitem;
    }

    public String printall() {
        String ret = "";
        boolean flag = false;
        BigInteger zero = new BigInteger("0", 10);
        for (int i = 1; i <= count; i++) {
            if (!itemsgroup[i].getConstant().equals(zero)) {
                if (itemsgroup[i].getSign() == '+' && flag) {
                    System.out.print("+");
                    ret += "+";
                }
                flag = true;
                System.out.print(itemsgroup[i].getstring());
                ret += itemsgroup[i].getstring();
            }
        }
        if (!flag) {
            System.out.print("0");
            ret += "0";
        }
        System.out.println("");
        return ret;
    }

    public void dervateall() {
        for (int i = 1; i <= count; i++) {
            itemsgroup[i].dervate();
        }
    }

    public int getcount() {
        return count;
    }
}
