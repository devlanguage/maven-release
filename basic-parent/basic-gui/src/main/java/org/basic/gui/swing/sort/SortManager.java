package org.basic.gui.swing.sort;


import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Locale;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
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
 * This Class is used to add sorting function the  the any JTable instances or JTable's Childrens.
 * This is a very convenient tool to plug in.Because it don't effect the table and the JTable
 * instance needn't to change the TableModel to achieve the sorting function.
 * <strong>Warning:</strong>
 * Due to this Class will use the TableHeader,so if your JTable instance do some special to TableHeader
 * may occur Exception unexcepted.
 * @author <a href="mailto:me_gtm@hotmail.com>G.T.M.</a>
 * @version 0.1
 * Chinese Descriptions:<br>
 * ����һ���ܷ�����࣬ʹ���߲���ҪΪ��ʵ�������ܶ��ر��ƶ�һ��TableMoel��ֻ��Ҫ��ʹ�ò���ʽ��������
 * ��������࣬�Ϳ��Է����ʵ�������ܡ�
 * <strong>����:</strong>
 * ���ڸ����ʵ�ֹ������TableHeader�Ĵ�������û���JTableʵ�����TableHeader�Ĵ������ܻ�������⡣
 */


public class SortManager implements TableModelListener {

    /**
     * upIcon
     * <br>
     * <code>UpDownArrow</code>
     *
     * @see #UpDownArrow
     */
    final static Icon upIcon = new UpDownArrow(0);
    /**
     * downIcon
     * <br>
     * <code>UpDownArrow</code>
     *
     * @see #UpDownArrow
     */
    final static Icon downIcon = new UpDownArrow(1);

    private JTable table;

    private TableModel dataModel;
    /**
     * sortColumn
     * <br>
     * The current sorting column.
     * <br>
     * Chinese Descriptions:
     * <br>
     * ��ǰ�������.
     *
     * <p>
     */
    private int sortColumn;

    private Row rows[];

    /**
     * ascending
     * <br>
     * The order of sorting.
     * <br>
     * Chinese Descriptions:
     * <br>
     * �����˳��
     */
    private boolean ascending;
    /**
     * sortableColumns
     * <br> It's used to point out which columns are sortable.
     * <br> Chinese Descriptions:
     * <br> ����ָ���ļ�������Ҫ�����ܵ�.
     *
     * <p>
     */
    private int sortableColumns[];


    /**
     * SortManager
     * <br>
     * The base way to use this class.It will sort all the columns when user
     * click it.
     * <br>
     * Chinese Descriptions:
     * <br>
     * ��������÷���Ĭ���ܸ����е�������
     *
     * <p>
     *
     * @param jtable JTable
     */
    public SortManager(JTable jtable) {
        rows = null;
        ascending = true;
        sortableColumns = null;
        table = jtable;
        int i = 0;
        int length = jtable.getModel().getColumnCount();
        final int[] columns = new int[length];
        for (; i < length; i++) {
            columns[i] = i;
        }
        sortableColumns = columns;
        initialize();
    }

    /**
     * SortManager
     * <br> The base way to use this class.It will sort the specified column
     * when user click it.
     * <br> Chinese Descriptions:
     * <br> ��������÷���Ĭ���ܸ�ָ����������
     *
     * <p>
     *
     * @param jtable JTable
     * @param i int<br>
     *   The column user specify to sort.<br>
     */
    public SortManager(JTable jtable, int i) {
        rows = null;
        ascending = true;
        sortableColumns = null;
        table = jtable;
        sortColumn = i;
        initialize();
    }

    /**
     * SortManager
     * <br> The base way to use this class.It will sort the specified columns
     * when user click it.
     * <br> Chinese Descriptions:
     * <br> ��������÷���Ĭ���ܸ�ָ����һ��������
     *
     * <p>
     *
     * @param jtable JTable
     * @param ai int[]<br> The columns user specify to sort.<br>
     */
    public SortManager(JTable jtable, int ai[]) {
        this(jtable, ai[0]);
        sortableColumns = (int[]) ai.clone();
    }

