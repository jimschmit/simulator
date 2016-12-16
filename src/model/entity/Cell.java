/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Jim_Laptop
 */
public abstract class Cell
{

    /**
     *
     */
    public Computer currentTarget;

    /**
     *
     */
    public Point oldPosition;

    /**
     *
     */
    public Point newPosition;

    /**
     *
     */
    public boolean toDelete;

    /**
     *
     * @param pOldPosition
     */
    public Cell(Point pOldPosition)
    {
        this.oldPosition = pOldPosition;
        this.newPosition = oldPosition;
    }

    /**
     * Cell does its next step
     */
    public abstract void nextStep();
    
    /**
     *
     */
    public void markToDelete(){
        toDelete = true;
    }

    /**
     * Draw cell
     *
     * @param gr Graphics Object
     * @param size
     * @param offSetHorizontal
     * @param offSetVertical
     */
    public abstract void draw(Graphics gr, int size, int offSetHorizontal, int offSetVertical);

    @Override
    public String toString()
    {
        return "Cell{" + "oldPosition=" + oldPosition + '}';
    }

    /**
     *
     * @return
     */
    public String getJson(){
        String currentJson = "\n\t{ \n" + "\t\t\"" + getClass().getSimpleName() + "\" : \n\t\t[\n\t"
                + "\t\t{\n"+
                                  "\t\t\"positionX\" : " + oldPosition.x + ", \n" + 
                                  "\t\t\"positionY\" : " + oldPosition.y + 
                              "\n \t }]"
                            + " \n}";
        return currentJson;
    }

   
    
}

