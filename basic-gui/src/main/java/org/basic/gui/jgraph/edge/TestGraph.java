package org.basic.gui.jgraph.edge;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JApplet;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import org.eclipse.draw2d.graph.DirectedGraph;
import org.eclipse.draw2d.graph.DirectedGraphLayout;
import org.eclipse.draw2d.graph.Edge;
import org.eclipse.draw2d.graph.Node;
import org.jgraph.JGraph;
import org.jgraph.graph.ConnectionSet;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;

//import com.realpersist.gef.layout.NodeJoiningDirectedGraphLayout;

/**
 * <pre>
 * 背景： 
 * jGraph具有相当高的交互性和自动化，是一套为图定做的组件。其主要用途是在一些需要表示图结构的应用中，比如流程图、UML、交通线路、网络等等。jGraph主要包括以下一些产品： 
 *         JGraph - The Java Open Source Graph Drawing Component    ( Open Source ) 
 *         JGraph Layout Pro - The Java Graph Layout Solution 
 *        JGraphpad Pro Diagram Editor Framework 
 *     我们选择jGraph来画有向图， jGraph Layout Pro是一个对图进行布局的软件，可以提供自动布局的功能，但是目前要收费的，为了解决这个问题，我们引入了draw2d的一个算法。 
 * 
 * 大体思路： 
 * 在构建有向图的时候，同时构建两幅图：一幅是jGraph图，另一幅是draw2d的gefGraph（两幅图节点和边的数据都是一样的并且通过HashMap关联起来），利用draw2d提供的算法计算自动布局后得到的X,Y坐标然后指定jGrahp图的节点同样的坐标，实际上只画jGraph的图。 
 * 
 * 遇到问题： 
 * 1、 当有向图产生回路的时候，自动布局算法可能会报错。 
 * 2、 当这个图形不是一个整体而是好几个部分的时候，自动布局算法会报错。 
 * 
 * 解决方案： 
 * 问题1，当有指向自身边的时候只是在jGraph图中添加这一条边，在gefGraph中不加这条边，这样这条边还是可以画出来，有一种特殊情况如果某个节点只有一条指向自己的边而和其他任何节点没有联系的话在gefGraph图中就不存在该节点，当然也就不能给出该节点自动布局的坐标，这个时候我们可以手工给出这些节点的坐标，为了不和自动算出来的坐标重合而难看，我们可以让出上端的一定高度，专门留给这些特殊节点，自动布局的节点的Y坐标向下顺延。 
 * 问题2，可以选取另一种布局算法，新的布局算法是原有布局算法的子类，对于非连通的有向图，它会自动添加一些虚拟边使其连通然后计算自动布局坐标，然后再去除虚拟边。对于连通图，使用原有布局算法效果要好于使用后者，但是前者不能计算非连通的有向图，所以我们的原则是：首先尝试使用第一种布局算法，如果报错我们捕获这个异常然后调用第二种布局算法。 
 * 
 * 代码示例（全部源代码附件中有下载）： 
 * 需要的jar包（全部jar包附件中有下载）： 
 * jgraph.jar  draw2d.jar  schemaeditor.jar（这个包里面有针对问题2的新的布局算法）
 * </pre>
 */
public class TestGraph extends JApplet {

    public Map gefNodeMap = null;

    public Map graphNodeMap = null;

    public List edgeList = null;

    DirectedGraph directedGraph = null;

    JGraph graph = null;

    public TestGraph() {

    }

    public void init() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        graphInit();

