package org.basic.arithmetic.structure.red_black;

/* AlgAnimFrame.java */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CheckboxMenuItem;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.net.URL;

import org.basic.arithmetic.data.ComPanel;
import org.basic.arithmetic.data.ImageButton;
import org.basic.arithmetic.data.TextFrame;

/**
 * The <code>AlgAnimFrame</code> class is a top-level window with a title and
 * border. The layout used is BorderLayout. There is a menubar created at the
 * north part of the panel. The drawing panel is at the center and a commentary
 * panel is located at the south.
 * <p>
 * This extended frame class holds a set references to the panel/frame objects
 * used in the algorithm animation.
 * 
 * @see Frame
 */
public class AlgAnimFrame extends Frame {

    private AlgAnimApp parentApp;
    private URL sourceURL;
    private String algname;
    private AlgThread alg = null;
    private int delay = 200;
    // changed this..
    // private Panel panel;
    private DrawingPanel dpBefore, dpAfter;
    private FlowPanel panel;
    // private DataPanel dataPanel;

    private TextFrame tf;
    private ComPanel cpanel;
    private boolean step = false, stepWait = false;
    private Dimension frameSize = new Dimension(850, 700);

    // control panel
    private MenuItem quitItem;
    private Button runItem, stopItem, stepItem, skipItem;
    private CheckboxMenuItem enableAnim, disableAnim;
    private CheckboxMenuItem[] dataChoice, delayChoice;
    private Menu dataMenu, delayMenu;
    private int dataSelected = 0;
    private boolean noAnim = false;
    Font helv10 = new Font("Helvetica", Font.PLAIN, 10);
    private Font helv14 = new Font("Helvetica", Font.PLAIN, 14);
    Font helv18 = new Font("Helvetica", Font.PLAIN, 18);
    Font helv24 = new Font("Helvetica", Font.PLAIN, 24);
    private ControlPanel cp = null;

    /**
     * Creates and shows the frame consists of a drawing panel, commentary panel
     * and control panel. The text frame is now displayed on a separate window.
     * 
     * @param parentApp
     *            The applet which results in the creation of this frame
     * @param sourceURL
     *            The URL of the source code to be displayed on the text frame
     * @see URL
     */
    public AlgAnimFrame(AlgAnimApp parentApp, URL sourceURL) {

        this.parentApp = parentApp;
        this.sourceURL = sourceURL;
        this.algname = parentApp.getParameter("algname");

        dpBefore = new DrawingPanel();
        dpAfter = new DrawingPanel();
        // dataPanel = new DataPanel();

        setTitle(algname);
        move(0, 0);
        alg = new AlgThread(this);

        setLayout(new BorderLayout());

        setFont(helv14);
        cp = new ControlPanel(this, algname);
        add("North", cp);
        runItem = cp.getRunButton();
        stopItem = cp.getStopButton();
        stepItem = cp.getStepButton();
        skipItem = cp.getSkipButton();

        // changed...
        panel = new FlowPanel(this);
        add("Center", panel);

        /* Source panel */
        tf = new TextFrame(sourceURL);

        /* Commentary panel */
        cpanel = new ComPanel(algname, 1);
        add("South", cpanel);

        setMenuBar(createMenuBar());
        setMenuBar(createMenuBar());

        // generate initial data set
        alg.generateData();

        pack();
        validate();
        show();
        // if (tf.getTextPanel().getNumLines() > 0)
        // tf.toFront();
        cp.refreshButtons();
    } // init()

