import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.*;

/**
 * Custom password field component with enhanced visual styling capabilities.
 * Extends JPasswordField to provide rounded corners, dynamic border colors for different
 * interaction states (normal, hover, focus), and anti-aliased rendering. Designed to integrate
 * seamlessly with the application's color theming system.
 *
 * This component supports:
 * - Customizable corner radius for rounded appearance
 * - Distinct border colors for normal, hover, and focused states
 * - Smooth anti-aliased rendering for professional visual quality
 * - Responsive hover and focus effects for improved user experience
 *
 * Usage example:
 * Passwords passwordField = new Passwords(200, 40,
 *     StandardColor.Blue.getColorMode(),    // Focus border color
 *     StandardColor.GreyMiddle.getColorMode(), // Hover border color
 *     StandardColor.GreyLower.getColorMode(),  // Normal border color
 *     new Font("SansSerif", Font.PLAIN, 14)
 * );
 */
public class Passwords extends JPasswordField {
    /** Current border color for normal state */
    private Color borderColor;

    /** Border color when component has keyboard focus */
    private Color focusBorderColor;

    /** Border color when mouse is hovering over component */
    private Color hoverBorderColor;

    /** Width of the border stroke in pixels */
    private int borderWidth = 2;

    /** Radius of rounded corners in pixels */
    private int cornerRadius = 30;

    /** Tracks mouse hover state for visual feedback */
    private boolean isHovered = false;

    /**
     * Constructs a fully customized password field with specified dimensions, interaction colors, and font.
     *
     * @param widths      Preferred width in pixels
     * @param heights     Preferred height in pixels
     * @param focus       Border color when component has focus
     * @param hover       Border color when mouse hovers over component
     * @param bordercolor Border color for normal state
     * @param font        Font for text rendering (note: overridden internally to Sans Serif 14pt)
     */
    public Passwords(int widths, int heights, Color focus, Color hover, Color bordercolor, Font font) {
        super();
        setfocusBorderColor(focus);
        sethoverBorderColor(hover);
        setborderColor(bordercolor);

        setOpaque(false);
        setFont(font);
        setForeground(Color.BLACK); // Default text color
        // Note: Font is overridden here to ensure consistent password field appearance
        setFont(new Font("Sans Serif", Font.PLAIN, 14));
        setPreferredSize(new Dimension(widths, heights));

        // Internal padding for password characters
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Mouse interaction handlers for hover effects
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint(); // Trigger visual update on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint(); // Revert visual state when mouse exits
            }
        });

        // Focus interaction handlers for active state
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                repaint(); // Update border on focus gain
            }

            @Override
            public void focusLost(FocusEvent e) {
                repaint(); // Update border on focus loss
            }
        });
    }

    /**
     * Custom painting method that renders the component with rounded corners and dynamic borders.
     * Handles anti-aliased rendering and state-based border coloring while maintaining password masking.
     *
     * @param g Graphics context for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        // Enable anti-aliasing for smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill background with rounded rectangle
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Determine current border color based on interaction state
        Color currentBorderColor = borderColor;
        if (hasFocus()) {
            currentBorderColor = focusBorderColor;
        } else if (isHovered) {
            currentBorderColor = hoverBorderColor;
        }

        // Draw border with rounded corners
        g2.setColor(currentBorderColor);
        g2.setStroke(new BasicStroke(borderWidth));
        g2.drawRoundRect(borderWidth / 2, borderWidth / 2,
                getWidth() - borderWidth, getHeight() - borderWidth,
                cornerRadius, cornerRadius);

        g2.dispose();

        // Render password characters (bullets) after custom background/border
        super.paintComponent(g);
    }

    /**
     * Defines internal padding for password characters within the field.
     *
     * @return Custom insets (top, left, bottom, right) in pixels
     */
    @Override
    public Insets getInsets() {
        return new Insets(5, 10, 5, 10); // Consistent padding for password bullets
    }

    // =============== Setter Methods ===============

    /**
     * Sets the border color for normal state.
     *
     * @param color New border color
     */
    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint(); // Update visual appearance
    }

    /**
     * Sets the border color for focused state.
     *
     * @param color New focus border color
     */
    public void setFocusBorderColor(Color color) {
        this.focusBorderColor = color;
        repaint(); // Update visual appearance
    }

    /**
     * Sets the border color for hover state.
     *
     * @param color New hover border color
     */
    public void setHoverBorderColor(Color color) {
        this.hoverBorderColor = color;
        repaint(); // Update visual appearance
    }

    /**
     * Sets the border width in pixels.
     *
     * @param width New border width
     */
    public void setBorderWidth(int width) {
        this.borderWidth = width;
        repaint(); // Update visual appearance
    }

    /**
     * Sets the corner radius for rounded edges.
     *
     * @param radius New corner radius in pixels
     */
    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint(); // Update visual appearance
    }

    // Legacy setter methods (maintained for backward compatibility)
    public void setfocusBorderColor(Color c) {
        focusBorderColor = c;
    }

    public void sethoverBorderColor(Color c) {
        hoverBorderColor = c;
    }

    public void setborderColor(Color c) {
        borderColor = c;
    }

    /**
     * Gets the current border color based on interaction state.
     *
     * @return Border color appropriate for current state (focus > hover > normal)
     */
    public Color getCurrentBorderColor() {
        if (hasFocus()) return focusBorderColor;
        if (isHovered) return hoverBorderColor;
        return borderColor;
    }
}