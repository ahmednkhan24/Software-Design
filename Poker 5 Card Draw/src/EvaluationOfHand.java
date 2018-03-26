/*
	EvaluationOfHand.java
	
	This class takes a sorted hand, and then returns the highest possible 
	ranking the hand can legally make according to the rules of the game
		
	Methods:
		Evaluate()			- main method for this class. returns highest hand
		getRSobject()		- creates an RS object and returns. See RankSuitCollection.java
		IsStraightFlush()	- Checks if hand is a straight flush
		IsFlush()			- Checks if hand is a flush
		IsStraight()		- Checks if hand is a straight 
		IsFourOfAKind()		- Checks if hand is 4 of a kind
		IsThreeOfAKind()	- Checks if hand is 3 of a kind
		IsFullHouse()		- Checks if hand is a full house
		IsTwoPair()			- Checks if hand is a 2 pair
		IsOnePair()			- Checks if hand is a 1 pair
		PrintHand()			- Prints the hand. Only used for testing
 */
import java.util.ArrayList;

public class EvaluationOfHand 
{	// OPEN
	
	//-------------------------------------------------------------------------------------------------
	//
	// Evaluate():
	//
	// this is the main method for this class
	// the method takes the hand, and then checks to see what kind of hand it is
	// it will return the highest possible hand that is possible
	//
	public int Evaluate(ArrayList<Card> Hand)
	{	// open Evaluate
	    
		// assume the hand is already sorted
		RankSuitCollection RanksAndSuits = getRSobject(Hand);
		
		
		// check what kind of hand it is
		
		if (IsStraightFlush(RanksAndSuits) == true)					// Case 9: Straight Flush
			return 9;
		else if (IsFourOfAKind(RanksAndSuits) != 0)					// Case 8: 4 of a kind
			return 8;
		else if (IsFullHouse(RanksAndSuits) == true)				// Case 7: Full House
			return 7;
		else if (IsFlush(RanksAndSuits) == true)					// Case 6: Flush
			return 6;
		else if (IsStraight(RanksAndSuits) == true)					// Case 5: Straight
			return 5;
		else if (IsThreeOfAKind(RanksAndSuits) != 0)				// Case 4: 3 of a kind
			return 4;
		else if (IsTwoPair(RanksAndSuits) != 0)						// Case 3: 2 Pair
			return 3;
		else if (IsOnePair(RanksAndSuits) != 0)						// Case 2: 1 Pair
			return 2;
		else 														// Case 1: High Card
			return 1;
	
	    
	}	// close Evaluate
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// getRSobject():
	//
	// takes the hand and returns an object containing all of the individual suits and ranks in the hand
	//
	public RankSuitCollection getRSobject(ArrayList<Card> Hand)
	{
		// get the suits of all the cards
		char suit0, suit1, suit2, suit3, suit4;
		
		suit0 = Hand.get(0).GetSuit();
		suit1 = Hand.get(1).GetSuit();
		suit2 = Hand.get(2).GetSuit();
		suit3 = Hand.get(3).GetSuit();
		suit4 = Hand.get(4).GetSuit();
		
		// get the ranks of all the cards
		int rank0, rank1, rank2, rank3, rank4;
		
		rank0 = Hand.get(0).GetRank();
		rank1 = Hand.get(1).GetRank();	
		rank2 = Hand.get(2).GetRank();	
		rank3 = Hand.get(3).GetRank();	
		rank4 = Hand.get(4).GetRank();
		
		// create a new object to hold all the suits and ranks so that it is easy to pass as parameter
		RankSuitCollection RanksAndSuits = new RankSuitCollection(suit0, suit1, suit2, suit3, suit4, 
																	rank0, rank1, rank2, rank3, rank4);
		return RanksAndSuits;
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// IsStraightFlush():
	//
	// A straight flush is check if the hand is a straight and a flush
	//
	public boolean IsStraightFlush(RankSuitCollection RS)
	{	
		// only 1 way for a straight flush
		
		if ((IsStraight(RS) == true) && (IsFlush(RS) == true))
			return true;
		else
			return false;
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// IsFlush():
	//
	// all five cards the same suit
	//
	public boolean IsFlush(RankSuitCollection RS)
	{
		// only 1 way for a flush
		if ((RS.GETsuit0() == RS.GETsuit1()) && 
				(RS.GETsuit1() == RS.GETsuit2()) && 
					(RS.GETsuit2() == RS.GETsuit3()) && 
						(RS.GETsuit3() == RS.GETsuit4()))
			return true;
		else
			return false;	
	}	
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// IsStraight():
	//
	//  All five card are in sequence
	//
	public boolean IsStraight(RankSuitCollection RS)
	{
		// ace card can be a 1 or a 14
		int rankA = 14;
		
		if (RS.GETrank0() == RS.GETrank1()-1 && 
				RS.GETrank1() == RS.GETrank2()-1 && 
					RS.GETrank2() == RS.GETrank3()-1 && 
						RS.GETrank3() == RS.GETrank4()-1)
			return true;
		else if (RS.GETrank0() == 1 && 
					(RS.GETrank1() == RS.GETrank2()-1 && 
					RS.GETrank2() == RS.GETrank3()-1 && 
						RS.GETrank3() == RS.GETrank4()-1 && 
							RS.GETrank4() == rankA-1))
			return true;
		else
			return false;	
	}	
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// IsFourOfAKind():
	//
	// Four of the five cards have the same rank
	//
	public int IsFourOfAKind(RankSuitCollection RS)
	{
		// there are two ways to get a 4 of a kind
		
		// 1.  1 free card              last 4 cards the same
		// 2.  first 4 cards the same   1 free card
		
		if (RS.GETrank1() == RS.GETrank2() && 
				RS.GETrank2() == RS.GETrank3() && 
					RS.GETrank3() == RS.GETrank4())
			return 1;
		else if (RS.GETrank0() == RS.GETrank1() && 
					RS.GETrank1() == RS.GETrank2() && 
						RS.GETrank2() == RS.GETrank3())
			return 2;
		else 
			return 0;
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// IsThreeOfAKind():
	//
	// Three of the five cards have the same rank
	//
	public int IsThreeOfAKind(RankSuitCollection RS)
	{
		// there are 3 ways to get a 3 of a kind
		
		// 1.  3 cards the same    1 free card        1 free card
		// 2.  1 free card         3 cards the same   1 free card
		// 3.  1 free card         1 free card        3 cards the same
		
		if (RS.GETrank0() == RS.GETrank1() && RS.GETrank1() == RS.GETrank2())
			return 1;
		else if (RS.GETrank1() == RS.GETrank2() && RS.GETrank2() == RS.GETrank3())
			return 2;
		else if (RS.GETrank2() == RS.GETrank3() && RS.GETrank3() == RS.GETrank4())
			return 3;
		else
			return 0;
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// IsFullHouse():
	//
	// Three of the cards have one rank and two of the cards have another rank.
	//
	public boolean IsFullHouse(RankSuitCollection RS)
	{	
		// there are 2 ways to get a full house
		
		// 1. 3 low cards and 2 high cards
		// 2. 2 low cards and 3 high cards

		if (RS.GETrank0() == RS.GETrank1() && 
				RS.GETrank1() == RS.GETrank2() && 
					RS.GETrank3() == RS.GETrank4())
			return true;
		else if (RS.GETrank0() == RS.GETrank1() && 
					RS.GETrank2() == RS.GETrank3() && 
						RS.GETrank3() == RS.GETrank4())
			return true;
		else
			return false;
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	// 
	// IsTwoPair():
	//
	// Two cards in the hand contain one rank and a different two cards in the hand contain another rank
	//
	public int IsTwoPair(RankSuitCollection RS)
	{
		// there are 3 ways to get a 2 pair
		
		// 1.  first one free card           middle 2 cards the same       last 2 cards the same
		// 2.  first two cards the same      middle one free card          last 2 cards the same
		// 3.  first two cards are the same  middle 2 cards are the same   last one free card
		
		if (RS.GETrank1() == RS.GETrank2() && RS.GETrank3() == RS.GETrank4())
			return 1;
		else if (RS.GETrank0() == RS.GETrank1() && RS.GETrank3() == RS.GETrank4())
			return 2;
		else if (RS.GETrank0() == RS.GETrank1() && RS.GETrank2() == RS.GETrank3())
			return 3;
		else
			return 0;
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	// IsOnePair():
	//
	// Two cards in the hand contain the same rank
	//
	public int IsOnePair(RankSuitCollection RS)
	{	
		// there are 4 possible ways to get a 1 pair
		
		// 1.   first 2 cards same    3 free cards
		// 2.   3 free cards          last 2 cards same
		// 3.   1 free card           middle 2 cards same      2 free cards
		// 4.   2 free cards          middle 2 cards same      1 free card
		
		
		if (RS.GETrank0() == RS.GETrank1())
			return 1;
		else if (RS.GETrank3() == RS.GETrank4())
			return 2;
		else if (RS.GETrank1() == RS.GETrank2())
			return 3;
		else if (RS.GETrank2() == RS.GETrank3())
			return 4;
		else 
			return 0;
		
	}
	//-------------------------------------------------------------------------------------------------
	public void PrintHand(ArrayList<Card> Hand)
	{
		Card tmp;

		for (int i = 0; i < Hand.size(); i++)
		{
			tmp = Hand.get(i);
			System.out.println(tmp.GetValue());
		}
	}
}	// CLOSE
//----------------------------------------------------------------------------------------------------------
