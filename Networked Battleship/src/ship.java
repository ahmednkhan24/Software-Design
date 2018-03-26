import javax.swing.*;

public class ship
{
	/*
	 	SHIP NAME			SHIP SIZE		SHIP VALUE
	 	Aircraft Carrier		5				9
	 	Battleship				4				8
	 	Destroyer				3				7
	 	Submarine				3				6
	 	Patrol Boat				2				5

	 	Ship value is only implemented to tell the difference
	 	between the Submarine and the Destroyer since they
	 	both have the same ship size
	 */


    // Eclipse did this to remove a warning...
    private static final long serialVersionUID = 1L;


    private String   shipName;
    private coord    start, end;
    private int      shipValue;
    private boolean  isPlaced;
    private boolean  isSunk;
    private int      shipSize;
    private coord[][] board;
    private char orientation;

    //--------------------------------------------------------------------------------
    //
    // constructor
    //
    public ship(coord[][] arr, String name, coord x, coord y)
    {
        this.board = arr;

        shipName = name.toUpperCase();

        start = x;
        end   = y;

        setShipType();

        isPlaced = true;
        isSunk = false;
        orientation = 'h';
    }
    //--------------------------------------------------------------------------------
    //
    // Second constructor
    //
    public ship(String name,int shipSize )
    {
        shipName = name.toUpperCase();
        isPlaced = false;
        isSunk = false;
        this.shipSize = shipSize;
        orientation = 'h';
    }

    //--------------------------------------------------------------------------------
    //
    // getters
    //
    public coord   getStart()     { return start;     }
    public coord   getEnd()       { return end;       }
    public String  getShipName()  { return shipName;  }
    public int     getShipValue() { return shipValue; }
    public int     getShipSize()  { return shipSize;  }
    public boolean shipIsPlaced() { return isPlaced;  }

    public boolean shipIsSunk()
    {
        this.updateSunkStatus();
        return isSunk;
    }
    //--------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------
    //
    // isHorizontal
    //
    // returns true if the ship is horizontally placed
    // returns false if the ship is vertically placed
    //
    public boolean isHorizontal()
    {
        if (start.getxPos() == end.getxPos())
            return true;
        else
            return false;
    }
    //--------------------------------------------------------------------------------
    //
    // placeShip
    //
    // sets the coordinates of the ship
    //
    public void setCoord(coord x, coord y)
    {
        start = x;
        end = y;
        isPlaced = true;
    }

    public char getOrientation(){
        return orientation;
    }

    public void setOrientation(char orientation){
        this.orientation = orientation;
    }
    //--------------------------------------------------------------------------------
    //
    // updateSunkStatus
    //
    // checks if this ship has been sunk or not, updating the isSunk variable
    //
    private void updateSunkStatus()
    {
        if (this.isSunk)
            return;

        if (this.isHorizontal())
        {
            for (int i = this.start.getyPos(); i <= this.end.getyPos(); i++)
            {
                coord cur = board[this.start.getxPos()][i];

                if (cur.getVal() != -1)
                    return;
            }
        }
        else
        {
            for (int i = this.start.getxPos(); i <= this.end.getxPos(); i++)
            {
                coord cur = board[i][this.start.getyPos()];

                if (cur.getVal() != -1)
                    return;
            }
        }

        // if we got here that means the boat is sunk
        this.isSunk = true;
    }
    //--------------------------------------------------------------------------------
    //
    // setShipType
    //
    // uses the ship name to determine the ship value and ship size
    //
    private void setShipType()
    {
        switch (shipName)
        {
            case "AIRCRAFT":
                shipValue = 9;
                shipSize  = 5;
                return;

            case "BATTLESHIP":
                shipValue = 8;
                shipSize  = 4;
                return;

            case "DESTROYER":
                shipValue = 7;
                shipSize  = 3;
                return;

            case "SUBMARINE":
                shipValue = 6;
                shipSize  = 3;
                return;

            case "PATROL":
                shipValue = 5;
                shipSize  = 2;
                return;
        }
    }
    //--------------------------------------------------------------------------------
}
