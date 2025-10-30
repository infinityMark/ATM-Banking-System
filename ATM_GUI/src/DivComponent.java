import javax.swing.*;
import java.awt.*;

public class DivComponent extends JPanel {
    private int cornerRadius = 20;
    private Color borderColor;
    private int borderThickness = 2;
    private Color backgroundColor;

    public void setBorderColor(Color c){
        borderColor = c;
    }

    public void setBackgroundColor(Color c){
        backgroundColor = c;
    }

    public DivComponent(Color background,Color bordercolor) {
        setBorderColor(bordercolor);
        setBackgroundColor(background);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(borderThickness));
        g2d.drawRoundRect(borderThickness / 2, borderThickness / 2,
                getWidth() - borderThickness, getHeight() - borderThickness,
                cornerRadius, cornerRadius);
    }
}