package org.basic.gui.swing.component;

import javax.swing.JTextField;

/**
 * <pre>
 *
 * </pre>
 */

public class LongField extends JTextField {

    public LongField(long min, long max, String text) {
        this(min, max, text, 0);
    }

    public LongField(long min, long max, int columns) {
        this(min, max, null, columns);
    }

    public LongField(long min, long max, String text, int columns) {
        super(new LongDocument(min, max), text, columns);
    }
}
