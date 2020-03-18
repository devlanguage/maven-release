package org.basic.jdk.core.jdk.jdk6.swing.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;

/**
 * 
 */
public class SaveListener extends BasicActionListener {

    public SaveListener(Component parent) {

        this.setParent(parent);
    }

    public void actionPerformed(ActionEvent e) {

        JFileChooser jf = new JFileChooser();
        jf.showSaveDialog(this.getParent());

    }

}
