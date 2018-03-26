//*******************************************************
//  Name:	 	 Ahmed N Khan
//   UIN:     	 652469935
// NetID:    	 akhan227
// Class:    	 CS 342 - Software Design - UIC 2017
// Assignment: 	 Programming Project #2: Fifteen Puzzle
//*******************************************************

/*
	mainProject.java
	
	This class contains the main method for this program
	creates the About title box that describes the game, 
	and provides help on how to use the program
	
	Methods:
		main()			- main method for program
		project()		- class constructor
		create()		- creates the about title box
		
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JTextField;


public class mainProject
{
	JFrame frame;
	private JTextField InfoText;
	private JTextField undotxt;
	private JTextField mixtxt;
	private JTextField solvetxt;
	private JTextField undoalltxt;
	private JTextField ectxt;

	
	
	// Main Method
	//----------------------------------------------------------------------
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					mainProject window = new mainProject();
					window.frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	//----------------------------------------------------------------------

	
	// method to call the initialize method
	//----------------------------------------------------------------------
	public mainProject() 
	{
		Create();
	}
	//----------------------------------------------------------------------
	
	
	// method to create the frame and run the game
	//----------------------------------------------------------------------
	private void Create() 
	{
		// create the frame for the main home page
		frame = new JFrame();
		
		// set the home page color
		frame.getContentPane().setBackground(Color.BLACK);
		
		// standard layout
		frame.getContentPane().setLayout(null);
		
		
		
	
		
		
		// create text field to hold name, due date, and project name
		InfoText = new JTextField();
		
		// set the color of the text field
		InfoText.setForeground(Color.WHITE);
		InfoText.setBackground(Color.BLACK);
		
		// set the text for the text field
		InfoText.setText("Ahmed Khan, due Oct 4, 2017, CS 342 Program #2");
		
		// set the coordinates of the text field
		InfoText.setBounds(65, 0, 297, 66);
		frame.getContentPane().add(InfoText);
		InfoText.setColumns(10);
		
		
		
		
		
		
		// button that will start the game
		JButton playGameBtn = new JButton("Play Game");
		playGameBtn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				FifteenPuzzle game = new FifteenPuzzle();
			}
		});
		
		// set the color for the button
		playGameBtn.setForeground(Color.BLACK);
		playGameBtn.setBackground(Color.GREEN);
		
		// set the coordinates for the button
		playGameBtn.setBounds(150, 187, 117, 53);
		frame.getContentPane().add(playGameBtn);
		

		
		
		
		
		// create text area for game description
		JTextArea txtrGetAllThe = new JTextArea();
		
		// set the color for the description text field
		txtrGetAllThe.setForeground(Color.RED);
		txtrGetAllThe.setBackground(Color.DARK_GRAY);
		
		// set the text for the description text field
		txtrGetAllThe.setText("Get all the numbers in ascending order to win!");
		
		// set the coordinates for the description text field
		txtrGetAllThe.setBounds(87, 67, 254, 26);
		frame.getContentPane().add(txtrGetAllThe);
		
		
		// create text prompts for the about for the program
		
		// text for Undo
		undotxt = new JTextField();
		undotxt.setForeground(Color.ORANGE);
		undotxt.setBackground(Color.DARK_GRAY);
		undotxt.setText("Undo: cancels the most recent move");
		undotxt.setBounds(102, 95, 205, 22);
		frame.getContentPane().add(undotxt);
		undotxt.setColumns(10);
		
		// text for mix/shuffle
		mixtxt = new JTextField();
		mixtxt.setForeground(Color.MAGENTA);
		mixtxt.setBackground(Color.DARK_GRAY);
		mixtxt.setText("Mix: re-shuffles the board");
		mixtxt.setBounds(132, 141, 150, 22);
		frame.getContentPane().add(mixtxt);
		mixtxt.setColumns(10);
		
		// text for auto-solve
		solvetxt = new JTextField();
		solvetxt.setForeground(Color.CYAN);
		solvetxt.setBackground(Color.DARK_GRAY);
		solvetxt.setText("Solve: Computer solves the puzzle");
		solvetxt.setBounds(111, 163, 196, 22);
		frame.getContentPane().add(solvetxt);
		solvetxt.setColumns(10);
		
		// text for undo all
		undoalltxt = new JTextField();
		undoalltxt.setForeground(Color.BLUE);
		undoalltxt.setBackground(Color.DARK_GRAY);
		undoalltxt.setText("Undo All: Resets the board");
		undoalltxt.setBounds(126, 120, 167, 22);
		frame.getContentPane().add(undoalltxt);
		undoalltxt.setColumns(10);
		
		// text telling grader if extra credit was attempted
		ectxt = new JTextField();
		ectxt.setBackground(Color.YELLOW);
		ectxt.setText("Extra Credit not attempted");
		ectxt.setBounds(270, 202, 150, 22);
		frame.getContentPane().add(ectxt);
		ectxt.setColumns(10);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

