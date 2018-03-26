// Zakee Jabbar (zjabba2)
// Azam Jamal (ajamal6)
// Ahmed Khan (akhan227)
// CS 342
// Project 3


import javax.swing.*;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;


// Makes the menus for the game
public class Menus extends JMenuBar
{
    private GraphicsBoard graphicsBoard;
    private Board internalBoard;
    private Timer solveTimer;
    private int attempts;

    // Constructor
    public Menus(GraphicsBoard gB, Board b)
    {
        super();

        graphicsBoard = gB;
        internalBoard = b;
        attempts = 0;

        // Timer for moves of solve
        solveTimer = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                if(internalBoard.isWinner())
                {
                    JOptionPane.showMessageDialog(null,"Congratulations puzzle solved!");
                    solveTimer.stop();
                }
                if(attempts == 10) // Too many iterations without any cell resolved
                {
                    JOptionPane.showMessageDialog(null,"Unsolvable puzzle!");
                    solveTimer.stop();

                }
                internalBoard.nakedPair(); // Naked Pair algorithm
                int[] coord = internalBoard.single(); // Single Algorithm
                if(coord[0] != -1)
                {
                    internalBoard.setPosValue(coord[0], coord[1], coord[2]);
                    graphicsBoard.setValue(coord[0] + 1, coord[1] + 1, coord[2], true);
                    internalBoard.updateCandidateListsRemove(coord[0], coord[1], coord[2]);
                    attempts = 0;
                }
                int ans[] = internalBoard.hiddenSingle(); // Hidden Single Algorithm
                if(ans != null)
                {
                    internalBoard.setPosValue(ans[0], ans[1], ans[2]);
                    graphicsBoard.setValue(ans[0] + 1, ans[1] + 1, ans[2], true);
                    internalBoard.updateCandidateListsRemove(ans[0], ans[1], ans[2]);
                    attempts = 0;
                }
                attempts++;
            }
        });

        // File Menu
        JMenu fileMenu = new JMenu( "File" );
        fileMenu.setMnemonic( 'F' );


        JMenuItem loadFile = new JMenuItem( "Load File" );
        loadFile.setMnemonic( 'l' );
        fileMenu.add(loadFile);
        loadFile.addActionListener(

                new ActionListener() {  // anonymous inner class

                    // Load file option
                    public void actionPerformed( ActionEvent event )
                    {
                        final JFileChooser fc = new JFileChooser();
                        fc.setFileFilter(new FileNameExtensionFilter("TEXT FILES", "txt", "text"));
                        File workingDirectory = new File(System.getProperty("user.dir"));
                        fc.setCurrentDirectory(workingDirectory);
                        Scanner s;

                        int returnVal = fc.showOpenDialog(null);
                        if (returnVal == JFileChooser.APPROVE_OPTION)
                        {
                            File file = fc.getSelectedFile();
                            try
                            {
                                internalBoard.resetBoard();
                                graphicsBoard.resetBoard();
                                internalBoard.initCandidateLists();
                                s = new Scanner(file);
                                // read the file and fill the array
                                while (s.hasNext()) {    // open while loop

                                    int row = Integer.parseInt(s.next());
                                    int col = Integer.parseInt(s.next());
                                    int val = Integer.parseInt(s.next());

                                    boolean inCand = internalBoard.inCandidateList(row - 1, col - 1, val);
                                    boolean isEmpty = internalBoard.isEmpty(row - 1, col - 1);
                                    if (row <= 9 && col <= 9 && val <= 9 && inCand && isEmpty)
                                    {
                                        internalBoard.setPosValue(row - 1, col - 1, val);
                                        graphicsBoard.setValue(row, col, val, false);
                                        internalBoard.initCandidateLists();
                                    }
                                    else
                                    {
                                        System.out.println(row + "," + col + "," + val + ": Invalid input!");
                                    }

                                }	// close while loop

                                // done! close the file

                                s.close();
                                internalBoard.initCandidateLists();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            System.out.println("Open command cancelled by user.");
                        }



                    }

                }  // end anonymous inner class

        ); // end call to addActionListener

        JMenuItem saveFile = new JMenuItem( "Save File" );
        loadFile.setMnemonic( 's' );
        fileMenu.add(saveFile);
        saveFile.addActionListener(

                new ActionListener() {  // anonymous inner class

                    // Save current puzzle into a user defined file name
                    public void actionPerformed( ActionEvent event )
                    {
                        File workingDirectory = new File(System.getProperty("user.dir"));
                        JFileChooser fc = new JFileChooser(workingDirectory);

                        // only text files allowed
                        fc.setFileFilter(new FileNameExtensionFilter("TEXT FILES", "txt", "text"));

                        fc.setDialogTitle("Save a file");

                        // JFileChooser returns a constant value, opens the box for user to choose file
                        int ReturnValue = fc.showSaveDialog(null);

                        // if user chose a text file to create and write to
                        if (ReturnValue == JFileChooser.APPROVE_OPTION)
                        {
                            File f;
                            // get the file path, and also add the .txt extension just in case they didn't
                            if(fc.getSelectedFile().getName().contains(".txt"))
                            {
                                f = new File(fc.getSelectedFile().getName());
                            }
                            else
                            {
                                f = new File(fc.getSelectedFile()  + ".txt");
                            }
                            int underArr[][] = internalBoard.getBoard();

                            try
                            {
                                // allow a file writer variable access to the file to write to
                                FileWriter fileWriter = new FileWriter(f.getPath());


                                // use a BufferedWriter for larger files
                                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                                // put every row, column, and value from the underlying array into the file
                                for (int i = 0; i < 9; i++)
                                {
                                    for (int j = 0; j < 9; j++)
                                    {

                                        String row = Integer.toString(i+1);
                                        String col = Integer.toString(j+1);
                                        String val = Integer.toString(underArr[i][j]);

                                        if(underArr[i][j] != 0)
                                        {
                                            bufferedWriter.write(row);
                                            bufferedWriter.write(" ");
                                            bufferedWriter.write(col);
                                            bufferedWriter.write(" ");
                                            bufferedWriter.write(val);

                                            bufferedWriter.newLine();		// add new line
                                        }

                                    }
                                }

                                // close file
                                bufferedWriter.close();
                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }


                        }



                    }

                }  // end anonymous inner class

        ); // end call to addActionListener

        // set up Exit menu item
        JMenuItem exitItem = new JMenuItem( "Exit" );
        exitItem.setMnemonic( 'x' );
        fileMenu.add( exitItem );
        exitItem.addActionListener(

                new ActionListener() {  // anonymous inner class

                    // terminate application when user clicks Exit
                    public void actionPerformed( ActionEvent event )
                    {
                        System.exit( 0 );
                    }

                }  // end anonymous inner class

        ); // end call to addActionListener

        add( fileMenu );

        // Help menu
        JMenu helpMenu = new JMenu( "Help" );
        fileMenu.setMnemonic( 'H' );

        // set up About... menu item
        JMenuItem aboutItem = new JMenuItem( "About..." );
        aboutItem.setMnemonic( 'A' );
        helpMenu.add( aboutItem );
        aboutItem.addActionListener(

                new ActionListener() {  // anonymous inner class

                    // display message dialog when user selects About...
                    public void actionPerformed( ActionEvent event )
                    {
                        JOptionPane.showMessageDialog( Menus.this,
                                "Authors:\nZakee Jabbar (zjabba2)\n" +
                                        "Ahmed Khan (akhan227)\n"
                                        + "Azam Jamal (ajamal6)\n",
                                "About", JOptionPane.PLAIN_MESSAGE);
                    }

                }  // end anonymous inner class

        ); // end call to addActionListener


        // How to use game item
        JMenuItem howItem = new JMenuItem( "How to use game..." );
        helpMenu.add( howItem );
        howItem.addActionListener(

                new ActionListener() {  // anonymous inner class

                    // display message dialog when user selects how to play
                    public void actionPerformed( ActionEvent event )
                    {
                        JOptionPane.showMessageDialog( Menus.this,
                                "File -> Load File: Load a puzzle\n" +
                                        "File -> Save file: Save current puzzle to file\n" +
                                        "File -> Quit: To Quit Game\n" +
                                        "Help -> About...: Authors information\n" +
                                        "Help -> How to use game: Get to here\n" + "Help -> How to play Sudoku: Rules of Sudoku\n" +
                                        "Hints -> Check on fill: Ensure that the input is valid\n" +
                                        "Hints -> Single: Resolve a spot using the Single Algorithm\n" +
                                        "Hints -> Hidden Single: Resolve a spot using the Hidden Single Algorithm\n" +
                                        "Hints -> Locked Candidate: Resolve a spot using the Locked Candidate Algorithm\n" +
                                        "Hints -> Naked Pairs: Resolve a spot using the Naked Pairs Algorithm\n" +
                                        "Hints -> Solve All: Repeatedly uses the algorithms to resolve all spots" +
                                        "\n\nThe current value/mode that is currently enabled will be filled with blue\n" +
                                        "while the other will be in red. There will be an error message if you try to\n" +
                                        "modify or remove any number that was originally part of the puzzle. For saving\n" +
                                        "a puzzle to file if you do not specify .txt it will be added for you.",
                                "How to use game", JOptionPane.PLAIN_MESSAGE );
                    }

                }  // end anonymous inner class

        ); // end call to addActionListener

        // How to play sudoku
        JMenuItem sudokuItem = new JMenuItem( "How to play Sudoku" );
        helpMenu.add( sudokuItem );
        sudokuItem.addActionListener(

                new ActionListener() {  // anonymous inner class

                    // display message dialog when user how to play sudoku
                    public void actionPerformed( ActionEvent event )
                    {
                        JOptionPane.showMessageDialog( Menus.this,
                                "The main goal of Sudoku is to fill the puzzle with the numbers 1-9\n" +
                                        "without the same number appearing more than once in each row, column,\n" +
                                        "and each sub group(3x3 squares).",
                                "How to play Sudoku", JOptionPane.PLAIN_MESSAGE );
                    }

                }  // end anonymous inner class

        ); // end call to addActionListener

        add(helpMenu);


        // Menu for the hints
        JMenu hintsMenu = new JMenu( "Hints" );
        fileMenu.setMnemonic( 'H' );

        // Check on fill
        JCheckBoxMenuItem checkItem = new JCheckBoxMenuItem("Check on fill", false);
        checkItem.setMnemonic( 'C' );
        hintsMenu.add( checkItem );
        checkItem.addActionListener(

                new ActionListener() {  // anonymous inner class

                    // Check on fill
                    public void actionPerformed( ActionEvent event )
                    {
                        graphicsBoard.setCheckFillMode(checkItem.getState());
                        if(checkItem.getState())
                        {
                            JOptionPane.showMessageDialog(null, "Check on fill mode is on");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Check on fill mode is off");
                        }
                    }

                }  // end anonymous inner class

        ); // end call to addActionListener



        // set up Single algorithm
        JMenuItem singleItem = new JMenuItem( "Single" );
        singleItem.setMnemonic( 'S' );
        hintsMenu.add( singleItem );
        singleItem.addActionListener(

                new ActionListener() {  // anonymous inner class

                    //Uses the single algorithm to resolve a cell
                    public void actionPerformed( ActionEvent event )
                    {
                        int[] coord = internalBoard.single();
                        if(coord[0] != -1)
                        {
                            internalBoard.setPosValue(coord[0], coord[1], coord[2]);
                            graphicsBoard.setValue(coord[0] + 1, coord[1] + 1, coord[2], true);
                            internalBoard.updateCandidateListsRemove(coord[0], coord[1], coord[2]);
                        }

                        if(internalBoard.isWinner())
                        {
                            JOptionPane.showMessageDialog(null,"Congratulations puzzle solved!");
                            solveTimer.stop();
                        }
                    }

                }  // end anonymous inner class

        ); // end call to

        // set up Hidden Single menu item
        JMenuItem hiddenItem = new JMenuItem( "Hidden Single" );
        hintsMenu.add( hiddenItem );
        hiddenItem.addActionListener(

                new ActionListener() {  // anonymous inner class

                    // Hidden Single Algorithm
                    public void actionPerformed( ActionEvent event )
                    {
                        int ans[] = internalBoard.hiddenSingle();
                        if(ans != null)
                        {
                            internalBoard.setPosValue(ans[0], ans[1], ans[2]);
                            graphicsBoard.setValue(ans[0] + 1, ans[1] + 1, ans[2], true);
                            internalBoard.updateCandidateListsRemove(ans[0], ans[1], ans[2]);
                        }

                        if(internalBoard.isWinner())
                        {
                            JOptionPane.showMessageDialog(null,"Congratulations puzzle solved!");
                            solveTimer.stop();
                        }

                    }

                }  // end anonymous inner class

        ); // end call to addActionListener

        // set up Locked Candidate menu item
        JMenuItem lockedItem = new JMenuItem( "Locked Candidate" );
        lockedItem.setMnemonic( 'L' );
        hintsMenu.add( lockedItem );
        lockedItem.addActionListener(

                new ActionListener() {  // anonymous inner class

                    // Locked candidate algorithm
                    public void actionPerformed( ActionEvent event )
                    {
                        JOptionPane.showMessageDialog(null, "Not fully functional but\n" +
                                "code in Board class");

                    }

                }  // end anonymous inner class

        ); // end call to addActionListener


        JMenuItem pairsItem = new JMenuItem( "Naked Pairs" );
        hintsMenu.add( pairsItem );
        pairsItem.addActionListener(

                new ActionListener() {  // anonymous inner class

                    // Naked pair algorithm
                    public void actionPerformed( ActionEvent event )
                    {
                        internalBoard.nakedPair();
                    }

                }  // end anonymous inner class

        ); // end call to addActionListener

        JMenuItem solveItem = new JMenuItem( "Solve all" );
        hintsMenu.add( solveItem );
        solveItem.addActionListener(

                new ActionListener() {  // anonymous inner class

                    // Solves the puzzle when clicked
                    public void actionPerformed( ActionEvent event )
                    {
                        attempts = 0;
                        solveTimer.start();
                    }

                }  // end anonymous inner class

        ); // end call to addActionListener

        add(hintsMenu);
    }

}