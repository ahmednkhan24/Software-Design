/*
	BrainsOfProgram.java
	
	This class performs the underlying work needed to run the game
	but doesn't output anything to the user
	
	Methods:
		SwapUndo()		- class constructor
		GetWinning()	- returns the winning Tuple found by the BFS
		Swap()			- performs a swap of two buttons
		UndoAll()		- Resets the board to the original state of the last shuffle
		Undo()			- undoes the last move performed
		isLegalMove()	- checks if the move about to be done follows the puzzle requirements
		bfs()			- performs a breadth first search on the board to see if the puzzle is solvable
		bfsswap()		- private swap method the bfs uses in its search
		GetM()			- creates and returns the moves needed in the bfs (labeled M from the program instructions)
		GetP()			- creates and returns the current state in the bfs (labeled P from the program instructions)
		GameWin()		- checks to see if the game has been won from values
		
 */

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.Timer;

import javax.swing.JButton;

public class BrainsOfProgram
{	// open

	private Stack<Integer> MoveStack;
	
	private Tuple Winning;
	
	
	
	//----------------------------------------------------------------------
	//
	// SwapUndo:
	//
	// class constructor
	//
	public BrainsOfProgram()
	{
		MoveStack = new Stack<Integer>();
	}
	//----------------------------------------------------------------------
	//----------------------------------------------------------------------
	//
	// GetWinning:
	//
	// returns the winning set derived from the BFS
	//
	public Tuple GetWinning()
	{
		return Winning;
	}
	//----------------------------------------------------------------------
	//----------------------------------------------------------------------	
	//
	// Swap:
	//
	// Swap method
	//
	public boolean Swap(JButton[] btns, int num1, int num2)
	{
		int EmptyPos = -1;
		int OtherPos = -1;
		
		// figure out which is the empty spot
		if (btns[num1].getText() == " ")
		{
			EmptyPos = num1;
			OtherPos = num2;
		}
		else if (btns[num2].getText() == " ")
		{
			EmptyPos = num2;
			OtherPos = num1;
		}
		else
			return false;
		
		
		// check if the move is legal
		if (isLegalMove(EmptyPos, OtherPos) == false)
			return false;
		
		
		// at this point, we know where the space is and if the move is legal
		
		// push the original values of each button 
		MoveStack.push(EmptyPos);
		MoveStack.push(OtherPos);
		
		
		// swap 
		String tmp = btns[EmptyPos].getText();
		btns[EmptyPos].setText(btns[OtherPos].getText());
		btns[OtherPos].setText(tmp);
		
		return true;
	}
	//----------------------------------------------------------------------
	//----------------------------------------------------------------------
	//
	// UndoAll:
	//
	// undoes all the moves in the game, resetting the board
	//
	public void UndoAll(JButton[] btns)
	{
		//Timer t = new Timer();
		while (!MoveStack.isEmpty())
		{
			//t.scheduleAtFixedRate(Undo(btns), new Date(0), 1000);
			Undo(btns);
		}
	}
	//----------------------------------------------------------------------
	//----------------------------------------------------------------------	
	//
	// Undo:
	//
	// Undoes the last move
	//
	public boolean Undo(JButton[] btns)
	{
		if (MoveStack.isEmpty())
			return false;
		
		int EmptyPos = MoveStack.pop();
		int OtherPos = MoveStack.pop();
		
		String tmp = btns[EmptyPos].getText();
		btns[EmptyPos].setText(btns[OtherPos].getText());
		btns[OtherPos].setText(tmp);
		
		return true;
	}
	//----------------------------------------------------------------------	
	//----------------------------------------------------------------------	
	//
	// isLegalMove:
	// 
	// returns true if the move is legal, otherwise returns false
	//
	private boolean isLegalMove(int EmptyIndex, int MoveIndex)
	{	// open isLegalMove
		
		// right:  index + 1
		// left:   index - 1
		// down:   index + 4
		// up:     index - 4
		
		switch (EmptyIndex)
		{	// open switch
			case 5:			// left right down up
			case 6:			// left right down up
			case 9:			// left right down up
			case 10:		// left right down up
				if ((EmptyIndex - 1 == MoveIndex) || (EmptyIndex + 1 == MoveIndex))			// left right
					return true;
				else if ((EmptyIndex - 4 == MoveIndex) || (EmptyIndex + 4 == MoveIndex))	// up down
					return true;
				break;	
			case 4:			// 		right down up
			case 8:			// 		right down up
				if ((EmptyIndex - 4 == MoveIndex) || (EmptyIndex + 4 == MoveIndex))			// up down
					return true;
				else if (EmptyIndex + 1 == MoveIndex)										// right
					return true;
				break;
			case 7:			// left 	  down up
			case 11:		// left 	  down up
				if ((EmptyIndex - 4 == MoveIndex) || (EmptyIndex + 4 == MoveIndex))			// up down
					return true;
				else if (EmptyIndex - 1 == MoveIndex)										// left
					return true;
				break;	
			case 1:			// left right down
			case 2:			// left right down
				if ((EmptyIndex - 1 == MoveIndex) || (EmptyIndex + 1 == MoveIndex))			// left right
					return true;
				else if (EmptyIndex + 4 == MoveIndex)										// down
					return true;
				break;	
			case 13:		// left right 		up	
			case 14:		// left right 		up
				if ((EmptyIndex - 1 == MoveIndex) || (EmptyIndex + 1 == MoveIndex))			// left right
					return true;
				else if (EmptyIndex - 4 == MoveIndex)										// up
					return true;
				break;	
			case 0:	
				if ((EmptyIndex + 1 == MoveIndex) || (EmptyIndex - 4 == MoveIndex))			// right down
					return true;
				break;
			case 3:			// left 	  down
				if ((EmptyIndex - 1 == MoveIndex) || (EmptyIndex + 4 == MoveIndex))			// left down
					return true;
				break;
			case 12:		// 		right 	   up
				if ((EmptyIndex + 1 == MoveIndex) || (EmptyIndex - 4 == MoveIndex))			// right up
					return true;
				break;
			case 15:		// left 			up
				if ((EmptyIndex - 1 == MoveIndex) || (EmptyIndex - 4 == MoveIndex))			// left up
					return true;
				break;
			default:																		// invalid input
				break;
		}	// close switch
		return false;
	}	// close isLegalMove
	//----------------------------------------------------------------------	
	
	
	
	
	
