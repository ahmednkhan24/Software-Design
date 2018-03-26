// Zakee Jabbar (zjabba2)
// Azam Jamal (ajamal6)
// Ahmed Khan (akhan227)
// CS 342
// Project 3



import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Scanner;

// Makes the sudoku board and deals with the user input
public class GraphicsBoard
{
    private JFrame window;
    private Grid grid;
    private SidePanel sidePanel;
    private Board internalBoard;
    private Menus menu;
    private JLabel candidateLabel;
    private boolean valMode;
    private boolean candidateMode;
    private boolean clearMode;
    private boolean checkFillMode;
    private int curValue;

    // Constructor 1
    public GraphicsBoard()
    {
        window = new JFrame("Sudoku Player w/ Solver"); // JFrame that holds everything
        window.setBackground(Color.BLACK);
        grid = new Grid(); // Panel holding the subgrids
        sidePanel = new SidePanel();
        candidateLabel = new JLabel(" ");
        candidateLabel.setVisible(false);
        candidateLabel.setForeground(Color.RED);

        internalBoard = new Board(); // Makes the internal board
        //myRules = new Rules(internalBoard); // Makes the rules object
        menu = new Menus(this, internalBoard); // Makes the menus

        valMode = false;
        candidateMode = false;
        clearMode = false;
        checkFillMode = false;
        curValue = 0;

        SidePanelListener panelListener = new SidePanelListener(internalBoard, this);
        sidePanel.addListener(panelListener);

        GridListener gridListener = new GridListener(internalBoard, this);
        grid.addListener(gridListener);


        // Change the visual layout of the frame/panel
        window.setSize(530, 541);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.setJMenuBar(menu);
        window.add(grid,BorderLayout.CENTER);
        window.add(sidePanel, BorderLayout.EAST);
        window.add(candidateLabel, BorderLayout.SOUTH);


        // Make frame visible
        window.setVisible(true);

    }

    // Constructor 2
    public GraphicsBoard(String file)
    {
        window = new JFrame("Sudoku Player w/ Solver"); // JFrame that holds everything
        window.setBackground(Color.BLACK);
        grid = new Grid(); // Panel holding the subgrids
        sidePanel = new SidePanel();
        candidateLabel = new JLabel(" ");
        candidateLabel.setVisible(false);
        candidateLabel.setForeground(Color.RED);

        internalBoard = new Board(); // Makes the internal board
        //myRules = new Rules(internalBoard); // Makes the rules object
        menu = new Menus(this, internalBoard); // Makes the menus

        valMode = false;
        candidateMode = false;
        clearMode = false;
        checkFillMode = false;
        curValue = 0;

        SidePanelListener panelListener = new SidePanelListener(internalBoard, this);
        sidePanel.addListener(panelListener);

        GridListener gridListener = new GridListener(internalBoard, this);
        grid.addListener(gridListener);


        // Change the visual layout of the frame/panel
        window.setSize(530, 541);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.setJMenuBar(menu);
        window.add(grid,BorderLayout.CENTER);
        window.add(sidePanel, BorderLayout.EAST);
        window.add(candidateLabel, BorderLayout.SOUTH);

        // Make frame visible
        window.setVisible(true);

        File f = new File(file);
        loadBoard(f);

    }


    // Set/Get/Reset/Load functions
    public void setCurValue(int curValue)
    {
        this.curValue = curValue;
    }

    public int getCurValue()
    {
        return curValue;
    }

    public void setCandidateMode(boolean candidateMode)
    {
        this.candidateMode = candidateMode;
    }

    public boolean isCandidateMode()
    {
        return candidateMode;
    }

    public boolean isValMode()
    {
        return valMode;
    }

    public void setValMode(boolean valMode)
    {
        this.valMode = valMode;
    }

    public void setClearMode(boolean clearMode)
    {
        this.clearMode = clearMode;
    }

    public boolean isClearMode()
    {
        return clearMode;
    }

    public void resetCurrentMode(int value)
    {
        sidePanel.resetPrevious(value);
    }

    public void setCandidateLabel(String text)
    {
        candidateLabel.setText(text);
    }

    public void toggeCandidateLabel(boolean isOn)
    {
        candidateLabel.setVisible(isOn);
        candidateLabel.setText(" ");
        window.repaint();
    }

    public void setValue(int xPos, int yPos, int value, boolean isRemovable)
    {
        grid.setValue(xPos, yPos, value, isRemovable);
    }

    public void resetBoard()
    {
        grid.resetGrid();
    }

    public boolean isCheckFillMode()
    {
        return checkFillMode;
    }

    public void setCheckFillMode(boolean checkFillMode)
    {
        this.checkFillMode = checkFillMode;
    }

    public void loadBoard(File f)
    {
        try
        {
            internalBoard.resetBoard();
            resetBoard();
            internalBoard.initCandidateLists();
            Scanner s = new Scanner(f);
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
                    setValue(row, col, val, false);
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
            System.err.println("File not found!");
        }
    }
}
