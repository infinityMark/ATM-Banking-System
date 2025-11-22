import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Custom text field component with enhanced visual styling capabilities.
 * Provides rounded corners, dynamic border colors for different interaction states (normal, hover, focus),
 * and special visual feedback for validation states. Designed to integrate seamlessly with the
 * application's color theming system through the StandardColor enumeration.
 *
 * This component supports:
 * - Customizable corner radius for rounded appearance
 * - Distinct border colors for normal, hover, and focused states
 * - Visual warning state for validation errors
 * - Anti-aliased rendering for smooth edges
 * - Custom content retrieval with fallback value
 *
 * Usage example:
 * TextFields amountField = new TextFields(200, 40,
 *     StandardColor.Blue.getColorMode(),    // Focus border color
 *     StandardColor.GreyMiddle.getColorMode(), // Hover border color
 *     StandardColor.GreyLower.getColorMode(),  // Normal border color
 *     new Font("SansSerif", Font.PLAIN, 18)
 * );
 * amountField.warning(); // Visual feedback for invalid input
 * String value = amountField.getContent(); // Safe retrieval with "0" fallback
 */
public class TextFields extends JTextField {
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
     * Constructs a fully customized text field with specified dimensions, interaction colors, and font.
     *
     * @param widths      Preferred width in pixels
     * @param heights     Preferred height in pixels
     * @param focus       Border color when component has focus
     * @param hover       Border color when mouse hovers over component
     * @param bordercolor Border color for normal state
     * @param font        Font for text rendering
     */
    public TextFields(int widths, int heights, Color focus, Color hover, Color bordercolor, Font font) {
        super();
        setfocusBorderColor(focus);
        sethoverBorderColor(hover);
        setborderColor(bordercolor);

        setOpaque(false);
        setForeground(StandardColor.GreyHighest.getColor(1)); // Default text color (black in light mode, white in dark mode)
        setPreferredSize(new Dimension(widths, heights));
        setFont(font);

        // Internal padding for text content
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
                setBackground(focus); // Optional: set background on focus
            }

            @Override
            public void focusLost(FocusEvent e) {
                repaint(); // Update border on focus loss
            }
        });
    }

    /**
     * Constructs a text field with minimal customization (dimensions and corner radius).
     * Note: This constructor is currently commented out and not used in the implementation.
     *
     * @param widths      Preferred width in pixels
     * @param heights     Preferred height in pixels
     * @param cornerRadius Radius for rounded corners
     */
    public TextFields(int widths, int heights, int cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    /**
     * Activates visual warning state for validation errors.
     * Sets background to red to indicate invalid input.
     */
    public void warning() {
        setBackground(StandardColor.Red.getColorMode());
    }

    /**
     * Resets component to normal visual state after warning.
     * Restores background to focus border color (typically a neutral shade).
     */
    public void normal() {
        setBackground(getFocusBorderColor());
    }

    /**
     * Safely retrieves field content with fallback value.
     *
     * @return Field text if not empty, otherwise returns "0" as default
     */
    public String getContent() {
        return getText().isEmpty() ? "0" : getText();
    }

    /**
     * Custom painting method that renders the component with rounded corners and dynamic borders.
     * Handles anti-aliased rendering and state-based border coloring.
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

        // Render text content after custom background/border
        super.paintComponent(g);
    }

    /**
     * Defines internal padding for text content within the field.
     *
     * @return Custom insets (top, left, bottom, right) in pixels
     */
    @Override
    public Insets getInsets() {
        return new Insets(5, 10, 5, 10); // Consistent padding for text
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
     * Gets the current focus border color.
     *
     * @return Focus border color
     */
    public Color getFocusBorderColor() {
        return focusBorderColor;
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

    // =============== Getter Methods ===============

    /**
     * Gets the normal state border color.
     *
     * @return Normal border color
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * Gets the hover state border color.
     *
     * @return Hover border color
     */
    public Color getHoverBorderColor() {
        return hoverBorderColor;
    }
}