    private MenuBar createMenuBar() {

        MenuBar mb = new MenuBar();

        mb.setFont(helv14);
        dataMenu = new Menu("Select");
        mb.add(dataMenu);
        dataChoice = new CheckboxMenuItem[alg.dataSets.length];
        for (int i = 0; i < alg.dataSets.length; i++) {
            dataChoice[i] = new CheckboxMenuItem(alg.dataSets[i]);
            dataMenu.add(dataChoice[i]);
        }
        if (alg.dataSets.length > 0)
            dataChoice[0].setState(true);
        dataMenu.addSeparator();
        quitItem = new MenuItem("Quit");
        dataMenu.add(quitItem);

        /*
         * Menu algMenu = new Menu("Algorithm", true); mb.add(algMenu); runItem =
         * new MenuItem("Run " + algname); stopItem = new MenuItem("Stop " +
         * algname); stepItem = new MenuItem("Next Step..."); skipItem = new
         * MenuItem("Skip..."); algMenu.add(runItem); algMenu.add(stopItem);
         * algMenu.add(stepItem); algMenu.add(skipItem);
         */

        Menu animMenu = new Menu("Animation");
        mb.add(animMenu);
        enableAnim = new CheckboxMenuItem("Enable");
        enableAnim.setState(true);
        disableAnim = new CheckboxMenuItem("Disable");
        animMenu.add(enableAnim);
        animMenu.add(disableAnim);
        animMenu.addSeparator();
        delayMenu = new Menu("Delay");
        animMenu.add(delayMenu);
        delayChoice = new CheckboxMenuItem[5];
        for (int i = 0; i < 5; i++) {
            delayChoice[i] = new CheckboxMenuItem("" + ((i + 1) * 200) + "msec");
            delayMenu.add(delayChoice[i]);
        }
        delayChoice[0].setState(true);

        Menu viewMenu = new Menu("View");
        mb.add(viewMenu);
        MenuItem srcCode = new MenuItem("Source Code");
        if (tf.getTextPanel().getNumLines() < 1)
            srcCode.disable();
        viewMenu.add(new MenuItem("Source Code"));
        // viewMenu.add(new MenuItem("Commentary Panel"));

        Menu helpMenu = new Menu("About");
        mb.setHelpMenu(helpMenu);
        helpMenu.add(new MenuItem("Credits"));
        helpMenu.add(new MenuItem("Copyrights"));

        return mb;
    } // createMenuBar()

    /**
     * Returns the preferred size of the frame. By default, it is set to
     * 850x700. It can be modified based on the specific application.
     * 
     * @return the dimension of the frame
     */
    public Dimension preferredSize() {

        return frameSize;
    }

    /**
     * Sets the size of the frame.
     * 
     * @param size
     *            The desired sized of the frame.
     */
    public void setDimension(Dimension size) {

        this.frameSize = size;
    }

    /**
     * Event handler of the frame. The main purpose of this method is to perform
     * the cleanup statements upon receival of the WINDOW_DESTROY event message.
     */
    public boolean handleEvent(Event event) {

        if (cp != null)
            cp.refreshButtons();
        if (event.id == Event.WINDOW_DESTROY) {
            if (alg != null) {
                alg.dpAfter = null;
                if (alg.isAlive())
                    alg.stop();
            }
            parentApp.start_button.enable();
            tf.dispose();
            dispose();
        }
        return super.handleEvent(event);
    }

    /**
     * Action handler for the buttons and choice buttons in the control panel.
     * 
     * @param e
     *            Event invoked
     * @param arg
     *            Object that invokes the event
     */
    public boolean action(Event e, Object arg) {

        Object target = e.target;

        if (target == quitItem) {
            getApplet().start_button.enable();
            if (getAlg() != null && getAlg().isAlive())
                getAlg().stop();
            getTextFrame().dispose();
            dispose();
        } else if (target instanceof CheckboxMenuItem) {
            Menu parent = (Menu) ((MenuItem) target).getParent();
            if (parent == dataMenu) {
                for (int i = 0; i < dataChoice.length; i++) {
                    if (target == dataChoice[i]) {
                        dataSelected = i;
                        alg.generateData();
                        dataChoice[i].setState(true);
                    } else
                        dataChoice[i].setState(false);
                }
            } else if (parent == delayMenu) {
                for (int i = 0; i < delayChoice.length; i++) {
                    if (target == delayChoice[i]) {
                        setDelay((i + 1) * 200);
                        getAlg().setDelay(getDelay());
                        setText(0, "Animation delay now set to " + getDelay() + " msec...");
                        delayChoice[i].setState(true);
                    } else
                        delayChoice[i].setState(false);
                }
            } else { // parent is Animation.. -> enable/disable anim
                if (target == enableAnim) {
                    disableAnim.setState(!enableAnim.getState());
                    noAnim = false;
                    dpAfter.setNoAnim(noAnim);
                } else if (target == disableAnim) {
                    enableAnim.setState(!disableAnim.getState());
                    noAnim = true;
                    dpAfter.setNoAnim(noAnim);
                }
            }
        } else {
            if (target instanceof MenuItem) {
                String text = ((MenuItem) target).getLabel();
                if (text.trim().equals("Source Code")) {
                    tf.show();
                    tf.toFront();
                } else if (text.trim().equals("Credits")) {
                    setText(0, "Author: Woi L Ang   Supervised by: John Morris");
                } else if (text.trim().equals("Copyrights")) {
                    setText(0,
                            "Copyright (c) 1998 The Department of Electrical and Electronic Engineering. University of Western Australia");
                }
            }
        }
        return false;
    } // action()

