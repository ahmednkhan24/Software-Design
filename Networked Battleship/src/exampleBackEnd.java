import java.util.Vector;

public class exampleBackEnd
{

    private static int SIZE = 10;

    private static coord underArr[][];


    public exampleBackEnd()
    {
        underArr = new coord[SIZE][SIZE];

        init();

        System.out.println("Empty Board:");
        print();

        ship aircraft   = new ship(underArr, "Aircraft",   new coord(0,0), new coord(0,4));
        ship battleship = new ship(underArr, "Battleship", new coord(6,9), new coord(9,9));
        ship destroyer  = new ship(underArr, "Destroyer",  new coord(4,4), new coord(4,6));
        ship submarine  = new ship(underArr, "Submarine",  new coord(7,1), new coord(9,1));
        ship patrol     = new ship(underArr, "Patrol",     new coord(1,7), new coord(2,7));

        ship extra      = new ship(underArr, "patrol", new coord(1,7), new coord(1, 9));


        gameChecks g = new gameChecks(underArr);


        // add all the ships to the board to make sure those functions work properly

        if (g.addShipToBoard(aircraft))
            System.out.println("Aircraft   added succesfully");
        if (g.addShipToBoard(battleship))
            System.out.println("Battleship added succesfully");
        if (g.addShipToBoard(destroyer))
            System.out.println("Destroyer  added succesfully");
        if (g.addShipToBoard(submarine))
            System.out.println("Submarine  added succesfully");
        if (g.addShipToBoard(patrol))
            System.out.println("Patrol     added succesfully");

        if (!g.addShipToBoard(extra))
            System.out.println("ship NOT   added succesfully (good)\n");

        System.out.println("Board after all pieces have been placed");
        print();


        Vector<ship> ships = new Vector<>();

        ships.add(aircraft);
        ships.add(battleship);
        ships.add(destroyer);
        ships.add(submarine);
        ships.add(patrol);

        // sink all the boats to see if those functions work properly

        // sink the aircraft
        for (int i = 0; i < 5; i++)
        {
            g.shoot(ships, new coord(0, i));
        }
        System.out.println("Board after the aircraft has been sunk");
        print();
        if (g.checkForLoss(ships))
            System.out.println("\n\nYOU WON!!!!!!!!!!!\n\n");


        // sink the battleship
        for (int i = 6; i < 10; i++)
        {
            g.shoot(ships, new coord(i, 9));
        }
        System.out.println("Board after the battleship has been sunk");
        print();
        if (g.checkForLoss(ships))
            System.out.println("\n\nYOU WON!!!!!!!!!!!\n\n");


        // sink the destroyer
        for (int i = 4; i < 7; i++)
        {
            g.shoot(ships, new coord(4, i));
        }
        System.out.println("Board after the destroyer has been sunk");
        print();
        if (g.checkForLoss(ships))
            System.out.println("\n\nYOU WON!!!!!!!!!!!\n\n");


        // sink the submarine
        for (int i = 7; i < 10; i++)
        {
            g.shoot(ships, new coord(i, 1));
        }
        System.out.println("Board after the submarine has been sunk");
        print();
        if (g.checkForLoss(ships))
            System.out.println("\n\nYOU WON!!!!!!!!!!!\n\n");


        // sink the patrol boat
        for (int i = 1; i < 3; i++)
        {
            g.shoot(ships, new coord(i, 7));
        }
        System.out.println("Board after the patrol boat has been sunk");
        print();
        if (g.checkForLoss(ships))
            System.out.println("\n\nYOU WON!!!!!!!!!!!\n\n");




    }



    private void init()
    {
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                underArr[i][j] = new coord(i, j);
            }
        }
    }

    private void print()
    {
        System.out.println("\n\n------------------------------");

        System.out.print("   ");
        for (int i = 0; i < SIZE; i++)
        {
            System.out.print(i + " ");
        }
        System.out.println("\n");


        for (int i = 0; i < SIZE; i++)
        {
            System.out.print(i + "  ");
            for (int j = 0; j < SIZE; j++)
            {
                System.out.print(underArr[i][j].getVal() + " ");
            }
            System.out.println();
        }
        System.out.println("------------------------------\n\n");
    }


}
