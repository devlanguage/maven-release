/*
===============================================================================
This material is the property of, and confidential to, Tellabs.  Unauthorized
use,  reproduction, or distribution is strictly prohibited.
Copyright 2007, Tellabs.

Project: OTS JClient

Notes:

History:
Date         Name            Modification
-----------  --------------- -------------------------------

===============================================================================
 */

package org.basic.gui.swing.common;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.RootPaneContainer;

import com.jidesoft.docking.DockableFrame;
import com.jidesoft.document.DocumentComponentEvent;
import com.jidesoft.document.DocumentComponentListener;

public abstract class PacketBasePanel extends JPanel implements DocumentComponentListener {
    protected EmsBaseMObject _baseMOClass;
    protected EmsBaseMObject _neBaseMOClass;
    protected EmsBaseMObject _pktdsBaseMOClass;
    protected EmsBaseMObject _shelfBaseMOClass;
    private String _selectedTabTitle = null;
    private String _key;
    private boolean _isDuplicatable = false;
    private String _command = null;
    protected JButton jButton_help = new JButton("Help");
    private PacketBasePanel _parentScreen = null;

    /**
     * Constructor
     */
    public PacketBasePanel() {
        super();
    }

    /**
     * Constructor
     */
    public PacketBasePanel(EmsBaseMObject baseMOClass) {
        super();

    }

    /**
     * Constructor
     * 
     * @param flagCompose
     *            boolean
     */
    public PacketBasePanel(boolean flagCompose) {
        super();
    }

    /**
     * Set the parent frame document key where this panel was launched
     * 
     * @param key
     *            the key string
     */
    public void setKey(String key) {
        _key = key;
    }

    /**
     * Get the parent frame document key where this panel was launched
     * 
     * @return the key string
     */
    public String getKey() {
        return _key;
    }

    /**
     * Cleanup here
     */
    public void cleanUp() {
    }

    /**
     * Children must override this
     * 
     * @param obj
     *            Object[]
     * @return boolean
     */
    public abstract boolean populateData(Object[] obj);

    /**
     * Chldren must override this
     * 
     * @param commandResultAny
     *            Any
     * @return boolean
     */
    public abstract boolean populateData(Object commandResultAny);

    public void initializeData() {
    }

    public void initialize() {
    }

    /**
     * Called from base class on a non-event thread to process long non-gui operations
     * 
     * @param command
     *            String
     * @return Any
     */
    protected Object processWorkerStart(String command) {
        return null;
    }

    /**
     * Subclasses cal override this method to perform GUI related operations on a n event dispatch thread
     * 
     * @param serverCommandName
     *            String
     * @param commandResultAny
     *            Any
     */
    protected void processWorkerFinished(String serverCommandName, Object commandResultAny) {
    }

    public JRootPane getAppropriateRootPane() {
        if (getRootPane() == null) {
            return getRootPaneAncestor(this);
        } else {
            return getRootPane();
        }
    }

    /**
     * This uitlity method find the first ancestor of the target container with a root pane.
     * 
     * @param target
     *            Container
     * @return JRootPane
     */
    public static JRootPane getRootPaneAncestor(Container target) {
        for (Container p = target.getParent(); p != null; p = p.getParent()) {
            if (p instanceof RootPaneContainer) {
                return ((RootPaneContainer) p).getRootPane();
            }

            if (p instanceof DockableFrame) {
                return ((DockableFrame) p).getRootPane();
            }
        }
        return Manager.getFrame().rootContainer.getRootPane();
    }

    public void documentComponentOpened(DocumentComponentEvent documentComponentEvent) {
    }

    public void documentComponentClosing(DocumentComponentEvent documentComponentEvent) {
        cleanUp();
    }

    public void documentComponentClosed(DocumentComponentEvent documentComponentEvent) {
    }

    public void documentComponentMoving(DocumentComponentEvent documentComponentEvent) {
    }

    public void documentComponentMoved(DocumentComponentEvent documentComponentEvent) {
    }

    public void documentComponentActivated(DocumentComponentEvent documentComponentEvent) {
    }

    public void documentComponentDeactivated(DocumentComponentEvent documentComponentEvent) {
    }

    /**
     * Swing worker class
     */
    protected ISMSwingWorker<String, Object> packetWorker = new ISMSwingWorker<String, Object>(
            PacketBasePanel.this.getAppropriateRootPane()) {
        // Get the rootpane of the tabbed frame if possible
        public void start(final String obj) {
            this.setRootPane(PacketBasePanel.this.getAppropriateRootPane());
            if (PacketBasePanel.this instanceof PacketBaseTabManagementPanel) {
                if (PacketBasePanel.this.getAppropriateRootPane().getGlassPane() instanceof JAnimatedGlassPane) {
                    ((JAnimatedGlassPane) PacketBasePanel.this.getAppropriateRootPane().getGlassPane()).setAnimateEnabled(true);
                }
            } else {
                if (PacketBasePanel.this.getAppropriateRootPane().getGlassPane() instanceof JAnimatedGlassPane) {
                    ((JAnimatedGlassPane) PacketBasePanel.this.getAppropriateRootPane().getGlassPane()).setAnimateEnabled(false);
                }
            }
            super.start(obj);
        }

        public Object invokeBusyCommandHandler(String command) {
            return PacketBasePanel.this.processWorkerStart(command);
        }

        public void swingWorkerFinished(String serverCommandName, Object commandResultAny) {
            PacketBasePanel.this.processWorkerFinished(serverCommandName, commandResultAny);
        }
    };

}
