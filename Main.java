import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Create the main frame for the card game
        JFrame frame = new JFrame("Card Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new gamePanel());
        frame.setVisible(true);
    }
}
                                //Özgür Sönmez 230201034//