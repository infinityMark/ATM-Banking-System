import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.awt.event.*;

public class BalanceInquiryUI extends JPanel {
    // Card names
    private static final String CARD_BALANCE = "BALANCE";

    // Layout components
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel mainPanel;

    // Balance data
    private int currentAccountNumber;
    private BankDatabase bankDatabase;

    // UI Components
    private JLabel accountTypeLabel;
    private JLabel availableBalanceLabel;
    private JLabel totalBalanceLabel;
    private JLabel interestLabel;
    private JLabel chequeLimitLabel;

    
    //private JButton[] leftButton = new JButton[3];
    //private JButton[] rightButton = new JButton[3];
    private ATMUIController controller;

    public BalanceInquiryUI(int accountNumber,
                //JButton[] leftButton, JButton[] rightButton,
            ATMUIController controller) {
        this.currentAccountNumber = accountNumber;
        this.bankDatabase = BankDatabase.getInstance();
        //this.leftButton = leftButton;
        //this.rightButton = rightButton;
        this.controller = controller;
        initializeUI();
        updateBalanceInfo();
    }

    private void initializeUI() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(StandardColor.GreyHighest.getColorMode());

        GridBagConstraints gbc = createDefaultGridBagConstraints();

        // Title
        JLabel taskTitle = createStyledLabel("Balance Inquiry",
                new Font(Font.SANS_SERIF, Font.BOLD, 40),
                StandardColor.Blue.getColorMode());
        taskTitle.setHorizontalAlignment(SwingConstants.LEFT);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        // Card layout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create balance card
        cardPanel.add(createBalanceCard(), CARD_BALANCE);

        // Add components to main panel
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(taskTitle, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.9;
        gbc.insets = new Insets(10, 0, 10, 0);
        mainPanel.add(cardPanel, gbc);

        // Start with balance card
        cardLayout.show(cardPanel, CARD_BALANCE);
    }

    private JPanel createBalanceCard() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(StandardColor.GreyHighest.getColorMode());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        // Title
        JLabel titleLabel = createStyledLabel("Account Balance Information",
                new Font(Font.SANS_SERIF, Font.BOLD, 32),
                StandardColor.GreyHighest.getOppositeColorMode());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        gbc.weighty = 0.1;
        panel.add(titleLabel, gbc);

        // Current date
        JLabel dateLabel = createStyledLabel("Date: " + getCurrentDate(),
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                StandardColor.Blue.getColorMode());
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        gbc.weighty = 0.05;
        panel.add(dateLabel, gbc);

        // Account information panel
        JPanel infoPanel = createInfoPanel();

        gbc.gridy = 2;
        gbc.weighty = 0.6;
        gbc.insets = new Insets(20, 50, 20, 50);
        panel.add(infoPanel, gbc);

        // Back button
        RoundedButton backButton = ATMUI.createActionButton("Back to Main Menu",
                StandardColor.Blue.getColorMode());
        backButton.addActionListener(e -> goBackToMainPanel());
        // sideButtonRegistrar();
        gbc.gridy = 3;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(20, 15, 10, 15);
        panel.add(backButton, gbc);

