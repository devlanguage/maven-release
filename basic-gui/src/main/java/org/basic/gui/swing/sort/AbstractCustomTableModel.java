package org.basic.gui.swing.sort;

import javax.swing.table.*;

/**
 * 
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
 */
public abstract class AbstractCustomTableModel extends AbstractTableModel
{

    /**
     * insertColumn <br>
     * add a column with the specific column name and data to the specific index
     * 
     * @param i
     *            location to add the column
     * @param columnName
     *            the column name of the column to add.
     * @param columnData
     *            the data of the column to add <br>
     *            Chinese Description: <br>
     *            把设定了列名和列数据的一列加到指定位置
     * @param i
     *            添加后列在表中的位置
     * @param columnName
     *            要添加的列的列名.
     * @param columnData
     *            要添加的列的列名，以一维数组中存储
     */
    public abstract void insertColumn(int i, Object columnName,
            Object[] columnData);

    /**
     * insertRow <br>
     * add a row with the specific data to the specific index
     * 
     * @param i
     *            location to add the row
     * @param aRow
     *            the data <br>
     *            Chinese Description: 把设定了数据的一行加到指定位置
     * @param i
     *            添加后行在表中的位置
     * @param aRow
     *            行数据
     */
    public abstract void insertRow(int i, Object aRow);
}