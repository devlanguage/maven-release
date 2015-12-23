/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.draw2d.uml.CompartmentFigure.java is created on 2008-5-5
 */
package org.draw2d.uml;

import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ToolbarLayout;

public class CompartmentFigure extends Figure {

    public CompartmentFigure() {

        ToolbarLayout layout = new ToolbarLayout();
        layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
        layout.setStretchMinorAxis(false);
        layout.setSpacing(2);
        setLayoutManager(layout);        
        setBorder(new CompartmentFigureBorder());
    }

    public class CompartmentFigureBorder extends AbstractBorder {

        public Insets getInsets(IFigure figure) {

            return new Insets(1, 0, 0, 0);
        }

        public void paint(IFigure figure, Graphics graphics, Insets insets) {

            graphics.drawLine(getPaintRectangle(figure, insets).getTopLeft(), tempRect
                    .getTopRight());
        }
    }
}
