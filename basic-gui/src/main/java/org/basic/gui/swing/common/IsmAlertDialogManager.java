package org.basic.gui.swing.common;

/*
 ====================================================================================================

 File Name:      IsmAlertDialogManager.java

 Project:        Universal Client (Framework)
 Company:        Tellabs Operations Inc.
 Copyright:      Copyright (c) 2004
 Author(s):      Hani Dweik
 Date:           March 2, 2004

 Revision History:

 Date        Name                Modification
 ----------------------------------------------------------------------------------------------------
 04/20/2004  Hani Dweik          Applied code review comments.

 ====================================================================================================
 */

import java.awt.Dimension;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.basic.common.bean.CommonLogger;

/**
 * Title: Ism Alert Dialog Manager
 *
 * <p>
 * Description: A utility class that manages the creation and display of alert dialogs.
 *
 * <PRE>
 *       It provides a number of helper methods to create and display alert messages.
 * 
 *       This class is a utility class and should be used whenever there is a need to display
 *       an alert message dialog.
 * 
 *       As Swing-specific operations should only be done on a Swing thread, this utility should not
 *       be invoked (used) on a non-swing thread.
 * </PRE>
 *
 * @see: javax.swing.JOptionPane
 */
public class IsmAlertDialogManager {

    private static IsmAlertDialogManager instance_s = null; // a single instance of this class
    static final CommonLogger logger = CommonLogger.getLogger(IsmAlertDialogManager.class);

    /**
     * Default Constructor
     */
    private IsmAlertDialogManager() {
    }

    /**
     * Creates and Returns a single instance of this class.
     *
     * @return IsmAlertDialogManager - the resulted object
     */
    public static synchronized IsmAlertDialogManager instance() {
        if (instance_s == null) {
            instance_s = new IsmAlertDialogManager();
        }

        return instance_s;
    }

    /**
     * Use to display a dialog alert box.
     *
     * @param message
     *            <code>String</code> - the message text
     * @param title
     *            <code>String</code> - the title text of the alert dialog
     * @param messageType
     *            <code>int</code> - one of the standard JOptionPane message types
     * @param owner
     *            <code>Window</code> - the dialog or frame that owns this alert box.
     */
    public void showMessage(String message, String title, int messageType, Window owner) {
        if (owner == null) {
            String error = "Owner window is null, will not display message dialog.";
            logger.info("showMessage", 0, error);
            return;
        }

        JDialog alertDialog = null;
        JOptionPane pane = null;

        if (owner.isVisible()) {
            pane = new JOptionPane(message, messageType);

            Dimension aSize = pane.getPreferredSize();
            aSize.width = aSize.width + 10;
            pane.setPreferredSize(aSize);

            alertDialog = pane.createDialog(owner, title);

            alertDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            alertDialog.setResizable(true);
            alertDialog.setVisible(true);
        } else {
            String error = "Owner window is not visible, cannot display message dialog.";
            logger.info("showMessage", 0, error);
        }
    }

    /**
     * Displays an Information type JOptionPane messages.
     *
     * @param message
     *            <code>String</code> - the message text
     * @param owner
     *            <code>Window</code> - the dialog or frame that owns this alert box.
     */
    public void showInformation(String message, Window owner) {
        this.showMessage(message, "Information", JOptionPane.INFORMATION_MESSAGE, owner);
    }

    /**
     * Displays a Warning type JOptionPane messages.
     *
     * @param message
     *            <code>String</code> - the message text
     * @param owner
     *            <code>Window</code> - the dialog or frame that owns this alert box.
     */
    public void showWarning(String message, Window owner) {
        this.showMessage(message, "Warning", JOptionPane.WARNING_MESSAGE, owner);
    }

    /**
     * Displays an Error type JOptionPane messages.
     *
     * @param message
     *            <code>String</code> - the message text
     * @param owner
     *            <code>Window</code> - the dialog or frame that owns this alert box.
     */
    public void showError(String message, Window owner) {
        this.showMessage(message, "Error", JOptionPane.ERROR_MESSAGE, owner);
    }

    /**
     * Displays an Error type JOptionPane messages. However, this format is reserved for errors that result from failure
     * of completing a server request (server command).
     *
     * @param message
     *            <code>String</code> - the message text
     * @param number
     *            <code>int</code> - the error number, which will be displayed in the title bar.
     * @param owner
     *            <code>Window</code> - the dialog or frame that owns this alert box.
     */
    public void showRequestFail(String message, int number, Window owner) {
        StringBuffer buf = new StringBuffer();
        buf.append("Request failed.\nReason: ").append(message);
        String msg = buf.toString();

        buf.setLength(0);
        buf.append("Error - ").append(number);
        String title = buf.toString();

        this.showMessage(msg, title, JOptionPane.ERROR_MESSAGE, owner);
    }

    /**
     * Displays a Confirmation type JOptionPane message/question
     *
     * @param message
     *            <code>String</code> - the message text
     * @param owner
     *            <code>Window</code> - the dialog or frame that owns this alert box.
     *
     * @return int - return value is (JOptionPane.YES_OPTION, or JOptionPane.NO_OPTION)
     */
    public int showConfirmation(String message, Window owner) {
        if (owner == null) {
            String error = "Owner window is null, will not display message dialog.";
            logger.info("showConfirmation", 0, error);
            return JOptionPane.CLOSED_OPTION;
        }

        int returnValue = JOptionPane.YES_OPTION;

        JDialog alertDialog = null;
        JOptionPane pane = null;

        if (owner.isVisible()) {
            pane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
            Dimension aSize = pane.getPreferredSize();
            aSize.width = aSize.width + 10;
            pane.setPreferredSize(aSize);

            alertDialog = pane.createDialog(owner, "Confirmation");

            alertDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            alertDialog.setResizable(true);
            alertDialog.setVisible(true);

            Object exitValue = pane.getValue();

            if (exitValue == null) // assume NO, it is safer!
            {
                returnValue = JOptionPane.NO_OPTION;
            }

            int iValue = Integer.parseInt(exitValue.toString());
            if (iValue == -1 || iValue == 1) // -1 => ESC key, 1=> NO
            {
                returnValue = JOptionPane.NO_OPTION;
            }
        } else {
            String error = "Owner window is not visible, cannot display confirmation dialog.";
            logger.info("showConfirmation", 0, error);
            // ErrorLogger.logError(ErrorLogger.MAJOR, location, error);
        }

        return returnValue;
    }

}// end Class
