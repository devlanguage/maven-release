package org.basic.gui.swing.component;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <pre>
 *
 * </pre>
 */

public class LabelledComponent extends JPanel {

    public LabelledComponent(String s, JComponent c) {
        this(s, c, 1);
    }

    public LabelledComponent(String s, JComponent c, int align) {
        this(new JLabel(s, 4), c, ((JLabel) (null)), align);
    }

    public LabelledComponent(String s, JComponent c, String s2) {
        this(s, c, s2, 1);
    }

    public LabelledComponent(String s, JComponent c, String s2, int align) {
        this(new JLabel(s, 4), c, new JLabel(s2, 4), align);
    }

    public LabelledComponent(JLabel l, JComponent c, JLabel l2, int align) {
        this.align = 1;
        userData = null;
        label = l;
        label2 = l2;
        component = c;
        this.align = align;
        initPanel();
    }

    public JPanel getLabelledComponent() {
        return panel;
    }

    public JLabel getLabel() {
        return label;
    }

    public JComponent getComponent() {
        return component;
    }

    public int getLabelWidth() {
        return label.getPreferredSize().width;
    }

    public int getComponentWidth() {
        return component.getPreferredSize().width;
    }

    public void setClientData(Object userData) {
        this.userData = userData;
    }

    public Object getClientData() {
        return userData;
    }

    public void setLabelText(String s) {
        if (label == null || s == null) {
            return;
        } else {
            label.setText(s);
            return;
        }
    }

    public void setLabelFont(Font f) {
        if (label == null || f == null) {
            return;
        } else {
            label.setFont(f);
            return;
        }
    }

    public void setEnabled(boolean b) {
        if (label != null)
            label.setEnabled(b);
        if (label2 != null)
            label2.setEnabled(b);
        if (component != null)
            enableComponents(component, b);
    }

    private void enableComponents(JComponent comp, boolean b) {
        for (int i = 0; i < comp.getComponentCount(); i++)
            comp.getComponent(i).setEnabled(b);

    }

    private void initPanel() {
        GridBagLayout gridbag = new GridBagLayout();
        setLayout(gridbag);
        GridBagConstraints gbc = new GridBagConstraints();
        label.setHorizontalAlignment(4);
        gbc.gridx = 0;
        gbc.gridy = 0;
        int labelAnchor;
        switch (align) {
            case 0: // '\0'
                labelAnchor = 12;
                break;

            case 1: // '\001'
                labelAnchor = 10;
                break;

            case 2: // '\002'
                labelAnchor = 14;
                break;

            default:
                labelAnchor = 10;
                break;
        }
        gbc.anchor = labelAnchor;
        gridbag.setConstraints(label, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = 10;
        gbc.insets = new Insets(0, 5, 0, 0);
        gbc.weightx = 1.0D;
        gbc.fill = 2;
        gridbag.setConstraints(component, gbc);
        add(label);
        add(component);
        if (label2 != null) {
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 5, 0, 0);
            gbc.anchor = labelAnchor;
            gbc.weightx = 0.0D;
            gridbag.setConstraints(label2, gbc);
            add(label2);
        }
    }

    public static final int NORTH = 0;
    public static final int CENTER = 1;
    public static final int SOUTH = 2;
    private JLabel label;
    private JLabel label2;
    private JComponent component;
    private JPanel panel;
    private int align;
    private Object userData;
}