        return panel;
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(StandardColor.GreyMiddle.getColorMode());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(StandardColor.Blue.getColorMode(), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Account Type
        JLabel accountTypeTitle = createStyledLabel("Account Type:",
                new Font(Font.SANS_SERIF, Font.BOLD, 20),
                StandardColor.GreyHighest.getOppositeColorMode());
        accountTypeLabel = createStyledLabel("",
                new Font(Font.SANS_SERIF, Font.PLAIN, 20),
                StandardColor.Blue.getColorMode());

        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(accountTypeTitle, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(accountTypeLabel, gbc);

        // Available Balance
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel availableBalanceTitle = createStyledLabel("Available Balance:",
                new Font(Font.SANS_SERIF, Font.BOLD, 20),
                StandardColor.GreyHighest.getOppositeColorMode());
        availableBalanceLabel = createStyledLabel("",
                new Font(Font.SANS_SERIF, Font.PLAIN, 20),
                StandardColor.Green.getColorMode());

        panel.add(availableBalanceTitle, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(availableBalanceLabel, gbc);

        // Total Balance
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel totalBalanceTitle = createStyledLabel("Total Balance:",
                new Font(Font.SANS_SERIF, Font.BOLD, 20),
                StandardColor.GreyHighest.getOppositeColorMode());
        totalBalanceLabel = createStyledLabel("",
                new Font(Font.SANS_SERIF, Font.PLAIN, 20),
                StandardColor.Green.getColorMode());

        panel.add(totalBalanceTitle, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(totalBalanceLabel, gbc);

        // Interest Rate (for Saving Account)
        interestLabel = createStyledLabel("",
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                StandardColor.Orange.getColorMode());
        interestLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 5, 10);
        panel.add(interestLabel, gbc);

        // Cheque Limit (for Cheque Account)
        chequeLimitLabel = createStyledLabel("",
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                StandardColor.Orange.getColorMode());
        chequeLimitLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 4;
        gbc.insets = new Insets(5, 10, 10, 10);
        panel.add(chequeLimitLabel, gbc);

        return panel;
    }

    private void updateBalanceInfo() {
        if (bankDatabase != null && currentAccountNumber != 0) {
            try {
                // Get account information
                String accountType = bankDatabase.getAccountType(currentAccountNumber);
                double availableBalance = bankDatabase.getAvailableBalance(currentAccountNumber);
                double totalBalance = bankDatabase.getTotalBalance(currentAccountNumber);
                double rateOrLimit = bankDatabase.getRateOrLimit(currentAccountNumber);

                // Update labels
                accountTypeLabel.setText(accountType);
                availableBalanceLabel.setText(String.format("HK$ %.2f", availableBalance));
                totalBalanceLabel.setText(String.format("HK$ %.2f", totalBalance));

                // Update interest/limit information based on account type
                if (accountType.equals("Saving Account")) {
                    interestLabel.setText(String.format("Interest Rate: %.2f%% per annum", rateOrLimit * 100));
                    chequeLimitLabel.setText(""); // Clear cheque limit label

                    // Calculate and display future value
                    SavingAccount savingAccount = (SavingAccount) bankDatabase.getAccount(currentAccountNumber);
                    if (savingAccount != null) {
                        double futureValue = savingAccount.calculateInterest(1, new Screen());
                        JLabel futureValueLabel = createStyledLabel(
                                String.format("Projected balance after 1 year: HK$ %.2f", futureValue),
                                new Font(Font.SANS_SERIF, Font.PLAIN, 20),
                                StandardColor.Mint.getColorMode());
                        futureValueLabel.setHorizontalAlignment(SwingConstants.CENTER);

                        // Add future value to panel if not already added
                        GridBagConstraints gbc = createDefaultGridBagConstraints();
                        gbc.gridx = 0;
                        gbc.gridy = 5;
                        gbc.gridwidth = 2;
                        gbc.anchor = GridBagConstraints.CENTER;
                        gbc.insets = new Insets(10, 10, 5, 10);

                        JPanel infoPanel = (JPanel) cardPanel.getComponent(0).getComponentAt(2, 2);
                        if (infoPanel != null) {
                            // Remove existing future value label if any
                            Component[] components = infoPanel.getComponents();
                            for (Component comp : components) {
                                if (comp instanceof JLabel && ((JLabel) comp).getText().contains("Projected")) {
                                    infoPanel.remove(comp);
                                }
                            }
                            infoPanel.add(futureValueLabel, gbc);
                            infoPanel.revalidate();
                            infoPanel.repaint();
                        }
                    }
                } else if (accountType.equals("Cheque Account")) {
                    chequeLimitLabel.setText(String.format("Cheque Limit: HK$ %.2f per transaction", rateOrLimit));
                    interestLabel.setText(""); // Clear interest label
                } else {
                    interestLabel.setText("");
                    chequeLimitLabel.setText("");
                }

            } catch (Exception e) {
                // Handle any errors gracefully
                accountTypeLabel.setText("Error");
                availableBalanceLabel.setText("N/A");
                totalBalanceLabel.setText("N/A");
                interestLabel.setText("Unable to retrieve account information");
                chequeLimitLabel.setText("");
            }
        } else {
            // Default values when no account is set
            accountTypeLabel.setText("Not Available");
            availableBalanceLabel.setText("HK$ 0.00");
            totalBalanceLabel.setText("HK$ 0.00");
            interestLabel.setText("Please log in to view account information");
            chequeLimitLabel.setText("");
        }
    }

    public void goBackToMainPanel() {
        controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
    }

    // Helper methods
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

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void refreshBalance(int accountNumber) {
        this.currentAccountNumber = accountNumber;
        updateBalanceInfo();
    }
    
    /*
    private void sideButtonRegistrar() {
        for (int i = 0; i < 3; i++) {
            leftButton[i].addActionListener(e -> goBackToMainPanel());
            rightButton[i].addActionListener(e -> goBackToMainPanel());
        }
    }
    */
}