import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WithdrawalUI extends JPanel {
    // Card names
    private static final String CARD_MENU = "MENU";
    private static final String CARD_AMOUNT = "AMOUNT";
    private static final String CARD_CONFIRMATION = "CONFIRMATION";
    private static final String CARD_RESULT = "RESULT";
    private static final String CARD_CUSTOM = "CUSTOM";

    // Layout components
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel mainPanel;
    
    // Withdrawal data
    private int selectedAmount;
    private int currentAccountNumber;
    private BankDatabase bankDatabase;
    private CashDispenser cashDispenser;
    private Withdrawal withdrawal;
    
    // UI Components
    private JLabel statusLabel;
    private JTextField customAmountField;

    // Constants for preset amounts
    private final int[] PRESET_AMOUNTS = {200, 400, 800, 1000};
    private final int CANCELED = 6;

    public WithdrawalUI(int accountNumber, BankDatabase bankDatabase, CashDispenser cashDispenser) {
        this.currentAccountNumber = accountNumber;
        this.bankDatabase = bankDatabase;
        this.cashDispenser = cashDispenser;
        this.withdrawal = new Withdrawal(currentAccountNumber, new Screen(), bankDatabase, new Keypad(), cashDispenser);
        
        initializeUI();
    }

    private void initializeUI() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(StandardColor.GreyHighest.getColorMode());

        GridBagConstraints gbc = createDefaultGridBagConstraints();

        // Title
        JLabel taskTitle = createStyledLabel("Withdrawal", 
                new Font(Font.SANS_SERIF, Font.BOLD, 40), 
                StandardColor.Blue.getColorMode());
        taskTitle.setHorizontalAlignment(SwingConstants.LEFT);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        // Card layout for different steps
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create all cards
        cardPanel.add(createMenuCard(), CARD_MENU);
        cardPanel.add(createCustomAmountCard(), CARD_CUSTOM);
        cardPanel.add(createConfirmationCard(), CARD_CONFIRMATION);
        cardPanel.add(createResultCard(), CARD_RESULT);

        // Add components to main panel
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(taskTitle, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.9;
        gbc.insets = new Insets(10, 0, 10, 0);
        mainPanel.add(cardPanel, gbc);

        // Start with menu card
        cardLayout.show(cardPanel, CARD_MENU);
    }

    private JPanel createMenuCard() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(StandardColor.GreyHighest.getColorMode());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        // Title
        JLabel titleLabel = createStyledLabel("Withdrawal Menu", 
                new Font(Font.SANS_SERIF, Font.BOLD, 32),
                StandardColor.GreyHighest.getOppositeColorMode());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        gbc.weighty = 0.1;
        panel.add(titleLabel, gbc);

        // Available balance
        double availableBalance = bankDatabase.getAvailableBalance(currentAccountNumber);
        JLabel balanceLabel = createStyledLabel(
                String.format("Available Balance: HK$ %.2f", availableBalance),
                new Font(Font.SANS_SERIF, Font.PLAIN, 20),
                StandardColor.Green.getColorMode());
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        gbc.weighty = 0.05;
        panel.add(balanceLabel, gbc);

        // Preset amount buttons
        JPanel amountPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        amountPanel.setBackground(StandardColor.GreyHighest.getColorMode());
        
        // Preset amounts
        for (int i = 0; i < PRESET_AMOUNTS.length; i++) {
            int amount = PRESET_AMOUNTS[i];
            RoundedButton amountButton = createAmountButton(String.format("HK$ %d", amount), amount);
            amountPanel.add(amountButton);
        }

        // Custom amount button
        RoundedButton customButton = createActionButton("Custom Amount", 
                StandardColor.Blue.getColorMode());
        customButton.addActionListener(e -> showCard(CARD_CUSTOM));
        amountPanel.add(customButton);

        // Cancel button
        RoundedButton cancelButton = createActionButton("Cancel", 
                StandardColor.Red.getColorMode());
        cancelButton.addActionListener(e -> goBackToMainPanel());
        amountPanel.add(cancelButton);

        gbc.gridy = 2;
        gbc.weighty = 0.6;
        gbc.insets = new Insets(20, 50, 20, 50);
        panel.add(amountPanel, gbc);

        return panel;
    }

    private JPanel createCustomAmountCard() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(StandardColor.GreyHighest.getColorMode());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        // Title
        JLabel titleLabel = createStyledLabel("Enter Custom Amount", 
                new Font(Font.SANS_SERIF, Font.BOLD, 32),
                StandardColor.GreyHighest.getOppositeColorMode());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        gbc.weighty = 0.1;
        panel.add(titleLabel, gbc);

        // Instruction
        JLabel instructionLabel = createStyledLabel(
                "Amount must be multiples of HK$ 100, 500, or 1000",
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                StandardColor.Orange.getColorMode());
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        gbc.weighty = 0.05;
        panel.add(instructionLabel, gbc);

        // Custom amount input
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(StandardColor.GreyHighest.getColorMode());
        
        JLabel amountLabel = createStyledLabel("HK$", 
                new Font(Font.SANS_SERIF, Font.BOLD, 20),
                StandardColor.GreyHighest.getOppositeColorMode());
        
        customAmountField = new JTextField(10);
        customAmountField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        customAmountField.setHorizontalAlignment(JTextField.RIGHT);

        inputPanel.add(amountLabel);
        inputPanel.add(customAmountField);

        gbc.gridy = 2;
        gbc.weighty = 0.1;
        panel.add(inputPanel, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(StandardColor.GreyHighest.getColorMode());

        RoundedButton confirmButton = createActionButton("Confirm", 
                StandardColor.Green.getColorMode());
        confirmButton.addActionListener(e -> processCustomAmount());

        RoundedButton backButton = createActionButton("Back", 
                StandardColor.Yellow.getColorMode());
        backButton.addActionListener(e -> showCard(CARD_MENU));

        buttonPanel.add(confirmButton);
        buttonPanel.add(backButton);

        gbc.gridy = 3;
        gbc.weighty = 0.1;
        panel.add(buttonPanel, gbc);

        // Status label
        statusLabel = createStyledLabel("", 
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                StandardColor.Red.getColorMode());
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 4;
        gbc.weighty = 0.05;
        panel.add(statusLabel, gbc);

        return panel;
    }

    private JPanel createConfirmationCard() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(StandardColor.GreyHighest.getColorMode());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        // Title
        JLabel titleLabel = createStyledLabel("Confirm Withdrawal", 
                new Font(Font.SANS_SERIF, Font.BOLD, 32),
                StandardColor.GreyHighest.getOppositeColorMode());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        gbc.weighty = 0.1;
        panel.add(titleLabel, gbc);

        // Amount confirmation
        JLabel amountLabel = createStyledLabel(
                String.format("Amount: HK$ %d", selectedAmount),
                new Font(Font.SANS_SERIF, Font.BOLD, 24),
                StandardColor.Green.getColorMode());
        amountLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        gbc.weighty = 0.1;
        panel.add(amountLabel, gbc);

        // Note breakdown (if available)
        if (cashDispenser.isSufficientCashAvailable(selectedAmount)) {
            cashDispenser.precheckNumberOfAmountType(selectedAmount);
            
            JLabel notesLabel = createStyledLabel(
                    String.format("You will receive:\n%d x HK$1000\n%d x HK$500\n%d x HK$100",
                            cashDispenser.getNumberOfOneThousand(),
                            cashDispenser.getNumberOfFiveHundred(),
                            cashDispenser.getNumberOfOneHundred()),
                    new Font(Font.SANS_SERIF, Font.PLAIN, 18),
                    StandardColor.Blue.getColorMode());
            notesLabel.setHorizontalAlignment(SwingConstants.CENTER);

            gbc.gridy = 2;
            gbc.weighty = 0.2;
            panel.add(notesLabel, gbc);
        }

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(StandardColor.GreyHighest.getColorMode());

        RoundedButton confirmButton = createActionButton("Confirm Withdrawal", 
                StandardColor.Green.getColorMode());
        confirmButton.addActionListener(e -> executeWithdrawal());

        RoundedButton cancelButton = createActionButton("Cancel", 
                StandardColor.Red.getColorMode());
        cancelButton.addActionListener(e -> showCard(CARD_MENU));

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 3;
        gbc.weighty = 0.2;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JPanel createResultCard() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(StandardColor.GreyHighest.getColorMode());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        // Result message
        JLabel resultLabel = createStyledLabel("Withdrawal Successful!", 
                new Font(Font.SANS_SERIF, Font.BOLD, 32),
                StandardColor.Green.getColorMode());
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        gbc.weighty = 0.2;
        panel.add(resultLabel, gbc);

        // Amount withdrawn
        JLabel amountLabel = createStyledLabel(
                String.format("HK$ %d has been withdrawn", selectedAmount),
                new Font(Font.SANS_SERIF, Font.PLAIN, 24),
                StandardColor.GreyHighest.getOppositeColorMode());
        amountLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        gbc.weighty = 0.1;
        panel.add(amountLabel, gbc);

        // Remaining balance
        double remainingBalance = bankDatabase.getAvailableBalance(currentAccountNumber);
        JLabel balanceLabel = createStyledLabel(
                String.format("Remaining Balance: HK$ %.2f", remainingBalance),
                new Font(Font.SANS_SERIF, Font.PLAIN, 20),
                StandardColor.Blue.getColorMode());
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 2;
        gbc.weighty = 0.1;
        panel.add(balanceLabel, gbc);

        // Instruction
        JLabel instructionLabel = createStyledLabel("Please take your cash now.", 
                new Font(Font.SANS_SERIF, Font.PLAIN, 18),
                StandardColor.Orange.getColorMode());
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 3;
        gbc.weighty = 0.1;
        panel.add(instructionLabel, gbc);

        // Continue button
        RoundedButton continueButton = createActionButton("Back to Main Menu", 
                StandardColor.Blue.getColorMode());
        continueButton.addActionListener(e -> goBackToMainPanel());

        gbc.gridy = 4;
        gbc.weighty = 0.2;
        panel.add(continueButton, gbc);

        return panel;
    }

    private RoundedButton createAmountButton(String text, int amount) {
        RoundedButton button = new RoundedButton(text, text,
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.Blue.getColorMode(),
                StandardColor.GreyHighest.getOppositeColorMode(),
                StandardColor.GreyHighest.getColorMode(),
                new Font(Font.SANS_SERIF, Font.BOLD, 18),
                new Font(Font.SANS_SERIF, Font.BOLD, 18),
                true, 150, 50);
        
        button.addActionListener(e -> {
            selectedAmount = amount;
            showCard(CARD_CONFIRMATION);
        });
        
        return button;
    }

    private void processCustomAmount() {
        try {
            String amountText = customAmountField.getText().trim();
            if (amountText.isEmpty()) {
                statusLabel.setText("Please enter an amount");
                return;
            }

            int amount = Integer.parseInt(amountText);
            
            // Check if amount is multiples of 100, 500, or 1000
            if (!withdrawal.isMultiplesCondition(amount)) {
                statusLabel.setText("Amount must be multiples of HK$ 100, 500, or 1000");
                return;
            }

            // Check if amount is positive
            if (amount <= 0) {
                statusLabel.setText("Amount must be greater than 0");
                return;
            }

            // Check account balance
            double availableBalance = bankDatabase.getAvailableBalance(currentAccountNumber);
            if (amount > availableBalance) {
                statusLabel.setText("Insufficient funds in your account");
                return;
            }

            // Check cash dispenser
            if (!cashDispenser.isSufficientCashAvailable(amount)) {
                statusLabel.setText("Insufficient cash available in ATM");
                return;
            }

            // Check account limit
            if (withdrawal.isLimitAccountConditionCheckerHappen(amount, "withdrawal")) {
                statusLabel.setText("Transaction exceeds account limit");
                return;
            }

            selectedAmount = amount;
            customAmountField.setText("");
            statusLabel.setText("");
            showCard(CARD_CONFIRMATION);

        } catch (NumberFormatException ex) {
            statusLabel.setText("Please enter a valid number");
        }
    }

    private void executeWithdrawal() {
        // Perform the withdrawal
        bankDatabase.debit(currentAccountNumber, selectedAmount);
        cashDispenser.dispenseCash();
        
        // Record transaction history
        new TransactionHistory(1, currentAccountNumber, 0, 0, 0, (double) selectedAmount);
        
        showCard(CARD_RESULT);
    }

    private void showCard(String cardName) {
        cardLayout.show(cardPanel, cardName);
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

    // Helper methods
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
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                true, 150, 40);
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

    public void resetToInitialState() {
        cardLayout.show(cardPanel, CARD_MENU);
        selectedAmount = 0;
        if (customAmountField != null) {
            customAmountField.setText("");
        }
        if (statusLabel != null) {
            statusLabel.setText("");
        }
    }
}