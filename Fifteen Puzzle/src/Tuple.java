/*
	Tuple.java
	
	This class creates a Tuple object needed for the breadth first search
	
	Methods:
		Tuple() - class constructor
		p()		- returns the current state of board (labeled p from program instructions)
		m()		- returns the possible moves (labeled m from program instructions)
	
 */

import java.util.ArrayList;

public class Tuple 
{
	
	private ArrayList<Integer> P;
	private ArrayList<Integer> M;
	
	// constructor
	public Tuple(ArrayList<Integer> p, ArrayList<Integer> m)
	{
		P = p;
		M = m;
	}
	
	// getters
	public ArrayList<Integer> p()
	{
		return P;
	}
	public ArrayList<Integer> m()	
	{
		return M;
	}

}
