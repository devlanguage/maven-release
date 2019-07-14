package org.basic.gui.swing.sort;


import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * <p>Title: Swing2CN Project</p>
 *
 * <p>Description: A Project for Swing Components</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Organization: Swing4CN Group</p>
 *  This is a utility class that can add a specific column or row the table.
 *  You even need not to specify the data and column name of the column to add.
 *  if you only set the index of the column, it  will add a column
 * use spreadsheet style column names and null values as data.
 *  @author <a href="mailto:webgis_www@163.com>webgis4cn</a>
 *  @version 0.1
 *  <br>Chinese Description
 *  <br>这是一个添加特定的行或列到表格的工具类，你可以方便地添加任何形式的数据，
 * 甚至你只要指定一个索引就可以了，它会自动地根据索引号生成Excel表单型的列名和
 * 空值为数据的列。
 */
public class ColumnRowCellOperator {
    /**
     * dataModel
     * <br>
     * the Model of the specific table.
     * <br>
     * Chinese Description
     * <br>
     * 传入的表格的模型
     */
    private TableModel dataModel;
    /**
     * table
     * <br>
     * the table to be dealt with.
     * <br>
     * Chinese Description
     * <br>
     *将要被处理的表格
     */
    private JTable table;
    /**
     * originColumnNames
     * <br>
     * the original column names
     * <br>
     * Chinese Description
     * <br>
     * 表格传进来时的原始列名
     */
    private Vector originColumnNames;
    static boolean isFirst = true;
    /**
     * mnuPopHeader
     * <br>
     *  Popmenu that shows on the Table Header.
     * <br>
     *  Chinese Descriptions:
     * <br>
     *  在表头弹出的右键上下文菜单.
     *
     * <p>
     */
    private JPopupMenu mnuPopHeader = null;
    /**
     * mtmInsertRow
     * <br>
     * A menuItem that can inert a row as the last row of the table.
     * <br>
     *  Chinese Descriptions:
     * <br>
     * 在表最后插入一行.
     *
     * <p>
     */
    private JMenuItem mtmInsertRow = null;
    /**
     * mtmInsertColFirst
     * <br>
     *  A menuItem that can insert a column before the first column.
     * <br>
     * Chinese Descriptions:
     * <br>
     *  在表第一列前插入一列.
     *
     * <p>
     */
    private JMenuItem mtmInsertColFirst = null;
    /**
     * mtmInsertColLast
     * <br>
     * A menuItem that can insert a column after the last column.
     * <br>
     *  Chinese Descriptions:
     * <br>
     *  在表最后一列后插入一列.
     *
     * <p>
     */
    private JMenuItem mtmInsertColLast = null;
    /**
     * mtmInsertColCurrent
     * <br>
     *   A menuItem that can insert a column before the current column.
     * <br>
     *  Chinese Descriptions:
     * <br>
     *  在当前列前插入一列.
     *
     * <p>
     */
    private JMenuItem mtmInsertColCurrent = null;
    /**
     * point
     * <br>
     * we can get the column on whose header we click the right key
     * of the mouse from the variable,because when we click the right key,
     * the position is the point.
     * <br>
     * Chinese Description:
     * 通过这个变量，可以获得在其顶部点击右键的列。因为在按下右键时，点击点的
     * 信息就保存在这个变量中了。
     */
    private Point point;
    /**
     * ColumnRowCellOperator
     *<br>
     *Contruct a Operator of the specifix table.
     *<br>
     *Chinese Description:
     *<br>
     *为指定表格设定添加行列的功能
     */
    public ColumnRowCellOperator(JTable table) {
        this.dataModel = table.getModel();
        this.table = table;
        addMouseListener(this.table);
    }

