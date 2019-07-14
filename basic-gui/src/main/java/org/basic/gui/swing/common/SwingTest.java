package org.basic.gui.swing.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


/**
 * <pre>
 *
 * </pre>
 */
public class SwingTest {
    public static void main(String[] args) {
        JFrame f = new JFrame();
        JButton okBtn = new JButton("Test");
        okBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
           

            }
        });

        f.getContentPane().add(okBtn);
        f.setVisible(true);
    }
}
