import javax.swing.*;
import java.awt.*;

public class StickFigure extends JPanel {
    private int stage; // Current stage of the stick figure
    private boolean[] visibleParts = {false, false, false, false, false, false}; // Array to keep track of which body parts are visible
    
    public static final int[] STAGES = {6, 5, 4, 3, 2, 1, 0}; // Reverse order of stages
    
 // Define body parts coordinates
    private int[][] bodyParts = {
        // Right leg
        {143, 232, 171, 292},
        // Left leg
        {143, 232, 112, 292},
        // Right arm
        {143, 142, 171, 172},
        // Left arm
        {143, 142, 112, 172},
        // Body
        {143, 132, 141, 232},
        // Head
        {126, 98, 36, 36}
    };

    public StickFigure() {
        setPreferredSize(new Dimension(200, 400)); // Set preferred size for the stick figure panel
        stage = 0; // Initialize stage
    }

    public void setStage(int stage) {
        this.stage = stage;
        // Update visible parts based on the current stage
        for (int i = 0; i < visibleParts.length; i++) {
            visibleParts[i] = (stage >= STAGES[i]);
        }
        repaint(); // Repaint the panel to update the stick figure
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set thicker stroke for lines
        g2d.setStroke(new BasicStroke(3)); // Adjust the thickness as needed

        // Draw hanger
        g2d.drawLine(50, 350, 150, 350); // Base line
        g2d.drawLine(100, 350, 100, 50); // Vertical line
        g2d.drawLine(100, 50, 150, 100); // Diagonal line

        // Draw stick figure body parts based on visibility
        for (int i = visibleParts.length - 1; i >= 0; i--) {
            if (visibleParts[i]) {
                int[] part = bodyParts[i];
                switch (i) {
                    case 0:
                        g2d.drawLine(part[0], part[1], part[2], part[3]); // Right leg
                        break;
                    case 1:
                        g2d.drawLine(part[0], part[1], part[2], part[3]); // Left leg
                        break;
                    case 2:
                        g2d.drawLine(part[0], part[1], part[2], part[3]); // Right arm
                        break;
                    case 3:
                        g2d.drawLine(part[0], part[1], part[2], part[3]); // Left arm
                        break;
                    case 4:
                        g2d.drawLine(part[0], part[1], part[2], part[3]); // Body
                        break;
                    case 5:
                        g2d.drawOval(part[0], part[1], part[2], part[3]); // Head
                        break;
                }
            }
        }
    }
}
