package org.basic.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {
    private static final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static void main(String args[]) {
        // adding emails to an array list
        List<String> emails = new ArrayList();
        // valid email addresses
        emails.add("alice@example.com");
        emails.add("alice.bob@example.com");
        emails.add("alice.bob@exampl.test-sdfe.com");
        emails.add("alice@example.me.org");
        // invalid email addresses
        emails.add("alice.example.com");
        emails.add("alice..bob@example.com");
        emails.add("alice@.example.me.org");

        // initialize the Pattern object
        Pattern pattern = Pattern.compile(regex);

        // searching for occurrences of regex
        for (String value : emails) {
            Matcher matcher = pattern.matcher(value);

            System.out.println("Email " + value + " is " + (matcher.matches() ? "valid" : "invalid"));
        }
    }
}