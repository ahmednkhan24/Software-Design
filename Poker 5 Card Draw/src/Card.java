/*
	Card.java
	
	This class holds the contents of 1 card:
		- The Rank Suit Pair stored as a string
		- The index of the card in the deck that is updated when shuffled
		- the key of the card from the un-shuffled deck. Remains unchanged
		- getter's and setters for the private variables
	
	Methods:
		Card()			- constructor for each card
		GetKey()		- returns the key of each card
		GetValue()		- returns the string name of each card i.e. RankSuit pair
		GetIndex()		- returns the index of the card in the deck
		UpdateIndex()	- updates the index of the card in the deck after the deck is shuffled
		GetRank()		- returns the rank of the card
		GetSuit()		- returns the suit of the card
 */
public class Card 
{	// open Card

	private int    Key;
	private String Value;
	private int    Index;
	
	
	// Constructor
	public Card(int key, String name, int index)
	{
		Key   = key;
		Value = name;
		Index = index;
	}
	// Getter for Key
	public int GetKey()
	{
		return Key;
	}
	// Getter for Value
	public String GetValue()
	{
			return Value;
	}
	// Getter for Index
	public int GetIndex()
	{
		return Index;
	}
	// Updates Indexes after deck is shuffled
	public void UpdateIndex(int num)
	{
		Index = num;
	}
	// Getter for card rank
	public int GetRank()
	{
		char Rank = Value.charAt(0);	// this will be either 'A', 'K', 'Q', 'J', 'X', or '2' - '9'
		
		if ((Rank == 'A') || (Rank == 'X') || (Rank == 'J') || (Rank == 'Q') || (Rank == 'K'))
		{
			if (Rank == 'K')
				return 13;
			else if (Rank == 'Q')
				return 12;
			else if (Rank == 'J')
				return 11;
			else if (Rank == 'X')
				return 10;
			else if (Rank == 'A')
				return 1;
			else
			{
				System.out.println("\n\nERROR ERROR ERROR GetRank()\n\n");
				return -1;
			}
		}
		else
		{
			int num = Character.getNumericValue(Rank);
			return num;
		}
		
	}
	
	// Getter for card suit
	public char GetSuit()
	{
		return Value.charAt(1);		// this will be either 'C', 'D', 'H', or 'S'
	}
}	// close Card

/*
 KEYS
		0. A of Clubs
		1. 2 of Clubs
		2. 3 of Clubs
		3. 4 of Clubs
		4. 5 of Clubs
		5. 6 of Clubs
		6. 7 of Clubs
		7. 8 of Clubs
		8. 9 of Clubs
		9. X of Clubs
		10. J of Clubs
		11. Q of Clubs
		12. K of Clubs
		13. A of Diamonds
		14. 2 of Diamonds
		15. 3 of Diamonds
		16. 4 of Diamonds
		17. 5 of Diamonds
		18. 6 of Diamonds
		19. 7 of Diamonds
		20. 8 of Diamonds
		21. 9 of Diamonds
		22. X of Diamonds
		23. J of Diamonds
		24. Q of Diamonds
		25. K of Diamonds
		26. A of Hearts
		27. 2 of Hearts
		28. 3 of Hearts
		29. 4 of Hearts
		30. 5 of Hearts
		31. 6 of Hearts
		32. 7 of Hearts
		33. 8 of Hearts
		34. 9 of Hearts
		35. X of Hearts
		36. J of Hearts
		37. Q of Hearts
		38. K of Hearts
		39. A of Spades
		40. 2 of Spades
		41. 3 of Spades
		42. 4 of Spades
		43. 5 of Spades
		44. 6 of Spades
		45. 7 of Spades
		46. 8 of Spades
		47. 9 of Spades
		48. X of Spades
		49. J of Spades
		50. Q of Spades
 */
