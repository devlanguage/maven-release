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



public class TestColumnAddTable extends JPanel {
    JTable table;
    ColumnRowCellOperator colRowCellOperator;
    public TestColumnAddTable()
    
 {
        init();
    }


    private void init()
    
 {
        setLayout(new BorderLayout());
        table = new JTable();
        Object[][] obj=new Object[][]{{"达安","12321","3as3","eqw22"},{"安达","asd","qweqw","as23"},{"信","r32re","ewfse","werew3"}};
        DefaultTableModel model=new DefaultTableModel(obj,new Object[]{"a","b","c","d"});
        table.setModel(model);
        new SortManager(table, 0);
     colRowCellOperator=new ColumnRowCellOperator(table);
        JScrollPane scrPane = new JScrollPane(table);
        JButton addColFirst = new JButton("第一列前新增列");
        JButton addColLast = new JButton("最后列后新增列");

        addColLast.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //colRowCellOperator.insertColumn(table.getColumnCount());
            }

        });
        addColFirst.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               // colRowCellOperator.insertColumn(0);
            }
        });


        JPanel btnPane = new JPanel();
        btnPane.add(addColFirst);
        btnPane.add(addColLast);
        add(scrPane);
        add("South", btnPane);
    }


    public static void main(String[] args)
    
 {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new TestColumnAddTable());
        frame.pack();
        frame.setVisible(true);
    }



}
