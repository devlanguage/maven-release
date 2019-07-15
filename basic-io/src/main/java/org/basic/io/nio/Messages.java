package org.basic.io.nio;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created on May 6, 2014, 4:44:14 PM
 */
public class Messages {
    private static final String BUNDLE_NAME = "org.basic.io.nio.messages"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private Messages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
