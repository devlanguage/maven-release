package org.basic.gui.swing.common;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * <pre>
 *
 * </pre>
 */
public class JAnimatedGlassPane extends JPanel {
    private JProgressBar jpbIndeterminate = new JProgressBar();
    private JLabel jlMessage;
    private static Color clrOverlay = new Color(60, 60, 60);
    public static final String DEFAULT_MESSAGE = "Loading Please Wait...";
    private boolean _enabled = true;

    /**
     * glass pane with default message
     */
    public JAnimatedGlassPane() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        jpbIndeterminate.setMaximumSize(jpbIndeterminate.getPreferredSize());
        jpbIndeterminate.setAlignmentX(Component.CENTER_ALIGNMENT);
        jpbIndeterminate.setIndeterminate(true);

        add(Box.createVerticalGlue());

        jlMessage = new JLabel(DEFAULT_MESSAGE);
        jlMessage.setForeground(Color.WHITE);
        jlMessage.setFont(jlMessage.getFont().deriveFont(Font.BOLD, 16f));
        jlMessage.setHorizontalAlignment(JLabel.CENTER);
        jlMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(jlMessage);
        add(Box.createVerticalStrut(10));
        add(jpbIndeterminate);
        add(Box.createVerticalGlue());
    }

    /**
     * @param message
     *            String - the desired message
     * @param icn
     *            Icon - the icon to display
     */
    public JAnimatedGlassPane(String message, Icon icn) {
        this();
        setIcon(icn);
        setMessage(message);
    }

    public void setAnimateEnabled(boolean enable) {
        _enabled = enable;
    }

    /**
     * overidden to paint a translucent background
     * 
     * @param g
     *            Graphics
     */
    public void paint(Graphics g) {
        if (_enabled) {
            jlMessage.setVisible(true);
            jpbIndeterminate.setVisible(true);
            Graphics2D g2d = ((Graphics2D) g);
            Composite compOld = g2d.getComposite();
            Color clrOld = g2d.getColor();

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
            g2d.setColor(clrOverlay);

            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setComposite(compOld);
            g2d.setColor(clrOld);
        } else {
            jlMessage.setVisible(false);
            jpbIndeterminate.setVisible(false);
        }

        super.paint(g);
    }

    /**
     * The message to display while waiting
     * 
     * @param message
     *            String
     */
    public void setMessage(String message) {
        jlMessage.setText(message);
    }

    /**
     * The icon to display while waiting
     * 
     * @param icn
     *            Icon
     */
    public void setIcon(Icon icn) {
        jlMessage.setIcon(icn);
    }
}
