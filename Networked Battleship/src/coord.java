import javax.swing.*;

public class coord extends JButton
{
    // Eclipse did this to remove a warning...
    private static final long serialVersionUID = 1L;


    private int      xPos;
    private  int     yPos;
    private boolean  isOccupied;
    private int      value;

    //these are used for GUI checks
    private ship     CurrentShip;
    private boolean  frontOfShip;
    private boolean  backOfShip;

    //--------------------------------------------------------------------------------
    //
    // constructor
    //
    public coord(int x, int y)
    {
        xPos = x;
        yPos = y;
        isOccupied = false;
        value = 0;

        //for keeping track of ship on GUI
        frontOfShip = false;
        backOfShip = false;
    }
    //--------------------------------------------------------------------------------
    //
    // getters
    //
    public int getxPos()            { return xPos;       }
    public int getyPos()            { return yPos;       }
    public int getVal()             { return value;      }
    public boolean isSpotOccupied() { return isOccupied; }
    public ship getCurrentShip()    {return CurrentShip; }
    public boolean getBackOfShip()  {return backOfShip;  }
    public boolean getFrontOfShip() {return frontOfShip; }

    //--------------------------------------------------------------------------------
    //
    // setters
    //
    public void setToOccupied()                 {isOccupied = true;}
    public void setValue(int v)                 { value = v; }
    public void setCurrentShip(ship ship)       { CurrentShip = ship; }
    public void setFrontOfShip(boolean front)   { frontOfShip = front; }
    public void setBackOfShip(boolean back)     {backOfShip = back; }
    //--------------------------------------------------------------------------------
}
