package poker;

/**
 * Student-written class.
 * 
 * This class evaluates poker hands. 
 *
 * @author 	CMSC131H Student: 	Praneet
 * 					 TA Name:	Yulu
 * 					 Section:	0101
 * 				   Last Work:	November 10th, 2012
 *
 */

public class PokerHandEvaluator 
{
	//YOUR IMPLEMENTATION HERE
	//THE METHODS MUST MATCH THE DESCRIPTIONS EXACTLY
	
	/*
	 * private helper method that counts how many repetitions of values of 
	 * 	cards exist.
	 *  
	 * Ex. how many 2's there are, etc... 
	 * 
	 * Used for multiple 'has' tests.
	 * 
	 * Expects array of cards to be tested to be passed in.
	 */
	private static int[] valueRepetitions(Card[] cards)
	{
		// variable to count how many repetitions of each value there are.
		// 	Sets all elements to zero.
		int[] repetitions = new int[14];
		
		for (int counter = 0; counter < 14; counter++)
		{
			repetitions[counter] = 0;
		}
		
		// for loop cycles through possible values of cards.
		for (int possibleValue = 1; possibleValue < 14; possibleValue++)
		{
			// for loop goes through each card in parameter array.
			for (int counter = 0; counter < cards.length; counter++)
			{
				// checks if each card is equal to 'possibleValue'.
				// increments 'possibleValue' element of repetitions array
				//	each time there is a match.
				if (cards[counter].getValue() == possibleValue)
					repetitions[possibleValue]++;
			}
		}
		
		return repetitions;
	}
	
	/*
	 * private helper method that counts how many repetitions of suits of 
	 * 	cards exist.
	 *  
	 * Ex. how many diamonds of cards there are, etc... 
	 * 
	 * Used for multiple 'has' tests.
	 * 
	 * Expects array of cards to be tested to be passed in.
	 */
	private static int[] suitRepetitions(Card[] cards)
	{
		// variable to count how many repetitions of each value there are.
		int[] repetitions = {0,0,0,0};
		
		// for loop cycles through possible values of cards.
		for (int possibleSuit = 0; possibleSuit <= 3; possibleSuit++)
		{
			// for loop goes through each card in parameter array.
			for (int counter = 0; counter < cards.length; counter++)
			{
				// checks if each card is equal to 'possibleValue'.
				// increments 'possibleValue' element of repetitions array
				//	each time there is a match.
				if (cards[counter].getSuit() == possibleSuit)
					repetitions[possibleSuit]++;
			}
		}
		
		return repetitions;
	}
	
	/* 
	 * private helper method that checks if hand has user-specified number of 
	 * 	repetitions of a card value.
	 * 
	 * Ex. are there 2 pairs?, etc.
	 * 
	 * Used in multiple 'has' tests.
	 * 
	 * Expects array to be tested, and integer of minimum number of 
	 * 	repetitions.
	 */
	private static boolean numRepetitions(Card[] cards, int numIn)
	{
		// boolean variable for whether or not there are enough repetitions.
		boolean hasEnough = false;
		
		// Call on valueRepetitions method to get array of number of
		//	repetitions of each value.
		int[] repetitions = valueRepetitions(cards);
		
		// checks each element of repetitions array if there is a value that 
		//	had enough repetitions.
		int counter = 0;
		
		while (!hasEnough && counter < repetitions.length)
		{
			if (repetitions[counter] >= numIn)
				hasEnough = true;
			
			counter ++;
		}
		
		return hasEnough;
	}
	
	/*
	 * private helper method that helps order cards in ascending order.
	 * 
	 * Used for multiple 'has' tests.
	 * 
	 * Expects array of cards to be ordered to be passed in.
	 */
	private static Card[] orderCards(Card[] cards)
	{	
		Card holder;
		
		for (int cycles = 0; cycles <= 3; cycles++)
		{
			for (int counter = 0; counter < cards.length - 1; counter++)
			{
				if (cards[counter + 1].getValue() < cards[counter].getValue())
				{
					holder = cards[counter];
					cards[counter] = cards[counter + 1];
					cards[counter + 1] = holder;
				}
			}
		}
		
		return cards;
	}
	
