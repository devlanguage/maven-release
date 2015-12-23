package org.basic.gui.swing.java2d;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawMap extends JPanel {
    public DrawMap() {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // 清空背景颜色

        String lineName = "drawString";
        int lineX = 300, lineY = 100;
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawString("sbcd", lineX, lineY);

        Line2D.Float line = new Line2D.Float(1.0f, 2.0f, lineX, lineY);
        g2D.draw(line);

        boolean raised = false;
        g2D.setColor(Color.RED);
        g2D.draw3DRect(50, 50, 100, 200, raised);

        // 通过该方法使图形去除锯齿状
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        float thick = 2.5f; // 设置画刷的粗细为 0.5
        Stroke stroke = g2D.getStroke(); // 得到当前的画刷
        g2D.setStroke(new BasicStroke(thick, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
      //设置笔刷为黑色 

//        g2D.setPaint(Color.black); 
        g2D.setColor(Color.GREEN);
        g2D.drawOval(50, 50, 100, 200);
        
        int Xs1[]={10,60,120,200,260,340}; 
        int Ys1[]={10,200,120,180,60,130}; 
        g2D.drawPolyline(Xs1,Ys1,Xs1.length); 


    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test");

        frame.setSize(350, 350);

        DrawMap map = new DrawMap();

        frame.getContentPane().add(map);
        frame.show();

    }
}
