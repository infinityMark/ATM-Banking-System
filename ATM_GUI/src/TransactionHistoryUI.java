import javax.swing.*;
import java.awt.*;

public class TransactionHistoryUI {
    private JTextArea history;
    private JPanel mainPanel;

    public TransactionHistoryUI(int accountNumber) {
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        JLabel titleLabel = new JLabel("Transaction History for Account: " + accountNumber, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.LIGHT_GRAY);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(titleLabel, gbc);

        history = new JTextArea(20, 40);
        history.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(history);

        String historyText = TransactionHistory.getHistoryAsString(accountNumber);
        history.setText(historyText);

        gbc.gridy = 1;
        gbc.weighty = 0.8;
        gbc.insets = new Insets(10, 20, 10, 20);
        mainPanel.add(scrollPane, gbc);

        JLabel reminderLabel = new JLabel("End of transaction history", SwingConstants.CENTER);
        reminderLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        reminderLabel.setForeground(Color.BLUE);
        reminderLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        reminderLabel.setOpaque(true);
        reminderLabel.setBackground(Color.WHITE);

        gbc.gridy = 2;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(reminderLabel, gbc);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}