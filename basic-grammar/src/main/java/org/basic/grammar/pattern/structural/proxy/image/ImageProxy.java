/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.StructureType.proxy.image.ImageProxy.java is created on 2008-9-16
 */
package org.basic.grammar.pattern.structural.proxy.image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;

// ==================================
@SuppressWarnings("serial")
class ImageProxy extends JPanel implements Runnable {

    int height, width;
    MediaTracker tracker;
    Image img;
    JFrame frame;
    Thread imageCheck; // to monitor loading

    // ------------------------------------

    public ImageProxy(URL url, int w, int h) {

        height = h;
        width = w;

        tracker = new MediaTracker(this);
        img = Toolkit.getDefaultToolkit().getImage(url);
        tracker.addImage(img, 0); // watch for image loading

        // imageCheck = new Thread(this);
        // imageCheck.start(); //start 2nd thread monitor

        // this begins actual image loading
        try {
            tracker.waitForID(0, 10000);
        } catch (InterruptedException e) {
        }
        imageCheck = new Thread(this);
        imageCheck.start(); // start 2nd thread monitor

    }

    // ------------------------------------
    public void paint(Graphics g) {

        if (tracker.checkID(0)) {
            height = img.getHeight(frame); // get height
            width = img.getWidth(frame); // and width

            g.setColor(Color.lightGray); // erase box
            g.fillRect(0, 0, width + 5, height + 5);
            g.drawImage(img, 0, 0, this); // draw loaded image
        } else {
            // draw box outlining image if not loaded yet
            g.setColor(Color.red);
            g.drawRect(1, 1, width + 10, height + 10);
        }
    }

    // ------------------------------------
    public Dimension getPreferredSize() {

        return new Dimension(width, height);
    }

    // public int getWidth() {return width;}
    // public int getHeight(){return height;}
    // ------------------------------------
    public void run() {

        // this thread monitors image loading
        // and repaints when done
        // the 1000 msec is artifically long
        // to allow demo to display with delay
        try {
            Thread.sleep(1000);
            while (!tracker.checkID(0))
                Thread.sleep(1000);
        } catch (Exception e) {
        }
        repaint();
    }
}