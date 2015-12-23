/**
 * Copyright reserved by Tellabs Communication Corp. LTD.
 * The file org.jgraph.hello.NetworkGraphModel.java is created on 2008-5-4
 */
package org.jgraph.hello;

import java.util.Iterator;
import java.util.Map;

import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoableEdit;

import org.jgraph.JGraph;
import org.jgraph.event.GraphModelListener;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.ConnectionSet;
import org.jgraph.graph.ExecutableChange;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.ParentMap;


/**
 *
 */
public class NetworkGraphModel implements GraphModel{

    public boolean acceptsSource(Object obj, Object obj1) {

        return false;
    }

    public boolean acceptsTarget(Object obj, Object obj1) {

        return false;
    }

    public void addGraphModelListener(GraphModelListener graphmodellistener) {

    }

    public void addUndoableEditListener(UndoableEditListener undoableeditlistener) {

    }

    public void beginUpdate() {

    }

    public Map cloneCells(Object[] aobj) {

        return null;
    }

    public boolean contains(Object obj) {

        return false;
    }

    public Iterator edges(Object obj) {

        return null;
    }

    public void edit(Map map, ConnectionSet connectionset, ParentMap parentmap, UndoableEdit[] aundoableedit) {

    }

    public void endUpdate() {

    }

    public void execute(ExecutableChange executablechange) {

    }

    public AttributeMap getAttributes(Object obj) {

        return null;
    }

    public Object getChild(Object obj, int i) {

        return null;
    }

    public int getChildCount(Object obj) {

        return 0;
    }

    public int getIndexOfChild(Object obj, Object obj1) {

        return 0;
    }

    public int getIndexOfRoot(Object obj) {

        return 0;
    }

    public Object getParent(Object obj) {

        return null;
    }

    public Object getRootAt(int i) {

        return null;
    }

    public int getRootCount() {

        return 0;
    }

    public Object getSource(Object obj) {

        return null;
    }

    public Object getTarget(Object obj) {

        return null;
    }

    public Object getValue(Object obj) {

        return null;
    }

    public void insert(Object[] aobj, Map map, ConnectionSet connectionset, ParentMap parentmap, UndoableEdit[] aundoableedit) {

    }

    public boolean isEdge(Object obj) {

        return false;
    }

    public boolean isLeaf(Object obj) {

        return false;
    }

    public boolean isPort(Object obj) {

        return false;
    }

    public void remove(Object[] aobj) {

    }

    public void removeGraphModelListener(GraphModelListener graphmodellistener) {

    }

    public void removeUndoableEditListener(UndoableEditListener undoableeditlistener) {

    }

    public void toBack(Object[] aobj) {

    }

    public void toFront(Object[] aobj) {

    }

    public Object valueForCellChanged(Object obj, Object obj1) {

        return null;
    }
    

}
