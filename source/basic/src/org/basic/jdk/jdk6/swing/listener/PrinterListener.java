package org.basic.jdk.jdk6.swing.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.PrintWriter;

import javax.swing.JFileChooser;


public class PrinterListener  extends BasicActionListener {

    public PrinterListener(Component parent) {

        this.setParent(parent);
    }

    public void actionPerformed(ActionEvent e) {
        
        


        // JFileChooser jf = new JFileChooser();
        // jf.showOpenDialog(this.getParent());

    }

}