package org.basic.grammar.jdk.jdk6.swing.listener;

import java.awt.Component;
import java.awt.event.ActionListener;

/**
 * 
 */
public abstract class BasicActionListener implements ActionListener{

    protected Component parent;

    /**
     * @return get method for the field parent
     */
    public Component getParent() {

        return this.parent;
    }

    /**
     * @param parent
     *            the parent to set
     */
    public void setParent(Component parent) {

        this.parent = parent;
    }

}
