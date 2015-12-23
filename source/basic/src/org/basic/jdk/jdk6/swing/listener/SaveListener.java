/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.swing.listener.SaveListener.java is created on Sep 28, 2007 1:21:24 PM
 */
package org.basic.jdk.jdk6.swing.listener;

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
