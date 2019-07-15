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
     *            ���趨�������������ݵ�һ�мӵ�ָ��λ��
     * @param i
     *            ��Ӻ����ڱ��е�λ��
     * @param columnName
     *            Ҫ��ӵ��е�����.
     * @param columnData
     *            Ҫ��ӵ��е���������һά�����д洢
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
     *            Chinese Description: ���趨�����ݵ�һ�мӵ�ָ��λ��
     * @param i
     *            ��Ӻ����ڱ��е�λ��
     * @param aRow
     *            ������
     */
    public abstract void insertRow(int i, Object aRow);
}