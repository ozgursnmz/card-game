import java.util.*;

public class Player {
    private String name;
    private int score;
    private ArrayList<Card> hand;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
    public void resetScore() {
        this.score = 0;
    }

    public void calculateScore(List<Card> deskCards) {
        int totalScore = 0;
        List<Card> allCards = new ArrayList<>(hand); // Combine player's hand and desk cards
        allCards.addAll(deskCards);

        Map<String, Integer> rankCount = new HashMap<>();
        Map<String, Integer> suitCount = new HashMap<>();

        // Calculate base score and count occurrences of ranks and suits
        for (Card card : allCards) {
            int cardValue = getCardValue(card.getRank()); // Use the getCardValue method
            totalScore += cardValue;

            // Count rank occurrences
            rankCount.put(card.getRank(), rankCount.getOrDefault(card.getRank(), 0) + 1);

            // Count suit occurrences
            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);
        }

        // Check for pairs, three of a kind, and four of a kind
        for (Map.Entry<String, Integer> rankEntry : rankCount.entrySet()) {
            int count = rankEntry.getValue();
            int cardValue = getCardValue(rankEntry.getKey());
            if (count == 2) { // Pair
                totalScore += cardValue; // Add the value again for doubling
            } else if (count == 3) { // Three of a Kind
                totalScore += 2 * cardValue; // Add twice the value for tripling
            } else if (count == 4) { // Four of a Kind
                totalScore += 3 * cardValue; // Add thrice the value for quadrupling
            }
        }

        // Check for straights (5 or more consecutive values)
        if (isStraight(allCards)) {
            totalScore *= 5; // Multiply total score by 5
        }

        // Check for flushes (5 or more cards of the same suit)
        for (Map.Entry<String, Integer> suitEntry : suitCount.entrySet()) {
            if (suitEntry.getValue() >= 5) { // Flush
                totalScore *= 6; // Multiply total score by 6
                break;
            }
        }

        this.score = totalScore;
    }

    private int getCardValue(String rank) {
        switch (rank) {
            case "ace":
                return 14;
            case "king":
                return 13;
            case "queen":
                return 12;
            case "jack":
                return 11;
            default:
                return Integer.parseInt(rank); // Return numeric value for number cards
        }
    }

    private boolean isStraight(List<Card> cards) {
        List<Integer> cardValues = new ArrayList<>();
        for (Card card : cards) {
            cardValues.add(getCardValue(card.getRank()));
        }
        Collections.sort(cardValues);

        int consecutiveCount = 1;
        for (int i = 1; i < cardValues.size(); i++) {
            if (cardValues.get(i) == cardValues.get(i - 1) + 1) { // Consecutive values
                consecutiveCount++;
                if (consecutiveCount >= 5) {
                    return true; // Straight of 5 or more
                }
            } else if (cardValues.get(i) != cardValues.get(i - 1)) {
                consecutiveCount = 1; // Reset count if not consecutive
            }
        }
        return false;
    }
}