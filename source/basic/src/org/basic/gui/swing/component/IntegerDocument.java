package org.basic.gui.swing.component;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * <pre>
 *
 * </pre>
 */
public class IntegerDocument extends PlainDocument {

    private static final long serialVersionUID = 2044820716776239633L;

    public IntegerDocument(long min, long max) {
        this.min = min;
        this.max = max;
    }

    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        for (int i = 0; i < str.length(); i++) {
            int keyCode = str.charAt(i);
            if ((keyCode < 48 || keyCode > 57) && (min >= 0L || offset != 0 || keyCode != 45)) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }
        }

        String sval = getText(0, getLength());
        sval = (new StringBuilder()).append(sval.substring(0, offset)).append(str).append(sval.substring(offset, sval.length())).toString();
        if (sval.length() > 18 || sval.startsWith("--")) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        if (!sval.equals("-") && sval.length() > 0) {
            long ival = Long.valueOf(sval).longValue();
            if (ival < min || ival > max) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }
        }
        super.insertString(offset, str, a);
    }

    long min;
    long max;
}
