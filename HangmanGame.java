import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class HangmanGame extends JPanel {
    private StickFigure stickFigure;
    private JLabel wordLabel;
    private JLabel guessedLettersLabel;
    private JButton[] letterButtons;
    private String[] words;
    private char[] guessedWord;
    private String hiddenWord;
    private int attemptsLeft;
    private int hangmanStage;
    private JPanel keyboardPanel;
    private StringBuilder guessedLetters = new StringBuilder(); // Initialize the guessedLetters list

    public HangmanGame() {
        setLayout(new BorderLayout()); // Set BorderLayout for the main panel

        // Initialize stick figure panel
        stickFigure = new StickFigure();
        add(stickFigure, BorderLayout.CENTER); // Add the StickFigure panel to the center

        // Load words from file
        loadWordsFromFile();

        // Initialize game
        initializeGame();

        // Panel to hold the word label and guessed letters vertically
        JPanel wordPanel = new JPanel();
        wordPanel.setLayout(new BoxLayout(wordPanel, BoxLayout.Y_AXIS));

        // Word label to display the hidden word with underscores
        wordLabel = new JLabel("Word: " + getSpacedWord(guessedWord), SwingConstants.CENTER);
        wordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        wordPanel.add(wordLabel); // Add word label to the wordPanel

        // Add a vertical gap between the word label and guessed letters label
        wordPanel.add(Box.createVerticalStrut(10));

        // Add guessed letters display
        guessedLettersLabel = new JLabel("Guessed Letters: ", SwingConstants.CENTER);
        guessedLettersLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        wordPanel.add(guessedLettersLabel); // Add guessed letters label to the wordPanel

        // Add the wordPanel to the bottom of the main panel
        add(wordPanel, BorderLayout.SOUTH);

        // Panel to hold the letter buttons
        keyboardPanel = new JPanel(new GridLayout(3, 9));
        letterButtons = new JButton[26];

        // Create and add letter buttons
        for (char c = 'A'; c <= 'Z'; c++) {
            int index = c - 'A';
            letterButtons[index] = new JButton(String.valueOf(c));
            letterButtons[index].setFont(new Font("Arial", Font.PLAIN, 16));
            letterButtons[index].addActionListener(new LetterButtonListener());
            keyboardPanel.add(letterButtons[index]);
        }

        add(keyboardPanel, BorderLayout.WEST); // Move the keyboard panel to the west

        // Add exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(exitButton);
        add(topPanel, BorderLayout.NORTH);
    }

    private void loadWordsFromFile() {
        try (Scanner scanner = new Scanner(new File("words.txt"))) {
            ArrayList<String> wordList = new ArrayList<>();
            while (scanner.hasNextLine()) {
                wordList.add(scanner.nextLine().toUpperCase());
            }
            words = wordList.toArray(new String[0]);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

    private void initializeGame() {
        try {
            // Choose a random word from the words array
            Random rand = new Random();
            hiddenWord = words[rand.nextInt(words.length)].trim().toUpperCase();
            guessedWord = new char[hiddenWord.length()];
            Arrays.fill(guessedWord, '_');

            // Initialize attempts left
            attemptsLeft = 6;

            // Initialize hangman stage
            hangmanStage = 0;
            stickFigure.setStage(hangmanStage);

            // Initialize guessed letters if it's null
            if (guessedLetters == null) {
                guessedLetters = new StringBuilder();
            } else {
                // Clear guessed letters
                guessedLetters.setLength(0);
            }
            guessedLettersLabel.setText("Guessed Letters: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getSpacedWord(char[] word) {
        StringBuilder spacedWord = new StringBuilder();
        for (char letter : word) {
            spacedWord.append(letter).append(" "); // Add space after each character
        }
        return spacedWord.toString().trim(); // Trim to remove the trailing space
    }

    private class LetterButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String letter = button.getText();
            // Process the clicked letter
            checkLetter(letter.charAt(0));
        }
    }

    private void checkLetter(char letter) {
        boolean found = false;
        for (int i = 0; i < hiddenWord.length(); i++) {
            if (hiddenWord.charAt(i) == letter) {
                found = true;
                guessedWord[i] = letter;
            }
        }
        if (!found) {
            attemptsLeft--;
            hangmanStage++;
            stickFigure.setStage(hangmanStage);
            // Add guessed letter to display
            guessedLetters.append(letter).append(" ");
            guessedLettersLabel.setText("Guessed Letters: " + guessedLetters.toString());
        }
        if (attemptsLeft == 0) {
            JOptionPane.showMessageDialog(this, "Game Over! The word was: " + hiddenWord);
            resetGame();
        } else if (!String.valueOf(guessedWord).contains("_")) {
            JOptionPane.showMessageDialog(this, "You Win! The word was: " + hiddenWord);
            resetGame();
        }
        wordLabel.setText("Word: " + getSpacedWord(guessedWord)); // Update word label here
    }

    private void resetGame() {
        initializeGame();
        stickFigure.reset(); // Reset the stick figure
        for (JButton button : letterButtons) {
            button.setEnabled(true);
        }
        wordLabel.setText("Word: " + getSpacedWord(guessedWord));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Hangman Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new HangmanGame());
                frame.pack(); // Pack the components to fit the preferred size
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
