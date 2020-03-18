package org.basic.jdk.core.pattern.structural.Adapter.test1;

/**
 * The Class Adapter in this sample
 */
public class TextShapeClassAdapter extends Text implements Shape {

    public TextShapeClassAdapter() {

    }

    public void draw() {

        System.out.println("Draw a shap ! Impelement Shape interface !");
    }

    public void border() {

        System.out.println("Set the border of the shap ! Impelement Shape interface !");
    }

    public static void main(String[] args) {

        TextShapeClassAdapter myTextShapeClass = new TextShapeClassAdapter();
        myTextShapeClass.draw();
        myTextShapeClass.border();
        myTextShapeClass.setContent("A test text !");
        System.out.println("The content in Text Shape is :" + myTextShapeClass.getContent());
    }
}