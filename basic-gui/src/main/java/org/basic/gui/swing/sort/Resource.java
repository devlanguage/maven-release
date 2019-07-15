package org.basic.gui.swing.sort;

import java.awt.event.ActionListener;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * 
 * <p>
 * Title: Swing2CN Project
 * </p>
 * 
 * <p>
 * Description: A Project for Swing Components
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Organization: Swing4CN Group
 * </p>
 * 
 */
public class Resource
{
    // Resource bundle for internationalized and accessible text
    public static ResourceBundle bundle = null;

    /**
     * Returns a mnemonic from the resource bundle. Typically used as keyboard
     * shortcuts in menu items. <br>
     * Chinese Descriptions: <br>
     * ����Դ���л�ÿ�ݼ�.
     * 
     * @param key
     *            String
     * @return char
     */
    public static char getMnemonic(String key)
    {
        return (getString(key)).charAt(0);
    }

    /**
     * Creates a generic menu item
     */
    public static JMenuItem createMenuItem(JPopupMenu menu, String label,
            String mnemonic, String accessibleDescription, ActionListener action)
    {
        JMenuItem mi = (JMenuItem) menu.add(new JMenuItem(getString(label)));
        mi.setMnemonic(getMnemonic(mnemonic));
        mi.getAccessibleContext().setAccessibleDescription(
                getString(accessibleDescription));
        mi.addActionListener(action);
        if (action == null)
        {
            mi.setEnabled(false);
        }
        return mi;
    }

    /**
     * This method returns a string from the resource bundle. <br>
     * Chinese Descriptions : <br>
     * ����Դ���л����Ӧ�ı����ַ���.
     * 
     * <p>
     * 
     * @param key
     *            String
     * @return String
     */
    public static String getString(String key)
    {
        String value = null;
        try
        {
            value = getResourceBundle().getString(key);
        } catch (MissingResourceException e)
        {
            System.out
                    .println("java.util.MissingResourceException: Couldn't find value for: "
                            + key);
        }
        if (value == null)
        {
            value = "Could not find resource: " + key + "  ";
        }
        return value;
    }

    /**
     * Returns the resource bundle associated with this project. Used to get
     * accessable and internationalized strings. <br>
     * Chinese Descriptins: <br>
     * �������Ŀ��ص���Դ��.
     * 
     * <p>
     * 
     * @return ResourceBundle
     */
    public static ResourceBundle getResourceBundle()
    {
        if (bundle == null)
        {
            bundle = ResourceBundle
                    .getBundle("org/swing2cn/resources/swing2cn");
        }
        return bundle;
    }

}