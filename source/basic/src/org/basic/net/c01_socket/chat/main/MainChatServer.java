/**
 * 
 */
package org.basic.net.c01_socket.chat.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JWindow;

import org.basic.net.c01_socket.chat.ui.ChatServerMenuBar;
import org.basic.net.c01_socket.chat.ui.ChatServerUI;

/**
 * @author ygong
 * 
 */
public class MainChatServer {

    /**
     * @param args
     */
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setSize(500, 400);
        window.setContentPane(new ChatServerUI());
        window.setJMenuBar(new ChatServerMenuBar());
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
}
