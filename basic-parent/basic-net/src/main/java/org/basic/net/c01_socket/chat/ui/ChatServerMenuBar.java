package org.basic.net.c01_socket.chat.ui;

import java.awt.MenuBar;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author ygong
 * 
 */
public class ChatServerMenuBar extends JMenuBar {

    /**
     * 
     */
    private static final long serialVersionUID = 967204292065201569L;

    public ChatServerMenuBar() {

        initMenuBar();
    }

    private void initMenuBar() {

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);
        this.add(fileMenu);

        JMenu actionMenu = new JMenu("Action");
        JMenuItem createMenuItem = new JMenuItem("create");
        actionMenu.add(createMenuItem);
        this.add(actionMenu);

    }

}
