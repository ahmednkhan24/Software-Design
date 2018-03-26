/*
	Player.java
	
	This class contains all the methods that are common between
	the aiPlayer class and the UserPlayer class. Those classes
	can access the methods in this class from inheritance
	
	Methods:
		InsertCard()		- adds the card passed to it into the hand passed to it
		DiscardCard()		- adds the card passed to it to the discard pile
		SortHand()			- sorts the hand from lowest rank to highest - Ace will be 1
		IsThereAceInHand()	- checks if there is an ace in the hand passed to it
		HowManyAcesInHand()	- determines how many aces are in the hand passed to it
		FindCardInHand()	- finds the index of the searched card in the hand 
		DiscardAndDraw()	- discards the card from the hand and then draws a new card
		
		EXTRA CREDIT
		SortHighToLow()		- sorts a new hand from highest rank to lowest rank
		ECSort()			- does the extra credit sort and returns the new hand
 */

import java.util.ArrayList;

public class Player
{	// open
	
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// InsertCard():
	//
	// Inserts the card into the hand
	//
	public void InsertCard(ArrayList<Card> Hand, Card c)
	{
		Hand.add(c);
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// DiscardCard():
	//
	// adds the card passed to it into the discard pile
	//
	public void DiscardCard(CardPile Deck, Card c)
	{
		Deck.AddToDiscardPile(c);
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// SortHand():
	//
	// Sorts the Hand by card rank starting with lowest rank going to highest using selection sort
	//
	public ArrayList<Card> SortHand(ArrayList<Card> Hand)
	{
		int index, i;
		Card temp;

		for (i = 0; i < Hand.size() - 1; i++)
		{
			index = i;
			
			for (int j = i+1; j < Hand.size(); j++)
			{
				
				if (Hand.get(j).GetRank() < Hand.get(index).GetRank())	// j is smaller
				{
					index = j;
				}
			}	
			
			if (index != i)	// Hand at index 'index' should be the lowest ranking card, so swap
			{
				temp = Hand.get(i);
				Hand.remove(i);					// removing will also decrease the size of the list by 1
				Hand.add(i, Hand.get(index-1));	
				
				Hand.remove(index);
				Hand.add(index, temp);
			}
			
		}
		return Hand;
	}
	/*
	 PIAZZA SAID WE CAN'T USE LAMBDA EXPRESSIONS
	private ArrayList<Card> SortHand(ArrayList<Card> Hand)
	{
		Collections.sort(Hand, new Comparator<Card>()
				{
					public int compare(Card c1, Card c2)
					{
						return Integer.valueOf(c1.GetRank()).compareTo(c2.GetRank());
					}
				});
		
		return Hand;
	}
	*/
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// IsThereAceInHand():
	//
	// Checks to see if there is at least 1 Ace in the hand
	//
	public boolean IsThereAceInHand(ArrayList<Card> Hand)
	{	
		if (Hand.get(0).GetRank() == 1)
			return true;
		else
			return false;
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	// 
	// HowManyAcesInHand()
	//
	// returns the total number of Aces in the hand
	//
	public int HowManyAcesInHand(ArrayList<Card> Hand)
	{
		int counter = 0;
		
		for (Card c : Hand)
		{
			if (c.GetRank() == 1)
				counter++;
		}
		return counter;
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// FindCardInHand():
	//
	// Takes the card name and finds the card in the hand and returns the index
	//
	private int FindCardInHand(ArrayList<Card> Hand, String s)
	{
		for (int i = 0; i < Hand.size(); i++)
		{
			if (Hand.get(i).GetValue().equals(s))
				return i;
		}
		return -1;
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	// 
	// DiscardAndDraw():
	//
	// Finds the card in the hand, removes the card from the deck, adds the card to discard pile,
	// and draws new card and adds it to the hand
	//
	public ArrayList<Card> DiscardAndDraw(CardPile Deck, ArrayList<Card> Hand, String discard)
	{	// open
		int index;
		
		index = FindCardInHand(Hand, discard);

		if (index == -1)
		{
			System.out.println("The card '" + discard + "' is not in your hand.");
			return Hand;
		}
		
		Card tmp = Hand.get(index);				// save the card we want to remove
		
		Hand.remove(index);					// remove the card from the hand
		
		Deck.AddToDiscardPile(tmp);			// add the card to the discard pile
		
		Hand.add(index, Deck.DrawCard());	// draw a new card to the hand
		
		return Hand;
		
	}	// close
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	// EXTRA CREDIT
	/*
	For 5 points extra credit, sort the cards so that any matched cards are listed first. When
	multiple matched cards are in the hand (i.e. two pair or full house), list the more important
	match first. This would be the match with the higher rank for two pair and the match of three
	for the full house. Doing this will make breaking ties extremely easy. So you are strongly
	encouraged to do this. Not only will you get extra credit, but this will simplify a potentially
	difficult part of the assignment
	*/ 
	
	//
	// SortHighToLow()
	//
	// selection sort sorting from high rank to low rank. used for the extra credit sort method
	//
	public ArrayList<Card> SortHighToLow(ArrayList<Card> Hand)
	{
		int index, i;
		Card temp;

		for (i = 0; i < Hand.size() - 1; i++)
		{
			index = i;
			
			for (int j = i+1; j < Hand.size(); j++)
			{	
				if (Hand.get(j).GetRank() > Hand.get(index).GetRank())	// j is smaller
					index = j;
			}	
			
			if (index != i)	// Hand at index 'index' should be the lowest ranking card, so swap
			{
				temp = Hand.get(i);
				Hand.remove(i);					// removing will also decrease the size of the list by 1
				Hand.add(i, Hand.get(index-1));	
				
				Hand.remove(index);
				Hand.add(index, temp);
			}
		}
		return Hand;
	}
	//
	// ECSort():
	//
	// creates a copy of the hand and sorts it by matching pairs
	//
	public ArrayList<Card> ECSort(ArrayList<Card> Hand)
	{
		
		ArrayList<Card> ECHand = new ArrayList<Card>();
		
		for (int i = 0; i < Hand.size(); i++)
		{
			ECHand.add(Hand.get(i));
		}
		
		ECHand = SortHighToLow(ECHand);
		
		
		Card StarCard, tmp;
		for (int i = 0; i < ECHand.size(); i++)
		{
			StarCard = ECHand.get(i);
			
			for (int j = 0; j < ECHand.size(); j++)
			{
				if (StarCard.GetRank() == ECHand.get(j).GetRank())
				{
					tmp = ECHand.get(j);
					ECHand.remove(j);
					ECHand.add(i, tmp);	
				}
			}
		}
		return ECHand;
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
}	// close
