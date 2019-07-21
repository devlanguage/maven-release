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
 *          ����һ���ǳ��������ΪJTable��ӵ�Ԫ��ĸ���,����,ճ��,���е����,���빦��.�����
 *          �����˿�����Ա�Ĵ�����,ֻ��Ҫһ��"new new KeyPlugin(table);".
 */
public class KeyPlugin implements MouseListener
{

    /**
     * table <br>
     * The table will be set up. <br>
     * Chinese Descriptions: <br>
     * �������JTable.
     */
    JTable table;
    /**
     * copy <br>
     * The Key register the Action "copy". <br>
     * Chinese Descriptions: <br>
     * ע��"����"�¼��ļ����¼�.
     * 
     * <p>
     */
    KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C,
            ActionEvent.CTRL_MASK, false);;
    /**
     * paste <br>
     * The Key register the Action "paste". <br>
     * Chinese Descriptions: <br>
     * ע��"ճ��"�¼��ļ����¼�.
     * 
     * <p>
     */
    KeyStroke paste = KeyStroke.getKeyStroke(KeyEvent.VK_V,
            ActionEvent.CTRL_MASK, false);
    /**
     * paste <br>
     * The Key register the Action "cut". <br>
     * Chinese Descriptions: <br>
     * ע��"����"�¼��ļ����¼�.
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
     * ����֧��DefaultTableModel��AbstractCustomTableModel��������ġ�ճ�������������ȫ�ֱ��������ж�������ģʽ��
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
     * �����Ĳ˵�֧�ֲ���һ�С�
     * 
     * @see javax.swing.JPopupMenu
     *      <p>
     */
    private JPopupMenu menu = new JPopupMenu();

    /**
     * KeyPlugin <br>
     * The Constructor to build with a parame with the Class JTable. <br>
     * Chinese Descriptions: <br>
     * KetPlugin�Ĺ��캯����
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
                        "����һ��")
                {
                    public void actionPerformed(ActionEvent evt)
                    {
                        rowOperate(true);
                    }
                });

        Resource.createMenuItem(menu, "ColumnRowCellOperator.mtmInsertRow",
                "OperatorMenu.addRow_mnemonic",
                "OperatorMenu.addRow_accessdesp",
                new AbstractAction("���һ��")
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
     * ����ʵ�ָ��ƺ͸��Ʊ������ֵ�ķ���,���ǵ��������ֵ��һ������String,������ͨ�õķ������String����ֵ.
     * �����Ķ���ȡ����Model������
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
         * �ҿ���һ�����¹���Excel���ݵĸ�ʽ,Ϊ���û��ı���,����֧������֮�䶯̬��������,
         * ���Բ�ȡ��ͬ�ĸ�ʽ.
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
     * ������ճ����Table�ķ���.
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
     * ��������������һ��,����֧��DefaultTableModel��AbstractCustomTableModel����Model.����Front���ж��û��ǲ��뻹�����һ������.
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
     * �⼸����������MouseListener�̳�����,mousePressed(MouseEvent e)
     * ��mouseReleased(MouseEvent e) Ϊ�˵����˵�.
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