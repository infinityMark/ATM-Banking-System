import javax.swing.*;
import java.awt.*;

public class GreetingUI {
    private static CardLayout cardLayout;
    private static JPanel cardPanel;
    private static JPanel mainPanel;

    private GridBagConstraints createDefaultGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    private JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    protected JPanel createGreetingUI(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        JLabel label = createStyledLabel("Welcome to use ATM system", ATMUI.NORMAL_FONT, StandardColor.GreyHighest.getColor(0));
        panel.add(label, gbc);

        return panel;
    }

    public JPanel GreetingUI() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(StandardColor.GreyHighest.getColorMode());

        GridBagConstraints gbc = createDefaultGridBagConstraints();

        JLabel taskTitle = createStyledLabel("Transfer", TransferUI.FONT_TITLE_LARGE, StandardColor.Blue.getColorMode());
        taskTitle.setHorizontalAlignment(SwingConstants.LEFT);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        JPanel menuCard = createGreetingUI();

//        addComponentToPanel(mainPanel, gbc, taskTitle, 0, 0.1);
        gbc.gridy = 1;
        gbc.weighty = 0.9;
        mainPanel.add(cardPanel, gbc);

        return mainPanel;
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
    }
}