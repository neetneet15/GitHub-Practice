package poker;

/**
 * Student-written class.
 * 
 * This class represents a deck of cards. Starts with 52 cards, but as cards
 * 	are dealt, the number of cards becomes smaller. 
 * 
 * First card in array will be considered card at top of the deck. When cards
 * 	are removed, they will be removed from the front of the array.
 * 
 * @author 	CMSC131H Student: 	Praneet
 * 					 TA Name:	Yulu
 * 					 Section:	0101
 * 				   Last Work:	November 4th, 2012
 *
 */

public class Deck 
{
	private Card[] cards;

	/* 
	 * Constructor initializes Deck with 52 card objects, representing 52 cards
	 * 	in a standard deck. First card is Ace of Spades, last is King of 
	 *	Diamonds.
	 *
	 * Expects no parameters. 
	 */
	final int FULL_DECK_LENGTH = 52;
	
	public Deck() 
	{
		cards = new Card[FULL_DECK_LENGTH];
		
		int currentCard = 0; 	// variable for current card in array.
		
		// Iterates through possible suits of cards.
		for (int suitCounter = 0; suitCounter <= 3; suitCounter++)
		{
			// Iterates through possible values of cards.
			for (int valCounter = 1; valCounter <= 13; valCounter++)
			{
				cards[currentCard] = new Card(valCounter, suitCounter);
				currentCard++;
			}
		}
	}

	// Copy constructor. Makes shallow copy of array of cards.
	// Expects deck of which a copy will be made to be passed in.
	public Deck(Deck other) 
	{		
		this.cards = other.cards;
	}

	// Returns the card at specified position in array using 0-based indexing.
	// Expects integer of position to be passed in.
	public Card getCardAt(int position) 
	{
		return cards[position]; 
	}

	/*
	 * Returns the size of the array of Cards. Starts off equal to 52, but 
	 * 	becomes smaller as cards are dealt.
	 * 
	 * Expects no parameters.
	 */
	public int getNumCards() 
	{
		return cards.length;
	}
	
	/*
	 * First private utility method that splits deck into two 'packets'. 
	 * 	This first method returns the top packet.
	 * 
	 * Expects integer position where deck will be split.
	 */
	private Card[] splitTop(int splitPositionIn)
	{
		Card[] topPacket = new Card[splitPositionIn];
		for (int counter = 0; counter < splitPositionIn; counter++)
		{
			topPacket[counter] = cards[counter];
		}
		
		return topPacket;
	}
	
	/*
	 * Second private utility method that splits deck into two 'packets'.
	 * 	This is the second part and returns the bottom packet.
	 * 
	 * Expects integer position where deck will be split.
	 */
	private Card[] splitLow(int splitPositionIn)
	{
		Card[] lowPacket = new Card[cards.length - splitPositionIn];
		for (int counter = splitPositionIn; counter < cards.length; counter++)
		{
			lowPacket[counter - splitPositionIn] = cards[counter];
		}
		
		return lowPacket;
	}
	
	 /* 
	  * hint: even and odd
	  * 
	  * Re-arrange cards that are in deck. How to do this? Split deck into 
	  *  two "packets" (halves). New deck will be first card from top packet, 
	  *  first card from bottom packet, second card from top packet, second 
	  *  card from bottom packet, etc... 
	  *  
	  *  If there is an odd number of cards in the deck, the top deck should
	  *   have one more card than the bottom deck.
	  *   
	  * Top of deck = front of array
	  * 
	  * Expects no parameters.
	  */  
	public void shuffle() 
	{
		int splitPosition; // position where deck will be split
		
		if (cards.length%2 != 0)	// if odd, top half will have one more card
			splitPosition = cards.length/2 + 1;	
		else	// if even, both halves will have equal number of cards
			splitPosition = cards.length/2;
		
		// Use split methods to get two 'packets' of split deck.
		Card[] topPacket = splitTop(splitPosition);
		Card[] lowPacket = splitLow(splitPosition);
		
		// Rearrange current cards. Get rid of old and add new ones. 
		// Alternate adding cards from top and low packets, starting with first  
		//	card from top packet.
		for (int counter = 0; counter < splitPosition-1; counter++)
		{
			cards[2*counter] = topPacket[counter];
			cards[2*counter + 1] = lowPacket[counter];
		}
	
		// Assign highest even term the last term of the topPacket.
		cards[(splitPosition-1) * 2] = topPacket[splitPosition - 1];
		
		// If length is even, assign last term (highest odd term), the last 
		//	term of the lowPacket.
		if (cards.length%2 == 0)	// if even length
		{
			cards[cards.length - 1] = lowPacket[splitPosition-1];
		}
	}

	/*
	 * Divides deck at user-specified position. Creates two 'packets': one with
	 * 	cards above that point, and one for all cards at and below that point.
	 *  Then, top packet and bottom packet are switched, i.e. bottom packet 
	 *  goes on top and top packet goes on bottom.
	 *  
	 * Expects integer position of where deck should be split.
	 */
	public void cut(int position) 
	{
		// Use split methods to get two 'packets' of split deck.
		Card[] topPacket = splitTop(position);
		Card[] lowPacket = splitLow(position);
		
		for (int counter = 0; counter < lowPacket.length; counter++)
		{
			cards[counter] = lowPacket[counter];
		}
		
		for (int x = lowPacket.length; x < cards.length; x++)
		{
			cards[x] = topPacket[x - lowPacket.length];
		}
	}

	/*
	 * Deal cards. Remove specified number of cards from top of deck and return
	 * 	them as array. Cards that are not removed become rest of deck. Uses 
	 *  split methods to do so. Top packet becomes dealt hand, and bottom 
	 *  packet becomes rest of deck. 
	 *  
	 * Expects integer number of cards to be dealt.
	 */
	public Card[] deal(int numCards) 
	{
		// Use split methods to get array of dealt cards (topPacket) and the
		// 	rest of the deck (lowPacket).
		Card[] dealtCards = splitTop(numCards);
		cards = splitLow(numCards);
		
		return dealtCards;
	} 
 
}
