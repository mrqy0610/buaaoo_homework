package olddervation;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Derivation {

    public static boolean judgechar(String str, int i) {
        if (i < 0 || i >= str.length()) {
            return false;
        }
        char c = str.charAt(i);
        return (c == '+' || c == '-');
    }

    public static String standard(String ori) {
        String change = ori;
        for (int i = 1; i < change.length() - 1; i++) {
            //turn + to ++, turn - to +-
            if (judgechar(change, i) && change.charAt(i - 1) != '*') {
                if (!((judgechar(change, (i - 1))) | judgechar(change, (i + 1)))) {
                    StringBuilder strbuild = new StringBuilder(change);
                    if (change.charAt(i) == '+') {
                        strbuild = strbuild.replace(i, i + 1, "++");
                    } else {
                        strbuild = strbuild.replace(i, i + 1, "+-");
                    }
                    change = strbuild.toString();
                    i++;
                }
            }
            //add sign for index
            if (!judgechar(change, i)) {
                if (i >= 2 && change.charAt(i - 2) == '*' && change.charAt(i - 1) == '*') {
                    StringBuilder strbuild = new StringBuilder(change);
                    strbuild = strbuild.insert(i, '+');
                    change = strbuild.toString();
                    i++;
                }
            }
        }
        return change;
    }

    public static String simplify(String ori) {
        String change = ori;
        //delete blank
        Pattern change1 = Pattern.compile(" ");
        Matcher match1 = change1.matcher(change);
        while (match1.find()) {
            change = match1.replaceAll("");
        }
        Pattern change2 = Pattern.compile("\t");
        Matcher match2 = change2.matcher(change);
        while (match2.find()) {
            change = match2.replaceAll("");
        }
        //turn -- to ++, turn -+ to +-
        Pattern change3 = Pattern.compile("--");
        Matcher match3 = change3.matcher(change);
        while (match3.find()) {
            change = match3.replaceAll("++");
        }
        Pattern change4 = Pattern.compile("-\\+");
        Matcher match4 = change4.matcher(change);
        while (match4.find()) {
            change = match4.replaceAll("+-");
        }
        //add sign at head and tail
        if (change.charAt(0) != '+' && change.charAt(0) != '-') {
            StringBuilder strbuild = new StringBuilder(change);
            strbuild = strbuild.insert(0, "+");
            change = strbuild.toString();
        }
        StringBuilder strbuild = new StringBuilder(change);
        strbuild = strbuild.append('+');
        change = strbuild.toString();
        change = standard(change);
        return change;
    }

    public static String runCode(String formula) {
        Scanner scan = new Scanner(System.in);
        // formula = scan.nextLine();
        formula = simplify(formula);
        char add = '+';
        String zero = "0";
        String one = "1";
        //System.out.println(formula);
        Itemgroup group = new Itemgroup(0);
        Pattern type1 = Pattern.compile("(\\+|-)(\\d+)(\\+|-)");
        Matcher match1 = type1.matcher(formula);
        while (match1.find()) {
            int x = match1.start();
            if ((x > 0 && formula.charAt(x - 1) != '*') || x == 0) {
                char a = match1.group(1).charAt(0);
                String b = match1.group(2);
                Item newitem = new Item(a, b, add, zero);
                group.additem(newitem);
            }
        }
        Pattern type2 = Pattern.compile("(\\+|-)x(\\+|-)");
        Matcher match2 = type2.matcher(formula);
        while (match2.find()) {
            char a = match2.group(1).charAt(0);
            Item newitem = new Item(a, one, add, one);
            group.additem(newitem);
        }
        Pattern type3 = Pattern.compile("(\\+|-)(\\d+)\\*x(\\+|-)");
        Matcher match3 = type3.matcher(formula);
        while (match3.find()) {
            char a = match3.group(1).charAt(0);
            String b = match3.group(2);
            Item newitem = new Item(a, b, add, one);
            group.additem(newitem);
        }
        Pattern type4 = Pattern.compile("(\\+|-)x\\*\\*(\\+|-)(\\d+)(\\+|-)");
        Matcher match4 = type4.matcher(formula);
        while (match4.find()) {
            char a = match4.group(1).charAt(0);
            char c = match4.group(2).charAt(0);
            String d = match4.group(3);
            Item newitem = new Item(a, one, c, d);
            group.additem(newitem);
        }
        Pattern type5 = Pattern.compile("(\\+|-)(\\d+)\\*x\\*\\*(\\+|-)(\\d+)(\\+|-)");
        Matcher match5 = type5.matcher(formula);
        while (match5.find()) {
            char a = match5.group(1).charAt(0);
            String b = match5.group(2);
            char c = match5.group(3).charAt(0);
            String d = match5.group(4);
            Item newitem = new Item(a, b, c, d);
            group.additem(newitem);
        }
        //group.printall();
        group.dervateall();
        String ret = group.printall();
        return ret;
    }
}
