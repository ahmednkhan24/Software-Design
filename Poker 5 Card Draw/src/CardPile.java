/*
	CardPile.java
	
	This class is a collection of cards.
	Handles anything that has to do with the deck
	
	Methods:
		CardPile()				- Constructor
		AddToDiscardPile()		- Adds the card passed to it to the discard pile
		PrintDeck()				- prints the deck including the key and index
		UpdateIndeces()			- updates indexes after deck is shuffled
		ShuffleDeck()			- shuffles deck
		TwoThru9()				- returns the ranks 2-9 in int form
		AceKingQueenJack10()	- returns the ranks A, K, Q, J or X in int form
		FindListKey()			- finds the key in the list of cards, NOT the deck
		FindCardInDeck()		- finds the index of the card in the deck
		DeleteCardFromDeck()	- deletes the desired card from the deck
		DrawCard()				- draws a card from the deck
 */

import java.util.ArrayList;
import java.util.Random;

public class CardPile 
{	// begin

	// List of all potential cards. This is NOT the deck
	
	private static String[] List = {"AC", "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "XC", "JC", "QC", "KC",
									"AD", "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "XD", "JD", "QD", "KD",
									"AH", "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "XH", "JH", "QH", "KH",
									"AS", "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "XS", "JS", "QS", "KS"};

	
	private Card[] Deck;
	private int DeckSize = 52;
	
	
	private ArrayList<Card> DiscardPile = new ArrayList<Card>();
	

	
	
	//-----------------------------------------------------------------------------
	//
	// CardPile():
	//
	// Constructor. Initializes the deck to 52 empty cards then fills each card
	//
	public CardPile()
	{
		Deck = new Card[DeckSize];
		
		for (int i = 0; i < DeckSize; i++)
		{
			Deck[i] = new Card(i, List[i], i);
		}
	}
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------
	// 
	// AddToDiscardPile():
	//
	// Inserts the desired discarded card into the discard pile
	//
	public void AddToDiscardPile(Card DiscardedCard)
	{
		DiscardPile.add(DiscardedCard);
	}
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------
	//
	// PrintDeck():
	//
	// Prints the deck in order   Key:      Value:      Index:  
	//
	public void PrintDeck()
	{
		for (int i = 0; i < DeckSize; i++)
		{
			System.out.print(i+1 + ".  Key: " + Deck[i].GetKey());
			System.out.print("    Value: " + Deck[i].GetValue());
			System.out.println("    Index: " + Deck[i].GetIndex());
		}
		System.out.println("\n");
		
	}
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------
	//
	// UpdateIndeces():
	//
	// resets indexes to go in order from 0-51
	//
	private void UpdateIndeces()
	{
		for (int i = 0; i < DeckSize; i++)
		{
			Deck[i].UpdateIndex(i);
		}
	}
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------
	//
	// ShuffleDeck():
	//
	// Shuffles the deck, and then updates the indexes
	//
	public void ShuffleDeck()
	{	// open 
		Random Rand = new Random();
		
		int Swap;
		Card Temp;
		
		for (int i = 0; i < DeckSize; i++)
		{	// open for loop
			
			Swap = Rand.nextInt(DeckSize);		// get random number between 0 and 51
			
			// use random numbers as index of Deck and swap with index i
		
			Temp = Deck[Swap];
			Deck[Swap] = Deck[i];
			Deck[i] = Temp;

		}
		
		UpdateIndeces();	// reset indexes to go in order from 0-51
		
		System.out.println("\nThe deck has just been shuffled.\n");
		
	}	// close
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------
	// start code for Delete method
	
	/*
	AceKingQueenJack10() and TwoThru9() will return the number associated with each variable
		 	
		 	NUMBER:
				 (char) A: (int) 0
				 (char) 2: (int) 1
				 (char) 3: (int) 2
				 (char) 4: (int) 3
				 (char) 5: (int) 4
				 (char) 6: (int) 5
				 (char) 7: (int) 6
				 (char) 8: (int) 7
				 (char) 9: (int) 8
				(char) 10: (int) 9
				(char)  J: (int) 10
				 (char) Q: (int) 11
				(char)  K: (int) 12
				
	*/
	private int TwoThru9(char rank)
	{
		int num = Character.getNumericValue(rank);
		
		return num - 1;
	}
	
	private int AceKingQueenJack10(char Rank)
	{
		if (Rank == 'K')
			return 12;
		else if (Rank == 'Q')
			return 11;
		else if (Rank == 'J')
			return 10;
		else if (Rank == 'X')
			return 9;
		else if (Rank == 'A')
			return 0;
		else
		{
			System.out.println("\n\nERROR ERROR ERROR AceKingQueenJack10()\n\n");
			return -1;
		}
	}
	
	/*
	There are 13 ranks, and 4 suits. The most efficient way to divide the 52 cards is first by suit.
 	
 	the main 5 if/else statements check for the suits by looking at index 1 of the string parameter.
 	it will be either C, D, H, or S for Clubs, Diamonds, Hearts, or Spades in that order.
 	
 	once the suit is figured out, now it is time to check for the rank at index 0
 	
 	there are 13 ranks: Ace, the numbers 2-9, 
 						X (the type char cannot take 10, so I opted to use
 						the Roman Numeral for 10 instead since it is one character)
 						Jack, Queen, and King
 						
 	The most efficient way to divide these 13 ranks are by numbers and characters.
 	
 	the method GetKey() will now call either the method AceKingQueenJack10() or the method TwoThru9();
 	
 	Once we know what the suit is and what the number is, we can figure out the rank of the card
	 	
	 	CARDS:
	 		 0-12: Clubs
	 		13-25: Diamonds
	 		26-38: Hearts
	 		39-51: Spades
	 		
	 	if the card is a club, all we have to do is return the local variable key.
	 	if the card is not a club, we have to return the key+the first number of each suit (13, 26, or 39).
 	*/
	
	//
	// FindCard():
	//
	// Searches the deck for the card associated with the Value and returns the Key
	// returns -1 if card not found
	//
	private int FindListKey(String Value)
	{	// open
		
		int Key = -1;
		
		char Rank = Value.charAt(0);	// this will be either 'A', 'K', 'Q', 'J', 'X', or '2' - '9'
		char Suit = Value.charAt(1);	// this will be either 'C', 'D', 'H', or 'S'
		
		
		
		// clubs
		if (Suit == 'C')
		{
			if ((Rank == 'A') || (Rank == 'X') || (Rank == 'J') || (Rank == 'Q') || (Rank == 'K'))
				Key = AceKingQueenJack10(Rank);
			else
				Key = TwoThru9(Rank);
			
			return Key;
		}
		// diamonds
		else if (Suit == 'D')
		{
			if ((Rank == 'A') || (Rank == 'X') || (Rank == 'J') || (Rank == 'Q') || (Rank == 'K'))
				Key = AceKingQueenJack10(Rank);
			else
				Key = TwoThru9(Rank);
			
			return Key + 13;
		}
		// hearts
		else if (Suit == 'H')
		{
			if ((Rank == 'A') || (Rank == 'X') || (Rank == 'J') || (Rank == 'Q') || (Rank == 'K'))
				Key = AceKingQueenJack10(Rank);
			else
				Key = TwoThru9(Rank);
			
			return Key + 26;
		}
		// spades
		else if (Suit == 'S')
		{
			if ((Rank == 'A') || (Rank == 'X') || (Rank == 'J') || (Rank == 'Q') || (Rank == 'K'))
				Key = AceKingQueenJack10(Rank);
			else
				Key = TwoThru9(Rank);
			
			return Key + 39;
		}
		// garbage
		else
		{
			System.out.println("\n\nERROR ERROR ERROR GetKey()\n\n");
			return -1;
		}
	
	}	// close
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------
	//
	// FindCardInDeck():
	//
	// finds the card in the Deck that corresponds to List[Key] and returns it
	// returns null if not found
	//
	private Card FindCardInDeck(int Key)
	{
		for (int i = 0; i < DeckSize; i++)
		{
			if (Deck[i].GetValue().equals(List[Key]))
				return Deck[i];
		}
		return null;
	}
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------
	//
	// DeleteCardFromDeck():
	//
	// Searches the list for the Key that corresponds to the String Value
	// then searches for the card using the same Key in the Deck
	// uses the card in the deck that has Key and gets the index in the deck
	// deletes that card from the deck
	// updates the indexes and updates the DeckSize
	//
	public void DeleteCardFromDeck(String Value)
	{	// open
		
		int Key = FindListKey(Value);		// the List[key] corresponds to Value. 

		// check if the Key exists
		if (Key < 0 || Key > 52)
		{
			System.out.println("\n\nERROR ERROR ERROR DeleteCardFromDeck()\n\n");
			return;
		}
		
		// Now we need to find the List[key] inside of the Deck
		Card CardToDiscard = FindCardInDeck(Key);
		
		// Check if the card exists in the deck
		if (CardToDiscard == null)
		{
			System.out.println("\n\nERROR ERROR ERROR DeleteCardFromDeck()\n\n");
			return;
		}
		
		// we found the card, now get the index of the card in the deck
		int index = CardToDiscard.GetIndex();
		
		// now delete
		for (int i = index; i < DeckSize - 1; i++)
		{
			Deck[i] = Deck[i+1];
		}
		
		// update the end of the deck and the deck size
		Deck[DeckSize-1] = null;	
		DeckSize--;
		
		UpdateIndeces();
		
	}	// close
	// end code for delete method
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------
	public Card DrawCard()
	{	
		Card top = Deck[0];
		
		DeleteCardFromDeck(top.GetValue());
		
		return top;
	}		
}	// end
