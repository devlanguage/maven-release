package org.basic.gui.swing.common;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;

import org.basic.pattern.behavioral.ChainOfResponsibility.test1.Manager;

import com.jidesoft.docking.DockableFrame;

/**
 * This class is intended lock a root pane while a server request is being executed. Locking is the process of blocking
 * user input while the action is occuring.
 * <p>
 *
 * Generics have been applied to ISMSwingWorker.
 *
 * P = paramters, this is the class that represents the paramaters you wish to send the worker R = results, this is the
 * type of object you want invokeBusyCommandHandler to return and swingWorkerFinished to deliver on job completion.
 *
 * ISMSwingWorker<String, Object[]> worker = new ISMSwingWorker<String, Object[]>()...
 *
 * This decarles a worker that takes a String for paramaters and returns an object[] from invokeBusyCommandHandler
 *
 * Work done on the worker thread should be places in invokeBusyCommandHandler Work to be done ON the Event Dispatch
 * Thread shoudl be placed in swingWorkerFinished
 *
 * Regardless of weather you impliment swingWorkerFinished or not the screen will always be unlocked.
 *
 * If you don't care about the paraamters then use DefaultISMSwingWorker
 *
 * @author Collin Fagan
 * @version 1.0
 */
public abstract class PacketSwingWorker<P, R> {
    // private R result;
    private ConcurrentHashMap<P, R> resultMap = new ConcurrentHashMap<P, R>();
    private JRootPane rootPane;
    private Component focusOwner;
    private int _queueCount = 0; // Used to keep track of multiple requests so not to unlock too early

    /**
     * @param theRootPane
     *            JRootPane - the root pane to be locked
     */
    public PacketSwingWorker(JRootPane theRootPane) {
        rootPane = theRootPane;
    }

    /**
     * @param rootPaneParent
     *            RootPaneContainer - a top level container ot be locked
     */
    public PacketSwingWorker(RootPaneContainer rootPaneParent) {
        this(rootPaneParent.getRootPane());
    }

    /**
     * @return JRootPane - the root pane that will currently be locked
     */
    public JRootPane getRootPane() {
        return rootPane;
    }

    /**
     * @param jrp
     *            JRootPane - the root pane you wish to be locked
     */
    public void setRootPane(JRootPane jrp) {
        rootPane = jrp;
    }

    public static final ExecutorService threadPool = Executors.newCachedThreadPool();

    /**
     *
     * @param theRootPane
     *            JRootPane
     * @param obj
     *            P
     */
    public void start(final P obj)

    {
        // lock the screen
        setLocked(true);

        Runnable runWorker = new Runnable() {
            public void run() {
                try {

                    R result = invokeBusyCommandHandler(obj);
                    if (result != null)
                        resultMap.put(obj, result);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // ErrorLogger.logError(ErrorLogger.CRITICAL, getClass().getName() + "construct",
                    // ex.getMessage());
                }

                SwingUtilities.invokeLater(new Runnable() {
                    public void run()

                    {
                        // Unlock the screen
                        R result = resultMap.remove(obj);
                        swingWorkerFinished(obj, result);

                        // If this is moved before the swingWorkerFinished
                        // is called then packet properties will never unlock
                        setLocked(false);
                    }

                });
            }
        };
        threadPool.execute(runWorker);
    }

    /*
     * public void start(final P obj)
     * 
     * { //lock the screen setLocked(true);
     * 
     * Runnable runWorker = new Runnable() { public void run() { R result = null; try { result =
     * invokeBusyCommandHandler(obj); } catch (Exception ex) { ex.printStackTrace(); //
     * ErrorLogger.logError(ErrorLogger.CRITICAL, getClass().getName() + "construct", // ex.getMessage()); }
     * 
     * swingWorkerFinished(obj, result);
     * 
     * // If this is moved before the swingWorkerFinished // is called then packet properties will never unlock
     * setLocked(false);
     * 
     * } }; SwingUtilities.invokeLater(runWorker); }
     */

    /**
     *
     * @param theRootPane
     *            JRootPane
     * @param obj
     *            P
     */
    public void start(final P obj, final boolean unlocked)

    {
        setLocked(true, unlocked);

        Runnable runWorker = new Runnable() {
            public void run() {
                try {
                    R result = invokeBusyCommandHandler(obj);
                    if (result != null)
                        resultMap.put(obj, result);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // ErrorLogger.logError(ErrorLogger.CRITICAL, getClass().getName() + "construct",
                    // ex.getMessage());
                }

                SwingUtilities.invokeLater(new Runnable() {
                    public void run()

                    {
                        setLocked(false, unlocked);
                        R result = resultMap.remove(obj);
                        swingWorkerFinished(obj, result);
                    }

                });
            }
        };
        threadPool.execute(runWorker);
    }

    /*
     * public void start(final P obj, final boolean unlocked)
     * 
     * { setLocked(true, unlocked);
     * 
     * Runnable runWorker = new Runnable() { public void run() { R result = null; try { result =
     * invokeBusyCommandHandler(obj); } catch (Exception ex) { ex.printStackTrace(); //
     * ErrorLogger.logError(ErrorLogger.CRITICAL, getClass().getName() + "construct", // ex.getMessage()); }
     * setLocked(false, unlocked); swingWorkerFinished(obj, result);
     * 
     * } }; SwingUtilities.invokeLater(runWorker); }
     */