    /**
     * Sets the text string to be displayed on a specific text field on the
     * commentary panel return from <code>getComPanel</code>.
     * 
     * @see AlgAnimFrame#getComPanel
     * @param n
     *            The text field to display the string. First is <b>0</b>.
     * @param s
     *            The string to be displayed.
     */
    public void setText(int n, String s) {

        cpanel.setText(n, s);
    }

    /**
     * Highlights the specified line of the source code on the text panel. If
     * the line is beyond the scroll pane, it will be scrolled to the center of
     * the window.
     * 
     * @param n
     *            The line to be highlighted.
     */
    public void Highlight(int n) {

        if (tf.getTextPanel().getNumLines() < 1)
            return;
        if (!((Component) tf).isShowing())
            return;
        tf.getTextPanel().Highlight(n);
        int numLineVisible = tf.getTextPanel().size().height / tf.getTextPanel().getLineSpace();

        if ((n < (tf.getTextPanel().getStart() + 2)) || (n > (tf.getTextPanel().getStart() + numLineVisible - 2))) {
            int max = tf.getVertScrollbar().getMaximum();
            int min = tf.getVertScrollbar().getMinimum();
            int startLine = n - numLineVisible / 2;
            if (startLine > 0) {
                tf.getTextPanel().setStart(startLine);
                tf.getVertScrollbar().setValue(startLine * (max - min) / tf.getTextPanel().getNumLines());
            } else {
                tf.getTextPanel().setStart(0);
                tf.getVertScrollbar().setValue(0);
            }
        }
        try {
            Thread.sleep(delay / 4);
        } catch (InterruptedException e) {
        }
    }

    /**
     * Restore the drawing panel at the end of the animation or during
     * initialization.
     */
    public void restoreDrawingPanel() {

        alg.restoreDrawingPanel();
    }

    /**
     * Start the animation algorithm if the <b>run</b> or <b>step</b> button
     * is pressed.
     */
    public void startAlg() {

        if (!stepWait) {
            ((ImageButton) runItem).setDisable();
            dataMenu.disable();
        }
        ((ImageButton) stopItem).setEnable();
        ((ImageButton) stepItem).setEnable();

        if (alg.isAlive() && !stepWait) {
            alg.stop();
            alg = new AlgThread(this);
            alg.start();
        } else if (alg.isAlive() && stepWait) {
            // alg.resume();
            step = !step;
            stepWait = false;
        } else { // alg.isAlive() == false
            alg = new AlgThread(this);
            alg.start();
        }
    }

    /**
     * This method is invoked at the end of the animation or when the <b>stop</b>
     * button is pressed. It restores the buttons status on the control panel.
     */
    public void finishAlg() {

        ((ImageButton) stopItem).setDisable();
        ((ImageButton) runItem).setEnable();
        ((ImageButton) skipItem).setEnable();
        dataMenu.enable();
        ((ImageButton) stepItem).setEnable();
        step = false;
        alg.restoreDrawingPanel();
    }

    /**
     * This method is called when the <b>step</b> execution mode is used. It is
     * normally added to the line where the execution will wait for the <b>step</b>
     * button to be pressed.
     */
    public void waitStep() {

        if (step) {
            setText(0, "Click NEXT STEP...");
            ((ImageButton) stepItem).setEnable();
            repaint();
            step = false;
            stepWait = true;
            while (!step) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
            if (!stepWait)
                step = false;
        }
    }

