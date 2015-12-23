package org.basic.pattern.structural.proxy.image;

/**
 * A Image
 */
import java.awt.MediaTracker;

@SuppressWarnings("serial")
public class ImageA implements IGraphic {

    public MediaTracker tracker;
    // private int height, width;
    private String fileName;

    public ImageA(String _fileName) {

        fileName = _fileName;

    }

    public void draw() {

        System.out.println(fileName);

    }

}