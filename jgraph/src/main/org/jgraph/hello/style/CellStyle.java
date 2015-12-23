/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.jgraph.hello.style.CellStyle.java is created on 2008-5-5
 */
package org.jgraph.hello.style;

import java.util.Hashtable;
import java.util.Map;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.GraphConstants;

/**
 * 
 */
public class CellStyle extends AttributeMap {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8257011677293848521L;

    public void setFont(java.awt.Font font) {

        this.put("font", font);
    }

    public java.awt.Font getFont() {

        java.awt.Font font = (java.awt.Font) this.get("font");
        if (font == null) {
            font = GraphConstants.DEFAULTFONT;
            // if(font != null)
            // this.setFont(org.jgraph.graph.GraphConstants.DEFAULTFONT.deriveFont(10));
        }
        return font;

    }

    public final void setRemoveAttributes(java.lang.Object aobj[]) {

        this.put("removeAttributes", aobj);
    }

    public final java.lang.Object[] getRemoveAttributes() {

        return (java.lang.Object[]) this.get("removeAttributes");
    }

    public final void setMoveableAxis(int i) {

        this.put("moveableAxis", new Integer(i));
    }

    public final int getMoveableAxis() {

        java.lang.Integer integer = (java.lang.Integer) this.get("moveableAxis");
        if (integer != null) {
            return integer.intValue();
        } else {
            return 0;
        }
    }

    public final void setSizeableAxis(int i) {

        this.put("sizeableAxis", new Integer(i));
    }

    public final int getSizeableAxis() {

        java.lang.Integer integer = (java.lang.Integer) this.get("sizeableAxis");
        if (integer != null) {
            return integer.intValue();
        } else {
            return 0;
        }
    }

    public final void setIcon(javax.swing.Icon icon) {

        this.put("icon", icon);
    }

    public final javax.swing.Icon getIcon() {

        return (javax.swing.Icon) this.get("icon");
    }

    public final void setOpaque(boolean flag) {

        this.put("opaque", new Boolean(flag));
    }

