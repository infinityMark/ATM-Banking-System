import javax.swing.*;
import java.awt.*;

/**
 * Greeting UI Component - Handles the welcome interface display for the ATM system
 * Provides welcome information and ATM-machine image display functionality
 */
public class GreetingUI extends ATMUI {
    /** Constant identifier for the greeting panel */
    public static final String GREETING_PANEL = "greeting";

    /** Main panel component */
    private static JPanel mainPanel;

    /**
     * Constructor - Initializes the greeting interface
     * Creates the greeting panel and sets up related components
     */
    public GreetingUI() {
        createGreetingPanel();
    }

    /**
     * Creates default GridBag constraints configuration
     * Provides unified layout constraint settings
     *
     * @return GridBagConstraints Configured constraints object
     */
    static protected GridBagConstraints createDefaultGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    /**
     * Creates a styled label component
     * Unifies label font and color styles
     *
     * @param text Label text content
     * @param font Label font style
     * @param color Label text color
     * @return JLabel Configured label component
     */
    static protected JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    /**
     * Creates operation reminder interface panel
     * Displays reminder information and Take out card image after operation completion
     *
     * @return JPanel Panel containing reminder information
     *
     * Features:
     * - Displays "Take out your card after operation" reminder message
     * - Includes ATM-related image display
     * - Uses center-aligned layout
     */
    static protected JPanel reminderUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        // Create operation reminder label
        JLabel label = createStyledLabel("Take out your card after operation", ATMUI.NORMAL_FONT,
                StandardColor.GreyHighest.getColor(1));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, gbc);

        // Add ATM-related image
        setImageSafety(panel, gbc, ATMUI.TAKE_OUT_CARD_IMAGE);

        return panel;
    }

    /**
     * Creates ATM welcome interface panel
     * Main welcome interface containing welcome message and ATM-machine image
     *
     * @return JPanel Configured welcome interface panel
     *
     * Features:
     * - Uses GridBagLayout for flexible component arrangement
     * - Displays welcome message text
     * - Loads ATM-machine image from GitHub repository
     * - Automatically handles image loading failures with fallback
     *
     * Layout Structure:
     * - Welcome message label at the top
     * - ATM image displayed below the message
     * - Uses GridBagConstraints for proper spacing and alignment
     *
     * Image Source:
     * - Loads from GitHub raw content URL
     * - Includes error handling for network issues or invalid URLs
     * - Provides fallback display if image fails to load
     *
     * Styling:
     * - Uses predefined fonts and colors from ATM UI class
     * - Centers all content horizontally
     * - Applies consistent spacing with insets
     */
    static protected JPanel createWelcomeATMUI() {
        // Create main panel with GridBagLayout for precise component positioning
        JPanel panel = new JPanel(new GridBagLayout());

        // Configure layout constraints for consistent spacing and alignment
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15); // Add padding around components

        // Create and configure welcome message label
        JLabel label = createStyledLabel("Welcome to use ATM system", ATMUI.NORMAL_FONT,
                StandardColor.GreyHighest.getColor(1));
        label.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text

        // Add welcome label to panel at position (0,0)
        panel.add(label, gbc);

        // Load and display ATM image from GitHub repository
        // URL points to raw image file in the project repository
        setImageSafety(panel, gbc, ATM_MACHINE_IMAGE);

        return panel;
    }

    /**
     * Creates the main greeting panel
     * Combines welcome interface and reminder panel in a unified layout
     *
     * @return JPanel Complete greeting panel with all components
     *
     * Layout Description:
     * - Title label at the top
     * - Welcome ATM UI on the left side
     * - Reminder panel on the right side
     * - Uses GridBagLayout for responsive component arrangement
     * - Applies consistent styling and spacing
     */
    @Override
    public JPanel createGreetingPanel() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setName(GREETING_PANEL);
        mainPanel.setBackground(StandardColor.GreyHighest.getColorMode());

        GridBagConstraints gbc = createDefaultGridBagConstraints();

        // Create title label
        JLabel taskTitle = createStyledLabel("Transfer", ATMUI.FONT_TITLE_LARGE,
                StandardColor.Blue.getColorMode());
        taskTitle.setHorizontalAlignment(SwingConstants.LEFT);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        // Create component panels
        JPanel atmPhoto = createWelcomeATMUI();
        JPanel reminderPanel = reminderUI();

        // Add components to main panel with proper layout constraints
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weighty = 1;
        mainPanel.add(atmPhoto, gbc);
        gbc.gridx = 1;
        mainPanel.add(reminderPanel, gbc);

        return mainPanel;
    }

    /**
     * Gets the main panel component
     *
     * @return JPanel The main greeting panel
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
}