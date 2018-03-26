// Zakee Jabbar (zjabba2)
// Azam Jamal (ajamal6)
// Ahmed Khan (akhan227)
// CS 342
// Project 3


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


// Grid class that holds the 9 3X3 subgrids
public class Grid extends JPanel
{
    private SubGrid panel1;
    private SubGrid panel2;
    private SubGrid panel3;
    private SubGrid panel4;
    private SubGrid panel5;
    private SubGrid panel6;
    private SubGrid panel7;
    private SubGrid panel8;
    private SubGrid panel9;

    // Constructor
    public Grid()
    {
        super();
        setLayout(new GridLayout(3, 3));
        setBackground(Color.black);

        panel1 = new SubGrid(0,3,0,3);
        add(panel1);
        panel2 = new SubGrid(0,3,3,6);
        add(panel2);
        panel3 = new SubGrid(0,3,6,9);
        add(panel3);
        panel4 = new SubGrid(3,6,0,3);
        add(panel4);
        panel5 = new SubGrid(3,6,3,6);
        add(panel5);
        panel6 = new SubGrid(3,6,6,9);
        add(panel6);
        panel7 = new SubGrid(6,9,0,3);
        add(panel7);
        panel8 = new SubGrid(6,9,3,6);
        add(panel8);
        panel9 = new SubGrid(6,9,6,9);
        add(panel9);
    }

    // Adds action listeners to all the panels
    public void addListener(GridListener gridListener)
    {
        panel1.addListener(gridListener);
        panel2.addListener(gridListener);
        panel3.addListener(gridListener);
        panel4.addListener(gridListener);
        panel5.addListener(gridListener);
        panel6.addListener(gridListener);
        panel7.addListener(gridListener);
        panel8.addListener(gridListener);
        panel9.addListener(gridListener);

    }

    // Sets value for a specific spot
    public void setValue(int xPos, int yPos, int value, boolean isRemovable)
    {
        if(xPos >= 1 && xPos <= 3) // First row of subgrids
        {
            if(yPos >= 1 && yPos <=3) // First column of subgrids
            {
                panel1.setValue((xPos - 1) % 3, (yPos - 1) % 3, value, isRemovable);
            }
            else if(yPos >= 4 && yPos <= 6) // Second column of subgrids
            {
                panel2.setValue((xPos - 1) % 3, (yPos - 1) % 3, value, isRemovable);
            }
            else // Third column of subgrids
            {
                panel3.setValue((xPos - 1) % 3, (yPos - 1) % 3, value, isRemovable );
            }

        }
        else if(xPos >= 4 && xPos <= 6) // Second row of subgrids
        {
            if(yPos >= 1 && yPos <=3) // First column of subgrids
            {
                panel4.setValue((xPos - 1) % 3, (yPos - 1) % 3, value, isRemovable);
            }
            else if(yPos >= 4 && yPos <= 6) // Second column of subgrids
            {
                panel5.setValue((xPos - 1) % 3, (yPos - 1) % 3, value, isRemovable);
            }
            else // Third column of subgrids
            {
                panel6.setValue((xPos - 1) % 3, (yPos - 1) % 3, value, isRemovable);
            }

        }
        else // Third row of subgrids
        {
            if(yPos >= 1 && yPos <=3) // First column of subgrids
            {
                panel7.setValue((xPos - 1) % 3, (yPos - 1) % 3, value, isRemovable);

            }
            else if(yPos >= 4 && yPos <= 6) // Second column of subgrids
            {
                panel8.setValue((xPos - 1) % 3, (yPos - 1) % 3, value, isRemovable);

            }
            else // Third column of subgrids
            {
                panel9.setValue((xPos - 1) % 3, (yPos - 1) % 3, value, isRemovable);

            }

        }

    }

    // Resets the whole grid
    public void resetGrid()
    {
        panel1.resetGrid();
        panel2.resetGrid();
        panel3.resetGrid();
        panel4.resetGrid();
        panel5.resetGrid();
        panel6.resetGrid();
        panel7.resetGrid();
        panel8.resetGrid();
        panel9.resetGrid();
    }





}
