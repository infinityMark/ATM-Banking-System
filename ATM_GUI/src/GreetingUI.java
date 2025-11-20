import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URL;

public class GreetingUI extends ATMUI{
    public static final String GREETING_PANEL = "greeting";
    private static JPanel mainPanel;

    public GreetingUI() {
        createGreetingPanel();
    }

    static protected GridBagConstraints createDefaultGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    static protected JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    static protected JPanel reminderUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        JLabel label = createStyledLabel("Take out your card after operation", ATMUI.NORMAL_FONT,
                StandardColor.GreyHighest.getColor(1));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, gbc);

        setImageSafety(panel,gbc,"https://raw.githubusercontent.com/infinityMark/SEHH2242-OOP_Group_Project_Part1_Group_101A-G03_UML_Showing/refs/heads/main/atm.png");

        return panel;
    }

    /**
     * Creates a welcome interface panel for the ATM system.
     * This panel includes a welcome message and an ATM machine image.
     *
     * @return JPanel Returns a configured welcome interface panel containing welcome text and ATM image
     *
     * Features:
     * - Uses GridBagLayout for flexible component arrangement
     * - Displays welcome message text
     * - Loads ATM machine image from GitHub repository
     * - Automatically handles image loading failures with fallback
     *
     * Layout Structure:
     * - Welcome message label at the top
     * - ATM image displayed below the message
     * - Proper spacing and alignment using GridBagConstraints
     *
     * Image Source:
     * - Loads from GitHub raw content URL
     * - Includes error handling for network issues or invalid URLs
     * - Provides fallback display if image fails to load
     *
     * Styling:
     * - Uses predefined fonts and colors from ATMUI class
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
        setImageSafety(panel, gbc, "https://raw.githubusercontent.com/infinityMark/SEHH2242-OOP_Group_Project_Part1_" +
                "Group_101A-G03_UML_Showing/refs/heads/main/atm-machine.png");

        return panel;
    }

    static public void goToPanel(String name) {
        Container parent = mainPanel.getParent();
        if (parent != null) {
            Container current = parent;
            while (current != null && !(current.getLayout() instanceof CardLayout)) {
                current = current.getParent();
            }

            if (current != null) {
                CardLayout layout = (CardLayout) current.getLayout();
                layout.show(current, name);
            }
        }
    }

    @Override
    public JPanel createGreetingPanel() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setName(GREETING_PANEL);
        mainPanel.setBackground(StandardColor.GreyHighest.getColorMode());

        GridBagConstraints gbc = createDefaultGridBagConstraints();

        JLabel taskTitle = createStyledLabel("Transfer", TransferUI.FONT_TITLE_LARGE,
                StandardColor.Blue.getColorMode());
        taskTitle.setHorizontalAlignment(SwingConstants.LEFT);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        JPanel atmPhoto = createWelcomeATMUI();
        JPanel reminderPanel = reminderUI();

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weighty = 1;
        mainPanel.add(atmPhoto, gbc);
        gbc.gridx = 1;
        mainPanel.add(reminderPanel, gbc);

        /*
         * mainPanel.addMouseListener(new MouseListener() {
         * 
         * @Override
         * public void mouseClicked(MouseEvent e) {
         * goToPanel(ATMUI.LOGIN_PANEL);
         * }
         * 
         * @Override
         * public void mousePressed(MouseEvent e) {
         * 
         * }
         * 
         * @Override
         * public void mouseReleased(MouseEvent e) {
         * 
         * }
         * 
         * @Override
         * public void mouseEntered(MouseEvent e) {
         * 
         * }
         * 
         * @Override
         * public void mouseExited(MouseEvent e) {
         * 
         * }
         * });
         */

        return mainPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}