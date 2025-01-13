import javax.swing.*;

public class Card {
    private String suit;
    private int value;
    private String rank;
    private ImageIcon frontImage;
    private ImageIcon backImage;
    private boolean isFaceUp;

    public enum Suit {
        h, d, c, s // Hearts, Diamonds, Clubs, Spades
    }

    // ranks in a deck
    public static final String[] Rank = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "ace", "jack", "king", "queen"};

    // Constructor
    public Card(String suit, int rankIndex) {
        this.suit = suit;
        this.rank = Rank[rankIndex];
        this.value = getValueFromRank(rankIndex);
        this.frontImage = loadImage(suit, rankIndex);
        this.backImage = loadBackImage();
        this.isFaceUp = true;
    }

    // Private helper method to determine the card's value based on its rank index
    private int getValueFromRank(int rankIndex) {
        switch (Rank[rankIndex]) {
            case "ace":
                return 14; // Ace has the highest value (14)
            case "jack":
                return 11; // Jack's value is 11
            case "queen":
                return 12; // Queen's value is 12
            case "king":
                return 13; // King's value is 13
            default:
                return Integer.parseInt(Rank[rankIndex]); // Numeric ranks have their face value
        }
    }

    // Private helper method to load the front face image of the card based on its suit and rank
    private ImageIcon loadImage(String suit, int rankIndex) {
        String imagePath = "src/Cards/" + Rank[rankIndex] + suit + ".jpg"; // Construct the image path
        ImageIcon icon = new ImageIcon(imagePath); // Load the image
        return icon; // Return the loaded image or a placeholder
    }

    // Private helper method to load the back face image of the card
    private ImageIcon loadBackImage() {
        String imagePath = "src/Cards/back.jpg"; // Path to the card's back image
        ImageIcon icon = new ImageIcon(imagePath); // Load the image
        return icon; // Return the loaded back image or a placeholder
    }

    // Method to flip the card's
    public void flip() {
        isFaceUp = !isFaceUp; // Toggle the boolean isFaceUp
    }

    // Method to get the current image of the card
    public ImageIcon getImage() {
        return isFaceUp ? frontImage : backImage; // Return front image if face-up, else back image
    }

    // Getter for the card's suit
    public String getSuit() {
        return suit;
    }


    // Getter for the card's rank
    public String getRank() {
        return rank;
    }



    // Method to explicitly set the card to face-up
    public void setFaceUp() {
        this.isFaceUp = true; // Set the card's state to face-up
    }

    // Method to explicitly set the card to face-down
    public void setFaceDown() {
        this.isFaceUp = false; // Set the card's state to face-down
    }
}