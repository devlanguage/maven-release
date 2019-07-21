package org.basic.gui.swing.java2d;

import java.awt.Color;
import java.awt.Graphics;

public class Keleyi {
    Graphics g;

    // 科赫曲线：
    public void draw1(int x1, int y1, int x2, int y2, int depth) {// 科赫曲线 keleyi.com
        g.drawLine(x1, y1, x2, y2);
        if (depth <= 1)
            return;
        else {// 得到三等分点
            double x11 = (x1 * 2 + x2) / 3;
            double y11 = (y1 * 2 + y2) / 3;

            double x22 = (x1 + x2 * 2) / 3;
            double y22 = (y1 + y2 * 2) / 3;

            double x33 = (x11 + x22) / 2 - (y11 - y22) * Math.sqrt(3) / 2;
            double y33 = (y11 + y22) / 2 - (x22 - x11) * Math.sqrt(3) / 2;

            // g.setColor(j.getBackground());
            g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
            g.setColor(Color.black);
            draw1((int) x1, (int) y1, (int) x11, (int) y11, depth - 1);
            draw1((int) x11, (int) y11, (int) x33, (int) y33, depth - 1);
            draw1((int) x22, (int) y22, (int) x2, (int) y2, depth - 1);
            draw1((int) x33, (int) y33, (int) x22, (int) y22, depth - 1);
        }
    }

    // 正方形：

    public void draw2(int x1, int y1, int m, int depth) {// 正方形 keleyi.com
        g.fillRect(x1, y1, m, m);
        m = m / 3;
        if (depth <= 1)
            return;
        else {
            double x11 = x1 - 2 * m;
            double y11 = y1 - 2 * m;

            double x22 = x1 + m;
            double y22 = y1 - 2 * m;

            double x33 = x1 + 4 * m;
            double y33 = y1 - 2 * m;

            double x44 = x1 - 2 * m;
            double y44 = y1 + m;

            double x55 = x1 + 4 * m;
            double y55 = y1 + m;

            double x66 = x1 - 2 * m;
            double y66 = y1 + 4 * m;

            double x77 = x1 + m;
            double y77 = y1 + 4 * m;

            double x88 = x1 + 4 * m;
            double y88 = y1 + 4 * m;

            draw2((int) x11, (int) y11, (int) m, depth - 1);

            draw2((int) x22, (int) y22, (int) m, depth - 1);

            draw2((int) x33, (int) y33, (int) m, depth - 1);

            draw2((int) x44, (int) y44, (int) m, depth - 1);

            draw2((int) x55, (int) y55, (int) m, depth - 1);

            draw2((int) x66, (int) y66, (int) m, depth - 1);

            draw2((int) x77, (int) y77, (int) m, depth - 1);

            draw2((int) x88, (int) y88, (int) m, depth - 1);
        }

    }

    // 谢冰斯基三角形：

    public void draw3(int x1, int y1, int x2, int y2, int x3, int y3, int depth) {// 三角形 keleyi.com

        double s = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x2, y2, x3, y3);
        g.drawLine(x1, y1, x3, y3);
        // if(s<3)
        // return;
        if (depth <= 1)
            return;
        else {
            /*
             * 上面的三角形
             */
            double x11 = (x1 * 3 + x2) / 4;
            double y11 = y1 - (s / 4) * Math.sqrt(3);

            double x12 = (x1 + x2 * 3) / 4;
            double y12 = y11;

            double x13 = (x1 + x2) / 2;
            double y13 = y1;

            /*
             * 左边的三角形
             */
            double x21 = x1 - s / 4;
            double y21 = (y1 + y3) / 2;

            double x22 = x1 + s / 4;
            double y22 = y21;

            double x23 = x1;
            double y23 = y3;

            /*
             * 右边的三角形
             */
            double x31 = x2 + s / 4;
            double y31 = (y1 + y3) / 2;

            double x32 = x2 - s / 4;
            double y32 = y21;

            double x33 = x2;
            double y33 = y3;

            draw3((int) x11, (int) y11, (int) x12, (int) y12, (int) x13, (int) y13, depth - 1);
            draw3((int) x21, (int) y21, (int) x22, (int) y22, (int) x23, (int) y23, depth - 1);
            draw3((int) x31, (int) y31, (int) x32, (int) y32, (int) x33, (int) y33, depth - 1);
        }
    }

}
