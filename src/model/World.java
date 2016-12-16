/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.serializer.PointSerializer;
import model.serializer.WorldSerializer;
import model.serializer.CellSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import model.entity.Cell;
import model.entity.AntiVirus;
import model.entity.Computer;
import model.entity.Virus;

/**
 *
 * @author Jim_Laptop
 */
public class World
{

    public int columns,

    /**
     *
     */
    rows;
    public transient int sizeOfCell,

    /**
     *
     */
    offSetHorizontal,

    /**
     *
     */
    offSetVertical;

    /**
     *
     */
    public ArrayList<Cell> alCells = null;

    /**
     *
     * @param columns
     * @param rows
     */
    public World(int columns, int rows)
    {
        this.columns = columns;
        this.rows = rows;
        alCells = new ArrayList<>();
    }

    /**
     *
     * @param alList
     * @return returns a string containing a representation of every cell in an array
     */
    public String listToString(ArrayList<Cell> alList)
    {
        String result = "";
        for (Cell alList1 : alList)
        {
            result += alList1.toString();
        }
        return result;
    }

    /**
     * @param cell
     * Adding a new cell to the arrayList
     */
    public void addCell(Cell cell)
    {
        boolean cellTaken = false;
        
        //checking if place to place cell is free
        for (Cell alCell : alCells)
        {
            if (alCell.oldPosition == cell.oldPosition)
            {
                cellTaken = true;
            }
        }
        if (!cellTaken)
        {
            alCells.add(cell);
        }
    }

    /**
     * Get all computers
     *
     * @return returns all computer cells
     */
    public ArrayList<Computer> getComputers()
    {
        ArrayList<Computer> result = new ArrayList<>();

        for (Cell alCell : alCells)
        {
            if (alCell instanceof Computer)
            {
                result.add((Computer) alCell);
            }
        }
        return result;
    }

    /**
     * Get all virus
     *
     * @return returns all virus cells
     */
    public ArrayList<Virus> getVirus()
    {
        ArrayList<Virus> result = new ArrayList<>();

        for (Cell alCell : alCells)
        {
            if (alCell instanceof Virus)
            {
                result.add((Virus) alCell);
            }
        }
        return result;
    }

    /**
     * Get all antivirus
     *
     * @return returns all antivirus cells
     */
    public ArrayList<AntiVirus> getAntivirus()
    {
        ArrayList<AntiVirus> result = new ArrayList<>();

        for (Cell alCell : alCells)
        {
            if (alCell instanceof AntiVirus)
            {
                result.add((AntiVirus) alCell);
            }
        }
        return result;
    }

    /**
     * next step for every cell, deleting marked cells
     */
    public void nextStep()
    {
        for (int i = alCells.size() - 1; i >= 0; i--)
        {
            if (!alCells.get(i).toDelete)
            {
                if (alCells.get(i).currentTarget == null)
                {
                    findTarget(alCells.get(i));
                }
                alCells.get(i).nextStep();
            }
        }
        deleteCells();
    }

    /**
     *
     * @param gr Graphics Object
     * @param pWidth width of the panel 
     * @param pHeight height of the panel
     */
    public void draw(Graphics gr, int pWidth, int pHeight)
    {
        Graphics2D g = (Graphics2D) gr;

        //determing size of cells
        sizeOfCell = Math.min(pWidth / columns, pHeight / rows);
        
        //determing offsets
        offSetHorizontal = (pWidth - columns * (sizeOfCell)) / 2;
        offSetVertical = (pHeight - rows * (sizeOfCell)) / 2;
        
        //activating antialias
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        //filling background with white
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, pWidth, pHeight);

        
        //drawing cells
        for (Cell alCell : alCells)
        {
            alCell.draw(gr, sizeOfCell, offSetHorizontal, offSetVertical);
        }
        
