/**
 * 
 */
package org.basic.net.c01_socket.chat.ui;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author ygong
 * 
 */
public class ChatServerUI extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 8218599854856187362L;

    public ChatServerUI() {

        addMainContent();
    }

    private void addMainContent() {
        this.add(new JButton("Send"));

    }
}
