package org.basic.jdk.core.pattern.structural.Composite.test1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * 合成模式：合成模式将对象组织到树结构中，可以用来描述整体与部分的关系。合成模式就是一个处理对象的树结构的模式。合成模式把部分与整体的关系用树结构
 * 表示出来。合成模式使得客户端把一个个单独的成分对象和由他们复合而成的合成对象同等看待。
 * 
 * 
 */
public class JxFrameTester extends JFrame {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -337280512502819830L;
    JScrollPane jScrollPane1;
    JScrollPane jScrollPane2;
    JPanel treeContentPanel;
    JTree jTree;
    DefaultMutableTreeNode rootNode;
    JTextArea cost;

    EmployeeTree employeeTree = new EmployeeTree();

    public JxFrameTester(String title) {

        super(title);
        initGUI();
    }

    private void initGUI() {

        getContentPane().add(treeContentPanel = new JPanel());
        treeContentPanel.setLayout(new BorderLayout());

        treeContentPanel.add(BorderLayout.WEST, jScrollPane1 = new JScrollPane());
        // treeContentPanel.add(BorderLayout.SOUTH, cost = new JLabel(" "));
        treeContentPanel.add(BorderLayout.CENTER, jScrollPane2 = new JScrollPane());

        treeContentPanel.setBorder(new BevelBorder(BevelBorder.RAISED));

        jTree = new JTree(rootNode = new DefaultMutableTreeNode(employeeTree.getBossRootNode()
                .getName()));

        /* Put the Tree in a scroller. */

        jScrollPane1.getViewport().add(jTree);
        jScrollPane2.getViewport().add(cost = new JTextArea("d", 10, 50));
        loadTree(rootNode);
    }

    // ------------------------------------
    public void loadTree(final DefaultMutableTreeNode troot) {

        // DefaultMutableTreeNode troot = new DefaultMutableTreeNode(topDog.getName());
        // treeContentPanel.remove(jTree = new JTree(troot));
        jTree.addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent evt) {

                TreePath path = evt.getPath();
                String selectedTerm = path.getLastPathComponent().toString();

                Employee emp = employeeTree.getBossRootNode().searchChild(selectedTerm);
                if (emp != null)
                    cost.setText(new Float(emp.getSalaries()).toString());
            }

        });

        addNodes(troot, employeeTree.getBossRootNode());
        jTree.expandRow(0);

        // repaint();
    }

    // --------------------------------------
    private void addNodes(DefaultMutableTreeNode pnode, Employee emp) {

        DefaultMutableTreeNode node;

        Enumeration e = emp.elements();
        while (e.hasMoreElements()) {
            Employee newEmp = (Employee) e.nextElement();
            node = new DefaultMutableTreeNode(newEmp.getName());
            pnode.add(node);
            addNodes(node, newEmp);
        }
    }

    // ------------------------------------------
    public static void main(String[] args) {

        // Force SwingApp to come up in the System L&F
        String laf = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(laf);
        } catch (UnsupportedLookAndFeelException exc) {
            System.err.println("Warning: UnsupportedLookAndFeel: " + laf);
        } catch (Exception exc) {
            System.err.println("Error loading " + laf + ": " + exc);
        }

        JxFrameTester testerUI = new JxFrameTester("Struct Type: composite");
        testerUI.setSize(new Dimension(200, 300));
        testerUI.setVisible(true);
        testerUI.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                System.exit(0);
            }
        });

    }
}