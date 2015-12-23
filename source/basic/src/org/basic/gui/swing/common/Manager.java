package org.basic.gui.swing.common;

import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.RootPaneContainer;

/**
 * <pre>
 *
 * </pre>
 */
class ManagerFrame {
    private JPanel jPanel_ContentPane = null; // local content pane
    private JToolBar myToolBar = null;
    public RootPaneContainer rootContainer = null;

}

public class Manager extends JApplet {
    static final ManagerFrame managerFrame = new ManagerFrame();

    public static final ManagerFrame getFrame() {
        return managerFrame;
    }

}
