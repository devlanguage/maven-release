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
 *  <br>����һ������ض����л��е����Ĺ����࣬����Է��������κ���ʽ�����ݣ�
 * ������ֻҪָ��һ�������Ϳ����ˣ������Զ��ظ�������������Excel���͵�������
 * ��ֵΪ���ݵ��С�
 */
public class ColumnRowCellOperator {
    /**
     * dataModel
     * <br>
     * the Model of the specific table.
     * <br>
     * Chinese Description
     * <br>
     * ����ı���ģ��
     */
    private TableModel dataModel;
    /**
     * table
     * <br>
     * the table to be dealt with.
     * <br>
     * Chinese Description
     * <br>
     *��Ҫ������ı��
     */
    private JTable table;
    /**
     * originColumnNames
     * <br>
     * the original column names
     * <br>
     * Chinese Description
     * <br>
     * ��񴫽���ʱ��ԭʼ����
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
     *  �ڱ�ͷ�������Ҽ������Ĳ˵�.
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
     * �ڱ�������һ��.
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
     *  �ڱ��һ��ǰ����һ��.
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
     *  �ڱ����һ�к����һ��.
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
     *  �ڵ�ǰ��ǰ����һ��.
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
     * ͨ��������������Ի�����䶥������Ҽ����С���Ϊ�ڰ����Ҽ�ʱ��������
     * ��Ϣ�ͱ���������������ˡ�
     */
    private Point point;
    /**
     * ColumnRowCellOperator
     *<br>
     *Contruct a Operator of the specifix table.
     *<br>
     *Chinese Description:
     *<br>
     *Ϊָ������趨������еĹ���
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
     * ��Excel��������������򷵻�һ�����ϵ�������
     * �������A��B,C,.....Z,AA,AB,�ȵȡ�����Ҳ����У����ؿ��ַ�����
     * @param column  ָ�����е�����
     * @return a string �е�����
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
     *�ڲ�������DefaultTableModel�����еķ���,ָ�����ģ�ͺ������Լ�����������
     *@param index ��Ӻ����ڱ��е�λ��
     * @param columnName Ҫ��ӵ��е�����.
     * @param columnData Ҫ��ӵ��е���������һά�����д洢
     * @param Model �����һ�еı��ģ�ͣ�������ָDefaultTableModel
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
     * ����Ϊ����ͷ�������,�����Ҽ��¼���
     *
     * @param table JTable
     */
    private void addMouseListener(final JTable table) {
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            boolean popupTrigger = false;
            public void mousePressed(java.awt.event.MouseEvent e) {
                popupTrigger = false;
                //��¼�Ҽ�����
                if (e.getButton() == 3) {
                    popupTrigger = true;
                }
            }

            public void mouseClicked(MouseEvent mouseevent) {
                //ֻ���Ҽ�����ʱ��Ӧ
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