    /**
     *
     * @param obj
     *            P
     * @return R
     */
    public abstract R invokeBusyCommandHandler(P obj);

    /**
     *
     * @param obj
     *            R
     */
    public void swingWorkerFinished(P paramObj, R resultObj) {
    }

    /**
     * The mouse adapter object, which is used to eat (consume) mouse events.
     */
    private static final MouseAdapter mouseAdapter = new MouseAdapter() {
    };

    /**
     * blocks mouse motion events
     */
    private static final MouseMotionAdapter mouseMotionAdapter = new MouseMotionAdapter() {
    };

    /**
     * blocks key events
     */
    private static final KeyListener keyListener = new KeyListener() {
        public void keyReleased(KeyEvent e) {
            e.consume();
        }

        public void keyPressed(KeyEvent e) {
            e.consume();
        }

        public void keyTyped(KeyEvent e) {
            e.consume();
        }
    };

    /**
     * Locks or unlocks the target GUI
     * 
     * @param lock
     *            boolean
     */
    protected void setLocked(boolean lock) {
        setLocked(lock, false);
    }

    /**
     * Locks or unlocks the target GUI
     * 
     * @param lock
     *            boolean
     */
    protected synchronized void setLocked(boolean lock, boolean unlocked) {
        // deal with glass pane
        JComponent glassPane = (JComponent) rootPane.getGlassPane();
        if (lock) {
            _queueCount++;
            focusOwner = lockGlassPane(glassPane, unlocked);
        } else {
            _queueCount--;
            if (_queueCount == 0) {
                unlockGlassPane(glassPane, focusOwner, unlocked);
            }
        }
    }

    public void updateQueueCount(int count) {
        _queueCount = _queueCount + count;
    }

    /**
     * static method to lock a glass pane in the standard way.
     *
     * returns the focus owner so that it can be restored later.
     *
     * @param glassPane
     *            JComponent
     * @return JComponent - the last focus owner before the glass pane takes over
     */
    public static Component lockGlassPane(JComponent glassPane) {
        return lockGlassPane(glassPane, false);
    }

    /**
     * static method to lock a glass pane in the standard way.
     *
     * returns the focus owner so that it can be restored later.
     *
     * @param glassPane
     *            JComponent
     * @return JComponent - the last focus owner before the glass pane takes over
     */
    public static Component lockGlassPane(JComponent glassPane, boolean unlocked) {
        Component currentfocusOwner;

        if (!unlocked) {
            glassPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            glassPane.addKeyListener(keyListener);
            glassPane.addMouseListener(mouseAdapter);
            glassPane.addMouseMotionListener(mouseMotionAdapter);
        }

        currentfocusOwner = findFocusOwner(SwingUtilities.getWindowAncestor(glassPane));
        currentfocusOwner.setFocusTraversalKeysEnabled(false);

        glassPane.setVisible(true);
        glassPane.setFocusCycleRoot(true);
        glassPane.setFocusTraversalKeysEnabled(false);
        glassPane.requestFocusInWindow();

        return currentfocusOwner;
    }

    /**
     * static method to unlock the glasspane in a standard way
     *
     * @param glassPane
     *            JComponent - the glass pane
     * @param focusOwner
     *            JComponent - the component to restore focus to
     */
    public static void unlockGlassPane(JComponent glassPane, Component focusOwner) {
        unlockGlassPane(glassPane, focusOwner, false);
    }

    /**
     * static method to unlock the glasspane in a standard way
     *
     * @param glassPane
     *            JComponent - the glass pane
     * @param focusOwner
     *            JComponent - the component to restore focus to
     */
    public static void unlockGlassPane(JComponent glassPane, Component focusOwner, boolean unlocked) {
        if (!unlocked) {
            glassPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            glassPane.removeKeyListener(keyListener);
            glassPane.removeMouseListener(mouseAdapter);
            glassPane.removeMouseMotionListener(mouseMotionAdapter);
        }

        glassPane.setVisible(false);
        glassPane.setFocusCycleRoot(false);
        glassPane.setFocusTraversalKeysEnabled(true);

        focusOwner.setFocusTraversalKeysEnabled(true);
        focusOwner.requestFocusInWindow();
    }

    /**
     * finds the focus owner or chooses one if not found
     * 
     * @param window
     *            Window
     * @return Component
     */
    private static Component findFocusOwner(Window window) {
        // if (window == null) {
        // window = Manager.getTopFrame();
        // }

        Component focusOwner = window.getFocusOwner();

        if (focusOwner == null) {
            Component[] components = window.getComponents();

            focusOwner = components[0];
            focusOwner.requestFocusInWindow();
        }
        return focusOwner;
    }

    /**
     * A simple wraper class that should be used for BACKWARD COMAPTABILITY ONLY It is recommended that you make your
     * own specialised paramter grouping classes if nessesary
     */
    public static class ParamGroup {
        public String commandName;
        public Object[] params;

        public ParamGroup(String command, Object... allParams) {
            commandName = command;
            params = allParams;
        }

        public ParamGroup(String command) {
            this(command, null);
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
        return null;
    }

}
