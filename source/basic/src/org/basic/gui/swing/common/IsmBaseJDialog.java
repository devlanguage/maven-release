package org.basic.gui.swing.common;

/*
 ====================================================================================================

 File Name:      IsmBaseJDialog.java

 Project:        Universal Client (Framework)
 Company:        Tellabs Operations Inc.
 Copyright:      Copyright (c) 2004
 Author(s):      Hani Dweik
 Date:           March 2, 2004

 Revision History:

 Date        Name                Modification
 ----------------------------------------------------------------------------------------------------
 04/20/2004  Hani Dweik          Added methods to enable/disable the OK/Help buttons.
 Also, corrected the JavaDoc parameters.
 04/20/2004  Hani Dweik          Applied code review comments.

 ====================================================================================================
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.RootPaneContainer;

/**
 * Title: Ism Base Java Dialog
 *
 * <p>
 * Description: Represents the base class for all other dialogs.
 *
 * <PRE>
 *       This is a pure Java Swing GUI that is indpendent of any application.
 *       Therefore, it can be extended to specialize it for specific use.
 * </PRE>
 *
 * <PRE>
 *       Features:
 * </PRE>
 *
 * <PRE>
 *       It implements all the basics of a dialog such as outer margin, command button row,
 *       separator of command button row form the component area, default command buttons
 *       (OK and Cancel) and an optional Help command button.
 * 
 *       It implements common GUI features such as dismissing the dialog when the
 *       ESC key is pressed, and activating the default command button when the ENTER key
 *       is pressed. And proper sizing and positioning of the dialog. This means subclasses
 *       don't need to implement code that sizes the dialog and positions it for the initial display.
 * 
 *       It implements the mechanism to add/remove and modify command buttons in the
 *       command button row - each button will get an actionListener added to it automatically.
 *       This means that subclasses don't need to add action listener to the any command buttons,
 *       however they need to implement the method onCommandButton() to respond to
 *       events triggered by the command buttons (except for the dispose and help buttons).
 * 
 *       If a sub-class needs to add/remove or modify command buttons it can override the
 *       method initializeCommandButtons()
 * 
 *       A subclass can register a certain component in order to get initial keyboard focus
 *       when the dialog is first displayed. A component can be registered simply by calling
 *       registerComponentForInitialFocus(...)
 * 
 *       This class provides the necessary plumbing to display a help window upon activation
 *       of the Help dialog, provided the subclass provides the help text.
 * 
 *       This class provides wrapper methods to facilitate the display of Alert Messages by
 *       forwarding the call to the proper method in the IsmAlertDialogManager class.
 *       Subclasses don't need to import the utilities or provide an owner to the message dialog.
 *       All they need to do is pass a message text.
 *       Similarly, it provides wrapper methods to the IsmCientSwingWorker, IsmStringConcatenator,
 *       the Framework's ErrorLogger utilities.
 * </PRE>
 *
 * <p>
 * Notes:
 *
 * <PRE>
 *       The following constants are reserved for the default command buttons
 *       (ActionCommandNames) and must not be used by sub-classes:
 *         0 => OK_COMMAND
 *         1 => DISPOSE_COMMAND
 *        -1 => HELP_COMMAND
 * 
 *       Sub-class must call super.initializeDialog() after it finishes its own
 *       initialization (i.e. after its try-catch block of the jbInit() method)
 * 
 *       The sub-class doesn't need to call the pack() method.
 * 
 *       Sub-class must add specific implementation to the method
 *       onCommandButton(String actionCommandName) in order to handle action events
 *       triggered by command buttons, other than the default dispose and the help buttons.
 * 
 *       If a subclass chooses to have the Help command button, then it must override the
 *       method getHelpInformation(...) in order to return the help information to be displayed.
 * </PRE>
 * <p>
 * Methods to override:
 *
 * <PRE>
 *
 *       protected void initializeCommandButtons() (if needed)
 * 
 *       protected void onCommandButton(String actionCommandName)
 *       public Any invokeBusyCommandHandler(String serverCommandName, Object[] objectArray)
 *       public void swingWorkerFinished(String serverCommandName, Any commandResultAny)
 * 
 *       protected Component getHelpInformation() (if needed)
 * </PRE>
 * 
 * @see javax.swing.JDialog
 */

