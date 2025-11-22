import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.*;

/**
 * Custom rounded button component with hover effects
 * Extends JButton to provide rounded corners and interactive styling
 */
public class RoundedButton extends JButton{
    private int borderWidth = 50;
    private boolean isroundedStatus;
    private int cornerRadius = 15;

    /**
     * Sets the rounded status of the button
     * @param roundedStatuss true to enable rounded corners, false for square corners
     */
    public void setroundedStatus(boolean roundedStatuss){
        isroundedStatus = roundedStatuss;
    }

    /**
     * Full-featured constructor with customizable appearance and hover effects
     *
     * @param content Default button text
     * @param changedContent Text to display on mouse hover
     * @param defaultBackgroundColor Normal background color
     * @param changedBackgroundColor Background color on hover
     * @param defaultFontColor Normal text color
     * @param changedFontColor Text color on hover
     * @param fontDefaultStyle Normal font style
     * @param fontChangedSize Font style on hover
     * @param roundedStatus Whether to use rounded corners
     * @param widths Button width
     * @param heights Button height
     */
    public RoundedButton(String content,String changedContent,Color defaultBackgroundColor,Color changedBackgroundColor,Color defaultFontColor,
                         Color changedFontColor,Font fontDefaultStyle,Font fontChangedSize,boolean roundedStatus,int widths,int heights) {

        super(content);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        setBackground(defaultBackgroundColor);
        setForeground(defaultFontColor);

        setFont(fontDefaultStyle);
        //setPreferredSize(new Dimension(widths,heights));
        this.setMaximumSize(new Dimension(widths,heights));

        // Add mouse listener for hover effects
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setFont(fontChangedSize);
                setForeground(changedFontColor);
                setBackground(changedBackgroundColor);
                setText(changedContent);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setFont(fontDefaultStyle);
                setForeground(defaultFontColor);
                setBackground(defaultBackgroundColor);
                setText(content);
            }
        });

        setroundedStatus(roundedStatus);
    }

    /**
     * Simplified constructor with default styling
     * Uses standard colors and basic hover effect
     *
     * @param content Button text content
     */
    public RoundedButton(String content){
        super(content);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        setBackground(StandardColor.GreyHighest.getColorMode());
        setForeground(StandardColor.GreyHighest.getOppositeColorMode());

        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        this.setMaximumSize(new Dimension(200,40));

        // Add basic hover effect
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(StandardColor.GreyLower.getColorMode());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(StandardColor.GreyHighest.getColorMode());
            }
        });

        setroundedStatus(true);
    }

    /**
     * Constructor with custom dimension and basic styling
     *
     * @param content Button text content
     * @param dimension Preferred button dimension
     */
    public RoundedButton(String content, Dimension dimension){
        super(content);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        setBackground(StandardColor.GreyHighest.getColorMode());
        setForeground(StandardColor.GreyHighest.getOppositeColorMode());

        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        setMinimumSize(new Dimension(80, 50));
        setPreferredSize(new Dimension(80, 50));
        setMaximumSize(dimension);

        // Add hover effect
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(StandardColor.GreyLower.getColorMode());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(StandardColor.GreyHighest.getColorMode());
            }
        });

        setroundedStatus(true);
    }

    /**
     * Constructor for image-based button with custom dimension
     *
     * @param content Image icon for the button
     * @param dimension Preferred button dimension
     */
    public RoundedButton(ImageIcon content, Dimension dimension){
        super(content);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        setBackground(StandardColor.GreyHighest.getColorMode());
        setForeground(StandardColor.GreyHighest.getOppositeColorMode());

        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        setMinimumSize(new Dimension(80, 50));
        setPreferredSize(new Dimension(80, 50));
        setMaximumSize(dimension);

        // Add hover effect for image button
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(StandardColor.GreyLower.getColorMode());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(StandardColor.GreyHighest.getColorMode());
            }
        });

        setroundedStatus(true);
    }

    /**
     * Custom painting method to create rounded corners
     * Overrides the default paintComponent to implement rounded rectangle
     *
     * @param g Graphics object for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill the rounded rectangle background
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Draw border (commented out but available for future use)
        //g2d.setColor(borderColor);
        //g2d.setStroke(new BasicStroke(borderWidth));
        //g2d.drawRoundRect(borderWidth / 2, borderWidth / 2,
        //getWidth() - borderWidth, getHeight() - borderWidth,
        //cornerRadius, cornerRadius);

        // Paint the button content (text or icon)
        super.paintComponent(g);
    }

    /**
     * Sets the border color of the button
     * Note: Border drawing is currently commented out in paintComponent
     *
     * @param color The color to set for the border
     */
    public void setBorderColor(Color color) {
        //this.borderColor = color;
        //this.defaultBorderColor = color;
        repaint();
    }

}