package org.basic.net.c01_socket.chat.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import org.basic.net.c01_socket.chat.ui.ChatClientMenuBar;
import org.basic.net.c01_socket.chat.ui.ChatClientUI;

/**
 * @author ygong
 * 
 */
public class MainChatClient {

    static void createAndShowGUI() {

        JFrame window = new JFrame();
        window.setSize(500, 400);
        window.setContentPane(new ChatClientUI());
        window.setJMenuBar(new ChatClientMenuBar());
        window.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {

                // TODO Auto-generated method stub

            }

            public void mouseEntered(MouseEvent e) {

                // TODO Auto-generated method stub

            }

            public void mouseExited(MouseEvent e) {

                // TODO Auto-generated method stub

            }

            public void mousePressed(MouseEvent e) {

                // TODO Auto-generated method stub

            }

            public void mouseReleased(MouseEvent e) {

                // TODO Auto-generated method stub

            }

        });
        window.addMouseMotionListener(new MouseMotionListener() {

            public void mouseDragged(MouseEvent e) {

                // TODO Auto-generated method stub

            }

            public void mouseMoved(MouseEvent e) {

                // TODO Auto-generated method stub

            }
        });
        window.addMouseWheelListener(new MouseWheelListener() {

            public void mouseWheelMoved(MouseWheelEvent e) {

                // TODO Auto-generated method stub

            }
        });
        window.addWindowListener(new WindowListener() {

            public void windowActivated(WindowEvent e) {

                // TODO Auto-generated method stub

            }

            public void windowClosed(WindowEvent e) {

            }

            public void windowClosing(WindowEvent e) {

            }

            public void windowDeactivated(WindowEvent e) {

                // TODO Auto-generated method stub

            }

            public void windowDeiconified(WindowEvent e) {

                // TODO Auto-generated method stub

            }

            public void windowIconified(WindowEvent e) {

                // TODO Auto-generated method stub

            }

            public void windowOpened(WindowEvent e) {

                // TODO Auto-generated method stub

            }

        });
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public static void main(String[] args) {

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                createAndShowGUI();
            }
        });
    }

}
