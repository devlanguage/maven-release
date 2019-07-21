package org.basic.grammar.pattern.structural.proxy.image;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImageProxyTester {

    public final static String IMAGE_NAME_ELLIOTT = "elliott.jpg";
    public final static String IMAGE_NAME_AXIS_STRUCTRUE = "Axis2_Structure.jpg";

    public ImageProxyTester() {

    }

    public final static void main(String[] argv) {

        JFrame jframe = new JFrame("Image proxy test");

        jframe.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                System.exit(0);
            }
        });
        jframe.setSize(370, 350);

        JPanel p = new JPanel();
        jframe.getContentPane().add(p);
        p.setLayout(new BorderLayout());
        // ImageProxy image = new ImageProxy(ProxyDisplay.class.getResource(IMAGE_NAME_ELLIOTT),
        // 321,
        // 271);
        ImageProxy image = new ImageProxy(ImageProxyTester.class
                .getResource(IMAGE_NAME_AXIS_STRUCTRUE), 321, 271);
        p.add("Center", image);
        p.add("North", new Label("North"));
        p.add("West", new Label("West"));

        jframe.setVisible(true);
    }
}
