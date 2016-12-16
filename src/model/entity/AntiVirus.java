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

/**
 *
 * @author Jim_Laptop
 */
public class AntiVirus extends Cell {
    private BufferedImage img;

    /**
     *
     * @param newPosition
     */
    public AntiVirus(Point newPosition)
    {
        super(newPosition);
        try
        {
            img = ImageIO.read(new File("antivirus.png"));
        }
        catch (IOException ex)
        {
            Logger.getLogger(AntiVirus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void nextStep()
    {
        if(currentTarget != null){
            if (this.oldPosition.x < currentTarget.oldPosition.x)
            {
                this.newPosition.x = this.oldPosition.x + 1;
            } 
            else
            {
                if (this.oldPosition.x > currentTarget.oldPosition.x)
                {
                    this.newPosition.x = this.oldPosition.x - 1;
                }
            }

            if (this.oldPosition.y < currentTarget.oldPosition.y)
            {
                this.newPosition.y = oldPosition.y + 1;
            } 
            else
            {
                if (this.oldPosition.y > currentTarget.oldPosition.y)
                {
                    this.newPosition.y = oldPosition.y - 1;
                }
            }
        }
    }

    @Override
    public void draw(Graphics gr, int size, int offSetHorizontal, int offSetVertical)
    {
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.RED);
        
            g.drawImage(img, offSetHorizontal + oldPosition.x * size, offSetVertical + oldPosition.y * size, size, size, null);
    }
}