        paintGraph();

    }

    private void paintGraph() {

        try {

            // 测试数据
            NodeBean a1 = new NodeBean("a1");
            NodeBean a11 = new NodeBean("a11");
            NodeBean a12 = new NodeBean("a12");
            NodeBean a13 = new NodeBean("a13");
            NodeBean a121 = new NodeBean("a121");
            NodeBean a122 = new NodeBean("a122");
            NodeBean a123 = new NodeBean("a123");
            NodeBean a1231 = new NodeBean("a1231");
            NodeBean a1232 = new NodeBean("a1232");
            NodeBean a1233 = new NodeBean("a1233");
            NodeBean a1234 = new NodeBean("a1234");

            NodeBean b1 = new NodeBean("b1");
            NodeBean b2 = new NodeBean("b2");
            NodeBean c = new NodeBean("c");

            EdgeBean as1 = new EdgeBean(a1, a11, new Long(20));
            EdgeBean as2 = new EdgeBean(a1, a12, new Long(20));
            EdgeBean as3 = new EdgeBean(a1, a13, new Long(20));
            EdgeBean as4 = new EdgeBean(a12, a121, new Long(20));
            EdgeBean as5 = new EdgeBean(a12, a122, new Long(20));
            EdgeBean as6 = new EdgeBean(a12, a123, new Long(20));
            EdgeBean as7 = new EdgeBean(a123, a1231, new Long(20));
            EdgeBean as8 = new EdgeBean(a123, a1232, new Long(20));
            EdgeBean as9 = new EdgeBean(a123, a1233, new Long(20));
            EdgeBean as10 = new EdgeBean(a123, a1234, new Long(20));
            EdgeBean as11 = new EdgeBean(a1232, a11, new Long(20));
            EdgeBean as12 = new EdgeBean(a1233, a122, new Long(20));
            EdgeBean as13 = new EdgeBean(a123, a1, new Long(20));
            // 这一条边和其他边没有连通
            EdgeBean as14 = new EdgeBean(b1, b2, new Long(20));
            // 这一条边和其他边没有连通且指向自身
            EdgeBean as15 = new EdgeBean(c, c, new Long(20));

            List edgeBeanList = new ArrayList();

            edgeBeanList.add(as1);
            edgeBeanList.add(as2);
            edgeBeanList.add(as3);
            edgeBeanList.add(as4);
            edgeBeanList.add(as5);
            edgeBeanList.add(as6);
            edgeBeanList.add(as7);
            edgeBeanList.add(as8);
            edgeBeanList.add(as9);
            edgeBeanList.add(as10);
            edgeBeanList.add(as11);
            edgeBeanList.add(as12);
            edgeBeanList.add(as13);
            edgeBeanList.add(as14);
            edgeBeanList.add(as15);

            // 解析数据，构造图
            gefNodeMap = new HashMap();
            graphNodeMap = new HashMap();
            edgeList = new ArrayList();
            directedGraph = new DirectedGraph();
            GraphModel model = new DefaultGraphModel();
            graph.setModel(model);
            Map attributes = new Hashtable();
            // Set Arrow
            Map edgeAttrib = new Hashtable();
            GraphConstants.setLineEnd(edgeAttrib, GraphConstants.ARROW_CLASSIC);
            GraphConstants.setEndFill(edgeAttrib, true);
            graph.setJumpToDefaultPort(true);

            if (edgeBeanList == null || edgeBeanList.size() == 0) {
                graph.repaint();
                return;
            }
            Iterator edgeBeanIt = edgeBeanList.iterator();
            while (edgeBeanIt.hasNext()) {
                EdgeBean edgeBean = (EdgeBean) edgeBeanIt.next();
                NodeBean sourceAction = edgeBean.getsourceNodeBean();
                NodeBean targetAction = edgeBean.gettargetNodeBean();
                Long ageLong = edgeBean.getStatCount();
                String edgeString = "(" + ageLong + ")";
                addEdge(sourceAction, targetAction, 20, edgeString);
            }

            // 自动布局 首先用DirectedGraphLayout如果出现异常（图不是整体连通的）则采用NodeJoiningDirectedGraphLayout
            // 后者可以对非连通图进行布局坐标计算，但效果不如前者好，所以首选前者，当前者不可以处理时采用后者
            try {
                new DirectedGraphLayout().visit(directedGraph);
            } catch (Exception e1) {
//                new NodeJoiningDirectedGraphLayout().visit(directedGraph);
            }

            int self_x = 50;
            int self_y = 50;
            int base_y = 10;
            if (graphNodeMap != null && gefNodeMap != null && graphNodeMap.size() > gefNodeMap.size()) {
                base_y = self_y + GraphProp.NODE_HEIGHT;
            }

            // 向图中添加节点node
            Collection nodeCollection = graphNodeMap.values();
            if (nodeCollection != null) {
                Iterator nodeIterator = nodeCollection.iterator();
                if (nodeIterator != null) {
                    while (nodeIterator.hasNext()) {
                        DefaultGraphCell node = (DefaultGraphCell) nodeIterator.next();
                        NodeBean userObject = (NodeBean) node.getUserObject();
                        if (userObject == null) {
                            continue;
                        }
                        Node gefNode = (Node) gefNodeMap.get(userObject);
                        if (gefNode == null) {
                            // 这是因为当一个节点有一个指向自身的边的时候，我们在gefGraph中并没有计算这条边（gefGraph不能计算包含指向自己边的布局），
                            // 所以当在所要画的图中该节点只有一条指向自身的边的时候，我们在gefNodeMap中就找不到相应节点了
                            // 这个时候，我们手工给出该节点的 X,Y 坐标
                            gefNode = new Node();
                            gefNode.x = self_x;
                            gefNode.y = self_y - base_y;
                            self_x += (10 + GraphProp.NODE_WIDTH);
                        }
                        Map nodeAttrib = new Hashtable();
                        GraphConstants.setBorderColor(nodeAttrib, Color.black);
                        Rectangle2D Bounds = new Rectangle2D.Double(gefNode.x, gefNode.y + base_y,
                                GraphProp.NODE_WIDTH, GraphProp.NODE_HEIGHT);
                        GraphConstants.setBounds(nodeAttrib, Bounds);
                        attributes.put(node, nodeAttrib);
                    }// while
                }
            }

            // 向图中添加边
            if (edgeList == null) {
                // logger.error("edge list is null");
                return;
            }
            for (int i = 0; i < edgeList.size(); i++) {
                UnionEdge unionEdge = (UnionEdge) edgeList.get(i);
                if (unionEdge == null) {
                    // logger.error("union edge is null");
                    continue;
                }
                ConnectionSet cs = new ConnectionSet(unionEdge.getEdge(), unionEdge.getSourceNode().getChildAt(0),
                        unionEdge.getTargetNode().getChildAt(0));
                Object[] cells = new Object[] { unionEdge.getEdge(), unionEdge.getSourceNode(),
                        unionEdge.getTargetNode() };
                attributes.put(unionEdge.getEdge(), edgeAttrib);
                model.insert(cells, attributes, cs, null, null);
            }

            graph.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void graphInit() {

        // Construct Model and Graph
        GraphModel model = new DefaultGraphModel();
        graph = new JGraph(model);
        graph.setSelectionEnabled(true);

        // 显示applet
        JScrollPane scroll = new JScrollPane(graph);
        this.getContentPane().add(scroll);

        this.setSize(new Dimension(800, 800));

    }

    /**
     * @param source
     * @param target
     */
    private void addEdge(NodeBean source, NodeBean target, int weight, String edgeString) {

        if (source == null || target == null) {
            return;
        }
        if (gefNodeMap == null) {
            gefNodeMap = new HashMap();
        }
        if (graphNodeMap == null) {
            graphNodeMap = new HashMap();
        }
        if (edgeList == null) {
            edgeList = new ArrayList();
        }
        if (directedGraph == null) {
            directedGraph = new DirectedGraph();
        }

        // 建立GEF的 node edge将来用来计算graph node的layout
        addEdgeGef(source, target, weight, edgeString);

        // 建立真正要用的graph的 node edge
        DefaultGraphCell sourceNode = null;
        DefaultGraphCell targetNode = null;
        sourceNode = (DefaultGraphCell) graphNodeMap.get(source);
        if (sourceNode == null) {
            sourceNode = new DefaultGraphCell(source);
            sourceNode.addPort();
            graphNodeMap.put(source, sourceNode);
        }
        targetNode = (DefaultGraphCell) graphNodeMap.get(target);
        if (targetNode == null) {
            targetNode = new DefaultGraphCell(target);
            targetNode.addPort();
            graphNodeMap.put(target, targetNode);
        }
        DefaultEdge edge = new DefaultEdge(edgeString);
        UnionEdge unionEdge = new UnionEdge();
        unionEdge.setEdge(edge);
        unionEdge.setSourceNode(sourceNode);
        unionEdge.setTargetNode(targetNode);

        edgeList.add(unionEdge);

    }

    /**
     * @param source
     * @param target
     * @param weight
     * @param edgeString
     */
    private void addEdgeGef(NodeBean source, NodeBean target, int weight, String edgeString) {

        if (source.equals(target)) {
            return;
        }
        // 建立GEF的 node edge将来用来计算graph node的layout
        Node gefSourceNode = null;
        Node gefTargetNode = null;
        gefSourceNode = (Node) gefNodeMap.get(source);
        if (gefSourceNode == null) {
            gefSourceNode = new Node();
            gefSourceNode.width = GraphProp.NODE_WIDTH;
            gefSourceNode.height = GraphProp.NODE_WIDTH;
            // gefSourceNode.setPadding(new Insets(GraphProp.NODE_TOP_PAD,GraphProp.NODE_LEFT_PAD,
            // GraphProp.NODE_BOTTOM_PAD,GraphProp.NODE_RIGHT_PAD));
            directedGraph.nodes.add(gefSourceNode);
            gefNodeMap.put(source, gefSourceNode);
        }

        gefTargetNode = (Node) gefNodeMap.get(target);
        if (gefTargetNode == null) {
            gefTargetNode = new Node();
            gefTargetNode.width = GraphProp.NODE_WIDTH;
            gefTargetNode.height = GraphProp.NODE_WIDTH;
            // gefTargetNode.setPadding(new Insets(GraphProp.NODE_TOP_PAD,GraphProp.NODE_LEFT_PAD,
            // GraphProp.NODE_BOTTOM_PAD,GraphProp.NODE_RIGHT_PAD));
            directedGraph.nodes.add(gefTargetNode);
            gefNodeMap.put(target, gefTargetNode);
        }

        Edge gefEdge1 = null;
        try {
            gefEdge1 = new Edge(gefSourceNode, gefTargetNode);
            gefEdge1.weight = weight;
            directedGraph.edges.add(gefEdge1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}