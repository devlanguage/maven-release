package org.basic.jdk.core.jdk.jdk6.swing.bean;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class PersonTable extends JTable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3167835879539878979L;

    static String data[][] = { { "China", "Beijing", "Chinese" },
            { "China", "Shanghai", "Chinese" }, { "China", "Xianyang", "Chinese" },
            { "China", "Xi'an", "Chinese" }, { "China", "Lanzhou", "Chinese" },
            { "China", "Hangzhou", "Chinese" }, { "China", "Tianshui", "Chinese" },
            { "America", "Washington", "English" }, { "Korea", "Seoul", "Korean" },
            { "Japan", "Tokyo", "Japanese" }, { "France", "Paris", "French" },
            { "England", "London", "English" }, { "Germany", "Berlin", "German" }, };
    static String titles[] = { "Country", "Capital", "Language" };
    private static DefaultTableModel model = null;
    private TableRowSorter<TableModel> tableRowSorter;

    static {
        model = new DefaultTableModel(data, titles);
    }

    public PersonTable() {

        this.setModel(model);
        this.setTableRowSorter(new TableRowSorter<TableModel>(model));// 为JTable设置排序�?        this.setAutoscrolls(true);

    }

    public static JPopupMenu getRightKeyMenu() {

        final JPopupMenu pm = new JPopupMenu("JPopMenu");
        pm.add(new JMenuItem("file1"));
        pm.add(new JMenuItem("file2"));
        pm.add(new JMenuItem("file3"));
        pm.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {

                maybeShowPopup(e);

            }

            public void mouseEntered(MouseEvent e) {

                maybeShowPopup(e);

            }

            public void mouseExited(MouseEvent e) {

                maybeShowPopup(e);

            }

            public void mousePressed(MouseEvent e) {

                maybeShowPopup(e);

            }

            public void mouseReleased(MouseEvent e) {

                maybeShowPopup(e);

            }

            private void maybeShowPopup(MouseEvent e) {

                if (e.isPopupTrigger()) {
                    pm.show(e.getComponent(), e.getX(), e.getY());
                }
            }

        });

        return pm;
    }

    public TableRowSorter<TableModel> getTableRowSorter() {

        return this.tableRowSorter;
    }

    public void setTableRowSorter(TableRowSorter<TableModel> tableRowSorter) {

        this.tableRowSorter = tableRowSorter;
        this.setRowSorter(this.tableRowSorter);
    }

}
