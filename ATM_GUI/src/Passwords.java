import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.*;

public class Passwords extends JPasswordField{
    private Color borderColor;
    private Color focusBorderColor;
    private Color hoverBorderColor;
    private int borderWidth = 2;
    private int cornerRadius = 30;
    private boolean isHovered = false;


    public Passwords(int widths, int heights, Color focus, Color hover, Color bordercolor, Font font) {
        super();
        setfocusBorderColor(focus);
        sethoverBorderColor(hover);
        setborderColor(bordercolor);

//        setPreferredSize(new Dimension(widths, heights));
        setOpaque(false);
        setFont(font);
        setForeground(Color.BLACK);
        setFont(new Font("Sans Serif", Font.PLAIN, 14));
        setPreferredSize(new Dimension(widths, heights));

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // 添加鼠标监听器实现悬停效果
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

        // 添加焦点监听器
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                repaint();
            }
        });
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
}