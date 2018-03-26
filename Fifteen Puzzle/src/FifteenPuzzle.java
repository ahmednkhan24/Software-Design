/*
	FifteenPuzzle.java
	
	This class is the brains behind the actual puzzle/game
	
	Methods:
		FifteenPuzzle()		- class constructor
		actionPerformed()	- called whenever user clicks a button
		EmptyPosIndex()		- finds the index of the empty position
		GameWin()			- checks to see if the game has been won from buttons
		Mix()				- Shuffles the board
		SolveGame()			- Solves the game
	
 */

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;




public class FifteenPuzzle extends JFrame implements ActionListener
{	
	
	private static final long serialVersionUID = 1L;

// open FifteenPuzzle
	//----------------------------------------------------------------------
	// array of buttons
	private JButton buttons[];
	
	// the display window
	JFrame window = new JFrame("Ahmed Khan");
	
	// panel holding the buttons
	JPanel panel1 = new JPanel();

	// the container
	private Container container;
	
	// will hold the layout (grid)
	private GridLayout grid;
	
	// these 2 numbers will store the value of the buttons clicked to swap
	private int num1, num2;
	
	// will hold the index of the space in the button array 
	private int EmptyPos;
	
	// SwapUndo object 
	private BrainsOfProgram Logic;
	
	// keeps track of the number of moves the player has taken
	private int NumMoves;
	
	// will be filled with random numbers 1-15 to populate the button text
	private ArrayList<Integer> NumbersArray;
	
	// will keep track if the player hit solve. If the player hit solve,
	// he/she did not win on his/her own, therefore Winning message 
	// should not be output
	private boolean PlayerClickedSolve = false;
	//----------------------------------------------------------------------
	

	
	
	
	
	//----------------------------------------------------------------------
	//
	// FifteenPuzzle:
	//
	// Class Constructor
	//
	public FifteenPuzzle()
	{	// start constructor
		
		// name of program
		super("Fifteen Puzzle");
		
		// initialize variables that will hold which buttons to swap
		num1 = -1;
		num2 = -1;
		
		// allows us to access methods from SwapUndo class
		Logic = new BrainsOfProgram();
		
		// initialize the number of moves to 0 when starting the game
		NumMoves = 0;
		
		// create the 4x4 grid
		grid = new GridLayout(5,4,10,10);
		
		
		container = getContentPane();
		container.setLayout(grid);
		
		// there are only 16 buttons, but 4 buttons for commands
		buttons = new JButton[20];
		
		// this will hold numbers ranging from 1 - 15 for the board
		NumbersArray = new ArrayList<Integer>();
		
		// fill the array list
		for (int i = 1; i <= 15; i++)
		{
			NumbersArray.add(i);
		}
		
		// shuffle the array list
		Collections.shuffle(NumbersArray);
		
		// fill the buttons in the array
		for (int i = 0; i < 20; i++)
		{
			// the empty button is always at the 15th index 
			if (i == 15)
			{
				buttons[i] = new JButton(" ");
				EmptyPos = 15;
			}
			else if (i > 15)
			{
				if (i == 16)
					buttons[i] = new JButton("UNDO");
				else if (i == 17)
					buttons[i] = new JButton("UNDO ALL");
				else if (i == 18)
					buttons[i] = new JButton("MIX");
				else if (i == 19)
					buttons[i] = new JButton("SOLVE");
			}
			else
				buttons[i] = new JButton(Integer.toString(NumbersArray.get(i)));
				//buttons[i] = new JButton(Integer.toString(i+1));	
			// UNCOMMENT THE CODE ABOVE THIS LINE AND COMMENT OUT THE LINE ABOVE THAT
			// IN ORDER TO CREATE THE BOARD IN ORDER FOR TESTING IF GAME CAN DETECT A WIN
			
			// add action listener so that interaction can be done
			buttons[i].addActionListener(this);
			container.add(buttons[i]);
		}
		
		// allows program to quit when you hit the X
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// size of box
		setSize(600, 600);
		// show box when run program
		setVisible(true);
		
	}	// end constructor
	//----------------------------------------------------------------------	
	
	
	
	
	
	
	
