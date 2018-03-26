import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Vector;

public class gameChecks
{	// open class
    // jfsklsjflk
    // the board
    coord [][] board;
    private int numShots;

    //--------------------------------------------------------------------------------
    //
    // Constructor
    //
    public gameChecks(coord [][] board)
    {
        this.board = board;
        this.numShots = 0;
    }
    //--------------------------------------------------------------------------------
    //
    // isLegal
    //
    // checks if the move is legal
    //
    public boolean isLegal(coord head, coord tail, char orientation, int shipSize)
    {
        int xPos = head.getxPos();
        int yPos = head.getyPos();

        int delim = shipSize;


        if (orientation == 'h')
        {
            // check if the size of the ship is within bounds of the board
            if (tail.getyPos() > 9)
                return false;

            // check to make sure the positions the ship is going to go on is empty
            for (int i = yPos; i < yPos + delim; i++)
            {
                coord cur = board[xPos][i];

                if (cur.getVal() != 0)
                    return false;
            }
        }
        else
        {
            // check if the size of the ship is within bounds of the board
            if (tail.getxPos() > 9)
                return false;


            // check to make sure the positions the ship is going to go on is empty
            for (int i = xPos; i < xPos + delim; i++)
            {
                coord cur = board[i][yPos];

                if (cur.getVal() != 0)
                    return false;
            }
        }

        return true;
    }
    //--------------------------------------------------------------------------------
    //
    // addShipToBoard
    //
    // add's the desired value into the board at the specific locations,
    // depending on how big the ship should be
    // returns true if add was successful
    // returns false otherwise
    //
    // val   = number denoting what type of ship occupies the location
    //
    public boolean addShipToBoard(ship s)
    {
        int delim = s.getShipSize();
        int val   = s.getShipValue();

        int counter = 0;

        coord start = s.getStart();

        int xPos = start.getxPos();
        int yPos = start.getyPos();


        // insert it horizontally
        if (s.isHorizontal())
        {
            // add the ship to the board
            for (int i = yPos; i < yPos + delim; i++)
            {
                board[xPos][i].setValue(val);
                board[xPos][i].setToOccupied();
                board[xPos][i].setCurrentShip(s);

                //add the iamges to the board
                addHImage(delim, counter, board[xPos][i]);
                counter++;
            }
        }
        // insert it vertically
        else
        {
            // add the ship to the board
            for (int i = xPos; i < xPos + delim; i++)
            {
                board[i][yPos].setValue(val);
                board[i][yPos].setToOccupied();
                board[i][yPos].setCurrentShip(s);
                //add the image to the board
                addVImage(delim, counter, board[i][yPos]);
                counter++;
            }
        }

        // insert successful
        return true;
    }


    //--------------------------------------------------------------------------------
    //function adds image to the board
    private void addHImage(int size, int index, coord spot){

        if (index == 0) {
            try {
                Image waterImage = ImageIO.read(getClass().getResource("hback.gif"));
                spot.setIcon(new ImageIcon(waterImage));
                spot.setBackOfShip(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //if if its the end of the boat
        else if (index == size-1){
            try {
                Image waterImage = ImageIO.read(getClass().getResource("hfront.gif"));
                spot.setIcon(new ImageIcon(waterImage));
                spot.setFrontOfShip(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                Image waterImage = ImageIO.read(getClass().getResource("hmiddle.gif"));
                spot.setIcon(new ImageIcon(waterImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //--------------------------------------------------------------------------------
    //function adds image to the board
    private void addVImage(int size, int index, coord spot){

        if (index == 0) {
            try {
                Image waterImage = ImageIO.read(getClass().getResource("vback.gif"));
                spot.setIcon(new ImageIcon(waterImage));
                spot.setBackOfShip(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //if if its the end of the boat
        else if (index == size-1){
            try {
                Image waterImage = ImageIO.read(getClass().getResource("vfront.gif"));
                spot.setIcon(new ImageIcon(waterImage));
                spot.setFrontOfShip(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                Image waterImage = ImageIO.read(getClass().getResource("vMiddle.gif"));
                spot.setIcon(new ImageIcon(waterImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //--------------------------------------------------------------------------------
    //
    // checkForLoss
    //
    // goes through the board passed to it and
    // returns true if all ships have been sunk
    // returns false otherwise
    //
    public boolean checkForLoss(Vector<ship> ships)
    {
        for (ship s : ships)
        {
            if (!s.shipIsSunk())
                return false;
        }
        return true;
    }
    //--------------------------------------------------------------------------------
    //
    // shoot
    //
    // attempts to make a shot on the board at the given location
    // updates board if a hit was achieved
    // returns true if a hit was achieved
    // returns false otherwise
    //
    public boolean shoot(Vector<ship> ships, coord location)
    {
        this.numShots++;

        boolean success;

        for (ship s : ships)
        {
            success = isaHit(s, location);

            if (success)
            {
                board[location.getxPos()][location.getyPos()].setValue(-1);

                return true;
            }

        }
        return false;
    }
    //--------------------------------------------------------------------------------
    //
    // isHit
    //
    // checks to see if the sent location would achieve a hit
    // returns true if hit can be achieved
    // false otherwise
    //
    private boolean isaHit(ship s, coord location)
    {
        coord start = s.getStart();
        coord end = s.getEnd();

        int x = location.getxPos();
        int y = location.getyPos();


        if (s.isHorizontal())
        {
            // check to see if the clicked button is in the same row as the ship,
            // then check to see if the y location creates a hit
            if (x == start.getxPos() && (y >= start.getyPos() && y <= end.getyPos()))
                return true;
        }
        else
        {
            // check to see if the clicked button is in the same column as the ship
            // then check to see if the x location creates a hit
            if (y == start.getyPos() && (x >= start.getxPos() && x <= end.getxPos()))
                return true;
        }

        return false;
    }
    //--------------------------------------------------------------------------------

}	// close class
