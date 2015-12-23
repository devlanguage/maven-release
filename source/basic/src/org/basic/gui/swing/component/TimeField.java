package org.basic.gui.swing.component;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.basic.gui.swing.common.MenuResources;

/**
 * <pre>
 *
 * </pre>
 */
public class TimeField extends JPanel {

    public TimeField(long max, String text) {
        this(max, text, 0);
    }

    public TimeField(long max, int columns) {
        this(max, null, columns);
    }

    public TimeField(long max, String text, int columns) {
        this(max, text, columns, false);
    }

    public TimeField(long max, String text, int columns, boolean showMillis) {
        msecPos = 0;
        secPos = 1;
        minPos = 2;
        hrPos = 3;
        dayPos = 4;
        this.showMillis = false;
        this.showMillis = showMillis;
        initGui(max, text, columns);
        setUnit(1);
    }

    public void addActionListener(ActionListener l) {
        intF.addActionListener(l);
    }

    public void setText(String s) {
        intF.setText(s);
    }

    public String getText() {
        return intF.getText();
    }

    public void setEnabled(boolean b) {
        intF.setEnabled(b);
        unitCB.setEnabled(b);
    }

    public void setUnit(int unit) {
        int index = secPos;
        switch (unit) {
            case 0: // '\0'
                index = msecPos;
                break;

            case 1: // '\001'
                index = secPos;
                break;

            case 2: // '\002'
                index = minPos;
                break;

            case 3: // '\003'
                index = hrPos;
                break;

            case 4: // '\004'
                index = dayPos;
                break;
        }
        if (index < 0 || index > dayPos) {
            return;
        } else {
            unitCB.setSelectedIndex(index);
            return;
        }
    }

    public long getValue() {
        String s = intF.getText();
        long tmpLong;
        try {
            tmpLong = Long.parseLong(s);
        } catch (Exception e) {
            return -1L;
        }
        int selIndex = unitCB.getSelectedIndex();
        if (showMillis) {
            if (selIndex == msecPos)
                return tmpLong;
            if (selIndex == secPos)
                return tmpLong * 1000L;
            if (selIndex == minPos)
                return tmpLong * 1000L * 60L;
            if (selIndex == hrPos)
                return tmpLong * 1000L * 60L * 60L;
            if (selIndex == dayPos)
                return tmpLong * 1000L * 60L * 60L * 24L;
        } else {
            if (selIndex == secPos)
                return tmpLong * 1000L;
            if (selIndex == minPos)
                return tmpLong * 1000L * 60L;
            if (selIndex == hrPos)
                return tmpLong * 1000L * 60L * 60L;
            if (selIndex == dayPos)
                return tmpLong * 1000L * 60L * 60L * 24L;
        }
        return -1L;
    }

    private void initGui(long max, String text, int collumns) {
        intF = new IntegerField(0L, max, text, collumns);
        String units[];
        if (showMillis) {
            msecPos = 0;
            secPos = 1;
            minPos = 2;
            hrPos = 3;
            dayPos = 4;
            units = new String[5];
            MenuResources _tmp = acr;
            units[msecPos] = acr.getString("A1226");
        } else {
            secPos = 0;
            minPos = 1;
            hrPos = 2;
            dayPos = 3;
            units = new String[4];
        }
        MenuResources _tmp1 = acr;
        units[secPos] = acr.getString("A1227");
        MenuResources _tmp2 = acr;
        units[minPos] = acr.getString("A1228");
        MenuResources _tmp3 = acr;
        units[hrPos] = acr.getString("A1229");
        MenuResources _tmp4 = acr;
        units[dayPos] = acr.getString("A1230");
        unitCB = new JComboBox(units);
        setLayout(new BorderLayout());
        add(intF, "Center");
        add(unitCB, "East");
    }

    public static final int MILLISECONDS = 0;
    public static final int SECONDS = 1;
    public static final int MINUTES = 2;
    public static final int HOURS = 3;
    public static final int DAYS = 4;
    private int msecPos;
    private int secPos;
    private int minPos;
    private int hrPos;
    private int dayPos;
    private boolean showMillis;
    private static MenuResources acr = MenuResources.getResources();
    private IntegerField intF;
    private JComboBox unitCB;

}
