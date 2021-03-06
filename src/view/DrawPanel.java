/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import model.World;
import model.entity.AntiVirus;
import model.entity.Computer;
import model.entity.Virus;

/**
 *
 * @author Jim_Laptop
 */
public class DrawPanel extends javax.swing.JPanel
{
    private World world = null;

    /**
     * Creates new form DrawPanel
     */
    public DrawPanel()
    {
        initComponents();
    }
    
    /**
     *
     * @param world
     */
    public void setWorld(World world){
        this.world = world;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(world != null)
            world.draw(g, getWidth(), getHeight());
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                formMousePressed(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMousePressed
    {//GEN-HEADEREND:event_formMousePressed
        int col = (evt.getX()- world.offSetHorizontal)/world.sizeOfCell;
        int row = (evt.getY()- world.offSetVertical)/world.sizeOfCell;
        if(col > -1 && row > -1 && col < world.rows && row < world.rows)
        if(evt.getButton() == 1){
            world.addCell(new Virus(new Point(col, row)));
        }
        else{
            if(evt.getButton() == 3){
                world.addCell(new AntiVirus(new Point(col, row)));
            }
            else{
                world.addCell(new Computer(new Point(col, row)));
            }
        }
        repaint();
    }//GEN-LAST:event_formMousePressed

    private void formKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_formKeyPressed
    {//GEN-HEADEREND:event_formKeyPressed
    }//GEN-LAST:event_formKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
