package org.basic.grammar.regexp;

public class RegExpTest {

    public static void main(String[] args) {
        String original = "192.168.0.1..1*!(@#)!@(!_@#&@#*()(*&^%$#@!.";

        String regex = "\\.";
        java.util.regex.Pattern pattern1 = java.util.regex.Pattern.compile(regex);
        String[] resultArray = pattern1.split(original);
        System.out.println(resultArray);// [120, 23, 33, , 1*!(@#)!@(!_@#&@#*()(*&^%$#@!]

        java.util.regex.Matcher matcher1 = pattern1.matcher(original);
        // System.out.println(matcher1.start());
        // System.out.println(matcher1.end());
        System.out.println(matcher1.groupCount());
        int i = 0;
        while (matcher1.find()) {
            if (i != 0) {
                System.out.print("\t");
            }
            System.out.print(original.substring(i, matcher1.start()));
            i = matcher1.end();
        }
        System.out.println("2323.rar".matches(regex));
    }
}
