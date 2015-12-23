package org.jgraph.hello;

public class HelloTester {

    public static void main(String[] args) {

        new HelloTester().start();
    }

    private void start() {
        new HelloMainWindow().setVisible(true);
    }
}
