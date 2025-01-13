import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class gamePanel extends JPanel {
    // Game components and variables
    private Player player1; // Player 1 instance
    private Player player2; // Player 2 instance
    private Deck deck; // Deck of cards
    private JPanel middlePanel; // Panel for desk cards
    private JPanel player1Panel; // Panel for Player 1's cards
    private JPanel player2Panel; // Panel for Player 2's cards
    private JButton changeButton; // Button to change cards
    private int player1Score; // Player 1's score
    private int player2Score; // Player 2's score
    private int roundCounter; // Count of completed rounds
    private boolean isPlayer1Turn; // Tracks the current turn
    private JCheckBox[] player1CheckBoxes; // Checkboxes for Player 1's cards
    private JCheckBox[] player2CheckBoxes; // Checkboxes for Player 2's cards
    private JLabel player1ScoreLabel; // Label to display Player 1's score
    private JLabel player2ScoreLabel; // Label to display Player 2's score
    private List<Card> deskCards; // List of desk cards
    private int player1RoundsWon = 0; // Rounds won by Player 1
    private int player2RoundsWon = 0; // Rounds won by Player 2

    public gamePanel() {
        // Set up layout and initialize panels
        setLayout(new BorderLayout());

        Dimension panelSize = new Dimension(250, 150); // Set panel size for uniformity

        // Initialize Player 1's panel
        player1Panel = new JPanel();
        player1Panel.setBorder(BorderFactory.createTitledBorder("Player 1's Cards"));
        player1Panel.setBackground(new Color(4, 185, 118));
        player1Panel.setPreferredSize(panelSize);
        add(player1Panel, BorderLayout.NORTH);

        // Initialize Player 2's panel
        player2Panel = new JPanel();
        player2Panel.setBorder(BorderFactory.createTitledBorder("Player 2's Cards"));
        player2Panel.setBackground(new Color(4, 185, 118));
        player2Panel.setPreferredSize(panelSize);
        add(player2Panel, BorderLayout.SOUTH);

        // Initialize middle panel for desk cards
        middlePanel = new JPanel();
        middlePanel.setBorder(BorderFactory.createTitledBorder("Middle Cards"));
        middlePanel.setBackground(new Color(4, 223, 143));
        middlePanel.setPreferredSize(panelSize);
        add(middlePanel, BorderLayout.CENTER);

        // Initialize button panel with a change button and score labels
        JPanel buttonPanel = new JPanel();
        changeButton = new JButton("Change");
        changeButton.setPreferredSize(new Dimension(80, 30));
        buttonPanel.add(changeButton);

        // Initialize score labels
        player1ScoreLabel = new JLabel("Player 1 Score: 0");
        player2ScoreLabel = new JLabel("Player 2 Score: 0");
        buttonPanel.add(player1ScoreLabel);
        buttonPanel.add(player2ScoreLabel);

        // Add button panel to the main panel
        add(buttonPanel, BorderLayout.EAST);

        // Initialize game variables
        deck = new Deck(); // Create a new deck
        player1 = new Player("Player 1"); // Create Player 1
        player2 = new Player("Player 2"); // Create Player 2

        player1CheckBoxes = new JCheckBox[5]; // Checkboxes for Player 1
        player2CheckBoxes = new JCheckBox[5]; // Checkboxes for Player 2

        // Initialize checkboxes for both players
        for (int i = 0; i < 5; i++) {
            player1CheckBoxes[i] = new JCheckBox();
            player2CheckBoxes[i] = new JCheckBox();
        }

        // Set initial values for variables
        isPlayer1Turn = true;
        player1Score = 0;
        player2Score = 0;
        roundCounter = 0;

        deskCards = new ArrayList<>(); // Initialize desk cards

        // Start the game
        gameStart();
        updateCheckBoxes();
        updateCardVisibility();

        // Set action listener for the change button
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPlayer1Turn) {
                    // Handle card change for Player 1
                    changeSelectedCards(player1, player1Panel, player1CheckBoxes);
                } else {
                    // Handle card change for Player 2 and compare scores
                    changeSelectedCards(player2, player2Panel, player2CheckBoxes);
                    compareScores();
                }
                endTurn(); // Switch turn and update visibility
                updateCheckBoxes();
            }
        });
    }

    private void gameStart() {
        // Reset players' scores
        player1.resetScore();
        player2.resetScore();

        // Clear previous hands and desk cards
        player1.getHand().clear();
        player2.getHand().clear();
        deskCards.clear();

        // Deal 5 cards to each player
        for (int i = 0; i < 5; i++) {
            if (deck.size() == 0) {
                deck.initializeDeck(); // Reinitialize the deck if empty
                deck.shuffle(); // Shuffle the new deck
            }
            player1.addCard(deck.drawCard()); // Deal to Player 1
            player2.addCard(deck.drawCard()); // Deal to Player 2
        }

        // Update Player 1's panel with new cards
        player1Panel.removeAll();
        for (int i = 0; i < player1.getHand().size(); i++) {
            Card card = player1.getHand().get(i);
            JLabel cardLabel = new JLabel(card.getImage());
            player1Panel.add(cardLabel);
            player1Panel.add(player1CheckBoxes[i]);
        }

        // Update Player 2's panel with new cards
        player2Panel.removeAll();
        for (int i = 0; i < player2.getHand().size(); i++) {
            Card card = player2.getHand().get(i);
            JLabel cardLabel = new JLabel(card.getImage());
            player2Panel.add(cardLabel);
            player2Panel.add(player2CheckBoxes[i]);
        }

        // Update the middle panel with desk cards
        middlePanel.removeAll();
        for (int i = 0; i < 5; i++) {
            if (deck.size() == 0) {
                deck.initializeDeck();
                deck.shuffle();
            }
            Card deskCard = deck.drawCard();
            deskCard.setFaceDown(); // Set desk cards to face down
            deskCards.add(deskCard);
            JLabel cardLabel = new JLabel(deskCard.getImage());
            middlePanel.add(cardLabel);
        }

        // Refresh the UI
        player1Panel.revalidate();
        player1Panel.repaint();
        player2Panel.revalidate();
        player2Panel.repaint();
        middlePanel.revalidate();
        middlePanel.repaint();
    }

    private void endTurn() {
        isPlayer1Turn = !isPlayer1Turn; // Switch turn

        // Update card visibility and score labels
        updateCardVisibility();
        updateScoreLabels();
    }

    public void updateCardVisibility() {
        // Update visibility: Show cards for the current player, hide for the other
        flipCards(player1, player1Panel, isPlayer1Turn);
        flipCards(player2, player2Panel, !isPlayer1Turn);
    }

    private void flipCards(Player player, JPanel playerPanel, boolean isFaceUp) {
        // Flip cards and update UI
        for (int i = 0; i < player.getHand().size(); i++) {
            Card card = player.getHand().get(i);
            if (isFaceUp) {
                card.setFaceUp();
            } else {
                card.setFaceDown();
            }
            JLabel cardLabel = (JLabel) playerPanel.getComponent(i * 2);
            cardLabel.setIcon(card.getImage());
        }
        playerPanel.revalidate();
        playerPanel.repaint();
    }

    private void changeSelectedCards(Player player, JPanel playerPanel, JCheckBox[] checkBoxes) {
        // Handle selected card replacement
        for (int i = 0; i < 5; i++) {
            if (checkBoxes[i].isSelected()) {
                if (i < player.getHand().size()) {
                    player.getHand().remove(i);
                    if (deck.size() > 0) {
                        Card newCard = deck.drawCard();
                        player.addCard(newCard);
                        JLabel cardLabel = new JLabel(newCard.getImage());
                        playerPanel.remove(i * 2);
                        playerPanel.add(cardLabel, i * 2);
                        checkBoxes[i].setSelected(false);
                    } else {
                        JOptionPane.showMessageDialog(this, "The deck is empty. Cannot draw more cards.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
        }
        updateCardVisibility();
    }

    private void updateCheckBoxes() {
        // Enable checkboxes for the current player, disable for the other
        for (JCheckBox checkBox : player1CheckBoxes) {
            checkBox.setEnabled(isPlayer1Turn);
        }
        for (JCheckBox checkBox : player2CheckBoxes) {
            checkBox.setEnabled(!isPlayer1Turn);
        }
    }

    private void compareScores() {
        Game game = new Game(player1, player2, deskCards);
        game.calculateScores();

        // Update the score labels
        updateScoreLabels();

        // Determine the result of the round
        String resultMessage;
        if (player1.getScore() > player2.getScore()) {
            resultMessage = "Player 1 wins the round!";
            player1RoundsWon++;
        } else if (player1.getScore() < player2.getScore()) {
            resultMessage = "Player 2 wins the round!";
            player2RoundsWon++;
        } else {
            resultMessage = "The round is a tie!";
        }

        JOptionPane.showMessageDialog(this, resultMessage, "Round Result", JOptionPane.INFORMATION_MESSAGE);

        // Handle game flow based on round count
        roundCounter++;
        if (roundCounter == 3) {
            determineWinner();
            resetGame();
        } else {
            gameStart();
        }
    }

    private void updateScoreLabels() {
        // Update the score labels for both players
        player1ScoreLabel.setText("Player 1 Score: " + player1.getScore());
        player2ScoreLabel.setText("Player 2 Score: " + player2.getScore());
    }

    private void determineWinner() {
        // Determine the winner after 3 rounds
        String finalMessage;
        if (player1RoundsWon > player2RoundsWon) {
            finalMessage = "Player 1 wins the game with " + player1RoundsWon + " rounds won!";
        } else if (player2RoundsWon > player1RoundsWon) {
            finalMessage = "Player 2 wins the game with " + player2RoundsWon + " rounds won!";
        } else {
            finalMessage = "The game is a tie with both players winning " + player1RoundsWon + " rounds!";
        }

        JOptionPane.showMessageDialog(this, finalMessage, "Game Result", JOptionPane.INFORMATION_MESSAGE);
        resetGame();
    }

    private void resetGame() {
        // Reset game state for a new game
        deck = new Deck();
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        isPlayer1Turn = true;
        player1Score = 0;
        player2Score = 0;
        roundCounter = 0;
        player1RoundsWon = 0;
        player2RoundsWon = 0;
        gameStart();
    }
}