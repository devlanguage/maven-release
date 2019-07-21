package org.basic.gui.swing.common;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JScrollPane;

/**
 * <pre>
 *
 * </pre>
 */
public class IsmBaseHelpDialog extends IsmBaseJDialog {
    private static final long serialVersionUID = -1423755764108425530L;
    private JScrollPane jScrollPane = null;
    private Component _helpInformation = null;

    /**
     * Default constructor
     *
     * @param owner
     *            <code>JDialog</code> - the parent Dialog of this dialog
     * @param title
     *            <code>String</code> - the title of the dialog
     * @param helpInfo
     *            <code>Component</code> - the Component containing the help information.
     */
    public IsmBaseHelpDialog(JDialog owner, String title, Component helpInfo) {
        super(owner, title, false);

        _helpInformation = helpInfo;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        initializeDialog();

        transferInitialFocusToButton(IsmBaseJDialog.OK_COMMAND);
    }

    /**
     * Initializes the dialog
     */
    protected void jbInit() throws Exception {
        jScrollPane = new JScrollPane();
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.getViewport().add(_helpInformation, null);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(jScrollPane, BorderLayout.CENTER);
    }

    /**
     * This method is automatically called to initialize the command buttons.
     */
    public void initializeCommandButtons() {
        removeCommandButton(DISPOSE_COMMAND); // remove the Cancel button
        modifyDefaultButton("Close", "Dismiss Help"); // convert OK to Close
    }

    /**
     * Clean up and dispose the dialog.
     */
    public void dispose() {
        _helpInformation = null;
        jScrollPane = null;

        super.dispose();
    }
}