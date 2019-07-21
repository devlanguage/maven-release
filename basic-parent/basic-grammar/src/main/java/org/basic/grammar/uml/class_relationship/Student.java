package org.basic.grammar.uml.class_relationship;

import java.awt.Color;

// Association relationship
public class Student {

    private String name;
    private int age;
    BasketBall aBall;

    public Student(String name, int age) {

        this.name = name;
        this.age = age;
    }

    public void getBall(BasketBall aBall) {

        this.aBall = aBall;
    }

    public void play() {

        System.out.println("I am playing basketball" + aBall);
    }
}

class BasketBall {

    private Color aColor;
    private int size;

    public BasketBall(Color aColor, int size) {

        this.aColor = aColor;
        this.size = size;
    }
}

class StudentAdmin {

    public static void main(String aa[]) {

        Student aStudent = new Student("Peter", 22);
        BasketBall aBasketBall = new BasketBall(Color.red, 32);
        aStudent.getBall(aBasketBall);
        aStudent.play();
    }
}