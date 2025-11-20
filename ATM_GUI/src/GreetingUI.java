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

    static protected void setImageSafety(JPanel panel, GridBagConstraints gbc,ImageIcon imageIcon){
        try {
            JLabel imageLabel = new JLabel(imageIcon);
            panel.add(imageLabel, gbc);
        }catch (RuntimeException e) {
            System.out.println("ATM image not found or failed to load: " + e.getMessage());
        }catch (Exception e) {
            System.out.println("Invalid image path");
        }
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
//        setImageSafety(panel,gbc,new ImageIcon(ClassLoader.getSystemResource("resources/atm.png")));

        return panel;
    }

    static protected JPanel createGreetingUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        JLabel label = createStyledLabel("Welcome to use ATM system", ATMUI.NORMAL_FONT,
                StandardColor.GreyHighest.getColor(1));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, gbc);

        setImageSafety(panel, gbc, "https://raw.githubusercontent.com/infinityMark/SEHH2242-OOP_Group_Project_Part1_Group_101A-G03_UML_Showing/refs/heads/main/atm-machine.png");

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

        JPanel atmPhoto = createGreetingUI();
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