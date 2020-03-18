package org.basic.common;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.text.Normalizer;
import java.text.Normalizer.Form;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import org.basic.common.util.StringUtils;

public class SwingTest {

    public static void main(String[] args) {
        System.out.println(StringUtils.normalizeText("-rw  . 1 1999 root 4036257 Oct 31 08:21 'oracle-ojdbc8 (1).jar'\r\n" + 
                ""));
    }
    public static void dfmain(String[] args) {
        Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
        EventQueue systemEventQueue = tk.getSystemEventQueue();
        systemEventQueue.push(new EventQueue() {
            @Override
            protected void dispatchEvent(AWTEvent event) {
                // System.out.println(event);
                try {
                    if (event instanceof KeyEvent) {
                        KeyEvent keyEvent = (KeyEvent) event;
                        if (keyEvent.getID() == KeyEvent.KEY_PRESSED && keyEvent.getKeyCode() == KeyEvent.VK_CANCEL
                                        && keyEvent.getModifiers() == 2) {
                        }
                    }
                } finally {
                    super.dispatchEvent(event);
                }
            }
        });
        final JPanel jp1 = new JPanel();
        javax.swing.SpringLayout springLayout = new SpringLayout();
        jp1.setLayout(springLayout);

        final JPanel jp21 = new JPanel();
        jp21.setPreferredSize(new Dimension(200, 40));

        final JPanel jp22 = new JPanel();
        jp22.setPreferredSize(new Dimension(200, 40));

        final java.awt.event.MouseAdapter mouseListener = new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (jp1 == e.getComponent()) {
                    System.out.println("jp1: "+  e.getComponent());
                }
                else if (jp21 == e.getComponent()) {
                    System.out.println("jp21: "+  e.getComponent());
                }
                else if (jp22 == e.getComponent()) {
                    System.out.println("jp22: "+  e.getComponent());
                }
                else {
                    System.out.println("not match: "+  e.getComponent());
                }

            };
        };

        JFrame jf = new JFrame();
        jp1.addMouseListener(mouseListener);

        jp21.setToolTipText("jp2");
        jp21.addMouseListener(mouseListener);
        jp21.setBackground(Color.BLACK);

        jp22.setBackground(Color.yellow);
        jp22.addMouseListener(mouseListener);

        
        jp1.add(jp21);
        jp1.add(jp22);
        
        springLayout.putConstraint(SpringLayout.WEST, jp21, 5, SpringLayout.WEST, jp1);
        springLayout.putConstraint(SpringLayout.NORTH, jp21, 5, SpringLayout.NORTH, jp1);
        springLayout.putConstraint(SpringLayout.WEST, jp22, 5, SpringLayout.WEST, jp1);
        springLayout.putConstraint(SpringLayout.NORTH, jp22, 5, SpringLayout.SOUTH, jp21);

        SpringLayout springlayeout21 = new SpringLayout();
        jp21.setLayout(springlayeout21);
        
        final JPanel jp211 = new JPanel();
        jp211.setBackground(Color.cyan);
        jp211.setPreferredSize(new Dimension(100, 40));
        JButton jbtn1 = new JButton("AA");
        
        jp21.add(jbtn1);
        jp21.add(jp211);
       
        springLayout.putConstraint(SpringLayout.WEST, jbtn1, 0, SpringLayout.WEST, jp21);
        springLayout.putConstraint(SpringLayout.NORTH, jbtn1, 0, SpringLayout.NORTH, jp21);
        springLayout.putConstraint(SpringLayout.WEST, jp211, 0, SpringLayout.WEST, jp21);
        springLayout.putConstraint(SpringLayout.NORTH, jp211, 0, SpringLayout.NORTH, jp21);
        
        SpringLayout springlayeout211 = new SpringLayout();
        jp211.setLayout(springlayeout211);
        
        
        final JPanel jp2111 = new JPanel(new GridBagLayout());
        jp2111.setBackground(Color.GREEN);
        jp2111.setPreferredSize(new Dimension(80, 40));
        jp2111.add(new JLabel());
        
        jp211.add(jp2111);
        
        
        jf.getContentPane().add(jp1, BorderLayout.CENTER);
        jf.pack();
        jf.setSize(tk.getScreenSize());
        jf.setVisible(true);
    }
}
