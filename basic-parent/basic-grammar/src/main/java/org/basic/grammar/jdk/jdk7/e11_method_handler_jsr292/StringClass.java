package org.basic.grammar.jdk.jdk7.e11_method_handler_jsr292;

import java.util.Arrays;
import java.util.Formatter;

/**
 * <pre>
 *
 * </pre>
 */
public class StringClass {
    byte[] b1;
    private String name;

    public StringClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    StringClass(byte[] b) {
        b1 = b;
    }

    StringClass substring(int s, int e) {
        byte[] b2 = Arrays.copyOfRange(b1, s, e);
        return new StringClass(b2);
    }

    public static String format(String format, Object... args) {
        return new Formatter().format(format, args).toString();
    }

    private void checkBounds(byte[] bytes, int offset, int length) {
        if (length < 0)
            throw new StringIndexOutOfBoundsException(length);
        if (offset < 0)
            throw new StringIndexOutOfBoundsException(offset);
        if (offset > bytes.length - length)
            throw new StringIndexOutOfBoundsException(offset + length);
    }

}
