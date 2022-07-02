/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java2ddrawings;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author al
 */
public class MyRectangle extends MyBoundedShapes{
    
    public MyRectangle(Point pntA, Point pntB, Paint paint, Stroke strk, boolean filled)
    {
        super(pntA, pntB, paint, strk, filled);
    }
    
    @Override   
    public void draw(Graphics2D g2d)
    {
        g2d.setPaint(getPaint());
        g2d.setStroke(getStroke());
        if(isFilled())
        {
            g2d.fill(new Rectangle2D.Double(getTopLeftX(), getTopLeftY(), getWidth(), getHeight()));
        }
        else
        {
            g2d.draw(new Rectangle2D.Double(getTopLeftX(), getTopLeftY(), getWidth(), getHeight()));
        }
        
    }
    
}
