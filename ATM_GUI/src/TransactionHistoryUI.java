import javax.swing.*;
import java.awt.*;

public class TransactionHistoryUI {
    private JTextArea history;
    private JPanel mainPanel;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private int currentAccountNumber;
    private ATMUIController controller;

    private static final Font FONT_TITLE_LARGE = new Font(Font.SANS_SERIF, Font.BOLD, 40);
    private static final Font FONT_NORMAL = new Font(Font.SANS_SERIF, Font.BOLD, 30);
    private static final Font FONT_SMALL = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

    private static final String CARD_HISTORY = "HISTORY";

    public TransactionHistoryUI(int accountNumber,ATMUIController controller) {
        this.controller = controller;
        this.currentAccountNumber = accountNumber;
        initializeMainPanel();
        createHistory(accountNumber);

        cardLayout.show(cardPanel, CARD_HISTORY);
    }

    public void setATMUIController(ATMUIController controller) {
        this.controller = controller;
    }

    private void initializeMainPanel() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(StandardColor.GreyHighest.getColorMode());

        GridBagConstraints gbc = createDefaultGridBagConstraints();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        JLabel taskTitle = createStyledLabel("Transaction History", FONT_TITLE_LARGE,
                StandardColor.Blue.getColorMode());
        taskTitle.setHorizontalAlignment(SwingConstants.LEFT);

        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(taskTitle, gbc);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(StandardColor.GreyHighest.getColorMode());

        gbc.gridy = 1;
        gbc.weighty = 0.9;
        gbc.insets = new Insets(10, 0, 10, 0);
        mainPanel.add(cardPanel, gbc);
    }

    private void createHistory(int accountNumber) {
        JPanel historyPanel = new JPanel(new GridBagLayout());
        historyPanel.setBackground(StandardColor.GreyHighest.getColorMode());
        historyPanel.setName(CARD_HISTORY);

        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        // title label
        JLabel titleLabel = createStyledLabel("Transaction History for Account: " + accountNumber,
                FONT_NORMAL, StandardColor.GreyHighest.getOppositeColorMode());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(StandardColor.Blue.getColorMode(), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(StandardColor.GreyHighest.getColorMode());

        gbc.gridy = 0;
        gbc.weighty = 0.1;
        historyPanel.add(titleLabel, gbc);

        // text area
        history = new JTextArea(50, 50);
        // cant edit
        history.setEditable(false);
        history.setFont(FONT_SMALL);
        history.setBackground(StandardColor.Green.getColorMode());
        history.setForeground(StandardColor.GreyHighest.getOppositeColorMode());
        history.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(StandardColor.Green.getColorMode(), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        history.setLineWrap(true);
        history.setWrapStyleWord(true);

        // scroll
        JScrollPane scrollPane = new JScrollPane(history);
        scrollPane.setBorder(BorderFactory.createLineBorder(StandardColor.GreyHighest.getColorMode(), 1));

        refreshHistory();

        gbc.gridy = 1;
        gbc.weighty = 0.7;
        gbc.insets = new Insets(20, 20, 20, 20);
        historyPanel.add(scrollPane, gbc);

        // back button
        RoundedButton backButton = new RoundedButton("Main Menu");
        gbc.gridy = 2;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(10, 10, 10, 10);
        historyPanel.add(backButton, gbc);
        backButton.addActionListener(e -> goBackToMainPanel());
        cardPanel.add(historyPanel, CARD_HISTORY);
    }

    // Refresh the transaction history display
    public void refreshHistory() {
        if (history != null) {
            String historyText = TransactionHistory.getHistoryAsString(currentAccountNumber);
            history.setText(historyText);
            history.revalidate();
            history.repaint();
        }
    }

    // Refresh with account number
    public void refreshHistory(int accountNumber) {
        this.currentAccountNumber = accountNumber;
        refreshHistory();
    }

    
    public void goBackToMainPanel() {
        controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
    }
        
    // Helper methods for UI
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

    public JPanel getMainPanel() {
        return mainPanel;
    }
}