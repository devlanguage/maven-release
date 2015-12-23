package org.basic.converage;

public class ClientTest2 {

    public static void main(String[] args) {

        ClientApp au = new ClientApp();
        for (int i : new int[] { -1, 0, 1 }) {
            au.testC(i);
        }
    }
}