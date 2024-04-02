import javax.swing.*;
import java.awt.*;

public class StickFigure extends JPanel {
    private int stage; // Current stage of the stick figure

    public StickFigure() {
        setPreferredSize(new Dimension(200, 400)); // Set preferred size for the stick figure panel
        stage = 0; // Initialize stage
    }

    public void setStage(int stage) {
        this.stage = stage;
        repaint(); // Repaint the panel to update the stick figure
    }

    public void reset() {
        stage = 0; // Reset the stage to initial state
        repaint(); // Repaint the panel to update the stick figure
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Draw stick figure based on the current stage
        switch (stage) {
            case 6:
                // Draw head
                g2d.drawOval(90, 30, 40, 40);
            case 5:
                // Draw body
                g2d.drawLine(110, 70, 110, 200);
            case 4:
                // Draw left arm
                g2d.drawLine(110, 80, 70, 120);
            case 3:
                // Draw right arm
                g2d.drawLine(110, 80, 150, 120);
            case 2:
                // Draw left leg
                g2d.drawLine(110, 200, 70, 280);
            case 1:
                // Draw right leg
                g2d.drawLine(110, 200, 150, 280);
            case 0:
                // Draw base
                g2d.drawLine(20, 320, 200, 320);
                g2d.drawLine(50, 320, 110, 30);
                g2d.drawLine(110, 30, 170, 320);
                break;
        }
    }
}
