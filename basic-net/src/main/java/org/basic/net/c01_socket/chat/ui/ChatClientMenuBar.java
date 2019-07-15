package org.basic.net.c01_socket.chat.ui;

import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * @author ygong
 * 
 */
class ConnectServerListener implements ActionListener {

    public ChatClientMenuBar parent;

    public ConnectServerListener(ChatClientMenuBar bar) {

        this.parent = bar;
    }

    public void actionPerformed(ActionEvent e) {

        ChatServerInfoDialog serverInfo = new ChatServerInfoDialog();

        serverInfo.setVisible(true);
//        JOptionPane.showOptionDialog(parentComponent, message, title, optionType, messageType, icon, options, initialValue)
    }
}

public class ChatClientMenuBar extends JMenuBar {

    /**
     * 
     */
    private static final long serialVersionUID = 967204292065201569L;

    public ChatClientMenuBar() {

        initMenuBar();
    }

    private void initMenuBar() {

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);
        this.add(fileMenu);

        JMenu actionMenu = new JMenu("Action");
        JMenuItem connectMenuItem = new JMenuItem("connect");
        connectMenuItem.addActionListener(new ConnectServerListener(this));
        actionMenu.add(connectMenuItem);
        this.add(actionMenu);

    }

}
