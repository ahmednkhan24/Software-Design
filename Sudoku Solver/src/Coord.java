// Zakee Jabbar (zjabba2)
// Azam Jamal (ajamal6)
// Ahmed Khan (akhan227)
// CS 342
// Project 3

// Helper class for algorithms
public class Coord
{
    private int xPos;
    private int yPos;

    public Coord(int x, int y)
    {
        xPos = x;
        yPos = y;
    }

    public int getxPos()
    {
        return xPos;
    }

    public int getyPos()
    {
        return yPos;
    }

    public boolean equals(Coord c1)
    {
        if(c1.xPos == this.xPos && c1.yPos == this.yPos)
        {
            return true;
        }
        return false;
    }
}
