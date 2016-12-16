/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import model.World;

/**
 *
 * @author Jim_Laptop
 */
public class Computer extends Cell
{

    /**
     *
     */
    public final static int TAKEN_BY_VIRUS = 1;

    /**
     *
     */
    public final static int TAKEN_BY_ANTIVIRUS = 0;

    /**
     *
     */
    public final static int NOT_TAKEN = -1;

    /**
     *
     */
    public int status = NOT_TAKEN;

    /**
     *
     */
    public boolean spawn = false;
    private BufferedImage img ;

    /**
     *
     * @param pOldPosition
     */
    public Computer(Point pOldPosition)
    {
        super(pOldPosition);
        try
        {
            img = ImageIO.read(new File("computer.png"));
                    }
        catch (IOException ex)
        {
            Logger.getLogger(Computer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void draw(Graphics gr, int size, int offSetHorizontal, int offSetVertical)
    {
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        
        g.drawImage(img, offSetHorizontal + oldPosition.x * size, offSetVertical + oldPosition.y * size, size, size, null);
        g.setColor(Color.RED);
        if (status == NOT_TAKEN)
        {
            g.drawString("0", (int) (offSetHorizontal + oldPosition.x * size + 0.5 * size), (int) (offSetVertical + oldPosition.y * size + 0.5 * size));
        } else if (status == TAKEN_BY_VIRUS)
        {
            g.drawString("V", (int) (offSetHorizontal + oldPosition.x * size + 0.5 * size), (int) (offSetVertical + oldPosition.y * size + 0.5 * size));
        } else
        {
            g.drawString("A", (int) (offSetHorizontal + oldPosition.x * size + 0.5 * size), (int) (offSetVertical + oldPosition.y * size + 0.5 * size));
        }
    }

    @Override
    public void nextStep()
    {
    }
}
