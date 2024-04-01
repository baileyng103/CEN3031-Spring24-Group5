import javax.swing.*;
import java.awt.*;

public class HangmanGame extends JPanel {
    public HangmanGame() {
        setLayout(new BorderLayout());

        JLabel gameLabel = new JLabel("Hangman Game");
        gameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(gameLabel, BorderLayout.NORTH);

        // Add game components (hangman graphics, word display, input field) here
    }
}
