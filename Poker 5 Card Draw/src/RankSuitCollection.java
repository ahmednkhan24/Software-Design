/*
	RankSuitCollection.java
	
	This class is a helper class for the program.
	It creates an object that stores each suit and rank in a sorted hand 
	for easier access. Variables are private and only getters are used
	
	Methods:
		RankSuitCollection()		- Constructor, stores all the ranks and suits
		GetSuit0() - GetSuit4()		- 4 getters that will return the suit called for
		GetRank0() - GetRank4()		- 4 getters that will return the rank called for
 */

public class RankSuitCollection 
{	// open

	private char suit0, suit1, suit2, suit3, suit4;
	private int  rank0, rank1, rank2, rank3, rank4;
	
	public RankSuitCollection(char s0, char s1, char s2, char s3, char s4, int r0, int r1, int r2, int r3, int r4)
	{
		suit0 = s0;
		suit1 = s1;
		suit2 = s2;
		suit3 = s3;
		suit4 = s4;
		
		rank0 = r0;
		rank1 = r1;
		rank2 = r2;
		rank3 = r3;
		rank4 = r4;
	}
	
	
	
	public char GETsuit0()
	{
		return suit0;
	}
	public char GETsuit1()
	{
		return suit1;
	}
	public char GETsuit2()
	{
		return suit2;
	}
	public char GETsuit3()
	{
		return suit3;
	}
	public char GETsuit4()
	{
		return suit4;
	}
	
	
	
	public int GETrank0()
	{
		return rank0;
	}
	public int GETrank1()
	{
		return rank1;
	}
	public int GETrank2()
	{
		return rank2;
	}
	public int GETrank3()
	{
		return rank3;
	}
	public int GETrank4()
	{
		return rank4;
	}
	
}	// close
