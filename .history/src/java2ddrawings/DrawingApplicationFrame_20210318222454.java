/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java2ddrawings;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author acv
 */
public class DrawingApplicationFrame extends JFrame {
    // Create the panels for the top of the application. One panel for each
    // line and one to contain both of those panels.
    private JPanel top = new JPanel();
    private JPanel line1 = new JPanel();
    private JPanel line2 = new JPanel();

    // create the widgets for the firstLine Panel.
    private final JButton undoButton;
    private final JButton clearButton;
    private final DefaultComboBoxModel shapesname = new DefaultComboBoxModel();
    private final JCheckBox filled, gradient, dashed;
    private final JLabel label1, label2, label3, label4, label5, label6;

    //create the widgets for the secondLine Panel.
    private final JButton colorOne;
    private final JButton colorTwo;
    private final JTextField line_width, dash_length;
    
    // Variables for drawPanel.
    //private DrawPanel drawPanel;
    private DrawPanel drawPanel= new DrawPanel();
    ArrayList<MyShapes> shapeList = new ArrayList();
    int xCoord;
    int yCoord;
    Boolean fill;
    Boolean gradients;
    MyShapes currShape;
    Stroke stroke;
    Paint paint, paint1, paint2;
    Color color1;
    Color color2;
     // add status label
    private  JLabel statusLabel = new JLabel("("+String.valueOf(xCoord)+","+String.valueOf(yCoord)+")");

    // Constructor for DrawingApplicationFrame
    public DrawingApplicationFrame() {
        super("Java 2D Drawings");
        // add widgets to panels
        setLayout(new BorderLayout());
        top = new JPanel(new GridLayout(2,1));
        add(top, BorderLayout.NORTH);
        top.add(line1);
        top.add(line2);

        undoButton = new JButton("Undo");
        line1.add(undoButton);
        clearButton = new JButton("Clear");
        line1.add(clearButton);

        label1 = new JLabel("Shapes: ");
        shapesname.addElement("Rectangle");
        shapesname.addElement("Oval");
        shapesname.addElement("Line");
        JComboBox shapes = new JComboBox(shapesname);
        line1.add(label1);
        line1.add(shapes);

        filled = new JCheckBox();
        line1.add(filled);
        label2 = new JLabel("Filled");
        line1.add(label2);

        // secondLine widgets
        gradient = new JCheckBox();
        label3 = new JLabel("Use Gradient");
        colorOne = new JButton("1st Color...");
        colorTwo = new JButton("2nd Color...");
        label4 = new JLabel("Line Width:");
        line_width = new JTextField("10");
        label5 = new JLabel("Dash Length: ");
        dash_length = new JTextField("10");
        dashed = new JCheckBox();
        label6 = new JLabel("Dashed");

        line2.add(gradient);
        line2.add(label3);
        line2.add(colorOne);
        line2.add(colorTwo);
        line2.add(label4);
        line2.add(line_width);
        line2.add(label5);
        line2.add(dash_length);
        line2.add(dashed);
        line2.add(label6);
        // add top panel of two panels

        // add topPanel to North, drawPanel to Center, and statusLabel to South
        drawPanel.setBackground(Color.WHITE);
        drawPanel.setVisible(true);
        add(drawPanel, BorderLayout.CENTER);

        statusLabel.setVisible(true);
        add(statusLabel, BorderLayout.SOUTH);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        
        //add listeners and event handlers
        colorOne.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                color1 = JColorChooser.showDialog(null,"Choose a Color" , Color.BLACK);
                paint1 = color1;
            }
        });

        colorTwo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                color2 = JColorChooser.showDialog(null,"Choose a Color", Color.BLACK);
                paint2 = color2;
            }
        });

        undoButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                shapeList.remove(shapeList.size()-1);
                repaint();
            }
        });

        clearButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                shapeList.clear();
                repaint();
            }
        });
    }

    // Create event handlers, if needed
    // Create a private inner class for the DrawPanel.
    private class DrawPanel extends JPanel
    {
        public DrawPanel()
        {
            MouseHandler mouseHandler1 = new MouseHandler();
            MouseHandler mouseHandler2 = new MouseHandler();
            addMouseListener(mouseHandler1);
            addMouseMotionListener(mouseHandler2);
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            //loop through and draw each shape in the shapes arraylist
            for(int i = 0; i < shapeList.size(); i++){
                shapeList.get(i).draw(g2d);
            }
        }


        private class MouseHandler extends MouseAdapter implements MouseMotionListener
        {

            public void mousePressed(MouseEvent event)
            {
                xCoord = event.getX();
                yCoord = event.getY();
                fill = filled.isSelected();
                gradients = gradient.isSelected();

                if (dashed.isSelected()){
                    stroke  = new BasicStroke(Float.parseFloat(line_width.getText()), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{Float.parseFloat(dash_length.getText())}, 0);
                }
                else{
                    stroke = new BasicStroke(Float.parseFloat(line_width.getText()));
                }

                if (gradients){
                    paint = new GradientPaint(0,0,color1,50,50,color2,true);
                }
                else{
                    paint = color1;
                }
                
                if (shapesname.getSelectedItem()== "Oval"){
                    currShape = new MyOval(new Point(xCoord,yCoord),new Point(xCoord,yCoord),paint,stroke,fill);
                    shapeList.add(currShape);}
                    
                if (shapesname.getSelectedItem()== "Line"){
                    currShape = new MyLine(new Point(xCoord,yCoord),new Point(xCoord,yCoord),paint,stroke);
                    shapeList.add(currShape);}
                
                if (shapesname.getSelectedItem()== "Rectangle"){
                    currShape = new MyRectangle(new Point(xCoord,yCoord),new Point(xCoord,yCoord),paint,stroke,fill);
                    shapeList.add(currShape);}
            }

            public void mouseReleased(MouseEvent event)
            {
            }

            @Override
            public void mouseDragged(MouseEvent event)
            {
                currShape.setEndPoint(new Point(event.getX(),event.getY()));
                repaint();
                //update whenever the mouse is move (drawing)
                //only update the endpoint of the last thing added to arraylist
                //use setendpoint method
                //call repaint
                
                //update co-ords in status label
                xCoord = event.getX();
                yCoord = event.getY();
                statusLabel.setText("("+String.valueOf(xCoord)+","+String.valueOf(yCoord)+")");

            }

            @Override
            public void mouseMoved(MouseEvent event)
            {
                xCoord = event.getX();
                yCoord = event.getY();
                statusLabel.setText("("+String.valueOf(xCoord)+","+String.valueOf(yCoord)+")");
            }
        }

    }
}