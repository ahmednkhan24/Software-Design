// Zakee Jabbar (zjabba2)
// Azam Jamal (ajamal6)
// Ahmed Khan (akhan227)
// CS 342
// Project 3

import javax.swing.*;
import java.awt.*;

// Implements a 3x3 Subgrid for the main grid
public class SubGrid extends JPanel
{
    private MyJButton buttons[][];

    // Constructor
    public SubGrid(int xStart, int xStop, int yStart, int yStop)
    {
        super();
        int xBut = 0;
        int yBut = 0;
        int value = 0;
        buttons = new MyJButton[3][3];
        for(int i = xStart; i < xStop; i++)
        {
            for(int j = yStart; j < yStop; j++)
            {
                buttons[xBut][yBut] = new MyJButton(i, j, true, 0);
                add(buttons[xBut][yBut]);
                yBut++;
                value++;
                if(yBut == 3)
                {
                    yBut = 0;
                }

            }
            xBut++;
        }

        setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.red));
        setLayout(new GridLayout(3,3));

    }

    // Adds a action listener to all the buttons
    public void addListener(GridListener gridListener)
    {
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                buttons[i][j].addActionListener(gridListener);
            }
        }
    }

    // Set value for a button
    public void setValue(int xPos, int yPos, int value, boolean isRemovable)
    {
        buttons[xPos][yPos].setValue(value);
        buttons[xPos][yPos].setText("" + value);
        buttons[xPos][yPos].setRemovable(isRemovable);
    }

    // Reset the whole grid
    public void resetGrid()
    {
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                buttons[i][j].setValue(0);
                buttons[i][j].setText("");
                buttons[i][j].setRemovable(true);
            }
        }
    }





}
