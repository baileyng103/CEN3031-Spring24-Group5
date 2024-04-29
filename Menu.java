import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class Menu extends JPanel implements ActionListener {
	private JPanel tPanel;
	private JLabel title;
	private JPanel startMenu;
	private JButton play;
	 


	public Menu() {
		setLayout(new BorderLayout());
		
		//setting up the title panel
		tPanel = new JPanel();
		tPanel.setLayout(new BoxLayout(tPanel, BoxLayout.Y_AXIS));
        tPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Double the padding
        
        // add title text
	    title = new JLabel("Hangman", SwingConstants.CENTER);
	    title.setFont(new Font("Arial", Font.BOLD, 36)); // Set font size and style
	    title.setForeground(Color.BLACK); // Set font color
	    tPanel.add(title); // Add word label to the wordPanel
	    add(tPanel, BorderLayout.NORTH);
        
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}



	}
