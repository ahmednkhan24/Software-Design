// Zakee Jabbar (zjabba2)
// Azam Jamal (ajamal6)
// Ahmed Khan (akhan227)
// CS 342
// Project 3

import javax.swing.*;

// My own JButton Class
public class MyJButton extends JButton
{
    private int xPos;
    private int yPos;
    private boolean isRemovable;
    private int value;

    // Multiple Constructors
    public MyJButton()
    {
        super("");
    }

    public MyJButton(int x, int y, boolean removable, int val)
    {
        super("");
        xPos = x;
        yPos = y;
        isRemovable = removable;
        value = val;
    }

    public MyJButton(int x, int y, boolean removable)
    {
        super("");
        xPos = x;
        yPos = y;
        isRemovable = removable;
    }


    // Get/Set functions
    public int getxPos()
    {
        return xPos;
    }

    public int getyPos()
    {
        return yPos;
    }

    public boolean isRemovable()
    {
        return isRemovable;
    }

    public void setRemovable(boolean removable)
    {
        isRemovable = removable;
    }

    public void setxPos(int xPos)
    {
        this.xPos = xPos;
    }

    public void setyPos(int yPos)
    {
        this.yPos = yPos;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}
