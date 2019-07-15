package org.basic.gui.swing.sort;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TestSortTable extends JPanel
{
    public TestSortTable()

    {
        init();
    }

    private void init()

    {
        setLayout(new BorderLayout());
        JTable table = new JTable();
        Object[][] obj = new Object[][] {
                { "asdas", "12321", "3as3", "eqw22" },
                { "das2", "asd", "qweqw", "as23" },
                { "aere", "r32re", "ewfse", "werew3" } };
        DefaultTableModel model = new DefaultTableModel(obj, new Object[] {
                "a", "b", "c", "d" });
        table.setModel(model);
        new SortManager(table, 0);
        JScrollPane scrPane = new JScrollPane(table);
        JButton addBtn = new JButton("������");
        addBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

            }
        });
        JPanel btnPane = new JPanel();
        btnPane.add(addBtn);

        add(scrPane);
        add("South", btnPane);
    }

    public static void main(String[] args)

    {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new TestSortTable());
        frame.pack();
        frame.setVisible(true);
    }

}