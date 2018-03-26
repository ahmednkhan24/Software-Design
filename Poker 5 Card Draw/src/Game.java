//***************************************************
//  Name:	  Ahmed N Khan
//   UIN:     652469935
// NetID:     akhan227
// Class:     CS 342 - Software Design - UIC 2017
//***************************************************

/*
	Game.java
	
	DISCLAIMER: THE RANK 10 IS REPRESENTED BY THE ROMAN NUMERAL FOR 10 WHICH IS X
	
	This class contains the main method for this program
	Plays the game of Poker - 5 Card Draw 
	
	Methods:
		main()					- main method for program
		PrintHand()				- prints the hand passed to it
		NameTheHand()			- takes a hand and prints out the best rank it has
		DealCards2Players()		- deals the cards to 2 players - User and 1 AI
		DealCards3Players()		- deals the cards to 3 players - User and 2 AI
		DealCards4Players()		- deals the cards to 4 players - User and 3 AI
		FindWinner2Players()	- compares the hands of 2 players and determines a winner
		FindWinner3Players()	- compares the hands of 3 players and determines a winner
		FindWinner4Players()	- compares the hands of 4 players and determines a winner
		Max2()					- returns the max value of 2 numbers
		Max3()					- returns the max value of 3 numbers
		Max4()					- returns the max value of 4 numbers
 */

import java.util.ArrayList;
import java.util.Scanner;


public class Game 
{	// OPEN

