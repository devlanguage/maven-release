/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.swing.listener.OpenListener.java is created on Sep 28, 2007 1:25:59 PM
 */
package org.basic.jdk.jdk6.swing.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileFilter;

/**
 * 
 */
public class OpenListener extends BasicActionListener {

    public OpenListener(Component parent) {

        this.setParent(parent);
    }

    public void actionPerformed(ActionEvent e) {

        JFileChooser jf = new JFileChooser();
        jf.showOpenDialog(this.getParent());
        jf.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {

                if (f.getName().endsWith("*.xml")) {
                    return true;
                }
                return false;
            }

            @Override
            public String getDescription() {

                return null;
            }

        });
       

    }

}