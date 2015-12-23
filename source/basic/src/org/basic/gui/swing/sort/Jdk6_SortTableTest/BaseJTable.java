package org.basic.gui.swing.sort.Jdk6_SortTableTest;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class BaseJTable extends JTable {
    private static final long serialVersionUID = 1855093175262811105L;

    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        return super.getCellEditor(row, column);
    }

    @Override
    public String getColumnName(int column) {
        // return columnHeaders[column];
        return super.getColumnName(column);
    }

    @Override
    public int getColumnCount() {
        // return columnHeaders.length;
        return super.getColumnCount();
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        return super.getCellRenderer(row, column);
    }
}
