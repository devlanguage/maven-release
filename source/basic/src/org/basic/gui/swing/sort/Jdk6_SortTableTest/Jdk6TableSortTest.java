package org.basic.gui.swing.sort.Jdk6_SortTableTest;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.basic.common.util.ImageCache;

import a.a.a.a.c;

class TableOperationListener extends AbstractAction {

    private BaseJTable jtable;
    BaseTableModel tableDataModel;

    public TableOperationListener(BaseJTable jtable) {

        this.jtable = jtable;
        this.tableDataModel = (BaseTableModel) jtable.getModel();
    }

    public void actionPerformed(ActionEvent e) {

        int columnCount = tableDataModel.getColumnCount();
        int newRow = tableDataModel.getRowCount();
        if (ActionCommand.Add.getActionName().equals(e.getActionCommand())) {
            Object[] rowData = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                rowData[i] = tableDataModel.getColumnName(i) + "--" + newRow;
            }

            tableDataModel.addRow(rowData);
        } else if (ActionCommand.Delete.getActionName().equals(e.getActionCommand())) {
            int[] selectedRows = jtable.getSelectedRows();
            if (0 == selectedRows.length) {
                JOptionPane.showMessageDialog(jtable, "Please selecte the row you want to remove");
            } else {
                tableDataModel.removeRow(selectedRows);
            }

        }

    }
}

enum TableCellEditorType {
    JTEXT_FILED, JCOMBO_BOX, JCHECK_BOX,
}

interface BaseTableColumn {
    public String getColumnName();

    public JComponent getNewComponent();

}

enum TableColumnPerson implements BaseTableColumn {
    ID(TableCellEditorType.JTEXT_FILED), //
    NAME(TableCellEditorType.JTEXT_FILED), //
    ADDRESS(TableCellEditorType.JTEXT_FILED), //
    AGE(TableCellEditorType.JTEXT_FILED), //
    SEX(TableCellEditorType.JCOMBO_BOX), //
    GROWNUP(TableCellEditorType.JCHECK_BOX), //
    DESC(TableCellEditorType.JTEXT_FILED);//

    private TableCellEditorType comp;
    private String columnName;

    private TableColumnPerson(TableCellEditorType comp_) {
        this.comp = comp_;
        this.columnName = name();
    }

    private TableColumnPerson(TableCellEditorType comp_, String columnName_) {
        this.comp = comp_;
        this.columnName = columnName_;
    }

    public JComponent getNewComponent() {
        switch (comp) {
            case JCOMBO_BOX:
                JComboBox<String> cmb = new JComboBox();
                cmb.addItem("xxxx");
                return cmb;
            case JCHECK_BOX:
                return new JCheckBox();
            default:
                return new JTextField();
        }

    }

    public String getColumnName() {
        return columnName;
    }
}

public class Jdk6TableSortTest extends JPanel {

    public static Object[][] tableData;

    @Override
    public String getToolTipText() {

        return Jdk6TableSortTest.class.getName();
    }

    public Jdk6TableSortTest() {

        setLayout(new BorderLayout());
        BaseJTable jtable = new BaseJTable();
        // BaseTableModel tableDataModel = new BaseTableModel(tableData);
        BaseTableModel tableDataModel = new BaseTableModel(TableColumnPerson.values());

        int row = 10;
        int columns = TableColumnPerson.values().length;
        tableData = new Object[row][columns];
        for (int i = 0; i < columns; ++i) {
            BaseTableColumn columnType = TableColumnPerson.values()[i];
            TableColumn tc = new TableColumn();
            TableCellEditor cellEditor = new BaseCellEditor(columnType.getNewComponent());
            tc.setCellEditor(cellEditor);
//            TableCellRenderer cellRenderer = new TableCellRenderer() {
//
//                @Override
//                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
//                                                               int column) {
//                    // TODO Auto-generated method stub
//                    return null;
//                }
//            };
//            tc.setCellRenderer(cellRenderer);
//
//            TableCellRenderer headerRenderer = new TableCellRenderer() {
//                @Override
//                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
//                                                               int column) {
//                    return null;
//                }
//            };
//            tc.setHeaderRenderer(headerRenderer);
            
            for (int r = 0; r < row; ++r) {
                tableData[r][i] = "Cell(" + r + "," + i + ")";
            }
            tc.setIdentifier(columnType);
            tc.setHeaderValue(columnType.getColumnName());
            jtable.addColumn(tc);
        }
        jtable.setModel(tableDataModel);
        tableDataModel.setData(tableData);

        // jtable.setAutoCreateRowSorter(true);
        jtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(jtable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnOk = new BaseButton(ActionCommand.OK, ImageCache.getInstance().getIcon("/resource/icons/40_OLT.gif"));
        JButton btnCancel = new BaseButton(ActionCommand.Cancel);
        JButton btnRefresh = new BaseButton(ActionCommand.Refresh);
        JButton btnAddRow = new BaseButton(ActionCommand.Add);
        JButton btnDeleteRow = new BaseButton(ActionCommand.Delete);

        btnOk.addActionListener(new TableOperationListener(jtable));
        btnCancel.addActionListener(new TableOperationListener(jtable));
        btnRefresh.addActionListener(new TableOperationListener(jtable));
        btnAddRow.addActionListener(new TableOperationListener(jtable));
        btnDeleteRow.addActionListener(new TableOperationListener(jtable));

        btnPanel.add(btnOk);
        btnPanel.add(btnCancel);
        btnPanel.add(btnRefresh);
        btnPanel.add(btnAddRow);
        btnPanel.add(btnDeleteRow);
        add(btnPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.getContentPane().add(new Jdk6TableSortTest());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
