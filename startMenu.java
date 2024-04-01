import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HangmanMenu extends JFrame implements ActionListener {
    private JButton startButton;
    private JLabel titleLabel;
    private HangmanGame hangmanGame;

    public HangmanMenu() {
        setTitle("Hangman Game");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        titleLabel = new JLabel("Hangman Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        startButton = new JButton("Start Game");
        startButton.addActionListener(this);

        panel.add(titleLabel);
        panel.add(startButton);

        add(panel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startGame();
        }
    }

    private void startGame() {
        setTitle("Hangman Game");
        getContentPane().removeAll();
        hangmanGame = new HangmanGame();
        add(hangmanGame);
        validate();
        repaint();
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HangmanMenu();
            }
        });
    }
}
