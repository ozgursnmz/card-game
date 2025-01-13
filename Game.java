import java.util.List;

public class Game {
    private Player player1;
    private Player player2;
    private List<Card> deskCards;

    public Game(Player player1, Player player2, List<Card> deskCards) {
        this.player1 = player1;
        this.player2 = player2;
        this.deskCards = deskCards;
    }

    public void calculateScores() {
        player1.calculateScore(deskCards); // Pass deskCards to Player 1's score calculation
        player2.calculateScore(deskCards); // Pass deskCards to Player 2's score calculation
    }
}