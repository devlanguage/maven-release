package org.basic.jdk.core.jdk.jdk6.swing.bean;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

import org.basic.common.util.BasicException;
import org.basic.jdk.core.jdk.jdk6.swing.listener.OpenListener;

/**
 * 
 */
public class BasicJFrame extends JFrame {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2011899524288601251L;
    private BasicJMenubar basicJMenuBar;

    public BasicJFrame(String title) {

        super(title);

    }

    public void buildListeners(BasicJMenubar basicJMenubar) {

        this.setBasicJMenuBar(basicJMenubar);
        MenuLevel menuLevel = null;
        try {
            menuLevel = basicJMenubar.getMenuLevel("level2_Open File");
        } catch (BasicException e) {
            e.printStackTrace();
        }
        JMenuItem openMenuItem = (JMenuItem) menuLevel.getMenuElement();
        openMenuItem.addActionListener(new OpenListener(this));

    }

    /**
     * @return get method for the field basicJMenuBar
     */
    public BasicJMenubar getBasicJMenuBar() {

        return this.basicJMenuBar;
    }

    /**
     * @param basicJMenuBar
     *            the basicJMenuBar to set
     */
    public void setBasicJMenuBar(BasicJMenubar basicJMenuBar) {

        this.setJMenuBar(basicJMenuBar);
        this.basicJMenuBar = basicJMenuBar;
    }

}
