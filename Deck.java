import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>(); // Initialize the cards list
        initializeDeck(); // Populate the deck with cards
        shuffle(); // Shuffle the deck
    }

     void initializeDeck() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (int rankIndex = 0; rankIndex < Card.Rank.length; rankIndex++) {
                cards.add(new Card(suit.name(), rankIndex));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            initializeDeck();
            shuffle();
        }
        return cards.remove(cards.size() - 1);
    }

    public int size() {
        return cards.size();
    }
}