package org.basic.gui.swing.sort.Jdk6_SortTableTest;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.table.DefaultTableModel;

public class BaseTableModel extends DefaultTableModel {

    public BaseTableModel() {
        super();
    }

    BaseTableColumn[] columns;

    public BaseTableModel(BaseTableColumn[] values) {
        super();
        columns = values;

    }

    @Override
    public int getRowCount() {
        return super.getRowCount();
        // return row.get();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
        // return super.getColumnCount();
    }

    @Override
    public String getColumnName(int column) {
        return columns[column].getColumnName();
    }

    @Override
    public Object getValueAt(int row, int column) {
        return super.getValueAt(row, column);
    }

    public void addRow(Object[] rowData) {
        Vector<Object> column = new Vector<Object>(rowData.length);
        for (Object obj : rowData) {
            column.addElement(obj);
        }
        dataVector.addElement(column);
        fireTableRowsInserted(dataVector.size() - 1, dataVector.size());
    }

    public void removeRow(int[] selectedRows) {
        int minRow = dataVector.size();
        int i = 0;
        for (int row : selectedRows) {
            if (row < minRow) {
                minRow = row;
            }
            if (row - i < dataVector.size()) {
                dataVector.remove(row - i);
                i++;
            }
        }
        fireTableRowsDeleted(minRow, dataVector.size());
    }

    public void setData(Object[][] testData) {
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        for (Object[] rowObj : testData) {
            Vector<Object> column = new Vector<Object>(rowObj.length);
            for (Object obj : rowObj) {
                column.addElement(obj);
            }
            data.add(column);
        }
        this.dataVector = data;
    }

}