	/* 
	 * The 'hasPair' method checks if the hand has a pair of values and returns
	 *	a boolean value describing whether or not the hand has a pair.
	 *
	 * Expects an array of 5 cards (the hand) to be passed in.
	 */
	public static boolean hasPair(Card[] cards)
	{
		boolean hasPair = numRepetitions(cards, 2);
		return hasPair;
	}
	
	/*
	 * The 'hasRainbow' method checks if the hand has a rainbow, i.e. each suit
	 *	is represented at least once. Returns boolean value indicating such.
	 *
	 * Expects an array of 5 cards (the hand) to be passed in.
	 */
	public static boolean hasRainbow(Card[] cards)
	{
		// boolean variable for whether or not there is a pair.
		boolean hasRainbow = false;
		
		// Call on suitRepetitions method to get array of number of repetitions
		//	of each suit.
		int[] repetitions = suitRepetitions(cards);
		
		// Checks if each element of repetitions array is equal to or greater
		// 	than 1.
		if (repetitions[0] >= 1 && repetitions[1] >= 1 && repetitions[2] >= 1
				&& repetitions[3] >= 1)
			hasRainbow = true;
		
		return hasRainbow;
	}
	
	/*
	 * The 'hasTwoPair' method checks if the hand has two pairs of DIFFERENT 
	 *  values. Returns boolean value indicating such.
	 *  
	 * Expects an array of 5 cards (the hand) to be passed in.
	 */
	public static boolean hasTwoPair(Card[] cards)
	{
		// boolean variable for whether or not two pairs exist.
		boolean hasTwoPairs = false;
		
		// Call on valueRepetitions method to get array of number of 
		// 	repetitions of each value.
		int[] repetitions = valueRepetitions(cards);
		
		// Checks if two elements of repetitions array had pairs.
		int counter = 0;	// array position.
		int numPairs = 0;	// number of elements that indicate pairs.
		
		while (counter < repetitions.length && numPairs < 2)
		{
			if (repetitions[counter] >= 2)
				numPairs++;
			
			counter++;
		}
		
		if (numPairs == 2)
		{
			hasTwoPairs = true;
		}
		
		return hasTwoPairs;
	}
	
	/* 
	 * The 'hasThreeOfAKind' method checks if the hand has a three cards with
	 * 	same values and returns a boolean value describing such.
	 *
	 * Expects an array of 5 cards (the hand) to be passed in.
	 */
	public static boolean hasThreeOfAKind(Card[] cards)
	{
		boolean hasThreeOfAKind = numRepetitions(cards, 3);
		return hasThreeOfAKind;
	}
	
	/*
	 * The 'hasOddStraight' method checks if the hand has an Odd Straight (see
	 * 	Wacky Poker Hands Rankings page for description) and returns boolean
	 * 	value describing such.
	 * 
	 * Expects an array of 5 cards (the hand) to be passed in.
	 */
	public static boolean hasOddStraight(Card[] cards)
	{
		// boolean value for if there is odd straight.
		boolean hasOddStraight = false;
		
		// Call on orderCards method to order cards.
		cards = orderCards(cards);
		
		// Make sure all cards are odd and are in straight..
		boolean isOdd = true;
		boolean isStraight = true;
		int counter = 1;
		
		while (isOdd && isStraight && counter < cards.length)
		{
			if (cards[counter - 1].getValue() %2 == 0)
			{
				return hasOddStraight;
			}
			
			if (cards[counter].getValue() - 
					cards[(2*counter) - (counter + 1)].getValue() != 2)
			{
				return hasOddStraight;
			}
			counter++;
		}
		
		hasOddStraight = true;
		return hasOddStraight;
	}
	
