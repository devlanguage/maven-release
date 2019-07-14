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
public class ByteField extends JPanel {

    private static final long serialVersionUID = 3960198647478107245L;

    public ByteField(long min, long max, String text) {
        this(min, max, text, 0);
    }

    public ByteField(long min, long max, int columns) {
        this(min, max, null, columns);
    }

    public ByteField(long min, long max, String text, int columns) {
        initGui(min, max, text, columns);
        setUnit(1);
    }

    public void addActionListener(ActionListener l) {
        lf.addActionListener(l);
    }

    public void setText(String s) {
        lf.setText(s);
    }

    public String getText() {
        return lf.getText();
    }

    public void setEnabled(boolean b) {
        lf.setEnabled(b);
        unitCB.setEnabled(b);
    }

    public void setUnit(int unit) {
        if (unit < 0 || unit > 2) {
            return;
        } else {
            unitCB.setSelectedIndex(unit);
            return;
        }
    }

    public int getUnit() {
        int selIndex = unitCB.getSelectedIndex();
        return selIndex;
    }

    public void setSizeString(String strVal) {
        String tmp = strVal.trim();
        SizeString ss;
        try {
            ss = new SizeString(tmp);
        } catch (Exception e) {
            return;
        }
        char c = tmp.charAt(tmp.length() - 1);
        long val;
        int unit;
        if (Character.isLetter(c)) {
            switch (c) {
                case 77: // 'M'
                case 109: // 'm'
                    val = ss.getMBytes();
                    unit = 2;
                    break;

                case 75: // 'K'
                case 107: // 'k'
                    val = ss.getKBytes();
                    unit = 1;
                    break;

                case 66: // 'B'
                case 98: // 'b'
                    val = ss.getBytes();
                    unit = 0;
                    break;

                default:
                    val = 0L;
                    unit = 0;
                    break;
            }
        } else {
            val = ss.getBytes();
            unit = 0;
        }
        setText(Long.toString(val));
        setUnit(unit);
    }

    public String getSizeString() {
        String strValue = getText();
        int unit = getUnit();
        String unitStr;
        switch (unit) {
            case 0: // '\0'
                unitStr = "b";
                break;

            case 1: // '\001'
                unitStr = "k";
                break;

            case 2: // '\002'
                unitStr = "m";
                break;

            default:
                unitStr = "b";
                break;
        }
        return (new StringBuilder()).append(strValue).append(unitStr).toString();
    }

    public long getValue() {
        String s = lf.getText();
        long tmpLong;
        try {
            tmpLong = Long.parseLong(s);
        } catch (Exception e) {
            return -1L;
        }
        int selIndex = unitCB.getSelectedIndex();
        switch (selIndex) {
            case 2: // '\002'
                return tmpLong * 1048576L;

            case 1: // '\001'
                return tmpLong * 1024L;

            case 0: // '\0'
                return tmpLong;
        }
        return -1L;
    }

    private void initGui(long min, long max, String text, int collumns) {
        lf = new LongField(min, max, text, collumns);
        String units[] = new String[3];
        MenuResources _tmp = acr;
        units[2] = acr.getString("A1219");
        MenuResources _tmp1 = acr;
        units[1] = acr.getString("A1220");
        MenuResources _tmp2 = acr;
        units[0] = acr.getString("A1221");
        unitCB = new JComboBox(units);
        setLayout(new BorderLayout());
        add(lf, "Center");
        add(unitCB, "East");
    }

    public static final int BYTES = 0;
    public static final int KILOBYTES = 1;
    public static final int MEGABYTES = 2;
    private static MenuResources acr = MenuResources.getResources();
    private LongField lf;
    private JComboBox unitCB;

}