    public final boolean isOpaque() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("opaque");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return false;
        }
    }

    public final void setGroupOpaque(boolean flag) {

        this.put("groupOpaque", new Boolean(flag));
    }

    public final boolean isGroupOpaque() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("groupOpaque");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return false;
        }
    }

    public final void setBorder(javax.swing.border.Border border) {

        this.put("border", border);
    }

    public final javax.swing.border.Border getBorder() {

        return (javax.swing.border.Border) this.get("border");
    }

    public final void setLineColor(java.awt.Color color) {

        this.put("linecolor", color);
    }

    public final java.awt.Color getLineColor() {

        return (java.awt.Color) this.get("linecolor");
    }

    public final void setBorderColor(java.awt.Color color) {

        this.put("bordercolor", color);
    }

    public final java.awt.Color getBorderColor() {

        return (java.awt.Color) this.get("bordercolor");
    }

    public final void setLineWidth(float f) {

        this.put("linewidth", new Float(f));
    }

    public final float getLineWidth() {

        java.lang.Float float1 = (java.lang.Float) this.get("linewidth");
        if (float1 != null) {
            return float1.floatValue();
        } else {
            return 1.0F;
        }
    }

    public final void setForeground(java.awt.Color color) {

        this.put("foregroundColor", color);
    }

    public final java.awt.Color getForeground() {

        return (java.awt.Color) this.get("foregroundColor");
    }

    public final void setBackground(java.awt.Color color) {

        this.put("backgroundColor", color);
    }

    public final java.awt.Color getBackground() {

        return (java.awt.Color) this.get("backgroundColor");
    }

    public final void setGradientColor(java.awt.Color color) {

        this.put("gradientColor", color);
    }

    public final java.awt.Color getGradientColor() {

        return (java.awt.Color) this.get("gradientColor");
    }

    public final void setVerticalAlignment(int i) {

        this.put("verticalAlignment", new Integer(i));
    }

    public final int getVerticalAlignment() {

        java.lang.Integer integer = (java.lang.Integer) this.get("verticalAlignment");
        if (integer != null) {
            return integer.intValue();
        } else {
            return 0;
        }
    }

    public final void setHorizontalAlignment(int i) {

        this.put("horizontalAlignment", new Integer(i));
    }

    public final int getHorizontalAlignment() {

        java.lang.Integer integer = (java.lang.Integer) this.get("horizontalAlignment");
        if (integer != null) {
            return integer.intValue();
        } else {
            return 0;
        }
    }

    public final void setVerticalTextPosition(int i) {

        this.put("verticalTextPosition", new Integer(i));
    }

    public final int getVerticalTextPosition() {

        java.lang.Integer integer = (java.lang.Integer) this.get("verticalTextPosition");
        if (integer != null) {
            return integer.intValue();
        } else {
            return 3;
        }
    }

    public final void setHorizontalTextPosition(int i) {

        this.put("horizontalTextPosition", new Integer(i));
    }

    public final int getHorizontalTextPosition() {

        java.lang.Integer integer = (java.lang.Integer) this.get("horizontalTextPosition");
        if (integer != null) {
            return integer.intValue();
        } else {
            return 0;
        }
    }

    public final void setDashPattern(float af[]) {

        this.put("dashPattern", af);
    }

    public final float[] getDashPattern() {

        return (float[]) this.get("dashPattern");
    }

    public final void setDashOffset(float f) {

        this.put("dashOffset", new Float(f));
    }

    public final float getDashOffset() {

        java.lang.Float float1 = (java.lang.Float) this.get("dashOffset");
        if (float1 != null) {
            return float1.floatValue();
        } else {
            return 1.0F;
        }
    }

    public final void setLineStyle(int i) {

        this.put("lineStyle", new Integer(i));
    }

    public final int getLineStyle() {

        java.lang.Integer integer = (java.lang.Integer) this.get("lineStyle");
        if (integer != null) {
            return integer.intValue();
        } else {
            return 11;
        }
    }

    public final void setBeginSize(int i) {

        this.put("beginSize", new Integer(i));
    }

    public final int getBeginSize() {

        java.lang.Integer integer = (java.lang.Integer) this.get("beginSize");
        if (integer != null) {
            return integer.intValue();
        } else {
            return GraphConstants.DEFAULTDECORATIONSIZE;
        }
    }

    public final void setEndSize(int i) {

        this.put("endSize", new Integer(i));
    }

    public final int getEndSize() {

        java.lang.Integer integer = (java.lang.Integer) this.get("endSize");
        if (integer != null) {
            return integer.intValue();
        } else {
            return GraphConstants.DEFAULTDECORATIONSIZE;
        }
    }

    public final void setLineBegin(int i) {

        this.put("lineBegin", new Integer(i));
    }

    public final int getLineBegin() {

        java.lang.Integer integer = (java.lang.Integer) this.get("lineBegin");
        if (integer != null) {
            return integer.intValue();
        } else {
            return 0;
        }
    }

    public final void setLineEnd(int i) {

        this.put("lineEnd", new Integer(i));
    }

    public final int getLineEnd() {

        java.lang.Integer integer = (java.lang.Integer) this.get("lineEnd");
        if (integer != null) {
            return integer.intValue();
        } else {
            return 0;
        }
    }

    public final void setValue(java.lang.Object obj) {

        this.put("value", obj);
    }

    public final java.lang.Object getValue() {

        return this.get("value");
    }

    public final void setLabelPosition(java.awt.geom.Point2D point2d) {

        this.put("labelposition", point2d);
    }

    public final java.awt.geom.Point2D getLabelPosition() {

        return (java.awt.geom.Point2D) this.get("labelposition");
    }

    public final void setExtraLabels(java.lang.Object aobj[]) {

        this.put("extraLabels", aobj);
    }

    public final java.lang.Object[] getExtraLabels() {

        return (java.lang.Object[]) this.get("extraLabels");
    }

    public final void setExtraLabelPositions(java.awt.geom.Point2D apoint2d[]) {

        this.put("extraLabelPositions", apoint2d);
    }

    public final java.awt.geom.Point2D[] getExtraLabelPositions() {

        return (java.awt.geom.Point2D[]) this.get("extraLabelPositions");
    }

    public final void setLabelAlongEdge(boolean flag) {

        this.put("labelAlongEdge", new Boolean(flag));
    }

    public final boolean isLabelAlongEdge() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("labelAlongEdge");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return false;
        }
    }

    public final void setEditable(boolean flag) {

        this.put("editable", new Boolean(flag));
    }

    public final boolean isEditable() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("editable");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return true;
        }
    }

    public final void setMoveable(boolean flag) {

        this.put("moveable", new Boolean(flag));
    }

    public final boolean isMoveable() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("moveable");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return true;
        }
    }

    public final void setSizeable(boolean flag) {

        this.put("sizeable", new Boolean(flag));
    }

    public final boolean isSizeable() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("sizeable");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return true;
        }
    }

    public final void setAutoSize(boolean flag) {

        this.put("autosize", new Boolean(flag));
    }

    public final boolean isAutoSize() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("autosize");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return false;
        }
    }

    public final void setResize(boolean flag) {

        this.put("resize", new Boolean(flag));
    }

    public final boolean isResize() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("resize");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return false;
        }
    }

    public final void setConstrained(boolean flag) {

        this.put("constrained", new Boolean(flag));
    }

    public final boolean isConstrained() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("constrained");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return false;
        }
    }

    public final void setSelectable(boolean flag) {

        this.put("selectable", new Boolean(flag));
    }

    public final boolean isSelectable() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("selectable");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return true;
        }
    }

    public final void setChildrenSelectable(boolean flag) {

        this.put("childrenSelectable", new Boolean(flag));
    }

    public final boolean isChildrenSelectable() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("childrenSelectable");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return true;
        }
    }

    public final void setBendable(boolean flag) {

        this.put("bendable", new Boolean(flag));
    }

    public final boolean isBendable() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("bendable");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return true;
        }
    }

    public final void setConnectable(boolean flag) {

        this.put("connectable", new Boolean(flag));
    }

    public final boolean isConnectable() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("connectable");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return true;
        }
    }

    public final void setDisconnectable(boolean flag) {

        this.put("disconnectable", new Boolean(flag));
    }

    public final boolean isDisconnectable() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("disconnectable");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return true;
        }
    }

    public final void setPoints(java.util.List list) {

        this.put("points", list);
    }

    public final java.util.List getPoints() {

        return (java.util.List) this.get("points");
    }

    public final void setRouting(org.jgraph.graph.Edge.Routing routing) {

        this.put("routing", routing);
    }

    public final org.jgraph.graph.Edge.Routing getRouting() {

        org.jgraph.graph.Edge.Routing routing = (org.jgraph.graph.Edge.Routing) this.get("routing");
        if (routing == null) {
            routing = GraphConstants.ROUTING_DEFAULT;
        }
        return routing;
    }

    public final void setBounds(java.awt.geom.Rectangle2D rectangle2d) {

        this.put("bounds", rectangle2d);
    }

    public final java.awt.geom.Rectangle2D getBounds() {

        return (java.awt.geom.Rectangle2D) this.get("bounds");
    }

    public final void setInset(int i) {

        this.put("inset", new Integer(i));
    }

    public final int getInset() {

        java.lang.Integer integer = (java.lang.Integer) this.get("inset");
        if (integer != null) {
            return integer.intValue();
        } else {
            return GraphConstants.DEFAULTINSET;
        }
    }

    public final void setSize(java.awt.Dimension dimension) {

        this.put("size", dimension);
    }

    public final java.awt.Dimension getSize() {

        return (java.awt.Dimension) this.get("size");
    }

    public final void setOffset(java.awt.geom.Point2D point2d) {

        this.put("offset", point2d);
    }

    public final java.awt.geom.Point2D getOffset() {

        return (java.awt.geom.Point2D) this.get("offset");
    }

    public final void setBeginFill(boolean flag) {

        this.put("beginFill", new Boolean(flag));
    }

    public final boolean isBeginFill() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("beginFill");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return false;
        }
    }

    public final void setEndFill(boolean flag) {

        this.put("endFill", new Boolean(flag));
    }

    public final boolean isEndFill() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("endFill");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return false;
        }
    }

    public final void setAbsolute(boolean flag) {

        org.jgraph.graph.GraphConstants.setAbsoluteX(this, flag);
        org.jgraph.graph.GraphConstants.setAbsoluteY(this, flag);
    }

    public final void setAbsoluteY(boolean flag) {

        this.put("absoluteY", new Boolean(flag));
    }

    public final boolean isAbsoluteY() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("absoluteY");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return false;
        }
    }

    public final void setAbsoluteX(boolean flag) {

        this.put("absoluteX", new Boolean(flag));
    }

    public final boolean isAbsoluteX() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("absoluteX");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return false;
        }
    }

    public final void setRemoveAll(boolean flag) {

        this.put("removeAll", new Boolean(flag));
    }

    public final boolean isRemoveAll() {

        java.lang.Boolean boolean1 = (java.lang.Boolean) this.get("removeAll");
        if (boolean1 != null) {
            return boolean1.booleanValue();
        } else {
            return false;
        }
    }

}
