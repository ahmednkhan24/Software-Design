/*
	aiPlayer.java
	
	This class will keep track of the computer player's hand
	and interact with the Artificial Intelligence
	
	Inherits from Player.java
	
	Methods:
		aiPlayer()				- constructor, creates the empty hand
		GetHand()				- returns the hand
		aiTurn()				- main method for this class. Interacts with the AI
		ComputerCheck1()		- check if the computer player already has a hand of One Pair or better, discard rest
		ComputerCheck2()		- check if the hand is "High Card", determine if the user has 4 cards same suit, discard lone card
		ComputerCheck3()		- check if the user has 4 cards in sequence, discard lone card
		ComputerCheck4()		- Next if the user has an Ace, discard the other four cards
		ComputerCheck5()		- keep the two highest cards and discard the other 3
		FourCardsInSequence()	- checks to see if 4 cards are in sequence
		FourCardsSameSuit()		- checks to see if 4 cards have the same suit
 */

import java.util.ArrayList;

public class aiPlayer extends Player
{	// OPEN

	private ArrayList<Card> Hand;
	
	
	//-------------------------------------------------------------------------------------------------
	//
	// aiPlayer():
	//
	// constructor
	//
	public aiPlayer()
	{
		Hand = new ArrayList<Card>();
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// GetHand()
	//
	// returns the hand
	//
	public ArrayList<Card> GetHand()
	{
		return Hand;
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	/*
	First check if the computer player already has a hand of One Pair or better. If so,
	discard all other card.
	*/
	public void aiTurn(CardPile Deck, int ComputerPlayerNum)
	{	// open
		
		// sort the hand
		Hand = SortHand(Hand);
		
		// get objects of other classes to access their methods
		EvaluationOfHand eval = new EvaluationOfHand();
		RankSuitCollection RS = eval.getRSobject(Hand);
		
		// evaluate the hand
		int num = eval.Evaluate(Hand);

		// if statement for computer check1
		if ((num >= 2) && (num != 5 && num != 6 && num != 7 && num != 9))
			ComputerCheck1(Deck, eval, RS, num, ComputerPlayerNum);
		else if (num == 1)
		{
			int FourSuit = FourCardsSameSuit(RS);
			int FourSeq  = FourCardsInSequence(RS);
			
			// if statement for computer check 2
			if (FourSuit != 0)
				ComputerCheck2(Deck, FourSuit, ComputerPlayerNum);
			
			// if statement for computer check 3
			else if (FourCardsInSequence(RS) != 0)
				ComputerCheck3(Deck, FourSeq, ComputerPlayerNum);
			
			// if statement for computer check 4
			else if (IsThereAceInHand(Hand) == true)
				ComputerCheck4(Deck, ComputerPlayerNum);
			
			// else statement for computer check 5
			else
				ComputerCheck5(Deck, ComputerPlayerNum);
		}
		
		// sort the hand again
		Hand = SortHand(Hand);
		
	}	// close
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// FourCardsSameSequence():
	//
	// checks to see if there are 4 cards in the hand that are in sequence
	//
	public int FourCardsInSequence(RankSuitCollection RS)
	{
		int rankA = 14;
		
		if (RS.GETrank0() == RS.GETrank1()-1 && 
				RS.GETrank1() == RS.GETrank2()-1 && 
					RS.GETrank2() == RS.GETrank3()-1)
			return 1;
		else if (RS.GETrank0() == 1 && 
					(RS.GETrank2() == RS.GETrank3()-1 && 
					RS.GETrank3() == RS.GETrank4()-1 && 
						RS.GETrank4() == rankA-1))
			return 2;
		else
			return 0;
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// FourCardsSameSuit():
	//
	// checks to see if there are 4 cards with the same suit
	//
	public int FourCardsSameSuit(RankSuitCollection RS)
	{
		//If the hand evaluates to "High Card", determine if the user has 4 cards of the same
		//suit. If so, discard the card of the different suit.
		
		
		// 1. first 4 cards have same suit
		// 2.  last 4 cards have same suit
		// 0. false
		
		if (RS.GETsuit0() == RS.GETsuit1() && RS.GETsuit1() == RS.GETsuit2() && RS.GETsuit2() == RS.GETrank3())
			return 1;
		else if (RS.GETsuit1() == RS.GETsuit2() && RS.GETsuit2() == RS.GETsuit3() && RS.GETsuit3() == RS.GETrank4())
			return 2;
		else
			return 0;
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	
	public void ComputerCheck5(CardPile Deck, int ComputerPlayerNum)
	{
		// keep the two highest cards and discard the other 3
		
		System.out.println("Player " + ComputerPlayerNum + " has discarded 3 cards.\n");
		
		Hand = DiscardAndDraw(Deck, Hand, Hand.get(0).GetValue());
		Hand = DiscardAndDraw(Deck, Hand, Hand.get(1).GetValue());
		Hand = DiscardAndDraw(Deck, Hand, Hand.get(2).GetValue());
	}
	
	public void ComputerCheck4(CardPile Deck, int ComputerPlayerNum)
	{
		// Next if the user has an Ace, discard the other four cards
		
		System.out.println("Player " + ComputerPlayerNum + " has discarded 4 cards.\n");
		
		Hand = DiscardAndDraw(Deck, Hand, Hand.get(1).GetValue());
		Hand = DiscardAndDraw(Deck, Hand, Hand.get(2).GetValue());
		Hand = DiscardAndDraw(Deck, Hand, Hand.get(3).GetValue());
		Hand = DiscardAndDraw(Deck, Hand, Hand.get(4).GetValue());
		
	}
	
	public void ComputerCheck3(CardPile Deck, int FourCards, int ComputerPlayerNum)
	{
		// check if the user has 4 cards in sequence, discard lone card
		
		System.out.println("Player " + ComputerPlayerNum + " has discarded 1 cards.\n");
		
		String discard;
		
		if (FourCards == 1)
			discard = Hand.get(4).GetValue();
		else
			discard = Hand.get(1).GetValue();
		
		Hand = DiscardAndDraw(Deck, Hand, discard);
	}
	
	public void ComputerCheck2(CardPile Deck, int FourCards, int ComputerPlayerNum)
	{
		// check if the hand is "High Card", determine if the user has 4 cards same suit, discard lone card
		
		System.out.println("Player " + ComputerPlayerNum + " has discarded 1 cards.\n");
		
		String discard;
		
		if (FourCards == 1)
			discard = Hand.get(4).GetValue();
		else
			discard = Hand.get(0).GetValue();
		
		Hand = DiscardAndDraw(Deck, Hand, discard);
	}
	
	public void ComputerCheck1(CardPile Deck, EvaluationOfHand eval, RankSuitCollection RS, int num, int ComputerPlayerNum)
	{	// open computerCheck1
		
		/*
		don't discard if 
			// straight flush 		9
			// full house 			7
			// flush				6
			// straight				5
			 * 
		do discard if 
			// four of a kind		8
			// three of a kind		4
			// two pair				3
			// one pair				2
		*/
		
		String discard, discard2, discard3;
		int num2;
		
		if (num == 8)		
		{
			System.out.println("Player " + ComputerPlayerNum + " has discarded 1 cards.\n");
			
			// 1.  1 free card              last 4 cards the same
			// 2.  first 4 cards the same   1 free card
			
			num2 = eval.IsFourOfAKind(RS);
			
			if (num2 == 1)
				discard = Hand.get(0).GetValue();
			else
				discard = Hand.get(4).GetValue();
			
			
			Hand = DiscardAndDraw(Deck, Hand, discard);
		}
		else if (num == 4)
		{
			System.out.println("Player " + ComputerPlayerNum + " has discarded 2 cards.\n");
			
			// 1.  3 cards the same    1 free card        1 free card
			// 2.  1 free card         3 cards the same   1 free card
			// 3.  1 free card         1 free card        3 cards the same
			
			num2 = eval.IsThreeOfAKind(RS);
			
			if (num2 == 1)
			{
				discard  = Hand.get(3).GetValue();
				discard2 = Hand.get(4).GetValue();
			}
			else if (num2 == 2)
			{
				discard  = Hand.get(0).GetValue();
				discard2 = Hand.get(4).GetValue();
			}
			else
			{
				discard  = Hand.get(0).GetValue();
				discard2 = Hand.get(1).GetValue();
			}
				
			Hand = DiscardAndDraw(Deck, Hand, discard);
			Hand = DiscardAndDraw(Deck, Hand, discard2);
		}
		else if (num == 3)
		{
			System.out.println("Player " + ComputerPlayerNum + " has discarded 1 cards.\n");
			
			// 1.  first one free card           middle 2 cards the same       last 2 cards the same
			// 2.  first two cards the same      middle one free card          last 2 cards the same
			// 3.  first two cards are the same  middle 2 cards are the same   last one free card
			
			num2 = eval.IsTwoPair(RS);
			
			if (num2 == 1)
				discard = Hand.get(0).GetValue();
			else if (num2 == 2)
				discard = Hand.get(2).GetValue();
			else
				discard = Hand.get(4).GetValue();
			
			Hand = DiscardAndDraw(Deck, Hand, discard);	
		}
		else
		{
			System.out.println("Player " + ComputerPlayerNum + " has discarded 3 cards.\n");
			
			// 1.   first 2 cards same    3 free cards
			// 2.   3 free cards          last 2 cards same
			// 3.   1 free card           middle 2 cards same      2 free cards
			// 4.   2 free cards          middle 2 cards same      1 free card
			
			num2 = eval.IsOnePair(RS);
			
			if (num2 == 1)
			{
				discard  = Hand.get(2).GetValue();
				discard2 = Hand.get(3).GetValue();
				discard3 = Hand.get(4).GetValue();
			}
			else if (num2 == 2)
			{
				discard  = Hand.get(0).GetValue();
				discard2 = Hand.get(1).GetValue();
				discard3 = Hand.get(2).GetValue();
			}
			else if (num2 == 3)
			{
				discard  = Hand.get(0).GetValue();
				discard2 = Hand.get(3).GetValue();
				discard3 = Hand.get(4).GetValue();
			}
			else
			{
				discard  = Hand.get(0).GetValue();
				discard2 = Hand.get(1).GetValue();
				discard3 = Hand.get(4).GetValue();
			}
			
			Hand = DiscardAndDraw(Deck, Hand, discard);
			Hand = DiscardAndDraw(Deck, Hand, discard2);
			Hand = DiscardAndDraw(Deck, Hand, discard3);
		}
	}	// close computerCheck1
}	// CLOSE
