package pokergame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class PokerGame {

	private final static int TOTAL_SETS = 4;
	private final static int TOTAL_RANKS = 13;
	static List<Card> cardList = new ArrayList<Card>();
	static HashMap<Integer, String> pokerMap = new HashMap<Integer, String>();
	static Scanner scan = new Scanner(System.in);
	
	
	public static void main(String[] args) {
		// Create 52 cards and add to list
		intializeCards();
		
		int highestRankPlayer = 10;
		int highestIndex = 0;
		for(int i=1; i<6; i++) {
			// If the player want's to pick, enter 1 or else player can just skip
			System.out.print("Player:"+ i + ",  1 for pick, 0 for  skip");
			int input = scan.nextInt();
			if (input == 1) {
				int playersRank = getPlayerRank();
				if (playersRank > 0) {
					if (highestRankPlayer >= playersRank) {
						highestRankPlayer = playersRank;
						highestIndex = i;
					}
				}
			}
		}
		
		if (highestIndex == 0) {
			System.out.println("Winner of the poker: Game Drawn");
			System.out.print("Won under Category : None");
		} else {
			System.out.println("Winner of the poker: Player"+ highestIndex);
			System.out.print("Won under Category : "  + pokerMap.get(highestRankPlayer));
		}
	}

   // Create 52 cards, and shuffle randomly.
	private static void intializeCards() {
		for(int i=1; i < 11; i++) {
			pokerMap.put(1, "Royal Flush");
			pokerMap.put(2, "Straight Flush");
			pokerMap.put(3, "Four of a Kind");
			pokerMap.put(4, "Full House");
			pokerMap.put(5, "Flush");
			pokerMap.put(6, "Straight");
			pokerMap.put(7, "Three of a kind");
			pokerMap.put(8, "Two Pairs");
			pokerMap.put(9, "One Pair");
			pokerMap.put(10, "High Card");
		}
		
		for (int setNum = 1; setNum <= TOTAL_SETS; setNum++)
		{
			for (int rank = 1; rank <= TOTAL_RANKS; rank++)
			{
				Card card = new Card();
				card.setSetType(setNum);
				card.setRank(rank);
				cardList.add(card);
			}
		}
		Collections.shuffle(cardList);
	}

	// Get Players Rank
	private static int getPlayerRank() {
		List<Card> playerCardList = new ArrayList<Card>();
		for (int i = 0; i < 5; i++) {
			playerCardList.add(cardList.get(i));
			cardList.remove(i);
		}
		Collections.sort(playerCardList, new Comparator<Card>() {

			@Override
			public int compare(Card c1, Card c2) {
	            return c2.getRank() - c1.getRank();
			}
		});
		if (royalFlush(playerCardList)) {
			return 1;
		}
		if (straightFlush(playerCardList)) {
			return 2;
		}
		if (fourOfaKind(playerCardList)) {
			return 3;
		}
		if (fullHouse(playerCardList)) {
			return 4;
		}
		if (flush(playerCardList)) {
			return 5;
		}
		if (straight(playerCardList)) {
			return 6;
		}
		if (threeOfaKind(playerCardList)) {
			return 7;
		}
		if (twoPairs(playerCardList)) {
			return 8;
		}
		if (onePair(playerCardList)) {
			return 9;
		}
		//Default it is will return 10. Which means, if none of the above categories matches, it assues its high Card 
		//highCard(playerCardList)
		return 10;
		
	}	
	
	// Royal flush
	public static boolean royalFlush(List<Card> cardsList) {
		if (cardsList.get(0).rank == 1 && cardsList.get(1).rank == 10 && cardsList.get(2).rank == 11 &&
				cardsList.get(3).rank == 12 && cardsList.get(4).rank == 13) {
			return true;
		}
		return false;
	}

	// Straight flush
	public static boolean straightFlush(List<Card> cardsList) {
		for (int index = 1; index < cardsList.size(); index++) {
			if (cardsList.get(0).getSetType() != cardsList.get(index).getSetType()) {
				return false;
			}
		}
		for (int index = 1; index < 5; index++) {
			if (cardsList.get(index - 1).getRank() != (cardsList.get(index).getRank() - 1))
			{
				return false;
			}
		}
		return true;
	}
	
	// Four of a kind
	public static boolean fourOfaKind(List<Card> cardsList) {
		if (cardsList.get(0).getRank() != cardsList.get(3).getRank() && cardsList.get(1).getRank() != cardsList.get(4).getRank()) {
			return false;
		}
		return true;
	}
	
	// Full house
	public static boolean fullHouse(List<Card> cardsList) {
		int firstMatchCount = 0;
		boolean secondMatch = false;
		for (int index = 1; index < 5; index++) {
			if (cardsList.get(index - 1).getRank() == cardsList.get(index).getRank()) {
				firstMatchCount++;
			}
		}
		if(cardsList.get(3) == cardsList.get(4)) {
			secondMatch = true;
		}

		if (firstMatchCount == 3 && (secondMatch == true)) {
			return true;
		}
		return false;
	}
	
	// Flush
	public static boolean flush(List<Card> cardsList) {
		for (int index = 1; index < 5; index++) {
			if (cardsList.get(0).getSetType() != cardsList.get(index).getSetType()) {
				return false;
			}
		}
		return true;
	}
	
	// Straight
	public static boolean straight(List<Card> cardsList) {
		for (int index = 1; index < 5; index++) {
			if (cardsList.get(index - 1).rank != (cardsList.get(index).rank - 1)) {
				return false;
			}
		}
		return true;
	}
	
	// Triple
	public static boolean threeOfaKind(List<Card> cardsList) {
		int firstMatchCount = 0;
		for (int index = 1; index < 5; index++) {
			if (cardsList.get(index - 1).getRank() == cardsList.get(index).getRank()) {
				firstMatchCount++;
			}
		}
		if (firstMatchCount == 3) {
			return true;
		}
		return false;
	}
	
	// Two pairs
	public static boolean twoPairs(List<Card> cardsList) {
		int firstMatchCount = 0;
		for (int index = 1; index < 5; index++) {
			if (cardsList.get(index - 1).getRank() == cardsList.get(index).getRank()) {
				firstMatchCount++;
			}
		}
		if (firstMatchCount == 3) {
			return true;
		}
		return false;
	}
	
	// One pair
	public static boolean onePair(List<Card> cardsList) {
		int firstMatchCount = 0;
		for (int index = 1; index < 5; index++) {
			if (cardsList.get(index - 1).getRank() == cardsList.get(index).getRank()) {
				firstMatchCount++;
			}
		}
		if (firstMatchCount == 1) {
			return true;
		}
		return false;
	}
	
	// Highest card
	public static int highCard(List<Card> cardsList) {
		int highCardRank = 0;
		for (int index = 0; index < 5; index++) {
			if (cardsList.get(index).getRank() > highCardRank)
			{
				highCardRank = cardsList.get(index).getRank();
			}
		}
		return highCardRank;
	}
}
