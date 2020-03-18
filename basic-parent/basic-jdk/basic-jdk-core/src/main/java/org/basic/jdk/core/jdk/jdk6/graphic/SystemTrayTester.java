package org.basic.jdk.core.jdk.jdk6.graphic;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 */
public class SystemTrayTester {

    private static SystemTray st;

    public static void main(String[] args) {

        if (SystemTray.isSupported()) {
            st = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage(SystemTrayTester.class.getResource("nimrodlf.JPG"));
            PopupMenu pm = createPopupMenu();
            TrayIcon ti = new TrayIcon(image, "Desktop Demo Tray", pm);
            try {
                st.add(ti);
            } catch (AWTException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static PopupMenu createPopupMenu() {

        PopupMenu pm = new PopupMenu();
        MenuItem openBrowser = new MenuItem("Open My Blog");
        openBrowser.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                // openBrowser("http://blog.csdn.net/chinajash");
            }
        });

        MenuItem sendMail = new MenuItem("Send Mail to me");
        sendMail.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                // sendMail("mailto:chinajash@yahoo.com.cn");
            }
        });

        MenuItem edit = new MenuItem("Edit Text File");
        sendMail.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                // edit();
            }
        });

        MenuItem exitMenu = new MenuItem("&Exit");
        exitMenu.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });
        pm.add(openBrowser);
        pm.add(sendMail);
        pm.add(edit);
        pm.addSeparator();
        pm.add(exitMenu);
        return pm;
    }

}