	//----------------------------------------------------------------------
	//
	// actionPerformed:
	//
	// method to handle actions
	//
	public void actionPerformed(ActionEvent event)
	{	// open action
		
		// get the action the user did
		JButton temp = (JButton) event.getSource();
		
		
		if (temp.getText().equals("UNDO"))
		{
			// undo the last move, and decrement the number of moves counter
			Logic.Undo(buttons);
			
			if (NumMoves != 0)
				NumMoves--;
			return;
		}
		else if (temp.getText().equals("UNDO ALL"))
		{
			// reset the board to its original state and reset the move counter
			Logic.UndoAll(buttons);
			NumMoves = 0;
			return;
		}
		else if (temp.getText().equals("MIX"))
		{
			// shuffle the board
			Mix();
			return;
		}
		else if (temp.getText().equals("SOLVE"))
		{
			SolveGame();
			return;
		}
		else
		{
			// a button was clicked to swap
			
			
			// keep track of the position of the button in the array
			int btnPos = -1;
			
			// loop through array to find the clicked button
			for (int i = 0; i < buttons.length; i++)
			{
				// if we found the clicked button, save the index
				if (temp.equals(buttons[i]))
				{
					btnPos = i;
					break;
				}
			}
		
			// we need to keep track of what buttons the user pressed
			// and make sure that we have 2 valid buttons pressed in
			// order to make the swap. 
			if (num1 == -1)
				num1 = btnPos;
			else if (num2 == -1)
				num2 = btnPos;
			
			// if num1 and num2 aren't -1, we have the values we want to swap
			// swap condition
			if (num1 != -1 && num2 != -1)
			{
				// call the swap method
				if (Logic.Swap(buttons, num1, num2) == true)
					NumMoves++;
				
				// reset number holders
				num1 = -1;
				num2 = num1;	
			}
			// print message to the console
			
			
			// check the board to see if the game has satisfied the win condition
			if (GameWin() == true)
			{
				if (PlayerClickedSolve == false)
					JOptionPane.showMessageDialog(this, "Congratulations, you won in " + NumMoves + " moves!");
				else
					JOptionPane.showMessageDialog(this, "Thanks for playing. Better luck next time!");
			}
			
		}
	}	// close action
	//----------------------------------------------------------------------	
	
	
	
	
	
	
	
	
	
	//----------------------------------------------------------------------
	//
	// EmptyPosIndex:
	//
	// Finds and returns the index of the empty button
	//
	private int EmptyPosIndex()
	{
		for (int i = 0; i < 16; i++)
		{
			if (buttons[i].getText().equals(" "))
				return i;
		}
		return EmptyPos;
	}
	//----------------------------------------------------------------------
	//----------------------------------------------------------------------
	// GameWin:
	//
	// checks if the game has been won
	//
	private boolean GameWin()
	{
		int counter = 0;
		
		// check the numbered buttons to see if they are in 
		// ascending order, which signifies a win
		for (int i = 0; i < 16; i++)
		{
			if (buttons[i].getText().equals(Integer.toString(i+1)))
				counter++;
		}
		
		// if the game has been won, counter will be 15
		if (counter == 15)
			return true;
		else
			return false;
	}	
	//----------------------------------------------------------------------
	//----------------------------------------------------------------------
	// Mix:
	//
	// shuffles the board 
	//
	private void Mix()
	{
		// declare a variable that will get random numbers
		Random rand = new Random();
		
		int swap;
		String temp;
		
		for (int i = 0; i < 15; i++)
		{
			// get a random number within the desired range 
			swap = rand.nextInt(15);
			
			// swap the numbers at i and the random number index to shuffle
			temp = buttons[swap].getText();
			buttons[swap].setText(buttons[i].getText());
			buttons[i].setText(temp);	
		}
		
		// find what index the empty button moved to
		EmptyPos = EmptyPosIndex();
		
		// swap the empty button with whatever button is at the 15th index
		temp = buttons[15].getText();
		buttons[EmptyPos].setText(temp);
		buttons[15].setText(" ");
	}
	//----------------------------------------------------------------------
	//----------------------------------------------------------------------
	// 
	// SolveGame:
	//
	// solves the game
	//
	private void SolveGame()
	{
		for (int i = 0; i < 15; i++)
		{
			buttons[i].setText(Integer.toString(i+1));
		}
		buttons[15].setText(" ");
		
		PlayerClickedSolve = true;
	}
	//----------------------------------------------------------------------
	
}	// close FifteenPuzzle