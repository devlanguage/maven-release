package org.basic.gui.swing.sort;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.UIManager;

public class UpDownArrow implements Icon
{
    /**
     * The size of the Direcation Icon.Due to the Icon is square,the height and
     * width of Icon is this value.
     */
    private int size = 12;

    /**
     * Static value of direction for this class.
     */
    public static final int UP = 0;
    /**
     * DOWN
     * 
     * Static value of direction for this class.
     */
    public static final int DOWN = 1;
    /**
     * direction The value of current direction of icon.
     */
    private int direction;

    public UpDownArrow(int i)
    {
        direction = i;
    }

    public int getIconHeight()
    {
        return size;
    }

    public int getIconWidth()
    {
        return size;
    }

    /**
     * paintIcon
     * 
     * Use the Java2D to paint the icon real time.
     */
    public void paintIcon(Component component, Graphics g, int i, int j)
    {
        int k = i + size / 2;
        int l = i + 1;
        int i1 = (i + size) - 2;
        int j1 = j + 1;
        int k1 = (j + size) - 2;
        Color color = (Color) UIManager.get("controlDkShadow");
        if (direction == 0)
        {
            g.setColor(Color.white);
            g.drawLine(l, k1, i1, k1);
            g.drawLine(i1, k1, k, j1);
            g.setColor(color);
            g.drawLine(l, k1, k, j1);
        } else
        {
            g.setColor(color);
            g.drawLine(l, j1, i1, j1);
            g.drawLine(l, j1, k, k1);
            g.setColor(Color.white);
            g.drawLine(i1, j1, k, k1);
        }
    }
}