	public static void main(String[] args) 
	{	// open main
		
		
		
		// declare new scanner variable to obtain user input
		Scanner UserInput = new Scanner(System.in);
		
		
		// print message
		System.out.println("Welcome to Poker - 5 Card Draw\n\n");
		System.out.println("DISCLAIMER: THE RANK 10 IS REPRESENTED BY THE ROMAN NUMERAL FOR 10 WHICH IS X\n");
		System.out.print("Enter the # of opponents you wish to play against (1, 2, or 3):  ");
		
		
		// obtain user input
		int TotalNumPlayers = UserInput.nextInt() + 1; 		// +1 for the user player
		
		
		// create the deck
		CardPile Deck = new CardPile();
		
		// shuffle the deck
		Deck.ShuffleDeck();

		
		// create the max amount of players, i.e. 1 user + 3 AI
		UserPlayer Player1 = new UserPlayer();
		aiPlayer   Player2 = new aiPlayer();
		aiPlayer   Player3 = new aiPlayer();
		aiPlayer   Player4 = new aiPlayer();
		

		System.out.println("The cards are being dealt to the " + TotalNumPlayers + " players.\n");
		
		// deal the cards and allow players to get their turn, and determine a winner
		int Winner = -1;
		EvaluationOfHand eval = new EvaluationOfHand();
		
		if (TotalNumPlayers == 2)
		{
			DealCards2Players(Deck, Player1, Player2);
			
			Player1.UserTurn(Deck);
			Player2.aiTurn(Deck, 2);
			
			Winner = FindWinner2Players(eval, Deck, Player1, Player2);
		}
		else if (TotalNumPlayers == 3)
		{
			DealCards3Players(Deck, Player1, Player2, Player3);
			
			Player1.UserTurn(Deck);
			Player2.aiTurn(Deck, 2);
			Player3.aiTurn(Deck, 3);
			
			Winner = FindWinner3Players(eval, Deck, Player1, Player2, Player3);
		}
		else if (TotalNumPlayers == 4)
		{
			DealCards4Players(Deck, Player1, Player2, Player3, Player4);
			
			Player1.UserTurn(Deck);
			Player2.aiTurn(Deck, 2);
			Player3.aiTurn(Deck, 3);
			Player4.aiTurn(Deck, 4);
			
			Winner = FindWinner4Players(eval, Deck, Player1, Player2, Player3, Player4);
		}
		else
			System.out.println("error");

		
	
		
		// tell the user who was the winner
		
		if (Winner == 0)
			System.out.println("There was a tie.\n");
		else if (Winner == 1)
			System.out.println("You won!!!!\n");
		else if (Winner == 2)
			System.out.println("Player 2 won.\n");
		else if (Winner == 3)
			System.out.println("Player 3 won.\n");
		else if (Winner == 4)
			System.out.println("Player 4 won.\n");
		else
			System.out.println("error");
		
		
		// EXTRA CREDIT this will hold the hands sorted according to pairs for the extra credit
		ArrayList<Card> p1EC, p2EC, p3EC, p4EC;
		p1EC = Player1.ECSort(Player1.GetHand());
		p2EC = Player2.ECSort(Player2.GetHand());
		

		// show each players hand and name the highest possible hand they got
		// PLAYER1
		System.out.println("\n\nYOUR HAND");
		PrintHand(p1EC);
		System.out.println();
		NameTheHand(Player1.GetHand());
		
		// PLAYER2
		System.out.println("\n\nPLAYER 2 HAND");
		PrintHand(p2EC);
		System.out.println();
		NameTheHand(Player2.GetHand());
		
		// PLAYER3
		if (TotalNumPlayers > 2)
		{
			System.out.println("\n\nPLAYER 3 HAND");
			p3EC = Player3.ECSort(Player3.GetHand());
			PrintHand(p3EC);
			System.out.println();
			NameTheHand(Player3.GetHand());
		}
		// PLAYER4
		if (TotalNumPlayers == 4)
		{
			System.out.println("\n\nPLAYER 4 HAND");
			p4EC = Player4.ECSort(Player4.GetHand());
			PrintHand(p4EC);
			System.out.println();
			NameTheHand(Player4.GetHand());
		}
		
		// end the game
		System.out.println("\n\n\n.\n..\n...\nThank you for playing!\n...\n..\n.\n\n");
		System.out.println("\nExiting program...");
		// close the scanner variable
		UserInput.close();
		
	}	// close main
	//-----------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------
	// 
	// PrintHand():
	//
	// prints out the rank and suit of each card in the hand
	//
	public static void PrintHand(ArrayList<Card> Hand)
	{
		for (Card c : Hand)
		{
			System.out.print(c.GetValue() + "  ");
		}
	}
	//-----------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------
	//
	// NameTheHand():
	//
	// will print out what hand the player has
	//
	public static void NameTheHand(ArrayList<Card> Hand)
	{
		EvaluationOfHand eval = new EvaluationOfHand();
		
		int num = eval.Evaluate(Hand);
		
		switch (num)
		{
			case 9: System.out.println("Straight Flush");
					break;
			case 8: System.out.println("4 of a kind");
					break;
			case 7: System.out.println("Full House");
					break;
			case 6:	System.out.println("Flush");
					break;
			case 5: System.out.println("Straight");
					break;
			case 4: System.out.println("3 of a kind");
					break;
			case 3: System.out.println("2 pair");
					break;
			case 2: System.out.println("1 pair");
					break;
			case 1: System.out.println("High Card");
					break;
			default:	System.out.println("error");	
		}
		}
	//-----------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------
	// 
	// These methods will deal each hand to each player, depending on how many players there actually are
	// 		Now we have to deal the cards
	// 		we have to figure out how many cards are going to be dealt in total
	//			if 2 players:   10 cards
	// 			if 3 players:   15 cards
	// 			if 4 players:   20 cards
	//
	public static void DealCards2Players(CardPile Deck, UserPlayer player1, aiPlayer player2)
	{
		for (int i = 1; i <= 10; i=i+2)
		{
			player1.InsertCard(player1.GetHand(), Deck.DrawCard());
			player2.InsertCard(player2.GetHand(), Deck.DrawCard());
		}
	}
	public static void DealCards3Players(CardPile Deck, UserPlayer player1, aiPlayer player2, aiPlayer player3)
	{ 	
		for (int i = 1; i <= 15; i=i+3)
		{
			player1.InsertCard(player1.GetHand(), Deck.DrawCard());
			player2.InsertCard(player2.GetHand(), Deck.DrawCard());
			player3.InsertCard(player3.GetHand(), Deck.DrawCard());
		}
	}
	public static void DealCards4Players(CardPile Deck, UserPlayer player1, aiPlayer player2, aiPlayer player3, aiPlayer player4)
	{
		for (int i = 1; i <= 20; i=i+4)
		{
			player1.InsertCard(player1.GetHand(), Deck.DrawCard());
			player2.InsertCard(player2.GetHand(), Deck.DrawCard());
			player3.InsertCard(player3.GetHand(), Deck.DrawCard());
			player4.InsertCard(player4.GetHand(), Deck.DrawCard());
		}		
	}
	//-----------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------
	//
	// these methods will find evaluate the hands passed to them, and return the winner
	//
	public static int FindWinner2Players(EvaluationOfHand eval, CardPile Deck, 
											UserPlayer Player1, aiPlayer Player2)
	{
		int p1Hand = eval.Evaluate(Player1.GetHand());
		int p2Hand = eval.Evaluate(Player2.GetHand());
		
		int max = Max2(p1Hand, p2Hand);
		
		if (max != 0)
			return max;
		else
		{
			// tie breaker
			ArrayList<Card> p1 = Player1.ECSort(Player1.GetHand());
			ArrayList<Card> p2 = Player2.ECSort(Player2.GetHand());
			
			for (int i = 0; i < p1.size(); i++)
			{
				if (p1.get(i).GetRank() > p2.get(i).GetRank())
					return 1;
				else if (p1.get(i).GetRank() < p2.get(i).GetRank())
					return 2;
			}
			return 0;	
		}
		
	}
	public static int FindWinner3Players(EvaluationOfHand eval, CardPile Deck, 
											UserPlayer Player1, aiPlayer Player2, aiPlayer Player3)
	{	
		int p1Hand = eval.Evaluate(Player1.GetHand());
		int p2Hand = eval.Evaluate(Player2.GetHand());
		int p3Hand = eval.Evaluate(Player3.GetHand());
		
		int max = Max3(p1Hand, p2Hand, p3Hand);
		
		if (max != 0)
			return max;
		else
		{
			// tie breaker
			ArrayList<Card> p1 = Player1.ECSort(Player1.GetHand());
			ArrayList<Card> p2 = Player2.ECSort(Player2.GetHand());
			ArrayList<Card> p3 = Player3.ECSort(Player3.GetHand());

			int rank1, rank2, rank3;
			for (int i = 0; i < p1.size(); i++)
			{
				rank1 = p1.get(i).GetRank();
				rank2 = p2.get(i).GetRank();
				rank3 = p3.get(i).GetRank();
				if (rank1 > rank2 && rank1 > 3)
					return 1;
				else if (rank2 > rank1 && rank2 > rank3)
					return 2;
				else if (rank3 > rank1 && rank3 > rank2)
					return 3;
			}
			return 0;	
		}	
	}
	public static int FindWinner4Players(EvaluationOfHand eval, CardPile Deck, UserPlayer Player1, 
											aiPlayer Player2, aiPlayer Player3, aiPlayer Player4)
	{
		int p1Hand = eval.Evaluate(Player1.GetHand());
		int p2Hand = eval.Evaluate(Player2.GetHand());
		int p3Hand = eval.Evaluate(Player3.GetHand());
		int p4Hand = eval.Evaluate(Player4.GetHand());
		
		int max = Max4(p1Hand, p2Hand, p3Hand, p4Hand);
		
		if (max != 0)
			return max;
		else
		{
			// tie breaker
			ArrayList<Card> p1 = Player1.ECSort(Player1.GetHand());
			ArrayList<Card> p2 = Player2.ECSort(Player2.GetHand());
			ArrayList<Card> p3 = Player3.ECSort(Player3.GetHand());
			ArrayList<Card> p4 = Player4.ECSort(Player4.GetHand());

			int rank1, rank2, rank3, rank4;
			for (int i = 0; i < p1.size(); i++)
			{
				rank1 = p1.get(i).GetRank();
				rank2 = p2.get(i).GetRank();
				rank3 = p3.get(i).GetRank();
				rank4 = p4.get(i).GetRank();
				
				if (rank1 > rank2 && rank1 > 3 && rank1 > rank4)
					return 1;
				else if (rank2 > rank1 && rank2 > rank3 && rank2 > rank4)
					return 2;
				else if (rank3 > rank1 && rank3 > rank2 && rank3 > rank4)
					return 3;
				else if (rank4 > rank1 && rank4 > rank2 && rank4 > rank3)
					return 4;
			}
			return 0;	
		}	
	}
	//-----------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------
	//
	// these methods will return the max value of its parameters, depending on how many numbers are passed.
	// 0 means a tie
	//
	public static int Max2(int num1, int num2)
	{
		if (num1 > num2)
			return 1;
		else if (num2 > num1)
			return 2;
		else
			return 0;
	}
	public static int Max3(int num1, int num2, int num3)
	{
		if (num1 > num2 && num1 > num3)
			return 1;
		else if (num2 > num1 && num2 > num3)
			return 2;
		else if (num3 > num1 && num3 > num2)
			return 3;
		else 
			return 0;
	}
	public static int Max4(int num1, int num2, int num3, int num4)
	{
		if (num1 > num2 && num1 > num3 && num1 > num4)
			return 1;
		else if (num2 > num1 && num2 > num3 && num2 > num4)
			return 2;
		else if (num3 > num1 && num3 > num2 && num3 > num4)
			return 3;
		else if (num4 > num1 && num4 > num2 && num4 > num3)
			return 4;
		else
			return 0;
	}
	//-----------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------
}	// CLOSE
