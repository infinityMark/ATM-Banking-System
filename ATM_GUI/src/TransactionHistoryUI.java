import javax.swing.*;
import java.awt.*;

public class TransactionHistoryUI {
    private JTextArea history;
    private JPanel mainPanel;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    private static final Font FONT_TITLE_LARGE = new Font(Font.SANS_SERIF, Font.BOLD, 40);
    private static final Font FONT_NORMAL = new Font(Font.SANS_SERIF, Font.BOLD, 30);
    private static final Font FONT_SMALL = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
    private static final Font FONT_BUTTON = new Font(Font.SANS_SERIF, Font.PLAIN, 16);

    private static final String CARD_HISTORY = "HISTORY";

    public void createTransactionHistoryUI(int accountNumber) {
        initializeMainPanel();
        createHistory(accountNumber);

        String historyText = TransactionHistory.getHistoryAsString(accountNumber);
        cardLayout.show(cardPanel, CARD_HISTORY);

    }

    public TransactionHistoryUI() {

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

        JScrollPane scrollPane = new JScrollPane(history);
        scrollPane.setBorder(BorderFactory.createLineBorder(StandardColor.GreyHighest.getColorMode(), 1));

        String historyText = TransactionHistory.getHistoryAsString(accountNumber);
        history.setText(historyText);

        gbc.gridy = 1;
        gbc.weighty = 0.8;// 80%
        gbc.insets = new Insets(20, 20, 20, 20);
        historyPanel.add(scrollPane, gbc);

        cardPanel.add(historyPanel, CARD_HISTORY);
    }

    public void goBackToMainPanel() {
        Container parent = mainPanel.getParent();
        if (parent != null) {
            Container current = parent;
            while (current != null && !(current.getLayout() instanceof CardLayout)) {
                current = current.getParent();
            }

            if (current != null) {
                CardLayout layout = (CardLayout) current.getLayout();
                layout.show(current, "mainMenu");
            }
        }
    }

    private GridBagConstraints createDefaultGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    private RoundedButton createActionButton(String text, Color backgroundColor) {
        return new RoundedButton(text, text,
                backgroundColor,
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.GreyHighest.getOppositeColorMode(),
                FONT_BUTTON,
                FONT_BUTTON,
                true, 200, 10);
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