	// begin code for the breadth fist search
	//----------------------------------------------------------------------
	/*
	1.  Create an empty queue and an empty set.
	2.  Let P represent the present arrangement of puzzle pieces.
	3.  Create the tuple (P, null)
	4.  Add (P, null) to the end of the queue (enqueue)
	5.  Add P to the set (mark it as visited)
	6.  While the queue is not empty
	7.  Current = front item in the queue; remove this item from the queue (dequeue)
	8.  If arrangement in Current is the solved puzzle
	 		Stop – found solution
	9.  For each arrangement R that can be formed by moving one piece from arrangement in Current
	10. If R is not in the set
	11. Create the tuple (R, M) where M is the movement list from Current with 
		the addition of the move that was used to form arrangement R
	12. Add (R, M) to the end of the queue (enqueue)
	13. Add R to the set
	14. If queue is empty
			No solution exists (the randomization created an unsolvable 
	*/
	//
	// BFS:
	//
	// Performs a breadth first search on the board to determine if the puzzle is solvable
	//
	public boolean BFS(JButton[] btns, int EmptyPos)
	{	// open bfs
		
		// 1. an empty queue and an empty set.
		Queue<Tuple> MyQueue = new LinkedList<Tuple>();
		Set<ArrayList<Integer>> MySet = new LinkedHashSet<ArrayList<Integer>>();
		
		// 2. Let P represent the present arrangement of puzzle pieces
		ArrayList<Integer> P = GetP(btns);
		
		// 3. Create the tuple (P, null)
		Tuple MyTuple = new Tuple(P, null);
		
		// 4. Add (P, null) to the end of the queue (enqueue)
		MyQueue.add(MyTuple);
		
		// 5. Add P to the set (mark it as visited)
		MySet.add(P);
		
		
		while (!MyQueue.isEmpty())	// 6. While the queue is not empty
		{	// open while
			
			// 7. Current = front item in the queue; remove this item from the queue (dequeue)
			Tuple Current = MyQueue.remove();	
			
			// 8. If arrangement in Current is the solved puzzle, Stop – found solution
			if (GameWin(Current.p()))
			{
				Winning = Current;
				return true;
			}
			
			// 9. For each arrangement R that can be formed by moving one piece from arrangement in Current
			ArrayList<Integer> moves = GetM(btns, EmptyPos);
		
			ArrayList<Integer> R;
			while (!moves.isEmpty())
			{
				int tmp = moves.get(0);
				moves.remove(0);
				
				R = bfsswap(Current.p(), EmptyPos, tmp);
				
				// 10. If R is not in the set
				if (!MySet.contains(R))
				{
					// 11. Create the tuple (R, M) where M is the movement list from Current with 
					//		the addition of the move that was used to form arrangement R
					Tuple NewTuple = new Tuple(R, GetM(btns, EmptyPos));
					
					// 12. Add (R, M) to the end of the queue (enqueue)
					MyQueue.add(NewTuple);
					
					// 13. Add R to the set
					MySet.add(R);
				}
			}
		}	// close while
		
		// 14. If queue is empty
		// 		No solution exists (the randomization created an unsolvable puzzle)
		// if we got here, that means we did not return in the while loop which automatically means
		// the queue is empty
		return false;
	}	// close bfs
	
