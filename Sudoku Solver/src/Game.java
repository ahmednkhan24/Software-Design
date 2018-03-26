// Zakee Jabbar (zjabba2)
// Azam Jamal (ajamal6)
// Ahmed Khan (akhan227)
// CS 342
// Project 3


// Executes the game
public class Game
{
    public static void main(String args[])
    {
        GraphicsBoard gB;
        // If command line argument is given with a file name
        if(args.length > 0)
        {
            gB = new GraphicsBoard(args[0]);
        }
        else // No command line argument make blank board
        {
            gB = new GraphicsBoard();
        }

    }
}
