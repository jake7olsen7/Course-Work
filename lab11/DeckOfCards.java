import java.util.ArrayList;
import java.util.*;
/**
 * Class object for a deck of cards.  This class uses
 * several methods in order to play a game like war
 * which is represented in the CardDealer class.
 * 
 * Reflection
 * 1. This program uses encapsulation by making the
 * actual array of cards private within the class
 * i cannot alter this array without using one of
 * the methods provided.  It also has great
 * encapsulation when you involve the enums
 * that control the cards.
 * 2.  Starting the program, with this much information
 * i tried to jump in and work on things without fully
 * reading the requirements of the lab, nor what each
 * method does.  I started off my constructor in the
 * completely wrong way where this portion was actually
 * already made in the lab notes.  Other than that it
 * was a simple lab.
 *
 * @author Jake Olsen
 */
public class DeckOfCards implements DeckOfCardsInterface {

	   private Card[] cards;
	   private final int DECKSIZE = 52;
	   private int nextCardIndex = 0;
	public DeckOfCards ()
	{
		restoreDeck();
	}
	@Override
	public void shuffle() {
		    Random generator = new Random();

		    //Attempt to swap each card with a random card in the deck.
		    //This isn't a perfect random shuffle but it is a simple one.
		    //A better shuffle requires a more complex algorithm.

		    for (int i=0; i< cards.length; i++) {
		        int j = generator.nextInt(cards.length);
		        Card temp = cards[i];
		        cards[i] = cards[j];
		        cards[j] = temp;
		    }

		    //Reset instance variable that keeps track of dealt and remaining cards.
		    nextCardIndex = 0;
		}

	@Override
	public Card draw() {
		if (nextCardIndex >= DECKSIZE)
			return null;
		else
		{
			Card drawn = cards[nextCardIndex];
			nextCardIndex++;
			return drawn;
		}
	}

	@Override
	public void restoreDeck() {
		cards = new Card[DECKSIZE];
		int i = 0;
		for(Suit s : Suit.values()) {
			  for(FaceValue v : FaceValue.values()) {
				  cards[i] = new Card(s, v);
				  i++;
			  }
			}
	}

	@Override
	public int numCardsRemaining() {
		int remainingCards = DECKSIZE - nextCardIndex;
		return remainingCards;
	}

	@Override
	public int numCardsDealt() {
		return nextCardIndex;
	}

	@Override
	public Card[] dealtCards() {
		if (nextCardIndex == 0)
			return null;
		else
		{
			Card[] dealtCards;
			dealtCards = new Card[nextCardIndex];
			for (int i = 0;i < nextCardIndex;i++)
			{
				dealtCards[i] = cards[i];
			}
			return dealtCards;
		}

	}

	@Override
	public Card[] remainingCards() {
		if ((DECKSIZE - nextCardIndex) == 0)
			return null;
		else
		{
			Card[] remCards;
			remCards = new Card[DECKSIZE - nextCardIndex];
			for (int i = nextCardIndex;i < DECKSIZE;i++)
			{
				remCards[i] = cards[i];
			}
			return remCards;
		}
	}
	public String toString() {
		StringBuilder builder = new StringBuilder();
		String output = new String();
		for (Card currentCard: cards)
		{
			builder.append(currentCard.toString());
			builder.append("  ");
		}
		output = builder.toString();
		return output;
	}
	
}