// DO NOT MAKE IT ABSTRACT
// JBUILDER DESIGNER DOES NOT WORK IF THIS IS ABSTRACT
public abstract class IsmBaseJDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = -7477822830908102555L;
    // Common Action Command Names - identifies each button uniquely
    public final static String OK_COMMAND = "0";
    public final static String DISPOSE_COMMAND = "1";
    public final static String HELP_COMMAND = "-1";

    private final int OUTER_MARGIN = 10; // 10-pixels outter margin

    // Counter of instances of this class
    private static int visibleInstancesCount_s = 0;

    // The component that gets the keyboard focus when the window is first opened
    private Component _focusOwnerComponent = null;

    // Did the GUI initialization complete successfully or not?
    private boolean _didInitializationSucceed = true;

    // Should this class provide a Help Button?
    private boolean _hasHelpButton = false; // NO!
    private boolean _hideHelpButton = true;

    // Common Command Buttons - in the command button row
    private JButton jButton_OK = null;
    private JButton jButton_Dispose = null;
    private JButton jButton_Help = null;

    // The root container of all widgets and its layout manager
    private Container container_ContentPane = null;
    private BorderLayout borderLayout_ContentPane = new BorderLayout(0, 2);

    // Container to all buttons in the command button row
    private JPanel jPanel_ButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    // Container to the buttons panel and the JSeparator Object
    private JPanel jPanel_SouthPanel = new JPanel(new BorderLayout());

    // Componenet container (the contentPane returned to the subclass)
    private JPanel jPanel_componentContainer = null;
    private JScrollPane jScrollPane_componentContainer = null;

    // Container to the south panel and the contentPane of the subclass.
    private JPanel jPanel_CenterPanel = new JPanel(new BorderLayout(0, 12));

    // Dialog's outer margins
    private Component north_margin = Box.createVerticalStrut(OUTER_MARGIN);
    private Component east_margin = Box.createHorizontalStrut(OUTER_MARGIN);
    private Component west_margin = Box.createHorizontalStrut(OUTER_MARGIN);
    private Component south_margin = Box.createVerticalStrut(OUTER_MARGIN);

    // Command Button Row separator object
    private JSeparator JS = new JSeparator(JSeparator.HORIZONTAL);

    private boolean _topLevelScrollEnabled = true;

    private boolean _shown = false;

    /**
     * Internal ISMSwingWorker that bridges form the new worker structure to the old dialog methods.
     *
     * This one bridges to invokeBusyCommandHandler and swingWorkerFinished
     */
    private class ISMSwingWorkerAny extends ISMSwingWorker<String, Vector> {
        public ISMSwingWorkerAny(RootPaneContainer rootPaneParent) {
            super(rootPaneParent);
        }

        @Override
        public Vector invokeBusyCommandHandler(String obj) {
            // TODO Auto-generated method stub
            return IsmBaseJDialog.this.invokeBusyCommandHandler(obj);
        }

        public void swingWorkerFinished(String obj, Vector vector) {
            IsmBaseJDialog.this.swingWorkerFinished(obj, vector);
        }

    };

    /**
     * Internal ISMSwingWorker that bridges form the new worker structure to the old dialog methods.
     *
     * This one bridges to invokeBusyCommandHandler2 and swingWorkerFinished2
     */
    private class ISMSwingWorkerObject extends ISMSwingWorker<String, Vector> {

        public ISMSwingWorkerObject(RootPaneContainer rootPaneParent) {
            super(rootPaneParent);
        }

        public void swingWorkerFinished(String obj, Object result) {
            swingWorkerFinished2(obj, result);
        }

        @Override
        public Vector invokeBusyCommandHandler(String obj) {
            return IsmBaseJDialog.this.invokeBusyCommandHandler2(obj);
        }
    };

    /**
     * Constructor - use to display the dialog on top of a <code>Frame</code>.
     *
     * @param frame
     *            <code>Frame</code> - the parent frame of this dialog
     * @param title
     *            <code>String</code> - the title of the dialog
     * @param isModal
     *            <code>boolean</code> - is the dialog modal or modeless
     * @param hasHelp
     *            <code>boolean</code> - should the dialog add the Help command button
     */
    public IsmBaseJDialog(Frame frame, String title, boolean isModal, boolean hasHelp) {
        super(frame, title, isModal);

        _hasHelpButton = hasHelp;
    }

    /**
     * Constructor - use to display a modal dialog on top of another dialog.
     *
     * @param dialog
     *            <code>JDialog</code> - the parent Dialog of this dialog
     * @param title
     *            <code>String</code> - the title of the dialog
     * @param hasHelp
     *            <code>boolean</code> - should the dialog add the Help command button
     */
    public IsmBaseJDialog(JDialog dialog, String title, boolean hasHelp) {
        super(dialog, title, true);

        _hasHelpButton = hasHelp;
    }

    /**
     * Constructor - use to display the dialog on top of the UC Frame.
     *
     * @param title
     *            <code>String</code> - the title of the dialog
     * @param isModal
     *            <code>boolean</code> - is the dialog modal or modeless
     * @param hasHelp
     *            <code>boolean</code> - should the dialog add the Help command button
     */
    public IsmBaseJDialog(String title, boolean isModal, boolean hasHelp) {
        // super(Manager.getManagerFrame().getCsDockableBarHolder(), title, isModal);

        _hasHelpButton = hasHelp;
    }

    /**
     * Constructor - use to display the dialog (modal) on top of the UC Frame.
     *
     * @param title
     *            <code>String</code> - the title of the dialog
     * @param hasHelp
     *            <code>boolean</code> - should the dialog add the Help command button
     */
    public IsmBaseJDialog(String title, boolean hasHelp) {
        this(title, true, hasHelp);
    }

    /**
     * Constructor - use to display untitled modal dialog on top of the UC Frame.
     *
     * @param hasHelp
     *            <code>boolean</code> - should the dialog add the Help command button
     */
    public IsmBaseJDialog(boolean hasHelp) {
        this("", hasHelp);
    }

    /**
     * Default constructor - display untitled modal dialog on top of the UC Frame, without Help Button.
     */
    public IsmBaseJDialog() {
        this(false);
    }

    /**
     * Handles action events triggered by activating command buttons in the command button row. Events of jButton_Cancel
     * are handled by calling the dispose() method. Events of jButton_Help are handled by calling the onHelp() method.
     *
     * All other events are forwarded to the subclass for specific implementation.
     *
     * @param e
     *            <code>ActionEvent</code> - the action event object
     */
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (actionCommand.equals(DISPOSE_COMMAND)) {
            this.dispose();
        } else if (actionCommand.equals(HELP_COMMAND)) {
            this.onHelp();
        } else {
            this.onCommandButton(actionCommand); // subclass handles the event
        }
    }

    /**
     * Handles the OK button event.
     *
     */
    protected void onOkay() {
        dispose();
    }

    /**
     * This method is automatically called by the IsmClientSwingWorker upon a request to start a busy cursor request.
     *
     * This method must be overriden and implemented in the sub-class.
     *
     * If the sub-class defines more than one server command, then the serverCommandName input parameter can be used to
     * identify the specific command.
     *
     * Based on the specific server command name, the sub-class will have to put together the required data (if any) and
     * invoke an appropriate command handler logic.
     *
     * This method retruns null, if the sub-class is not overriding it.
     *
     * Note: THIS METHOD IS RUN ON A NON-SWING (NON-GUI) THREAD. NO GUI UPDATE SHOULD TAKE PLACE INSIDE IT. GUI UPDATE
     * CAN (SHOULD) BE PREFORMED INSIDE THE swingWorkerFinished(...) METHOD, WHICH IS AUTOMATICALLY CALLED AFTER THE
     * IsmClientwingWorker HAS COMPLETED ITS TASK.
     *
     * @param serverCommandName
     *            <code>String</code> - the server command name (lengthy command).
     * @param objectArray
     *            <code>Object[]</code> - an array of object that can hold any Java object.
     *
     * @return Any - the CORBA Any object received from the server.
     */
    protected Vector invokeBusyCommandHandler(String serverCommandName) {
        // must be implemented in sub-class.
        return null;
    }

    protected Vector invokeBusyCommandHandler2(String serverCommandName) {
        // must be implemented in sub-class.

        return null;
    }

    /**
     * This method will be automatically called by the SwingWorker when the finished(...) method is reached to notify
     * the calling object that the swing worker has finished.
     *
     * @param serverCommandName
     *            <code>String</code> - the server command name (lengthy command).
     * @param commandResultAny
     *            <code>Any</code> - the CORBA Any object received from the server
     */
    protected void swingWorkerFinished(String serverCommandName, Vector vector) {
        // Subclass must implement the specific event handling code.
    }

    protected void swingWorkerFinished2(String serverCommandName, Object result) {
        // Subclass must implement the specific event handling code.
    }

    /**
     * Center the dialog and display
     *
     * @param isVisible
     *            <code>boolean</code> - true => make it visible, false => hide it.
     */
    public void setVisible(boolean isVisible) {
        if (!this.isVisible() && isVisible) {
            if (!wasInitializationSuccessful()) // Was GUI class successfully initialized?
            {
                return;
            }

            if (this.getOwner() == null) {
                centerRelativeToScreen();
            } else if (this.getOwner() instanceof JFrame) {
                visibleInstancesCount_s++; // increment for dialog's with UCFrame as the owner

                centerRelativeToOwnerWindow(this);
            } else if (this.getOwner() instanceof JDialog) {
                setLocationRelativeToDialog(); // the owner window is a Dialog
            } else // Owner is unknown
            {
                centerRelativeToScreen();
            }
        }
        super.setVisible(isVisible);
    }

    /**
     * sets the visiability and ignores automatic positioning based on the type of the owner
     * 
     * @param isVisible
     *            boolean
     */
    public void setVisibleIgnoreOwner(boolean isVisible) {
        super.setVisible(isVisible);
    }

    /**
     * Clean up and dispose the dialog.
     */
    public void dispose() {
        _focusOwnerComponent = null;

        if (!(this.getOwner() instanceof JDialog)) {
            visibleInstancesCount_s--; // decrement for dialog's with UCFrame as the owner
        }
        super.dispose();
    }

    /**
     * Override getContentPane() to return Work Area Panel (jPanel_CenterPane).
     *
     * The content pane of the subclass is added to the CENTER of jPanel_CenterPane All GUI widgets should be added to
     * the content pane in the subclass.
     *
     * Note: JBuilder GUI designer calls this method get the contianer.
     *
     * @return Container - a JPanel object to contain all objects (widgets) of the sub-class.
     */
    public Container getContentPane() {
        if (jPanel_componentContainer == null) {
            jPanel_componentContainer = new JPanel();
            jPanel_componentContainer.setLayout(new BorderLayout());
        }

        return this.jPanel_componentContainer;
    }

    /**
     * Sets a button to be the default command button of the dialog. That is the button that gets clicked by pressing
     * the ENTER Key.
     *
     * @param button
     *            <code>JButton</code> - the command button to become the default.
     */
    public void setDefaultCommandButton(JButton button) {
        this.getRootPane().setDefaultButton(button);
    }

    /**
     * Sets a button to be the default command button of the dialog. That is the button that gets clicked by pressing
     * the ENTER Key.
     *
     * @param commandName
     *            <code>String</code> - the command name of the button to become the default.
     */
    public void setDefaultCommandButton(String commandName) {
        JButton button = this.findTheCommandButton(commandName);

        if (button != null) {
            this.setDefaultCommandButton(button);
        }
    }

    /**
     * Enables or Disables a Command Button.
     *
     * @param button
     *            <code>JButton</code> - the command button to become the default.
     */
    public void setCommandButtonEnabled(JButton button, boolean isEnabled) {
        try {
            if (button == jButton_Help) {
                _hideHelpButton = false;
                if (!_hideHelpButton && !button.isVisible()) {
                    jButton_Help.setVisible(true);
                }
            }

            button.setEnabled(isEnabled);
        } catch (Exception e) // The button object is null. There is nothing to do.
        {
            e.printStackTrace();
        }
    }

    /**
     * Enables or Disables a Command Button.
     *
     * @param commandName
     *            <code>String</code> - the command name of the button to become the default.
     */
    public void setCommandButtonEnabled(String commandName, boolean isEnabled) {
        JButton button = this.findTheCommandButton(commandName);

        if (button != null) {
            this.setCommandButtonEnabled(button, isEnabled);
        }
    }

    /**
     * Sets the Keyboard Focus on the Command Button, when the dialog is first opened.
     *
     * @param button
     *            <code>JButton</code> - the command button to become the default.
     */
    public void transferInitialFocusToButton(JButton button) {
        this.registerComponentForInitialFocus(button);
    }

    /**
     * Sets the Keyboard Focus on the Command Button, when the dialog is first opened.
     *
     * @param commandName
     *            <code>String</code> - the command name of the button to become the default.
     */
    public void transferInitialFocusToButton(String commandName) {
        JButton button = this.findTheCommandButton(commandName);

        if (button != null) {
            this.transferInitialFocusToButton(button);
        }
    }

    /**
     * Transfers the Keyboard Focus from the button to the next component in the traversal cycle
     *
     * @param button
     *            <code>JButton</code> - the command button to become the default.
     */
    public void transferFocusFromButton(JButton button) {
        button.transferFocus();
    }

    /**
     * Transfers the Keyboard Focus from the button to the next component in the traversal cycle
     *
     * @param commandName
     *            <code>String</code> - the command name of the button to become the default.
     */
    public void transferFocusFromButton(String commandName) {
        JButton button = this.findTheCommandButton(commandName);

        if (button != null) {
            this.transferFocusFromButton(button);
        }
    }

    /**
     * Tests if the initialization of the GUI was sucessful or not. This flag is set to false only if the GUI
     * initialization catches an exception.
     *
     * @return boolean - (Default is true).
     */
    public boolean wasInitializationSuccessful() {
        return _didInitializationSucceed;
    }

    /**
     * This method is automatically called whenever a button from the Command Button Row is clicked.
     *
     * This method must be implemented in the sub-class.
     *
     * In the specific implementation, the button that triggered the event can be uniquely identified via its Action
     * Command Name (actionCommandName).
     *
     * @param serverCommandName
     *            <code>String</code> - the server command name (lengthy command).
     */
    protected void onCommandButton(String actionCommandName) {
        if (actionCommandName.equals(this.OK_COMMAND)) {
            onOkay();
        }
    }

    /**
     * Initializes the dialog.
     *
     * A sub-class should call this method last (after it finishes its own initialization).
     */
    protected void initializeDialog() {
        try {
            if (!wasInitializationSuccessful()) {

                String msg = "GUI Initialization Error. Check log files for more details.";

                IsmAlertDialogManager.instance().showError(msg, this.getOwner());
            } else {
                initCommonComponent(); // Local initialization
                jbInit();
                initializeButtonsPanel(); // initialize local Command Buttons
                initializeCommandButtons(); // initialize sub-class Command Buttons
                setPreferedButtonsSize(); // give all Command Buttons the same dimensions

                pack(); // pack the dialog to fit all its components

                this.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        if (_focusOwnerComponent != null) {
                            IsmBaseJDialog.this._focusOwnerComponent.requestFocusInWindow();
                        }
                    }

                    public void windowClosing(WindowEvent e) {
                        // simulate the cancel behaviour
                        jButton_Dispose.doClick();
                    }
                });
            }
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    /**
     * Sets a flag to indicate whether GUI initializtion was successful or not.
     * 
     * @param isSuccess
     *            boolean
     */
    protected void setInitializationStatus(boolean isSuccess) {
        _didInitializationSucceed = isSuccess;
    }

    /**
     * Registers the component that is interested in receiving keyboard focus, when the window is first opened.
     *
     * This method should be called, before the window is opened.
     *
     * @param component
     *            <code>Component</code> - the component to get initial keyboard focus
     */
    protected void registerComponentForInitialFocus(Component component) {
        _focusOwnerComponent = component;
    }

    /**
     * This method is automatically called when the Help button is activated. If a subclass uses the Help button, then
     * it must implement this method.
     *
     * This method returns null (if it is not overriden).
     *
     * @return Component - the component that contians the help information.
     */
    protected Component getHelpInformation() {
        // Sub-class can optionally override this method.
        return null;
    }

    /**
     * This method is automatically called to initialize the sub-class command buttons.
     *
     * It must be implemented in the sub-classes, if the sub-class needs to add/remove/modify Command Buttons. If not,
     * then don't worry about it.
     */
    protected void initializeCommandButtons() {
        // Sub-class can optionally override this method.
    }

    /**
     * This method adds command buttons to the command button row. The button object is passed in as an input parameter.
     * The button's location in the command button row is determined by the index number.
     *
     * Note: The command button row uses a FlowLayout Manager.
     *
     * @param aButton
     *            <code>JButton</code> - the JButton Object to add to the button's row
     * @param index
     *            <code>int</code> - the location of the button in the command button row
     * @param commandName
     *            <code>String</code> - the action command name for this button
     * @param tooltip
     *            <code>String</code> - the tooltip help text for this button
     * @param mnemonicKey
     *            <code>int</code> - the mnemonic KEY for this button
     */
    protected void addCommandButtonAt(JButton aButton, int index, String commandName, String tooltip, int mnemonicKey) {
        aButton.setActionCommand(commandName);
        aButton.setToolTipText(tooltip);
        aButton.setMnemonic(mnemonicKey);

        try {
            this.jPanel_ButtonsPanel.add(aButton, index);
        } catch (java.lang.IllegalArgumentException e) {
            // Illegal index, so place button first from the right
            if (e.getMessage().equals("illegal component position")) {
                this.jPanel_ButtonsPanel.add(aButton);
            }
        }

        aButton.addActionListener(this); // add action listener to the button
    }

    /**
     * This method adds command buttons to the command button row. The button object is passed in as an input parameter.
     * The button is placed first form the right.
     *
     * Note: The command button row uses a FlowLayout Manager.
     *
     * Note: index -99 is an illegal position, hence button is added first from the right
     *
     * @param aButton
     *            <code>JButton</code> - the JButton Object to add to the button's row
     * @param commandName
     *            <code>String</code> - the action command name for this button
     * @param tooltip
     *            <code>String</code> - the tooltip help text for this button
     * @param mnemonicKey
     *            <code>int</code> - the mnemonic KEY for this button
     */
    protected void addCommandButton(JButton aButton, String commandName, String tooltip, int mnemonicKey) {
        this.addCommandButtonAt(aButton, -99, commandName, tooltip, mnemonicKey);
    }

    /**
     * This method adds command buttons to the command button row. The button object is created and returned. The
     * button's location in the command button row is determined by the index number.
     *
     * Note: The command button row uses a FlowLayout Manager.
     *
     * @param buttonName
     *            <code>String</code> - the text (caption) that appears on the button.
     * @param index
     *            <code>int</code> - the location of the button in the command button row
     * @param commandName
     *            <code>String</code> - the action command name for this button
     * @param tooltip
     *            <code>String</code> - the tooltip help text for this button
     * @param mnemonicKey
     *            <code>int</code> - the mnemonic KEY for this button
     *
     * @return JButton - the button object reference.
     */
    protected JButton addCommandButtonAt(String buttonName, int index, String commandName, String tooltip, int mnemonicKey) {
        JButton aButton = new JButton(buttonName);

        this.addCommandButtonAt(aButton, index, commandName, tooltip, mnemonicKey);

        return aButton;
    }

    /**
     * This method adds command buttons to the command button row. The button object is created and returned. The button
     * is located as the first from the right.
     *
     * Note: The command button row uses a FlowLayout Manager.
     *
     * Note: index -99 is an illegal position, hence button is added first from the right
     *
     * @param buttonName
     *            <code>String</code> - the text (caption) that appears on the button.
     * @param commandName
     *            <code>String</code> - the action command name for this button
     * @param tooltip
     *            <code>String</code> - the tooltip help text for this button
     * @param mnemonicKey
     *            <code>int</code> - the mnemonic KEY for this button
     *
     * @return JButton - the button object reference.
     */
    protected JButton addCommandButton(String buttonName, String commandName, String tooltip, int mnemonicKey) {
        JButton aButton = null;

        aButton = this.addCommandButtonAt(buttonName, -99, commandName, tooltip, mnemonicKey);

        return aButton;
    }

    /**
     * Modifies attributes of a command button in the command button row.
     *
     * @param aButton
     *            <code>JButton</code> - the JButton Object to modify its properties
     * @param newButtonName
     *            <code>String</code> - the new caption
     * @param newCommandName
     *            <code>String</code> - the new action command name
     * @param newTooltip
     *            <code>String</code> - the new tooltip help text for this button
     * @param newMnemonicKey
     *            <code>int</code> - the new mnemonic KEY for this button
     */
    protected void modifyCommandButton(JButton aButton, String newButtonName, String newCommandName, String newTooltip, int newMnemonicKey) {
        aButton.setText(newButtonName);
        aButton.setToolTipText(newTooltip);
        aButton.setMnemonic(newMnemonicKey);
        aButton.setActionCommand(newCommandName);
    }

    /**
     * Modifies attributes of a command button in the command button row.
     *
     * @param oldCommandName
     *            <code>String</code> - the old action command name
     * @param newCommandName
     *            <code>String</code> - the new action command name
     * @param newButtonName
     *            <code>String</code> - the new caption
     * @param newTooltip
     *            <code>String</code> - the new tooltip help text for this button
     * @param newMnemonicKey
     *            <code>int</code> - the new mnemonic KEY for this button
     */
    protected void modifyCommandButton(String oldCommandName, String newCommandName, String newButtonName, String newTooltip,
                                       int newMnemonicKey) {
        JButton theButton = findTheCommandButton(oldCommandName);

        if (theButton != null) {
            modifyCommandButton(theButton, newButtonName, newCommandName, newTooltip, newMnemonicKey);
        }
    }

    protected void modifyCommandButton(String commandName, ActionListener newListener) {
        JButton theButton = findTheCommandButton(commandName);

        if (theButton != null) {
            theButton.removeActionListener(this);
            theButton.addActionListener(newListener);
        }
    }

    /**
     * Modifies attributes of a command button in the command button row.
     *
     * @param index
     *            <code>int</code> - the index (position) that identifies the button to modify
     * @param newCommandName
     *            <code>String</code> - the new action command name
     * @param newButtonName
     *            <code>String</code> - the new caption
     * @param newTooltip
     *            <code>String</code> - the new tooltip help text for this button
     * @param newMnemonicKey
     *            <code>int</code> - the new mnemonic KEY for this button
     */
    protected void modifyCommandButtonAt(int index, String newCommandName, String newButtonName, String newTooltip, int newMnemonicKey) {
        JButton theButton = (JButton) this.jPanel_ButtonsPanel.getComponent(index);

        if (theButton != null) {
            modifyCommandButton(theButton, newButtonName, newCommandName, newTooltip, newMnemonicKey);
        }
    }

    /**
     * Modifies attributes of the default command button
     *
     * @param newButtonName
     *            <code>String</code> - the new caption
     * @param newTooltip
     *            <code>String</code> - the new tooltip help text for this button
     */
    protected void modifyDefaultButton(String newButtonName, String newTooltip) {
        this.jButton_OK.setText(newButtonName);
        this.jButton_OK.setToolTipText(newTooltip);
    }

    /**
     * Modifies attributes of the default command button
     *
     * @param enabled
     *            <code>boolean</code> - enabled
     */
    protected void modifyDefaultButton(boolean enabled) {
        this.jButton_OK.setEnabled(enabled);
    }

    /**
     * Modifies attributes of the default dispose button
     *
     * @param newButtonName
     *            <code>String</code> - the new caption
     * @param newTooltip
     *            <code>String</code> - the new tooltip help text for this button
     */
    protected void modifyDisposeButton(String newButtonName, String newTooltip) {
        this.jButton_Dispose.setText(newButtonName);
        this.jButton_Dispose.setToolTipText(newTooltip);
    }

    /**
     * Removes a button from the command button row, except for the default command button
     *
     * @param aButton
     *            <code>JButton</code> - the button to be removed
     */
    protected void removeCommandButton(JButton aButton) {
        if (aButton.equals(this.jButton_OK)) {
            return;
        }

        aButton.removeActionListener(this);

        this.jPanel_ButtonsPanel.remove(aButton);
    }

    /**
     * @param commandName
     *            String
     * @param force
     *            boolean - forces removal no matter whihc buttonit is
     */
    protected void removeCommandButton(String commandName, boolean force) {
        JButton theButton = findTheCommandButton(commandName);

        if (theButton != null) {
            if (force) {
                theButton.removeActionListener(this);
                this.jPanel_ButtonsPanel.remove(theButton);
            } else {
                removeCommandButton(theButton);
            }
        }
    }

    /**
     * Removes a button from the command button row
     *
     * @param commandName
     *            <code>String</code> - the Action Command Name of the button to be removed
     */
    protected void removeCommandButton(String commandName) {
        JButton theButton = findTheCommandButton(commandName);

        if (theButton != null) {
            this.removeCommandButton(theButton);
        }
    }

    /**
     * Removes a button from the command button row
     *
     * @param index
     *            <code>intg</code> - the index (position) of the button to be removed
     */
    protected void removeCommandButtonAt(int index) {
        JButton theButton = (JButton) this.jPanel_ButtonsPanel.getComponent(index);

        if (theButton != null) {
            this.removeCommandButton(theButton);
        }
    }

    /**
     * Fowards the request to display Confirmation type messages/questions and returns an int (indicates user selection
     * (Msg.YES_OPTION or Msg.NO_OPTION)
     *
     * @param msg
     *            <code>String</code> - the message (question) to display
     * @return int - the value corresponding to the YES or NO button.
     */
    public int showConfirmation(String msg) {
        int iValue = IsmAlertDialogManager.instance().showConfirmation(msg, this);

        return iValue; // return option either ( YES or NO)
    }

    /**
     * Fowards the request to display Information type messages
     *
     * @param msg
     *            <code>String</code> - the message text to display
     */
    public void showInformation(String msg) {
        IsmAlertDialogManager.instance().showInformation(msg, this);
    }

    /**
     * Fowards the request to display Warning type messages
     *
     * @param msg
     *            <code>String</code> - the message text to display
     */
    public void showWarning(String msg) {
        IsmAlertDialogManager.instance().showWarning(msg, this);
    }

    /**
     * Fowards the request to display Error type messages. This format is reserved to indicate a user's action or data
     * validation resulted in this error.
     *
     * @param msg
     *            <code>String</code> - the message text to display
     */
    public void showError(String msg) {
        IsmAlertDialogManager.instance().showError(msg, this);
    }

    /**
     * Fowards the request to display Error type messages. This format is reserved for errors (request failures) due to
     * problems caught while communicating with the server.
     *
     * @param msg
     *            <code>String</code> - the message text to display
     * @param number
     *            <code>int</code> - the error number (error code) to display in the title bar
     */
    public void showRequestFail(String msg, int number) {
        IsmAlertDialogManager.instance().showRequestFail(msg, number, this);
    }

    /**
     * Fowards the request to start a swing worker thread and forward the busy cursor request on it.
     *
     * @param serverCommandName
     *            <code>String</code> - the server command name (lengthy command).
     * @param objectArray
     *            <code>Object[]</code> - an array of object that can hold any Java object.
     */
    protected void startBusyWorkerThread(final String commandName) {
        new ISMSwingWorkerAny(this).start(commandName);
    }

    /**
     * Fowards the request to start a swing worker thread and forward the busy cursor request on it.
     *
     * @param serverCommandName
     *            <code>String</code> - the server command name (lengthy command).
     * @param objectArray
     *            <code>Object[]</code> - an array of object that can hold any Java object.
     */
    protected void startBusyWorkerThread2(final String commandName) {
        new ISMSwingWorkerObject(this).start(commandName);
    }

    /**
     * Initializes all the GUI widgets of this dialog
     */
    private void initCommonComponent() throws Exception {
        this.jPanel_SouthPanel.add(JS, BorderLayout.NORTH);
        this.jPanel_SouthPanel.add(jPanel_ButtonsPanel, BorderLayout.CENTER);

        this.jPanel_CenterPanel.add(jPanel_SouthPanel, BorderLayout.SOUTH);

        if (_topLevelScrollEnabled) {
            jScrollPane_componentContainer = new JScrollPane(this.getContentPane());
            jScrollPane_componentContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            jScrollPane_componentContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            jScrollPane_componentContainer.setBorder(null);

            this.jPanel_CenterPanel.add(jScrollPane_componentContainer, BorderLayout.CENTER);
        } else {
            this.jPanel_CenterPanel.add(this.getContentPane(), BorderLayout.CENTER);
        }

        container_ContentPane = super.getRootPane().getContentPane();
        container_ContentPane.setLayout(borderLayout_ContentPane);

        container_ContentPane.add(this.jPanel_CenterPanel, BorderLayout.CENTER);

        if (isMarginSettable()) {
            container_ContentPane.add(north_margin, BorderLayout.NORTH);
            container_ContentPane.add(south_margin, BorderLayout.SOUTH);
            container_ContentPane.add(east_margin, BorderLayout.EAST);
            container_ContentPane.add(west_margin, BorderLayout.WEST);
        }

        /* Default Close Operation - dispose dialog when (x) button is clicked */
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        /* Dispose the Dialog when the ESC key is Pressed. */
        ActionListener ESC_actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // simulate the cancel behaviour
                jButton_Dispose.doClick();
            }
        };

        this.getRootPane().registerKeyboardAction(ESC_actionListener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    protected abstract void jbInit() throws Exception;

    public boolean isMarginSettable() {
        return true;
    }

    /**
     * Initializes the buttons panel of the dialog. It adds all the common buttons
     */
    private void initializeButtonsPanel() {
        String OK = "OK";
        String CANCEL = "Cancel";
        String HELP = "Help";
        String OK_TOOLTIP = "Accept and Close";
        String CANCEL_TOOLTIP = "Dismiss";
        String HELP_TOOLTIP = "Show Help";

        jButton_OK = new JButton(OK);
        jButton_OK.setActionCommand(OK_COMMAND);
        jButton_OK.setToolTipText(OK_TOOLTIP);

        jButton_OK.addActionListener(this);

        this.jPanel_ButtonsPanel.add(this.jButton_OK, 0);

        /* Default Key - Activated when ENTER is pressed */
        this.setDefaultCommandButton(this.jButton_OK);

        jButton_Dispose = new JButton(CANCEL);
        jButton_Dispose.setActionCommand(DISPOSE_COMMAND);
        jButton_Dispose.setToolTipText(CANCEL_TOOLTIP);

        jButton_Dispose.addActionListener(this);

        this.jPanel_ButtonsPanel.add(this.jButton_Dispose, 1);

        if (_hasHelpButton) {
            jButton_Help = addCommandButtonAt(HELP, 2, HELP_COMMAND, HELP_TOOLTIP, KeyEvent.VK_H);

            /* Display the Help Dialog when the F1 key is Pressed. */
            ActionListener F1_actionListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (IsmBaseJDialog.this.jButton_Help.isEnabled()) {
                        IsmBaseJDialog.this.onHelp();
                    }

                }
            };

            this.getRootPane().registerKeyboardAction(F1_actionListener, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, false),
                    JComponent.WHEN_IN_FOCUSED_WINDOW);

        }
    }

    /**
     * Sets the prefered size of each button in the command button row. It figures out the widest button, gets its
     * prefered dimension, and then uses it to set the prefered size of all the other buttons.
     *
     * This method needs to be called only once after all buttons have been added.
     */
    private void setPreferedButtonsSize() {
        Dimension buttonSize = new Dimension();
        Dimension tempSize = null;
        int tempWidth = 0;

        int numberOfButtons = jPanel_ButtonsPanel.getComponentCount();

        /* Find the widest button in the command button row */
        for (int i = 0; i < numberOfButtons; i++) {
            JButton button = (JButton) this.jPanel_ButtonsPanel.getComponent(i);

            tempSize = button.getPreferredSize();
            tempWidth = tempSize.width;

            if (buttonSize.width < tempWidth) {
                buttonSize = tempSize;
            }
        }

        if (buttonSize.width > 0) {
            /* Set all command buttons to the same dimesion */
            for (int i = 0; i < numberOfButtons; i++) {
                JButton button = (JButton) this.jPanel_ButtonsPanel.getComponent(i);
                button.setPreferredSize(buttonSize);
            }
        }
    }

    /**
     * Searches the button panel for a specific button by testing the button's action command name. If the button is
     * found, it is returned. If not, then null is returned.
     *
     * @param actionCommandName
     *            <code>String</code> - the action command name of the button to find
     *
     * @return JButton - the button object, if found or null.
     */
    private JButton findTheCommandButton(String actionCommandName) {
        JButton theButton = null;

        int numberOfButtons = jPanel_ButtonsPanel.getComponentCount();

        for (int i = 0; i < numberOfButtons; i++) {
            JButton tmpButton = (JButton) this.jPanel_ButtonsPanel.getComponent(i);

            if (tmpButton.getActionCommand().equals(actionCommandName)) {
                theButton = tmpButton; // Found the button with the actionCommandName
                break;
            }
        }

        return theButton;
    }

    /**
     * Center the dialog relative the frame
     *
     * @param ownerWindow
     *            <code>Window</code> - the window object that will parent this dialog.
     */
    private void centerRelativeToOwnerWindow(Window ownerWindow) {
        if (visibleInstancesCount_s <= 1) // only one single dialog is visible on the Frame.
        {
            this.setLocationRelativeTo(ownerWindow);
        } else if (visibleInstancesCount_s >= 2) // more than one dialog is visible on the Frame.
        {
            this.centerAndStagger(ownerWindow);
        } else // just in case something goes wrong...
        {
            this.setLocationRelativeTo(ownerWindow);
        }
    }

    /**
     * Center the dialog on the frame, but stagger (south-east)
     *
     * @param ownerWindow
     *            <code>Window</code> - the window object that will parent this dialog.
     */
    private void centerAndStagger(Window ownerWindow) {
        int staggeringFactor = 0;

        if (visibleInstancesCount_s > 1) {
            staggeringFactor = 40 * (visibleInstancesCount_s - 1);
        }

        Dimension windowSize = ownerWindow.getSize(); // Frame size
        Point frameLocation = ownerWindow.getLocation(); // Frame Location
        Dimension dialogSize = this.getSize(); // Dialog size

        int xLocation = ((windowSize.width - dialogSize.width) / 2) + frameLocation.x + staggeringFactor;

        int yLocation = ((windowSize.height - dialogSize.height) / 2) + frameLocation.y + staggeringFactor;

        this.setLocation(xLocation, yLocation);
    }

    /**
     * Sets the location of a child dialog relative to its parent (owner) dialog.
     *
     */
    private void setLocationRelativeToDialog() {
        int factor = 50;

        Window owner = this.getOwner();

        Point ownerLocation = owner.getLocation();
        Dimension ownerSize = owner.getSize();
        int ownerWidth = ownerSize.width;
        int ownerHeight = ownerSize.height;

        Dimension dialogSize = this.getSize();
        int dialogWidth = dialogSize.width;
        int dialogHeight = dialogSize.height;

        if ((ownerWidth > dialogWidth + factor) && (ownerHeight > dialogHeight + factor)) {
            this.setLocationRelativeTo(owner);
        } else {
            int xLocation = ((ownerWidth - dialogWidth) / 2) + ownerLocation.x + factor;
            int yLocation = ((ownerHeight - dialogHeight) / 2) + ownerLocation.y + factor;

            this.setLocation(xLocation, yLocation);
        }
    }

    /**
     * Centers the dialog relative to the screen
     */
    private void centerRelativeToScreen() {
        // setting this to NULL centers the dialog
        setLocationRelativeTo(null);
    }

    /**
     * Retrieves the help document by calling the getHelpInformation() and displays it.
     */
    private void onHelp() {
        Component helpInfo = this.getHelpInformation();

        if (helpInfo != null) {
            IsmBaseHelpDialog helpDialog = new IsmBaseHelpDialog(this, "Help", helpInfo);

            helpDialog.setVisible(true);
        } else {
            showError("Unable to display help, can't retrieve help information.");
        }
    }

    /**
     * Figures out the class name of this instance, then appends to it '.' and the method name and '()'. And returns the
     * full location name.
     *
     * @param methodName
     *            <code>String</code> - the string text (method name)
     * @return String - the full path location string.
     */
    private String getFullLocationName(String methodName) {
        String className = getClass().getName();

        return new StringBuffer(className).append(".").append(methodName).append("()").toString();
    }

    public void show() {
        if (!_shown) {
            Dimension d = getSize();
            d.setSize(d.getWidth() + 16, d.getHeight() + 16);
            setSize(d);
            _shown = true;
            if (_hasHelpButton && _hideHelpButton && getHelpInformation() == null) {
                jButton_Help.setVisible(false);
            }

        }
        super.show();
    }

    protected void center() {
        // setting this to NULL centers the dialog
        setLocationRelativeTo(null);
    }

    public boolean isTopLevelScrollEnabled() {
        return _topLevelScrollEnabled;
    }

    /**
     * Sometimes a dialog is capable of handling all scrolling by itself. this will turn off the automatic scrolling
     * functionality if set before initDialog is called.
     * 
     * @param topLevelScrollEnabled
     */
    public void setTopLevelScrollEnabled(boolean topLevelScrollEnabled) {
        this._topLevelScrollEnabled = topLevelScrollEnabled;
    }

    public JButton getJButton_Dispose() {
        return jButton_Dispose;
    }

    public void setJButton_Dispose(JButton button_Dispose) {
        jButton_Dispose = button_Dispose;
    }

    public JButton getJButton_Help() {
        return jButton_Help;
    }

    public void setJButton_Help(JButton button_Help) {
        jButton_Help = button_Help;
    }

    public JButton getJButton_OK() {
        return jButton_OK;
    }

    public void setJButton_OK(JButton button_OK) {
        jButton_OK = button_OK;
    }

} // End of IsmBaseJDialog