    /**
     * initialize
     * <br> Initializing the JTable came in,escpecailly to add the listeners to
     * JTable.
     * <br>
     * Chinese Descriptions:
     * <br>
     * ��ʼ��JTableʵ�������������Ӧ��������
     */
    public void initialize() {
        dataModel = table.getModel();
        ((AbstractTableModel) dataModel).addTableModelListener(this);
        addMouseListener(table);
        JTableHeader jtableheader = table.getTableHeader();
        jtableheader.setDefaultRenderer(createHeaderRenderer());
        if (table.getRowCount() > 0) {
            reinitialize();
        }
    }

    /**
     * createHeaderRenderer
     * <br>
     * Create the specify HeaderRender for the table,can response the mouse
     * action to sort the column.
     * <br>
     * Chinese Description:
     * <br>
     * Ϊtable�����ܹ���Ӧ���ʱ�������TableHeaderRender��
     *
     * <p>
     *
     * @return TableCellRenderer
     */
    protected TableCellRenderer createHeaderRenderer() {
        DefaultTableCellRenderer defaultHeaderRenderer = new SortHeaderRenderer();
        defaultHeaderRenderer.setHorizontalAlignment(0);
        defaultHeaderRenderer.setHorizontalTextPosition(2);
        return defaultHeaderRenderer;
    }

    public void reinitialize() {
        rows = null;
        if (table.getRowCount() > 0) {
            rows = new Row[table.getRowCount()];
            for (int i = 0; i < rows.length; i++) {
                rows[i] = new Row();
                rows[i].index = i;
            }

            if (columnIsSortable(sortColumn)) {
                sort();
            }
        }
    }

