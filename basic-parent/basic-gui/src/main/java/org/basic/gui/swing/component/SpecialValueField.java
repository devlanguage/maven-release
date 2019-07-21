package org.basic.gui.swing.component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * <pre>
 *
 * </pre>
 */
public class SpecialValueField extends JPanel implements ActionListener {

    public SpecialValueField(JComponent comp, String specialValueStr) {
        specialValueSet = true;
        this.comp = comp;
        this.specialValueStr = specialValueStr;
        initGui();
        setSpecialValueSet(true);
    }

    public boolean isSpecialValueSet() {
        return specialValueSet;
    }

    public void setSpecialValueSet(boolean b) {
        if (b) {
            specialValueRB.setSelected(true);
            doSpecialValueRBSelected();
        } else {
            normalValueRB.setSelected(true);
            doNormalValueRBSelected();
        }
    }

    public JComponent getComponent() {
        return comp;
    }

    public void setEnabled(boolean b) {
        if (comp != null)
            comp.setEnabled(b);
        if (specialValueLabel != null)
            specialValueLabel.setEnabled(b);
        if (specialValueRB != null)
            specialValueRB.setEnabled(b);
        if (normalValueRB != null)
            normalValueRB.setEnabled(b);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == specialValueRB)
            doSpecialValueRBSelected();
        else if (source == normalValueRB)
            doNormalValueRBSelected();
    }

    private void doSpecialValueRBSelected() {
        specialValueLabel.setEnabled(true);
        comp.setEnabled(false);
        specialValueSet = true;
    }

    private void doNormalValueRBSelected() {
        specialValueLabel.setEnabled(false);
        comp.setEnabled(true);
        specialValueSet = false;
    }

    private void initGui() {
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(gbl);
        gbc.anchor = 10;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        specialValueRB = new JRadioButton();
        specialValueRB.addActionListener(this);
        gbl.setConstraints(specialValueRB, gbc);
        add(specialValueRB);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = 1;
        specialValueLabel = new JLabel(specialValueStr, 2);
        gbl.setConstraints(specialValueLabel, gbc);
        add(specialValueLabel);
        gbc.fill = 0;
        gbc.gridx = 0;
        gbc.gridy = 1;
        normalValueRB = new JRadioButton();
        normalValueRB.addActionListener(this);
        gbl.setConstraints(normalValueRB, gbc);
        add(normalValueRB);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbl.setConstraints(comp, gbc);
        add(comp);
        ButtonGroup bg = new ButtonGroup();
        bg.add(specialValueRB);
        bg.add(normalValueRB);
    }

    private JComponent comp;
    private JLabel specialValueLabel;
    private JRadioButton specialValueRB;
    private JRadioButton normalValueRB;
    private String specialValueStr;
    private boolean specialValueSet;
}
