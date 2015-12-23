package org.basic.common.bean;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * <pre>
 *
 * </pre>
 */
public class CommonResourceBundle extends ResourceBundle {

    public CommonResourceBundle(ResourceBundle rb) {
        this.rb = null;
        convertEOL = false;
        cache = null;
        this.rb = rb;
        String s = System.getProperty("line.separator");
        if (!s.equals(UnixEOL)) {
            convertEOL = true;
            cache = new HashMap();
            if (DEBUG)
                System.out.println((new StringBuilder()).append(getClass().getName()).append(": Will convert messages to use native EOL.")
                        .toString());
        }
    }

    public Object handleGetObject(String key) {
        if (convertEOL) {
            Object o = null;
            synchronized (cache) {
                o = cache.get(key);
                if (o == null) {
                    o = rb.getObject(key);
                    if (o instanceof String) {
                        o = unix2native((String) o);
                        cache.put(key, o);
                    }
                }
            }
            return o;
        } else {
            return rb.getObject(key);
        }
    }

    public String getString(String key, Object arg) throws MissingResourceException {
        if (arg instanceof Object[]) {
            return MessageFormat.format(getString(key), (Object[]) (Object[]) arg);
        } else {
            Object args[] = { arg };
            return MessageFormat.format(getString(key), args);
        }
    }

    public String getString(String key, Object arg1, Object arg2) throws MissingResourceException {
        Object args[] = { arg1, arg2 };
        return MessageFormat.format(getString(key), args);
    }

    public String getString(String key, Object args[]) throws MissingResourceException {
        return MessageFormat.format(getString(key), args);
    }

    public String getKString(String key) throws MissingResourceException {
        return (new StringBuilder()).append("[").append(key).append("]: ").append(getString(key)).toString();
    }

    public String getKString(String key, Object arg) throws MissingResourceException {
        return (new StringBuilder()).append("[").append(key).append("]: ").append(getString(key, arg)).toString();
    }

    public String getKString(String key, Object arg1, Object arg2) throws MissingResourceException {
        return (new StringBuilder()).append("[").append(key).append("]: ").append(getString(key, arg1, arg2)).toString();
    }

    public String getKString(String key, Object args[]) throws MissingResourceException {
        return (new StringBuilder()).append("[").append(key).append("]: ").append(getString(key, args)).toString();
    }

    public String getKTString(String key, Object args[]) throws MissingResourceException {
        return (new StringBuilder()).append("[").append(key).append("]: ").append("[").append(Thread.currentThread()).append("]")
                .append(getString(key, args)).toString();
    }

    public char getChar(String key) throws MissingResourceException {
        String s = getString(key);
        char c;
        try {
            c = s.charAt(0);
        } catch (Exception e) {
            c = '\0';
        }
        return c;
    }

    public String getCString(String key) throws MissingResourceException {
        return (new StringBuilder()).append(getString(key)).append(":").toString();
    }

    public static String unix2native(String s) {
        boolean converting = false;
        StringBuffer sb = null;
        String EOL = System.getProperty("line.separator");
        int start = 0;
        if (s == null)
            return "<null>";
        if (EOL.equals("\n"))
            return s;
        for (int n = 0; n < s.length(); n++) {
            if (s.charAt(n) != '\n')
                continue;
            if (sb == null)
                sb = new StringBuffer(2 * s.length());
            sb.append(s.substring(start, n));
            sb.append(EOL);
            start = n + 1;
        }

        if (sb != null) {
            if (start < s.length())
                sb.append(s.substring(start, s.length()));
            return sb.toString();
        } else {
            return s;
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append((new StringBuilder()).append(getClass().getName()).append(": convertEOL=").append(convertEOL).toString());
        if (convertEOL && cache != null) {
            sb.append((new StringBuilder()).append(" cache=").append(cache.toString()).toString());
            sb.append("\n");
        }
        sb.append((new StringBuilder()).append(" resourceBundle=").append(rb.toString()).toString());
        return sb.toString();
    }

    public Enumeration getKeys() {
        return rb.getKeys();
    }

    public Locale getLocale() {
        return rb.getLocale();
    }

    public static final String NL = System.getProperty("line.separator", "\n");
    private ResourceBundle rb;
    private boolean convertEOL;
    private static String UnixEOL = "\n";
    private HashMap cache;
    private static boolean DEBUG = false;

}