    /**
     * This method is called when the <b>skip</b> execution mode is used. It is
     * normally added to the line where the execution will wait after the
     * <b>skip</b> button to be pressed.
     */
    public void waitSkip() {

        alg.waitSkip();
    }

    /**
     * Sets the attribute which indicate if the <b>skip</b> execution mode is
     * current.
     */
    public void setSkip(boolean skip) {

        dpAfter.setSkip(skip);
    }

    /**
     * Sets the attribute which indicate if the <b>step</b> execution mode is
     * current.
     */
    public void setStep(boolean step) {

        // setText(2, "Stepping through the next line(s)...");
        this.step = step;
        ((ImageButton) stepItem).setDisable();
        ((ImageButton) runItem).setEnable();
        dataMenu.enable();
        if (step) {
            ((ImageButton) stepItem).setDisable();
            stepWait = true;
        } else
            stepWait = false;
    }

    /**
     * Returns the reference to the AlgThread which contains the details and
     * execution sequences of the algorithm.
     * 
     * @see AlgThread
     */
    public AlgThread getAlg() {

        return alg;
    }

    /**
     * Set the delay for highlighting text.
     */
    public void setDelay(int delay) {

        this.delay = delay;
    }

    /**
     * Get the delay for highlighting text.
     */
    public int getDelay() {

        return delay;
    }

    /**
     * Get the applet which contains a button to start up this window.
     * 
     * @return Returns the applet which contains the button to start up this
     *         window.
     */
    public AlgAnimApp getApplet() {

        return parentApp;
    }

    /**
     * Returns an instance of the drawing panel which is cast to its super class
     * <code>Panel</code>.
     */
    public DrawingPanel getDrawingPanel() {

        return dpAfter;
    }

    public DrawingPanel getBeforeDp() {

        return dpBefore;
    }

    /**
     * Sets the drawing panel which is cast to its super class
     * <code>Panel</code>. This instance is used to set the GridBagConstraint
     * of the layout manager.
     * 
     * @see DrawingPanel
     */
    public void setDrawingPanel(DrawingPanel panel) {

        this.dpAfter = panel;
    }

    /**
     * Returns an instance of the <code>TextFrame</code> used to set the
     * layout constraints and highlight certain lines of the source code.
     * 
     * @see TextFrame
     */
    public TextFrame getTextFrame() {

        return tf;
    }

    /**
     * Get the commentary panel that displays messages of any type.
     * 
     * @return Commentary panel, in which each text field within can be set to
     *         display text string from this class.
     * @see ComPanel
     */
    public ComPanel getComPanel() {

        return cpanel;
    }

    /**
     * Get the index of selected choice from the 'Select' pull menu.
     * 
     * @return The choice of the data selected.
     */
    public int getDataChoice() {

        return dataSelected;
    }

    /**
     * Get the skip button from the control panel.
     * 
     * @return The skip button.
     */
    public Button getSkipItem() {

        return skipItem;
    }

    /**
     * Get the run button from the control panel.
     * 
     * @return The run button.
     */
    public Button getRunItem() {

        return runItem;
    }

    /**
     * Get the stop button from the control panel.
     * 
     * @return The stop button.
     */
    public Button getStopItem() {

        return stopItem;
    }

    /**
     * Obtain the status of the preferred animation style.
     * 
     * @return True is the animation is kept to a minimum for the animated
     *         algorithm; false otherwise.
     */
    public boolean isNoAnim() {

        return noAnim;
    }

    /**
     * Get the menu item which specify if the animation is enabled.
     * 
     * @return The checkbox menu item to enable the Animation of the alg.
     */
    public CheckboxMenuItem getEnableAnim() {

        return enableAnim;
    }

    /**
     * Get the menu item which specify if the animation is disabled.
     * 
     * @return The checkbox menu item to disable the Animation of the alg.
     */
    public CheckboxMenuItem getDisableAnim() {

        return disableAnim;
    }
} // class AlgAnimApp