    /**
     * getMnuPopHeader
     *
     * @return javax.swing.JPopupMenu
     */
    private JPopupMenu getMnuPopHeader() {
        if (mnuPopHeader == null) {
            mnuPopHeader = new JPopupMenu();
            ActionListener colFirstAction = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    insertColumn(0);
                }

            };
            ActionListener colLastAction = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    insertColumn(ColumnRowCellOperator.this.table.
                                 getColumnCount());
                }

            };
            ActionListener colCurrAction = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    insertColumn(ColumnRowCellOperator.this.table.columnAtPoint(
                            ColumnRowCellOperator.this.point));
                }

            };
            Resource.createMenuItem(mnuPopHeader,
                                    "ColumnRowCellOperator.mtmInsertRow",
                                    "OperatorMenu.addRow_mnemonic",
                                    "OperatorMenu.addRow_accessdesp" ,null);//replace "OperatorMenu.addRow_accessdesp" by G.T.M. 2005,02-03
            Resource.createMenuItem(mnuPopHeader,
                                    "ColumnRowCellOperator.mtmInsertColLast",
                                    "OperatorMenu.addColLast_mnemonic",
                                    "OperatorMenu.addColLast_accessdesp",
                                    colLastAction);
            Resource.createMenuItem(mnuPopHeader,
                                    "ColumnRowCellOperator.mtmInsertColFirst",
                                    "OperatorMenu.addColFirst_mnemonic",
                                    "OperatorMenu.addColFirst_accessdesp",
                                    colFirstAction);
            Resource.createMenuItem(mnuPopHeader,
                                    "ColumnRowCellOperator.mtmInsertColCurrent",
                                    "OperatorMenu.addColCurr_mnemonic",
                                    "OperatorMenu.addColCurr_accessdesp",
                                    colCurrAction);

        }
        return mnuPopHeader;
    }

    /**
     * getColumnName
     *  Returns a default name for the column using spreadsheet conventions:
     *  A, B, C, ... Z, AA, AB, etc.  If <code>column</code> cannot be found,
     *  returns an empty string.
     *
     * @param column  the column being queried
     * @return a string containing the default name of <code>column</code>
     * <br>
     * Chinese Description
     * <br>
     * 按Excel工作表的列名规则返回一个获认的列名。
     * 如获至宝A，B,C,.....Z,AA,AB,等等。如果找不到列，返回空字符串。
     * @param column  指定的列的索引
     * @return a string 列的名字
     */
    protected String getColumnName(int column) {
        String result = "";
        for (; column >= 0; column = column / 26 - 1) {
            result = (char) ((char) (column % 26) + 'A') + result;
        }
        return result;
    }

    /**
     * insertColumn(int index,Object columnName,Object [] columnData,DefaultTableModel Model)
     * <br>add a column with the specific column name and data
     * to the specific index  and model
     * @param index location to add the column
     * @param columnName the column name of the column to add.
     * @param columnData the data of the column to add
     * @param Model Model to add the column in,DefaultTableModel here.
     *<br>
     *Chinese Description:
     *内部处理在DefaultTableModel插入列的方法,指定表格模型和索引以及列名和数据
     *@param index 添加后列在表中的位置
     * @param columnName 要添加的列的列名.
     * @param columnData 要添加的列的列名，以一维数组中存储
     * @param Model 需插入一列的表格模型，这里特指DefaultTableModel
     * <br>
     * @see #org.swing2cn.table.AbstractCustomTableModel
     */
    protected void insertColumn(int index, Object columnName,
                                Object[] columnData, DefaultTableModel Model) {
        if (isFirst) {
            originColumnNames = new Vector();
            for (int i = 0; i < Model.getColumnCount(); i++) {
                this.originColumnNames.add(Model.getColumnName(i));
            }
            isFirst = false;
        }
        int columnCount = Model.getColumnCount();
        int rowCount = Model.getRowCount();
        String[] columnNames = new String[columnCount + 1];
        for (int i = 0; i < columnCount + 1; i++) {
            if (i < index) {
                if (this.originColumnNames.contains(Model.getColumnName(i))) {
                    columnNames[i] = Model.getColumnName(i);
                } else {
                    columnNames[i] = getColumnName(i);
                }

            }
            if (i == index) {
                columnNames[i] = columnName.toString();
            }
            if (i > index) {
                if (this.originColumnNames.contains(Model.getColumnName(i - 1))) {
                    columnNames[i] = Model.getColumnName(i - 1);
                } else {
                    columnNames[i] = getColumnName(i);
                }

            }
        }
        Vector data = Model.getDataVector();
        Vector columnDataTemp = convertToVector(columnData);
        for (int i = 0; i < rowCount; i++) {
            Vector row = (Vector) data.elementAt(i);
            row.setSize(row.size() + 1);
            if (columnDataTemp == null) {
                row.insertElementAt(null, index);
            } else {
                row.insertElementAt(columnDataTemp.get(i), index);
            }
        }
        Model.setDataVector(data, convertToVector(columnNames));
    }

    /**
     * insertColumn(int index)
     * @param index
     * @see #insertColumn(int, Object, Object[], DefaultTableModel)
     * @see #org.swing2cn.table.AbstractCustomTableModel
     */
    public void insertColumn(int index) {

        TableModel model = table.getModel();
        if (model instanceof DefaultTableModel) {
            DefaultTableModel Model = (DefaultTableModel) model;
            String newColumnName = getColumnName(index);
            insertColumn(index, newColumnName, null, Model);

        }
        if (model instanceof AbstractCustomTableModel) {
            AbstractCustomTableModel Model = (AbstractCustomTableModel) model;
            Model.insertColumn(index, getColumnName(index), (Object[])null);
        }
        ((AbstractTableModel) model).fireTableStructureChanged();

    }

    /**
     * insertColumn(int index,Object columnName)
     * @param index
     * @param columnName
     * @see #insertColumn(int, Object, Object[], DefaultTableModel)
     *  @see #org.swing2cn.table.AbstractCustomTableModel
     */
    protected void insertColumn(int index, Object columnName) {

        TableModel model = table.getModel();
        if (model instanceof DefaultTableModel) {
            DefaultTableModel Model = (DefaultTableModel) model;
            insertColumn(index, columnName, null, Model);
        }
        if (model instanceof AbstractCustomTableModel) {
            AbstractCustomTableModel Model = (AbstractCustomTableModel) model;
            Model.insertColumn(index, columnName, (Object[])null);
        }
        ((AbstractTableModel) model).fireTableStructureChanged();

    }

    /***
     * insertColumn(int index,Object columnName,Object [] columnData)
     * @param index
     * @param columnName
     * @param columnData
     * @see #insertColumn(int, Object, Object[], DefaultTableModel)
     * @see #org.swing2cn.table.AbstractCustomTableModel
     */
    protected void insertColumn(int index, Object columnName,
                                Object[] columnData) {
        TableModel model = table.getModel();
        if (model instanceof DefaultTableModel) {
            DefaultTableModel Model = (DefaultTableModel) model;
            insertColumn(index, columnName, columnData, Model);
        }
        if (model instanceof AbstractCustomTableModel) {
            AbstractCustomTableModel Model = (AbstractCustomTableModel) model;

            Model.insertColumn(index, columnName, columnData);
        }
        ((AbstractTableModel) model).fireTableStructureChanged();

    }

    /**
     * Returns a vector that contains the same objects as the array.
     * @param anArray  the array to be converted
     * @return  the new vector; if <code>anArray</code> is <code>null</code>,
     *              returns <code>null</code>
     */
    protected static Vector convertToVector(Object[] anArray) {
        if (anArray == null) {
            return null;
        }
        Vector v = new Vector(anArray.length);
        for (int i = 0; i < anArray.length; i++) {
            v.addElement(anArray[i]);
        }
        return v;
    }

    /**
     * addMouseListener
     * <br>
     * <br> Chinese Description:
     * <br>
     * 负责为表格表头添加侦听,处理右键事件。
     *
     * @param table JTable
     */
    private void addMouseListener(final JTable table) {
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            boolean popupTrigger = false;
            public void mousePressed(java.awt.event.MouseEvent e) {
                popupTrigger = false;
                //记录右键按下
                if (e.getButton() == 3) {
                    popupTrigger = true;
                }
            }

            public void mouseClicked(MouseEvent mouseevent) {
                //只有右键按下时响应
                if (popupTrigger) {
                    if (getMnuPopHeader() != null) {
                        point = mouseevent.getPoint();
                        getMnuPopHeader().show(table.getTableHeader(),
                                               mouseevent.getX(),
                                               mouseevent.getY());
                    }
                }
            }

        });
    }
}