import javax.swing.*;
import javax.swing.Timer;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class HangmanGame extends JPanel {
    protected static boolean setmainMenu;
	private HangmanGUI hangmanGUI;
    private String[] words;
    private char[] guessedWord;
    private String hiddenWord;
    private int attemptsLeft;
    private StringBuilder guessedLetters = new StringBuilder(); // Initialize the guessedLetters list
  public Menu menu;
   
  private static boolean mainMenu = true;
  

	 
  
  public static boolean getmainMenu() {
  	return mainMenu;
  }
  public void setmainMenu() {
	  mainMenu = false;
  }

    
    

    public HangmanGame() {
        setLayout(new BorderLayout()); // Set BorderLayout for the main panel
        
        // Load words from file
        loadWordsFromFile();

        // Initialize game
        initializeGame();

        // Initialize HangmanGUI
        hangmanGUI = new HangmanGUI(this);
        add(hangmanGUI, BorderLayout.CENTER); // Add HangmanGUI panel to the center

        // Add exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the game?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
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

    public void initializeGame() {
        try {
            // Choose a random word from the words array
            Random rand = new Random();
            hiddenWord = words[rand.nextInt(words.length)].trim().toUpperCase();
            guessedWord = new char[hiddenWord.length()];
            Arrays.fill(guessedWord, '_');

            // Initialize attempts left
            attemptsLeft = 6;

            // Initialize guessed letters if it's null
            if (guessedLetters == null) {
                guessedLetters = new StringBuilder();
            } else {
                // Clear guessed letters
                guessedLetters.setLength(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getHiddenWord() {
        return hiddenWord;
    }

    public char[] getGuessedWord() {
        return guessedWord;
    }

    public StringBuilder getGuessedLetters() {
        return guessedLetters;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public void checkLetter(char letter) {
        boolean found = false;
        for (int i = 0; i < hiddenWord.length(); i++) {
            if (hiddenWord.charAt(i) == letter) {
                found = true;
                guessedWord[i] = letter;
            }
        }
        if (!found) {
            attemptsLeft--;
            // Add guessed letter to display
            guessedLetters.append(letter).append(" ");
        }
        if (attemptsLeft == 0 || !String.valueOf(guessedWord).contains("_")) {
            boolean gameOver = attemptsLeft == 0;
            Timer timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    hangmanGUI.updateGameDisplay(); // Update the game display to fully populate the word label
                    hangmanGUI.showEndGameMessage(gameOver); // Show end game message after delay
                }
            });
            timer.setRepeats(false); // Execute the task only once
            timer.start(); // Start the timer
        } else {
            hangmanGUI.updateGameDisplay();
        }
    }

    public void resetGame() {
        initializeGame();
        hangmanGUI.enableLetterButtons();
        hangmanGUI.updateGameDisplay();
        hangmanGUI.resetLetterButtons(); // Reset letter buttons appearance
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Hangman Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1024, 800);
                frame.setBackground(Color.WHITE);
                frame.setForeground(Color.WHITE);
                frame.getContentPane().add(new HangmanGame());
                //frame.pack(); // Pack the components to fit the preferred size, Created Problems with resizing.
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