    /**
     * columnIsSortable
     * <br> Check out that if the specify column is sortable. It depend on the
     * constructor.
     * <br>
     * Chinese Descriptions:
     * <br>
     * �ж�ָ�������Ƿ�����򣬸÷����������߼���������Ĺ��캯������ʱ������
     *
     * <p>
     *
     * @param i int
     * @return boolean
     * @see #public SortModel(Container container1, JTable jtable, int i)
     * @see #public SortModel(Container container1, JTable jtable, int ai[])
     * @see #sortColumn
     * @see #sortablrColumns
     */
    private boolean columnIsSortable(int i) {
        if (rows != null) {
            if (sortableColumns != null) {
                for (int j = 0; j < sortableColumns.length; j++) {
                    if (i == sortableColumns[j]) {
                        return true;
                    }
                }

            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * mouseClicked
     * <br> The real Method to add mouselistener to JTable.Due to the
     * SortHeaderRender is a inner class of this class.So the sortcolumn user
     * want can share and be managed in whole class field.
     * <br> Chinese Description:
     * <br>
     * ������������Ĵ�������ķ���֮һ������JTable������ʱ������������û��������ĳ�У�����SortHeaderRender���ڲ��࣬���Կ��Թ��������ݲ�����
     *
     * @param table JTable
     */
    public void addMouseListener(final JTable table) {
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            boolean popupTrigger = false;
            public void mousePressed(java.awt.event.MouseEvent e) {
                //���ڿ����Ƿ������Ҽ�
                popupTrigger = false;

                if (e.getButton() == 3) {
                    popupTrigger = true;
                }

            }

            public void mouseClicked(MouseEvent mouseevent) {
                if (!popupTrigger) {
                    int i = table.columnAtPoint(mouseevent.getPoint());
                    int j = table.convertColumnIndexToModel(i);

                    if (table.getTableHeader().getResizingColumn() != null) {
                        return;
                    }
                    //ת�����û���������к͵ײ����ݵ��У�Ȼ���ж�
                    if (!columnIsSortable(j)) {
                        return;
                    }
                    if (j == sortColumn) {
                        ascending = !ascending;
                    } else {
                        ascending = true;
                        sortColumn = j;
                    }
                    sort();

                }

            }

        });
    }

    /**
     * sort
     * <br> The main method to sort the rows depends on the specify column to
     * sort.
     * <br> Chinese Descriptions:
     * <br> ��Ҫ��������ķ�����
     * Fix bug:table.UpdateUI();--->
     *             table.recalidate();
     *             table.repaint();
     *
     * @see #resetData()
     */
    public void sort() {
        if (rows == null) {
            return;
        } else {

            ((AbstractTableModel) dataModel).removeTableModelListener(this);
            Arrays.sort(rows);
            resetData();
            ((AbstractTableModel) dataModel).fireTableDataChanged();
            ((AbstractTableModel) dataModel).addTableModelListener(this);
            table.revalidate();
            table.repaint();
            //Bug found by zhousi.Fixed by G.T.M. 05.1.29
            table.getTableHeader().repaint();
            /////////////////////////////////////////////
            return;
        }
    }

    /**
     * resetData
     * <br>
     * The last step to sort.We reset the sorted to  the table and show the
     * changes.
     * <br>
     * Chinese Descriptions:
     * <br>
     * ��������һ�������ź�����������·���table���档
     *
     * @see #sort()
     */
    public void resetData() {
        Vector data = new Vector(dataModel.getRowCount());
        int i = 0;
        for (; i < dataModel.getRowCount(); i++) {
            int j = 0;
            final Vector vv = new Vector(dataModel.getColumnCount());
            for (; j < dataModel.getColumnCount(); j++) {
                vv.add(dataModel.getValueAt(i, j));
            }
            data.add(vv);
        }
        i = 0;
        for (; i < rows.length; i++) {
            if (rows[i].index != i) {
                int j = 0;
                final Vector vv = (Vector) data.get(rows[i].index);
                for (; j < dataModel.getColumnCount(); j++) {
                    dataModel.setValueAt(vv.get(j), i, j);
                }
            }
        }
    }

    public void tableChanged(TableModelEvent tablemodelevent) {
        reinitialize();
    }


    private class SortHeaderRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable jtable,
                Object obj, boolean flag, boolean flag1, int i, int j) {
            if (jtable != null) {
                JTableHeader jtableheader = jtable.getTableHeader();
                if (jtableheader != null) {
                    setForeground(jtableheader.getForeground());
                    setBackground(jtableheader.getBackground());
                    setFont(jtableheader.getFont());
                }
            }
            setText(obj != null ? obj.toString() : "");
            int k = jtable.convertColumnIndexToModel(j);
            if (k == sortColumn) {
                setIcon(ascending ? SortManager.downIcon : SortManager.upIcon);
            } else {
                setIcon(null);
            }
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            return this;
        }
    }


    private class Row implements Comparable {
        private Row() {
        }

        /*  public int compareTo(Object obj) {
              int result=compare(obj);
              Row row = (Row) obj;
              Vector row1=new Vector(dataModel.getColumnCount());
              Vector row2=new Vector(dataModel.getColumnCount());
              int i=0;
              for(;i<dataModel.getColumnCount();i++){
                  row1.add(dataModel.getValueAt(index,i));
                  row2.add(dataModel.getValueAt(row.index,i));
              }
              if(result>=0){

              }else{
                  int j=0;
                  for(;j<dataModel.getColumnCount();j++){
                      dataModel.setValueAt(row1.get(j),row.index,j);
                      dataModel.setValueAt(row2.get(j),index,j);
                  }

              }
              return result;
          }*/

        public int compareTo(Object obj) {
            Row row = (Row) obj;
            //bug found and fixed by chenxing 05.1.24.Support Local String sorting,such as Chinese.
            //Chinese Descriptions:
            //֧�ֱ����ַ��ıȽ��������磺��������
            java.text.Collator cnCollator = java.text.Collator.getInstance(
                    Locale.getDefault());
            //////////////////////////////////////////
            Object obj1 = dataModel.getValueAt(index, sortColumn);
            Object obj2 = dataModel.getValueAt(row.index, sortColumn);
            if (ascending) {
                if (!(obj1 instanceof Comparable)) {
                    return -1;
                }
                if (!(obj2 instanceof Comparable)) {
                    return 1;
                } else {
                    //  if(obj1 instanceof String||obj2 instanceof String)
                    //  {
                    return cnCollator.compare(obj1, obj2);
                    //  }
                    // else
                    //  {
                    //   return ((Comparable) obj1).compareTo(obj2);
                    //  }
                }
            }
            if (!(obj1 instanceof Comparable)) {
                return 1;
            }
            if (!(obj2 instanceof Comparable)) {
                return -1;
            } else {
                // if(obj1 instanceof String||obj2 instanceof String)
                //  {
                return cnCollator.compare(obj2, obj1);
                // }
                // else
                // {
                //    return ((Comparable) obj2).compareTo(obj1);
                // }
            }

        }

        public int index;
    }

}