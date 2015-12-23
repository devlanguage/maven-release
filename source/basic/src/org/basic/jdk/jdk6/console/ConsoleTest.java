package org.basic.jdk.jdk6.console;

import java.io.Console;

public class ConsoleTest {

    public static void main(String[] args) {

        Console console = System.console();
        if (console != null) {
            String user = new String(console.readLine("Enter user:"));
            String pwd = new String(console.readPassword("Enter passowrd:"));

            console.printf("User is:" + user + "\n");
            console.printf("Password is:" + pwd + "\n");
        } else {
            System.out.println("Console is unavailable");
        }

    }
}
