package org.basic.converage;

public class ClientApp {

    public void testA(int i) {

        if (i > 0) {
            System.out.println("Positive: i=" + i);
        } else if (i == 0) {
            System.out.println("Zero: i=" + i);
        } else {
            System.out.println("Negative: i=" + i);
        }
    }

    public void testB(int i) {

        if (i > 0) {
            System.out.println("Positive: i=" + i);
        } else if (i == 0) {
            System.out.println("Zero: i=" + i);
        } else {
            System.out.println("Negative: i=" + i);
        }
    }

    public void testC(int i) {

        if (i > 0) {
            System.out.println("Positive: i=" + i);
        } else if (i == 0) {
            System.out.println("Zero: i=" + i);
        } else {
            System.out.println("Negative: i=" + i);
        }
    }

    public static void main(String[] args) {

        ClientApp au = new ClientApp();
        for (int i : new int[] { -1, 0, 1 }) {
            au.testA(i);
            au.testB(i);
            au.testC(i);
        }
    }
}