	//----------------------------------------------------------------------
	//
	// bfsswap:
	//
	// private swap method used for the bfs to look at potential moves
	//
	private ArrayList<Integer> bfsswap(ArrayList<Integer> board, int EmptyPos, int OtherPos)
	{
		ArrayList<Integer> arr = new ArrayList<Integer>();
		
		arr = board;
		
		int tmp = board.get(EmptyPos);
		
		arr.remove(EmptyPos);
		
		arr.add(EmptyPos-1, arr.get(OtherPos));
		
		arr.add(OtherPos, tmp);
		
		return arr;	
	}
	//----------------------------------------------------------------------
	//----------------------------------------------------------------------
	// 
	// GetM:
	//
	// creates 'M'. According to project handout, M is the movement list from 
	// Current with  the addition of the move that was used to form arrangement R
	//
	private ArrayList<Integer> GetM(JButton[] btns, int EmptyPos)
	{
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		
		if (isLegalMove(EmptyPos, EmptyPos-1))
			tmp.add(EmptyPos-1);
		if (isLegalMove(EmptyPos, EmptyPos+1))
			tmp.add(EmptyPos+1);
		if (isLegalMove(EmptyPos, EmptyPos-4))
			tmp.add(EmptyPos-4);
		if (isLegalMove(EmptyPos, EmptyPos+4))
			tmp.add(EmptyPos+4);
		
		return tmp;
		
	}
	//----------------------------------------------------------------------
	//----------------------------------------------------------------------
	// 
	// creates 'P'. According to project handout, 
	// P represent the present arrangement of puzzle pieces
	//
	private ArrayList<Integer> GetP(JButton[] btns)
	{
		ArrayList<Integer> arr = new ArrayList<Integer>();
		
		for (int i = 0; i < 16; i++)
		{
			arr.add(Integer.parseInt(btns[i].getText()));
		}
		
		return arr;
	}
	//----------------------------------------------------------------------
	//----------------------------------------------------------------------
	// 
	// GameWin:
	// 
	// uses an ArrayList to determine if the game is in a winning state for the bfs
	//
	private boolean GameWin(ArrayList<Integer> buttons)
	{
		int counter = 0;
		
		for (int i = 0; i < 16; i++)
		{
			if (buttons.get(i) == i+1)
				counter++;
		}
		
		if (counter == 15)
			return true;
		else
			return false;
	}	
	//----------------------------------------------------------------------
}	// close
