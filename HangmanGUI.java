import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HangmanGUI extends JPanel {
    private HangmanGame hangmanGame;
    private StickFigure stickFigure;
    private JLabel wordLabel;
    private JLabel guessesLeftLabel; // New label for displaying guesses left

    private JButton[] letterButtons;
    private JPanel keyboardPanel;

    public HangmanGUI(HangmanGame hangmanGame) {
        this.hangmanGame = hangmanGame;
        setLayout(new BorderLayout()); // Set BorderLayout for the main panel

        // Initialize stick figure panel
        stickFigure = new StickFigure();
        add(stickFigure, BorderLayout.CENTER); // Add the StickFigure panel to the center

        // Panel to hold the word label and guesses left label vertically
        JPanel wordPanel = new JPanel();
        wordPanel.setLayout(new BoxLayout(wordPanel, BoxLayout.Y_AXIS));
        wordPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Double the padding

        // Word label to display the hidden word with underscores
        wordLabel = new JLabel("Word: " + getSpacedWord(hangmanGame.getGuessedWord()), SwingConstants.CENTER);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font size and style
        wordLabel.setForeground(Color.BLUE); // Set font color
        wordPanel.add(wordLabel); // Add word label to the wordPanel

        // Add a vertical gap between the word label and guesses left label
        wordPanel.add(Box.createVerticalStrut(20)); // Double the vertical gap

        // Add guesses left display
        guessesLeftLabel = new JLabel("Incorrect Guesses Left: " + hangmanGame.getAttemptsLeft(), SwingConstants.CENTER);
        guessesLeftLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        guessesLeftLabel.setForeground(Color.RED); // Set font color
        wordPanel.add(guessesLeftLabel); // Add guesses left label to the wordPanel

        // Add the wordPanel to the bottom of the main panel with padding
        add(wordPanel, BorderLayout.SOUTH);

        // Panel to hold the letter buttons
        keyboardPanel = new JPanel(new GridLayout(3, 9));
        keyboardPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Double the padding
        letterButtons = new JButton[26];

        // Create and add letter buttons
        for (char c = 'A'; c <= 'Z'; c++) {
            int index = c - 'A';
            letterButtons[index] = new JButton(String.valueOf(c));
            letterButtons[index].setFont(new Font("Arial", Font.PLAIN, 16));
            letterButtons[index].setForeground(Color.BLACK); // Set font color
            letterButtons[index].setBackground(Color.WHITE); // Set button background color
            letterButtons[index].addActionListener(new LetterButtonListener());
            keyboardPanel.add(letterButtons[index]);
        }

        add(keyboardPanel, BorderLayout.WEST); // Move the keyboard panel to the west
    }

    private String getSpacedWord(char[] word) {
        StringBuilder spacedWord = new StringBuilder();
        for (char letter : word) {
            spacedWord.append(letter).append(" "); // Add space after each character
        }
        return spacedWord.toString().trim(); // Trim to remove the trailing space
    }

    public void updateGameDisplay() {
        wordLabel.setText("Word: " + getSpacedWord(hangmanGame.getGuessedWord()));
        guessesLeftLabel.setText("Incorrect Guesses Left: " + hangmanGame.getAttemptsLeft()); // Update guesses left display
        int attemptsLeft = hangmanGame.getAttemptsLeft(); // Get the number of attempts left
        // Ensure attemptsLeft is within the valid range
        if (attemptsLeft >= 0 && attemptsLeft < StickFigure.STAGES.length) {
            // Call setStage method with the correct value of attempts left
            stickFigure.setStage(StickFigure.STAGES[attemptsLeft]); // Update the stick figure
        } else {
            System.err.println("Invalid attempts left: " + attemptsLeft);
        }
    }

    public void enableLetterButtons() {
        for (JButton button : letterButtons) {
            button.setEnabled(true);
        }
    }

    // Define method to reset letter buttons
    public void resetLetterButtons() {
        for (JButton button : letterButtons) {
            button.setEnabled(true);
            button.setBackground(Color.WHITE); // Reset button background color
            button.setForeground(Color.BLACK); // Reset button text color
        }
    }

    public void showEndGameMessage(boolean gameOver) {
        // Implement the logic to show the end game message here
        // For example:
        if (gameOver) {
            JOptionPane.showMessageDialog(this, "Game Over! The word was: " + hangmanGame.getHiddenWord());
        } else {
            JOptionPane.showMessageDialog(this, "You Win! The word was: " + hangmanGame.getHiddenWord());
        }
        resetGame(); // Reset game after showing end game message
    }

    // Define method to reset game and stick figure
    public void resetGame() {
        hangmanGame.resetGame();
    }

    private class LetterButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String letter = button.getText();
            // Process the clicked letter
            hangmanGame.checkLetter(letter.charAt(0));
            button.setEnabled(false); // Disable the clicked button
            button.setBackground(Color.GRAY); // Change button background color
            button.setForeground(Color.WHITE); // Change button text color
        }
    }
}