        //drawing the grid
        g.setColor(Color.BLACK);
        for (int x = 0; x <= columns; x++)
        {
            g.drawLine(offSetHorizontal + x * (sizeOfCell), offSetVertical, offSetHorizontal + x * (sizeOfCell), pHeight - offSetVertical);
            for (int y = 0; y <= rows; y++)
            {
                g.drawLine(offSetHorizontal, offSetVertical + y * (sizeOfCell), pWidth - offSetHorizontal, offSetVertical + y * (sizeOfCell));
            }
        }
    }

    /**
     *Final step after every cell has done his step,
     * Collision checking and marking for deletion is done here
     */
    public void commit()
    {
        for (int i = alCells.size() - 1; i >= 0; i--)
        {
            Cell currentCell = alCells.get(i);
            if (currentCell instanceof Virus)
            {
                if (collidedWithTarget(currentCell))
                {
                    currentCell.markToDelete();
                    currentCell.currentTarget.status = Computer.TAKEN_BY_VIRUS;
                }
                else if (collidedWithAntiVirus((Virus) currentCell))
                {
                    currentCell.markToDelete();
                }
                else
                {
                    currentCell.oldPosition = currentCell.newPosition;
                }
            }
            else if (currentCell instanceof AntiVirus)
            {
                if (collidedWithTarget(currentCell))
                {
                    currentCell.markToDelete();
                    currentCell.currentTarget.status = Computer.TAKEN_BY_ANTIVIRUS;
                }
                else if (collidedWithVirus((AntiVirus) currentCell))
                {
                    currentCell.markToDelete();
                }
                else
                {
                    currentCell.oldPosition = currentCell.newPosition;
                }
            }
            else
            {
                Computer computer = (Computer) currentCell;
                if (computer.status == Computer.TAKEN_BY_ANTIVIRUS)
                {
                    spawnAntiVirus(computer);
                }
                else if (computer.status == Computer.TAKEN_BY_VIRUS)
                {
                    spawnVirus(computer);
                }
            }
        }
        deleteCells();
    }

    /**
     *
     * @param p the point to check if it is free
     * @return
     */
    public boolean isPositionFree(Point p)
    {
        boolean free = true;
        for (Cell cell : alCells)
        {
            if (cell.oldPosition.equals(p))
            {
                free = false;
            }
        }
        return free;
    }

    /**
     *Delete marked cells
     */
    public void deleteCells()
    {
        for (int i = alCells.size() - 1; i >= 0; i--)
        {
            if (alCells.get(i).toDelete)
            {
                alCells.remove(i);
            }
        }
    }

    /**
     * Spawning virus every step, to whether random target or nearest target,
     * only to free targets or targets taken by virus
     * @param computer The computer which should spawn cells
     */
    public void spawnVirus(Computer computer)
    {
        int randomX = (int) (Math.random() * (1 + 1 + 1)) - 1;
        int randomY = (int) (Math.random() * (1 + 1 + 1)) - 1;
        
        boolean spawnPossible = true;
        int x = -1;
        Point targetedCell = new Point(computer.oldPosition.x + randomX, computer.oldPosition.y + randomY);
        
        //only spawn if antivirus are there
        if (getAntivirus().isEmpty())
        {
            spawnPossible = false;
        }
        
        //checking if any cell is nearby the computer
        while (spawnPossible == true && x <= 1)
        {
            if (x != 0)
            {
                int y = -1;
                while (y <= 1 && spawnPossible == true)
                {
                    if (y != 0)
                    {
                        if (!isPositionFree(targetedCell))
                        {
                            spawnPossible = false;
                        }
                    }
                    y++;
                }
            }
            x++;
        }
        if (spawnPossible)
        {
            alCells.add(new Virus(targetedCell));
        }
    }

    /**
     *Spawning antivirus every step, to whether random target or nearest target,
     * only to free targets or targets taken by virus
     * @param computer the computer which should spawn antivirus
     */
    public void spawnAntiVirus(Computer computer)
    {
        int randomX = (int) (Math.random() * (1 + 1 + 1)) - 1;
        int randomY = (int) (Math.random() * (1 + 1 + 1)) - 1;

        boolean spawnPossible = true;
        int x = -1;
        Point targetedCell = new Point(computer.oldPosition.x + randomX, computer.oldPosition.y + randomY);

        if (getVirus().isEmpty())
        {
            spawnPossible = false;
        }

        while (spawnPossible == true && x <= 1)
        {
            if (x != 0)
            {
                int y = -1;
                while (y <= 1 && spawnPossible == true)
                {
                    if (y != 0)
                    {
                        if (!isPositionFree(targetedCell))
                        {
                            spawnPossible = false;
                        }
                    }
                    y++;
                }
            }
            x++;
        }
        if (spawnPossible)
        {
            alCells.add(new AntiVirus(targetedCell));
        }
    }

    /**
     *
     * @param targetedCell Point of where to place computer
     */
    public void spawnComputer(Point targetedCell)
    {
        if (isPositionFree(targetedCell))
        {
            alCells.add(new Computer(targetedCell));
        }
    }

    /**
     *
     * @param virus the virus to check against antivirus
     * @return collision or none
     */
    public boolean collidedWithAntiVirus(Virus virus)
    {
        boolean collided = false;
        int i = 0;
        while (collided == false && i < alCells.size())
        {
            if (alCells.get(i) instanceof AntiVirus
                    && alCells.get(i).newPosition.equals(virus.newPosition))
            {
                collided = true;
            }
            else
            {
                i++;
            }
        }
        return collided;
    }

    /**
     *
     * @param antiVirus antivirus to check against virus
     * @return collision or none
     */
    public boolean collidedWithVirus(AntiVirus antiVirus)
    {
        boolean collided = false;
        int i = 0;
        while (collided == false && i < alCells.size())
        {
            if (alCells.get(i) instanceof Virus
                    && alCells.get(i).newPosition.equals(antiVirus.newPosition))
            {
                collided = true;
            }
            else
            {
                i++;
            }
        }
        return collided;
    }

    /**
     *
     * @param cell the cell to check against its current target
     * @return collision or none
     */
    public boolean collidedWithTarget(Cell cell)
    {
        return cell.newPosition.equals(cell.currentTarget.newPosition);
    }

    /**
     *
     * @param originCell first cell
     * @param targetCell target cell
     * @return distance between the two cells
     */
    public int distanceTo(Cell originCell, Cell targetCell)
    {
        int distX = Math.abs(originCell.oldPosition.x - targetCell.oldPosition.x);
        int distY = Math.abs(originCell.oldPosition.y - targetCell.oldPosition.y);

        return (int) Math.sqrt((Math.pow(distX, 2)) + Math.pow(distY, 2));
    }

    /**
     *
     * @param cell the cell to find a target for
     */
    public void findTarget(Cell cell)
    {
        if (!(cell instanceof Computer))
        {
            if (cell instanceof Virus)
            {
                findTarget((Virus) cell);
            }
            else
            {
                findTarget((AntiVirus) cell);
            }
        }
    }

    /**
     *
     * @param virus
     */
    public void findTarget(Virus virus)
    {
        boolean randomTarget = false;
        int random = (int) (Math.random() * (1 + 1));

        if (random == 1)
        {
            randomTarget = true;
        }
        else
        {
            randomTarget = false;
        }

        if (randomTarget == false)
        {
            for (int i = getComputers().size() - 1; i >= 0; i--)
            {
                Computer currentComputer = getComputers().get(i);
                if (currentComputer.status == Computer.NOT_TAKEN || currentComputer.status == Computer.TAKEN_BY_ANTIVIRUS)
                {
                    if (virus.currentTarget == null)
                    {
                        virus.currentTarget = currentComputer;
                    }
                    else if (distanceTo(virus, currentComputer) < distanceTo(virus, virus.currentTarget))
                    {
                        virus.currentTarget = currentComputer;
                    }
                }
            }
        }
        else
        {
            ArrayList<Computer> alTemp = getComputers();
            boolean validFound = false;
            int i = alTemp.size() - 1;
            while (!validFound && i >= 0)
            {
                random = (int) (Math.random() * (alTemp.size()));
                int currentStatus = alTemp.get(i).status;
                if (currentStatus == Computer.NOT_TAKEN || currentStatus == Computer.TAKEN_BY_ANTIVIRUS)
                {
                    validFound = true;
                    virus.currentTarget = alTemp.get(i);
                }
                else
                {
                    alTemp.remove(random);
                }
                i--;
            }
        }
        if (virus.currentTarget == null)
        {
            virus.markToDelete();
        }
    }

    /**
     *
     * @param antiVirus
     */
    public void findTarget(AntiVirus antiVirus)
    {
        for (int i = getComputers().size() - 1; i >= 0; i--)
        {
            Computer currentComputer = getComputers().get(i);

            if (currentComputer.status == Computer.NOT_TAKEN || currentComputer.status == Computer.TAKEN_BY_VIRUS)
            {
                if (antiVirus.currentTarget == null)
                {
                    antiVirus.currentTarget = currentComputer;
                }
                else if (distanceTo(antiVirus, currentComputer) < distanceTo(antiVirus, antiVirus.currentTarget))
                {
                    antiVirus.currentTarget = currentComputer;
                }
            }
        }
        if (antiVirus.currentTarget == null)
        {
            antiVirus.markToDelete();
        }
    }

    @Override
    public String toString()
    {
        return "World{" + "columns=" + columns + ", rows=" + rows + ", sizeOfCell=" + sizeOfCell + ", offSetHorizontal=" + offSetHorizontal + ", offSetVertical=" + offSetVertical + ", alCells=" + listToString(alCells);
    }

    /**
     *
     * @return
     */
    public String getJson()
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(World.class, new WorldSerializer());
        gsonBuilder.registerTypeHierarchyAdapter(Cell.class, new CellSerializer());
        gsonBuilder.registerTypeAdapter(Point.class, new PointSerializer());
        gsonBuilder.setPrettyPrinting();

        Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }
    
    
}
