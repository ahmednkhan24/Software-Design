/*
	UserPlayer.java
	
	This class holds all the necessary methods for the user player
	keeps track of the user's hand and interacts with the user interface
	
	Inherits from Player.java
	
	Methods:
		UserPlayer()			- constructor, creates the empty hand
		GetHand()				- returns the hand
		UserTurn()				- main method for this class. Interacts with the user
		HowManyCardsEntered()	- checks how many cards the user entered to discard
		PrintHand()				- Prints the required texts for the user when it's their turn
		disdraw()				- helper method that will call the true discard method from player.java
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class UserPlayer extends Player
{	// OPEN

	private ArrayList<Card> Hand;
	
	
	//
	// UserPlayer():
	// 
	// Constructor
	//
	public UserPlayer()
	{
		Hand = new ArrayList<Card>();
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// GetHand():
	//
	// returns the hand
	//
	public ArrayList<Card> GetHand()
	{
		return Hand;
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// UserTurn():
	//
	// When it is the user's turn, this method will be the 'main' method called for the user's turn
	//
	public void UserTurn(CardPile Deck)
	{	// open
		Hand = SortHand(Hand);				// sort the hand
		
		boolean TheresAnAce = PrintHand();	// check if there's an ace in the hand
		
		disdraw(Deck, TheresAnAce);			// allow user to discard cards
		
		Hand = SortHand(Hand);				// sort the hand again before leaving
		
	}	// close
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// HowManyCardsEntered()
	//
	// returns the number of cards the user input by tracking commas
	//
	private int HowManyCardsEntered(String Line)
	{
		int counter = 0;
		
		for (int i = 0; i < Line.length(); i++)
		{
			if (Line.charAt(i) == ',')
				counter++;
		}
		return counter+1;
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// PrintHand()
	//
	// Prints the hand with the extra features for Aces added
	//
	private boolean PrintHand()
	{	// open PrintHand
		System.out.println("The cards in your hand are: ");
		
		boolean TheresAnAce = false;
		
		int cards = 1;
		int NumAces = 0;
		
		if (IsThereAceInHand(Hand) == true)
		{
			TheresAnAce = true;
			
			// we have to print the aces first, because their rank for the program
			// is 1, but the ace is the strongest rank
			
			NumAces = HowManyAcesInHand(Hand);
			
			for (int i = 0; i < NumAces; i++)
			{
				if (Hand.get(i).GetRank() == 1)
				{
					System.out.print(cards + ") " + Hand.get(i).GetValue() + " ");
					cards++;
				}
			}
		}
		
		// after printing the aces, print out the rest of the cards in order
		
		for (int i = Hand.size(); i > 0+NumAces; i--)
		{
			System.out.print(cards + ") " + Hand.get(i-1).GetValue() + " ");
			cards++;
		}
		System.out.println();
		
		
		if (TheresAnAce == true)
		{
			System.out.println("\nSince you have an Ace, you can keep the Ace and dicard the other\nfour cards.");
		}
		
		return TheresAnAce;
	}	// close PrintHand
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	//
	// disdraw()
	//
	// Asks the user to input the cards he/she wishes to discard, stores them, analyzes them then calls 
	// the discard and draw method
	//
	private void disdraw(CardPile Deck, boolean IsThereAce)
	{
		
		Scanner UserInput = new Scanner(System.in);
		String Line;
		int NumEntries = 0;
		
		// do while loop to make sure the user enters the appropriate number of cards to discard
		do
		{
			System.out.println("\nList the card(s) that you wish to discard.\n(Enter the rank(s) & suit(s) as listed above\n"
					+ "seperated by commas and no spaces) [use capital letter(s) for the suit(s)]\n> ");
			
			Line = UserInput.nextLine();
			
			NumEntries = HowManyCardsEntered(Line);
		}
		while (IsThereAce == false && NumEntries > 3);
		
		
		// tokenize the data to distingish the end of a card
		
		StringTokenizer strtok = new StringTokenizer(Line, ",");	// Tokenize the data for commas
		
		String discard;
		
		// discard the cards entered
		while (strtok.hasMoreTokens())
		{
			discard = strtok.nextToken();
			
			Hand = DiscardAndDraw(Deck, Hand, discard);
		}
		
		UserInput.close();
	}
}	// CLOSE
