package org.jgraph.hello;

import java.awt.Color;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.ConnectionSet;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.hello.style.CellStyle;

public class HelloMainWindow extends JFrame {

    LayoutManager lm = null;

    HelloMainWindow() {

        init();

    }

    @SuppressWarnings("unchecked")
    void init() {

        this.setMainMenu();
        this.setGlobalAttributes();
        this.setGlobalEvents();

        DefaultGraphModel graphmodel = new DefaultGraphModel();
        JGraph jgraph = new JGraph(graphmodel);

        // 定义Edge的外观
        Map map = new Hashtable();
        GraphConstants.setLineEnd(map, GraphConstants.ARROW_CLASSIC);
        GraphConstants.setEndFill(map, true);
        GraphConstants.setLabelAlongEdge(map, true);
        jgraph.getAttributes(null).applyMap(map);

        org.jgraph.graph.ConnectionSet connectionset = new ConnectionSet();
        Map<GraphCell, Map> graphCellMap = new Hashtable<GraphCell, Map>();

        org.jgraph.graph.AttributeMap cellStyle1 = new AttributeMap();
        // org.jgraph.graph.GraphConstants.setLineBegin(attributemap, 2);
        cellStyle1.put(GraphConstants.LINEBEGIN, Integer.valueOf(2));
        // org.jgraph.graph.GraphConstants.setBeginSize(attributemap, 10);
        map.put("beginSize", new Integer(10));
//        org.jgraph.graph.GraphConstants.setDashPattern(attributemap, new float[] { 3F, 3F });
        map.put(GraphConstants.DASHPATTERN, new float[]{3F, 4F});
        if (org.jgraph.graph.GraphConstants.DEFAULTFONT != null) {
            org.jgraph.graph.GraphConstants.setFont(cellStyle1,
                    org.jgraph.graph.GraphConstants.DEFAULTFONT.deriveFont(10));
        }
        
        CellStyle cellStyle2 = new CellStyle();        
        cellStyle2.setLineBegin(2);        
        cellStyle2.setBeginFill(true);
        cellStyle2.setBeginSize(10);

        CellStyle cellStyle3 = new CellStyle();
        cellStyle3.setBorderColor(Color.YELLOW);
        cellStyle3.setBorder(BorderFactory.createTitledBorder("cellStyle3"));
        cellStyle3.setLineBegin(9);
        cellStyle3.setBeginFill(true);
        cellStyle3.setBeginSize(6);
        cellStyle3.setLineEnd(4);
        cellStyle3.setLabelPosition(new java.awt.geom.Point2D.Double(500D, 0.0D));        

        // Model Group cell list, blue,
        org.jgraph.graph.DefaultGraphCell graphModelCell = new DefaultGraphCell("GraphModel");
        graphCellMap.put(graphModelCell, org.jgraph.JGraph.createBounds(new AttributeMap(), 20,
                100, java.awt.Color.blue));
        graphModelCell.addPort(null, "GraphModel/Center");

        org.jgraph.graph.DefaultGraphCell defaultGraphModelCell = new DefaultGraphCell(
                "DefaultGraphModel");
        graphCellMap.put(defaultGraphModelCell, org.jgraph.JGraph.createBounds(new AttributeMap(),
                20, 180, java.awt.Color.blue));
        defaultGraphModelCell.addPort(null, "DefaultGraphModel/Center");

        org.jgraph.graph.DefaultEdge implementsEdge = new DefaultEdge("implements");
        connectionset.connect(implementsEdge, graphModelCell.getChildAt(0), defaultGraphModelCell
                .getChildAt(0));
        graphCellMap.put(implementsEdge, cellStyle1);

        // /Group them into one
        org.jgraph.graph.DefaultGraphCell modelGroupCells = new DefaultGraphCell("ModelGroup");
        modelGroupCells.add(graphModelCell);
        modelGroupCells.add(defaultGraphModelCell);
        modelGroupCells.add(implementsEdge);

        // Component and JGraph cell list, green
        org.jgraph.graph.DefaultGraphCell jComponentGraphCell = new DefaultGraphCell("JComponent");
        graphCellMap.put(jComponentGraphCell, org.jgraph.JGraph.createBounds(new AttributeMap(),
                180, 20, java.awt.Color.green));
        jComponentGraphCell.addPort(null, "JComponent/Center");

        org.jgraph.graph.DefaultGraphCell jGraphGraphCell = new DefaultGraphCell("JGraph");
        graphCellMap.put(jGraphGraphCell, org.jgraph.JGraph.createBounds(new AttributeMap(), 180,
                100, java.awt.Color.green));
        jGraphGraphCell.addPort(null, "JGraph/Center");

        org.jgraph.graph.DefaultEdge extendsEdge = new DefaultEdge("extends");
        connectionset.connect(extendsEdge, jComponentGraphCell.getChildAt(0), jGraphGraphCell
                .getChildAt(0));
        graphCellMap.put(extendsEdge, cellStyle2);

        // UI group cell list, red
        org.jgraph.graph.DefaultGraphCell componentUIGraphCell = new DefaultGraphCell("ComponentUI");
        graphCellMap.put(componentUIGraphCell, org.jgraph.JGraph.createBounds(new AttributeMap(),
                340, 20, java.awt.Color.red));
        componentUIGraphCell.addPort(null, "ComponentUI/Center");
        org.jgraph.graph.DefaultGraphCell defaultgraphcell6 = new DefaultGraphCell("GraphUI");
        graphCellMap.put(defaultgraphcell6, org.jgraph.JGraph.createBounds(new AttributeMap(), 340,
                100, java.awt.Color.red));
        defaultgraphcell6.addPort(null, "GraphUI/Center");
        org.jgraph.graph.DefaultGraphCell defaultgraphcell7 = new DefaultGraphCell("BasicGraphUI");
        graphCellMap.put(defaultgraphcell7, org.jgraph.JGraph.createBounds(new AttributeMap(), 340,
                180, java.awt.Color.red));
        defaultgraphcell7.addPort(null, "BasicGraphUI/Center");

        org.jgraph.graph.DefaultEdge extendsUIEdge = new DefaultEdge("extends");
        connectionset.connect(extendsUIEdge, componentUIGraphCell.getChildAt(0), defaultgraphcell6
                .getChildAt(0));
        graphCellMap.put(extendsUIEdge, cellStyle2);
        org.jgraph.graph.DefaultEdge implementsUIEdge = new DefaultEdge("implements");
        connectionset.connect(implementsUIEdge, defaultgraphcell6.getChildAt(0), defaultgraphcell7
                .getChildAt(0));
        graphCellMap.put(implementsUIEdge, cellStyle1);

        org.jgraph.graph.DefaultGraphCell uiGraphCells = new DefaultGraphCell("UIGroup");
        uiGraphCells.add(componentUIGraphCell);
        uiGraphCells.add(defaultgraphcell6);
        uiGraphCells.add(defaultgraphcell7);
        uiGraphCells.add(implementsUIEdge);
        uiGraphCells.add(extendsUIEdge);

        // Edge List
        org.jgraph.graph.DefaultEdge modelEdge = new DefaultEdge("model");
        connectionset.connect(modelEdge, jGraphGraphCell.getChildAt(0), graphModelCell
                .getChildAt(0));
        graphCellMap.put(modelEdge, cellStyle3);

        org.jgraph.graph.DefaultEdge uiEdge = new DefaultEdge("ui");
        connectionset.connect(uiEdge, jComponentGraphCell.getChildAt(0), componentUIGraphCell
                .getChildAt(0));
        graphCellMap.put(uiEdge, cellStyle3);

        java.lang.Object aobj[] = { modelEdge, uiEdge, modelGroupCells, jComponentGraphCell,
                jGraphGraphCell, extendsEdge, uiGraphCells };
        graphmodel.insert(aobj, graphCellMap, connectionset, null, null);

        Container contentPane = this.getContentPane();
        // lm = new BorderLayout();
        // this.setLayout(lm);
        contentPane.add(new JScrollPane(jgraph));

        // this.add(new JButton("Start"), BorderLayout.NORTH);

        // 定义画布的属性
        setBackground(Color.decode("#C0C0C0"));
        jgraph.setEditable(true);
        jgraph.setCloneable(true);
        jgraph.setInvokesStopCellEditing(true);
        jgraph.setJumpToDefaultPort(true);
        jgraph.setPortsVisible(true);
        jgraph.setGridEnabled(true);
        jgraph.setGridVisible(true);
        jgraph.setGridMode(JGraph.CROSS_GRID_MODE);
        jgraph.setGridColor(Color.decode("#808080"));
        jgraph.setGridSize(10);

    }

    private void setGlobalEvents() {

        this.addWindowListener(new WindowListener() {

            public void windowActivated(WindowEvent e) {

            }

            public void windowClosed(WindowEvent e) {

                System.exit(0);
            }

            public void windowClosing(WindowEvent e) {

                System.out.println(e);
            }

            public void windowDeactivated(WindowEvent e) {

            }

            public void windowDeiconified(WindowEvent e) {

            }

            public void windowIconified(WindowEvent e) {

            }

            public void windowOpened(WindowEvent e) {

                System.out.println("Window is open");

            }

        });
    }

    private void setGlobalAttributes() {

        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(1, 1, 800, 600);
        // this.pack();
        setDefaultLookAndFeelDecorated(false);
    }

    private void setMainMenu() {

        JMenuBar jmb = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        jmb.add(fileMenu);
        this.setJMenuBar(jmb);
    }
}