	/*
	 * The 'hasEvenStraight' method checks if the hand has an Even Straight (see
	 * 	Wacky Poker Hands Rankings page for description) and returns boolean
	 * 	value describing such.
	 * 
	 * Expects an array of 5 cards (the hand) to be passed in.
	 */
	public static boolean hasEvenStraight(Card[] cards)
	{	
		// boolean variable for whether or not even straight exists.
		boolean hasEvenStraight = false;
		
		// Call on orderCards method to order cards.
		cards = orderCards(cards);
		
		// In the special case that hand has Ace.
		if (cards[0].getValue() == 1 && cards[1].getValue() == 6 &&
				cards[2].getValue() == 8 && cards[3].getValue() == 10 && 
				cards[4].getValue() == 12)
		{
			hasEvenStraight = true;
			return hasEvenStraight;
		}
		
		// Make sure all cards are odd and are in straight.
		boolean isEven = true;
		boolean isStraight = true;
		int counter = 1;
		
		while (isEven && isStraight && counter < cards.length)
		{
			// if card values aren't even.
			if (cards[counter].getValue() %2 != 0)
			{
				return hasEvenStraight;
			}
			
			// if cards values don't form straight.
			if (cards[counter].getValue() - 
					cards[(2*counter) - (counter + 1)].getValue() != 2)
			{
				return hasEvenStraight;
			}
			counter++;
		}
		
		hasEvenStraight = true;
		return hasEvenStraight;
	}
	
	/*
	 * The 'hasFlush' method checks if the hand has a flush, or 5 cards of the
	 * 	same suit and returns boolean value describing such.
	 * 
	 * Expects an array of 5 cards (the hand) to be passed in.
	 */
	public static boolean hasFlush(Card[] cards)
	{
		// boolean variable indicating whether or not hand has flush.
		boolean hasFlush = false;
		
		// call on suitRepetitions method to get array of number of repetitions 
		//	of each suit.
		int[] repetitions = suitRepetitions(cards);
		
		// Checks repetitions array if any element indicates one suit has 5 
		//	repetitions.
		int counter = 0;
		
		while (!hasFlush && counter < repetitions.length)
		{
			if (repetitions[counter] == 5)
				hasFlush = true;
			
			counter++;
		}
		
		return hasFlush;
	}
	
	/*
	 * The 'hasFullHouse' method checks if the hand has a full house, a hand
	 *	where has a pair of cards share the same value and the other three
	 *	cards share another value, and returns a boolean value describing such.
	 *
	 * Expects an array of 5 cards (the hand) to be passed in.
	 */
	public static boolean hasFullHouse(Card[] cards)
	{
		// boolean variable indicating whether or not hand has full house.
		boolean hasFullHouse = false;
		
		if (hasTwoPair(cards) && hasThreeOfAKind(cards))
		{
			hasFullHouse = true;
		}
		
		return hasFullHouse;
	}
	
	/*
	 * The 'fourOfAKind' method checks if the hand has four cards of the same
	 * 	value and returns a boolean value indicating such.
	 * 
	 * Expects an array of 5 cards (the hand) to be passed in.
	 */
	public static boolean hasFourOfAKind(Card[] cards)
	{
		// boolean variable indicating whether or not there are four of a kind.
		boolean hasFourOfAKind = numRepetitions(cards, 4);
		return hasFourOfAKind;
	}
	
	/*
	 * The 'straightRainbow' method checks if the hand has both a straight
	 * 	(either even or odd) and a rainbow, and returns a boolean value 
	 * 	indicating such.
	 * 
	 * Expects an array of 5 cards (the hand) to be passed in.
	 */
	public static boolean hasStraightRainbow(Card[] cards)
	{
		// boolean variable indicating whether or not there is a straight
		//	rainbow.
		boolean hasStraightRainbow = false;
		
		if ((hasEvenStraight(cards) || (hasOddStraight(cards))) && 
				hasRainbow(cards))
			hasStraightRainbow = true;
		
		return hasStraightRainbow;
	}
	
	/*
	 * The 'straightFlush' method checks if the hand has both a straight 
	 * 	(either even or odd) and a flush, and returns a boolean value
	 * 	indicating such.
	 * 
	 * Expects an array of 5 cards (the hand) to be passed in.
	 */
	public static boolean hasStraightFlush(Card[] cards)
	{
		// boolean variable indicating whether or not there is a straight
		//	flush.
		boolean hasStraightFlush = false;
		
		if ((hasEvenStraight(cards) || (hasOddStraight(cards))) && 
				hasFlush(cards))
			hasStraightFlush = true;
		
		return hasStraightFlush;
	}
	
}

