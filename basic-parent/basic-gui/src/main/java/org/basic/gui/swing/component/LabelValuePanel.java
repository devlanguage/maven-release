package org.basic.gui.swing.component;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <pre>
 *
 * </pre>
 */
public class LabelValuePanel extends JPanel {

    public LabelValuePanel(LabelledComponent items[]) {
        vgap = 5;
        hgap = 5;
        this.items = items;
        init();
    }

    public LabelValuePanel(LabelledComponent items[], int hgap, int vgap) {
        this.vgap = 5;
        this.hgap = 5;
        this.items = items;
        this.hgap = hgap;
        this.vgap = vgap;
        init();
    }

    public LabelledComponent[] getLabelledComponents() {
        return items;
    }

    private void init() {
        int numItems = items.length;
        int longest = 0;
        setBorder(BorderFactory.createEmptyBorder(vgap, hgap, vgap, hgap));
        GridBagLayout gridbag = new GridBagLayout();
        setLayout(gridbag);
        GridBagConstraints c = new GridBagConstraints();
        for (int i = 0; i < numItems; i++) {
            if (items[i].getLabelWidth() > longest)
                longest = items[i].getLabelWidth();
            c.gridx = 0;
            c.gridy = i;
            c.ipadx = hgap;
            c.ipady = vgap;
            c.anchor = 17;
            c.weightx = 1.0D;
            c.fill = 2;
            gridbag.setConstraints(items[i], c);
            add(items[i]);
        }

        for (int i = 0; i < items.length; i++) {
            JLabel l = items[i].getLabel();
            Dimension dim = l.getPreferredSize();
            dim.width = longest;
            l.setPreferredSize(dim);
        }

    }

    private int vgap;
    private int hgap;
    private JPanel panel;
    private LabelledComponent items[];
}