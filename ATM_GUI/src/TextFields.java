import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextFields extends JTextField {
    private Color borderColor;
    private Color focusBorderColor;
    private Color hoverBorderColor;
    private int borderWidth = 2;
    private int cornerRadius = 30;
    private boolean isHovered = false;

    public TextFields(int widths, int heights, Color focus, Color hover, Color bordercolor, Font font) {
        super();
        setfocusBorderColor(focus);
        sethoverBorderColor(hover);
        setborderColor(bordercolor);

        setOpaque(false);
        setForeground(Color.BLACK);
        setPreferredSize(new Dimension(widths, heights));
        setFont(font);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
        });

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                repaint();
                setBackground(focus);
            }

            @Override
            public void focusLost(FocusEvent e) {
                repaint();
            }
        });

    }


    public TextFields(int widths, int heights, int cornerRadius) {
//        this(widths, heights);
        this.cornerRadius = cornerRadius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        Color currentBorderColor = borderColor;
        if (hasFocus()) {
            currentBorderColor = focusBorderColor;
        } else if (isHovered) {
            currentBorderColor = hoverBorderColor;
        }

        g2.setColor(currentBorderColor);
        g2.setStroke(new BasicStroke(borderWidth));
        g2.drawRoundRect(borderWidth / 2, borderWidth / 2,
                getWidth() - borderWidth, getHeight() - borderWidth,
                cornerRadius, cornerRadius);

        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    public Insets getInsets() {
        return new Insets(5, 10, 5, 10);
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }

    public void setFocusBorderColor(Color color) {
        this.focusBorderColor = color;
        repaint();
    }

    public void setHoverBorderColor(Color color) {
        this.hoverBorderColor = color;
        repaint();
    }

//    public Color getFocusBorderColor

    public void setBorderWidth(int width) {
        this.borderWidth = width;
        repaint();
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    public void setfocusBorderColor(Color c){
        focusBorderColor=c;
    }
    public void sethoverBorderColor(Color c){
        hoverBorderColor=c;
    }
    public void setborderColor(Color c){
        borderColor=c;
    }

    public Color getCurrentBorderColor() {
        if (hasFocus()) return focusBorderColor;
        if (isHovered) return hoverBorderColor;
        return borderColor;
    }

    public Color getBorderColor(){
        return borderColor;
    }

    public Color getHoverBorderColor(){
        return hoverBorderColor;
    }
}