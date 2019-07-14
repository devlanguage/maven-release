package org.basic.gui.swing.component;

import javax.swing.JTextField;

/**
 * <pre>
 *
 * </pre>
 */

public class IntegerField extends JTextField {

    private static final long serialVersionUID = -2850065538501615301L;

    public IntegerField(long min, long max, String text) {
        this(min, max, text, 0);
    }

    public IntegerField(long min, long max, int columns) {
        this(min, max, null, columns);
    }

    public IntegerField(long min, long max, String text, int columns) {
        super(new IntegerDocument(min, max), text, columns);
    }
}
