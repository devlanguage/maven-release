package org.basic.gui.swing.sort;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

/**
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
 * @author G.T.M.
 * @version 0.1 This is a convenient tool to add cell row
 *          copying,pasting,cutting and adding functions to a JTable. And it
 *          needn't User to code so many but one line "new new
 *          KeyPlugin(table);". <br>
 *          Chinese Descriptions:<br>
 *          这是一个非常方便的类为JTable添加单元格的复制,剪切,粘贴,和行的添加,插入功能.它大大
 *          减少了开发人员的代码量,只需要一句"new new KeyPlugin(table);".
 */
public class KeyPlugin implements MouseListener
{

    /**
     * table <br>
     * The table will be set up. <br>
     * Chinese Descriptions: <br>
     * 被处理的JTable.
     */
    JTable table;
    /**
     * copy <br>
     * The Key register the Action "copy". <br>
     * Chinese Descriptions: <br>
     * 注册"复制"事件的键盘事件.
     * 
     * <p>
     */
    KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C,
            ActionEvent.CTRL_MASK, false);;
    /**
     * paste <br>
     * The Key register the Action "paste". <br>
     * Chinese Descriptions: <br>
     * 注册"粘贴"事件的键盘事件.
     * 
     * <p>
     */
    KeyStroke paste = KeyStroke.getKeyStroke(KeyEvent.VK_V,
            ActionEvent.CTRL_MASK, false);
    /**
     * paste <br>
     * The Key register the Action "cut". <br>
     * Chinese Descriptions: <br>
     * 注册"剪切"事件的键盘事件.
     * 
     * <p>
     */

    KeyStroke cut = KeyStroke.getKeyStroke(KeyEvent.VK_X,
            ActionEvent.CTRL_MASK, false);

    /**
     * model <br>
     * We try to support two Models of JTable's Model.One is
     * DefaultTableModel,the other is our AbstractCustomTableModel subclass.
     * This variable used to estimate the Model <br>
     * Chinese Descriptions: <br>
     * 我们支持DefaultTableModel和AbstractCustomTableModel这两种类的“粘贴”动作。这个全局变量用来判断是那种模式。
     * 
     * @see #javax.swing.table.DefaultTableColumnModel
     * @see AbstractCustomTableModel
     */
    int model;
    /**
     * The predifine variable hint the operate's Model
     * 
     * @see #operate(Model)
     */
    private final int Cut = 1;
    /**
     * The predifine variable hint the operate's Model
     * 
     * @see #operate(Model)
     */
    private final int Copy = 0;

    /**
     * menu <br>
     * The Menu to pop for inserting row. <br>
     * Chineses Descriptions: <br>
     * 弹出的菜单支持插入一行。
     * 
     * @see javax.swing.JPopupMenu
     *      <p>
     */
    private JPopupMenu menu = new JPopupMenu();

    /**
     * KeyPlugin <br>
     * The Constructor to build with a parame with the Class JTable. <br>
     * Chinese Descriptions: <br>
     * KetPlugin的构造函数。
     * 
     * @param in
     *            JTable
     * @see javaw.swing.JTable
     */
    public KeyPlugin(JTable in)
    {
        super();
        table = in;
        if (table.getModel() instanceof DefaultTableModel)
        {
            model = 0;
        }
        if (table.getModel() instanceof AbstractCustomTableModel)
        {
            model = 1;
        }
        init();

    }

    protected void init()
    {
        table.getInputMap(table.WHEN_FOCUSED).put(copy, "copy");
        table.getInputMap(table.WHEN_FOCUSED).put(paste, "paste");
        table.getInputMap(table.WHEN_FOCUSED).put(cut, "cut");
        AbstractAction act = new AbstractAction("copy")
        {
            public void actionPerformed(ActionEvent evt)
            {
                operate(Copy);
            }
        };
        AbstractAction act2 = new AbstractAction("paste")
        {
            public void actionPerformed(ActionEvent evt)
            {
                paste();
            }
        };
        AbstractAction act3 = new AbstractAction("paste")
        {
            public void actionPerformed(ActionEvent evt)
            {
                operate(Cut);
            }
        };
        table.getActionMap().put("cut", act3);
        table.getActionMap().put("paste", act2);
        table.getActionMap().put("copy", act);
        // Use the Local String to add Item.
        Resource.createMenuItem(menu, "ColumnRowCellOperator.mtmAppendRow",
                "OperatorMenu.appendRow_mnemonic",
                "OperatorMenu.appendRow_accessdesp", new AbstractAction(
                        "插入一行")
                {
                    public void actionPerformed(ActionEvent evt)
                    {
                        rowOperate(true);
                    }
                });

        Resource.createMenuItem(menu, "ColumnRowCellOperator.mtmInsertRow",
                "OperatorMenu.addRow_mnemonic",
                "OperatorMenu.addRow_accessdesp",
                new AbstractAction("添加一行")
                {
                    public void actionPerformed(ActionEvent evt)
                    {
                        rowOperate(false);
                    }
                });
        table.addMouseListener(this);

    }

    /**
     * operate <br>
     * The real method to copy and cut the value of table.Considering that maybe
     * the Object in Table cell is not a String type.So we use the common way to
     * get the String type of the value: <br>
     * Chinese Descriptions: <br>
     * 真正实现复制和复制表格里面值的方法,考虑到表里面的值不一定都是String,所以用通用的方法获得String风格的值.
     * 操作的动作取决于Model变量。
     * 
     * <p>
     * 
     * @param Model
     *            The param deside whether the value in cell to be cut.
     * @see java.awt.datatransfer.StringSelection
     * @see java.awt.datatransfer.datatransfer
     */

    protected void operate(int Model)
    {
        int[] rows = table.getSelectedRows();
        int[] columns = table.getSelectedColumns();
        int i = 0;
        final StringBuffer tempData = new StringBuffer();
        /**
         * Data of copy format. Well,I saw a article about Excel data format.So
         * I consider support the data exchanging each other.So I use the format
         * of "\t" on each cell.<br>
         * Chinese Descriptions:<br>
         * 我看过一遍文章关于Excel数据的格式,为了用户的便利,决定支持两者之间动态交互数据,
         * 所以采取相同的格式.
         * <p>
         */
        for (; i < rows.length; i++)
        {
            int j = 0;
            for (; j < columns.length; j++)
            {
                final Object obj = table.getModel().getValueAt(rows[i],
                        columns[j]);
                if (Model == Cut)
                {
                    table.getModel().setValueAt("", rows[i], columns[j]);
                }
                tempData.append(obj.toString());
                tempData.append("\t");
            }
        }
        final Clipboard system = Toolkit.getDefaultToolkit()
                .getSystemClipboard();
        system.setContents(new StringSelection(tempData.toString()), null);
    }

    /**
     * paste <br>
     * The method to patse the data in to Table. <br>
     * Chinese Descriptions: <br>
     * 把数据粘贴到Table的方法.
     * 
     * @see javax.swing.table.DefaultTableColumnModel
     * @see AbstractCustomTableModel
     * @see #copy()
     */
    protected void paste()
    {
        final Clipboard system = Toolkit.getDefaultToolkit()
                .getSystemClipboard();
        String content = null;
        final Transferable tran = system.getContents(null);
        if (tran.isDataFlavorSupported(DataFlavor.stringFlavor))
        {
            try
            {
                content = (String) tran
                        .getTransferData(DataFlavor.stringFlavor);
            } catch (IOException ex)
            {
            } catch (UnsupportedFlavorException ex)
            {
            }
        }
        int[] columns = table.getSelectedColumns();
        int[] rows = table.getSelectedRows();
        int all = columns.length * rows.length; // To sum all cells be selected.
        if (content != null)
        {
            String[] list = content.split("\t");
            int length = list.length;
            for (int i = 0, count = 0; i < rows.length; i++)
            {
                int j = 0;
                for (; j < columns.length; count++, j++)
                {
                    String value = null;
                    if (count + 1 < all)
                    {
                        value = list[count];
                    } else
                    {
                        value = "";
                        while (count < length)
                        {
                            value = value + list[count++];
                        }
                    }
                    table.getModel().setValueAt(value, rows[i], columns[j]);
                }
            }
        }
    }

    /**
     * rowOperate <br>
     * This is used to add a row.We support the DefaultTableModel and
     * AbstractCustomTableModel.The param Front is to hint that the user wang to
     * insert or append a row. <br>
     * Chinese Descriptions: <br>
     * 这个方法用来添加一行,我们支持DefaultTableModel和AbstractCustomTableModel两种Model.参数Front来判断用户是插入还是添加一行数据.
     * 
     * @param Front
     *            boolean
     */
    protected void rowOperate(boolean Front)
    {
        int[] rows = table.getSelectedRows();
        int last = table.getRowCount() - 1;
        int frist = last;
        if (rows.length != 0)
        {
            last = rows[rows.length - 1] + 1;
            frist = rows[0];
        }
        if (model == 0)
        {
            final DefaultTableModel tempModel = (DefaultTableModel) table
                    .getModel();
            tempModel.insertRow(Front ? frist : last, new Vector());
        } else
        {
            final AbstractCustomTableModel tempModel = (AbstractCustomTableModel) table
                    .getModel();
            tempModel.insertRow(Front ? frist : last, new Vector());
            /**
             * Special Object to be added in to Model.And it's rewrite the
             * method. We set a example of Vector to add.
             */
        }

    }

    /**
     * These several methods implements form MouseListener.The
     * mousePressed(MouseEvent e) and mouseReleased(MouseEvent e) are used to
     * popup the menu and show Items to be selected. <br>
     * Chinese Descriptions: <br>
     * 这几个方法是由MouseListener继承下来,mousePressed(MouseEvent e)
     * 和mouseReleased(MouseEvent e) 为了弹出菜单.
     * 
     * @see MouseListener
     * @param e
     *            MouseEvent
     */
    public void mouseClicked(MouseEvent e)
    {
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
        if (e.isPopupTrigger())
        {
            menu.show(table, e.getX(), e.getY());
        }
    }

    public void mouseReleased(MouseEvent e)
    {
        if (e.isPopupTrigger())
        {
            menu.show(table, e.getX(), e.getY());
        }
    }

}