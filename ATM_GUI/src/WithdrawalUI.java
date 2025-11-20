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
    private BankDatabase bankDatabase = new BankDatabase();// 给你添加了个临时的，为了让代码能运行起来
    private CashDispenser cashDispenser = new CashDispenser();// 给你添加了个临时的，为了让代码能运行起来
    private Withdrawal withdrawal;

    // UI Components
    private JLabel statusLabel;
    private TextFields customAmountField;

    // Constants for preset amounts
    private final int[] PRESET_AMOUNTS = { 200, 400, 800, 1000 };
    private final int CANCELED = 6;

    public WithdrawalUI() {
        // 我仅仅是把你的constructor里的东西拆分，并没有改变你的logic
    }

    public void createWithdrawalUI(int accountNumber) {
        this.currentAccountNumber = accountNumber;
        initializeUI();
    }

    private void initializeUI() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(StandardColor.GreyHighest.getColorMode());

        GridBagConstraints gbc = createDefaultGridBagConstraints();

        // Title 
        JLabel taskTitle = createStyledLabel("Withdrawal",
                new Font(Font.SANS_SERIF, Font.BOLD, 48), 
                StandardColor.Blue.getColorMode());
        taskTitle.setHorizontalAlignment(SwingConstants.LEFT);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        // Card layout for different steps
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create all cards
        cardPanel.add(createMenuCard(), CARD_MENU);
        cardPanel.add(createCustomAmountCard(), CARD_CUSTOM);
        // Confirmation and result cards will be created dynamically

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
                new Font(Font.SANS_SERIF, Font.BOLD, 36), 
                StandardColor.GreyHighest.getOppositeColorMode());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        gbc.weighty = 0.1;
        panel.add(titleLabel, gbc);

        // Available balance 
        double availableBalance = bankDatabase.getAvailableBalance(currentAccountNumber);
        JLabel balanceLabel = createStyledLabel(
                String.format("Available Balance: HK$ %.2f", availableBalance),
                new Font(Font.SANS_SERIF, Font.PLAIN, 24), 
                StandardColor.Green.getColorMode());
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        gbc.weighty = 0.05;
        panel.add(balanceLabel, gbc);

        // Preset amount buttons
        JPanel amountPanel = new JPanel(new GridLayout(3, 2, 15, 15)); 
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
                new Font(Font.SANS_SERIF, Font.BOLD, 36), 
                StandardColor.GreyHighest.getOppositeColorMode());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        gbc.weighty = 0.1;
        panel.add(titleLabel, gbc);

        // Instruction 
        JLabel instructionLabel = createStyledLabel(
                "Amount must be multiples of HK$ 100, 500, or 1000",
                new Font(Font.SANS_SERIF, Font.PLAIN, 20), 
                StandardColor.Orange.getColorMode());
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        gbc.weighty = 0.05;
        panel.add(instructionLabel, gbc);

        // Custom amount input 
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(StandardColor.GreyHighest.getColorMode());

        JLabel amountLabel = createStyledLabel("HK$",
                new Font(Font.SANS_SERIF, Font.BOLD, 24), 
                StandardColor.GreyHighest.getOppositeColorMode());

        customAmountField = new TextFields(250, 40, 
                StandardColor.GreyLower.getColor(0),
                StandardColor.Blue.getColor(0),
                StandardColor.GreyHighest.getColor(1),
                new Font(Font.SANS_SERIF, Font.BOLD, 36)); 
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
                new Font(Font.SANS_SERIF, Font.PLAIN, 20), 
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
                new Font(Font.SANS_SERIF, Font.BOLD, 36), 
                StandardColor.GreyHighest.getOppositeColorMode());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        gbc.weighty = 0.1;
        panel.add(titleLabel, gbc);

        // Amount confirmation 
        JLabel amountLabel = createStyledLabel(
                String.format("Amount: HK$ %d", selectedAmount),
                new Font(Font.SANS_SERIF, Font.BOLD, 28), 
                StandardColor.Green.getColorMode());
        amountLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        gbc.weighty = 0.1;
        panel.add(amountLabel, gbc);

        // Calculate banknote breakdown
        cashDispenser.precheckNumberOfAmountType(selectedAmount);
        int thousands = cashDispenser.getNumberOfOneThousand();
        int fiveHundreds = cashDispenser.getNumberOfFiveHundred();
        int hundreds = cashDispenser.getNumberOfOneHundred();

        // Note breakdown 
        JLabel notesLabel = createStyledLabel(
                String.format("You will receive:\n%d x HK$1000    \n%d x HK$500   \n%d x HK$100",
                        thousands, fiveHundreds, hundreds),
                new Font(Font.SANS_SERIF, Font.PLAIN, 22), // 
                StandardColor.Blue.getColorMode());
        notesLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 2;
        gbc.weighty = 0.2;
        panel.add(notesLabel, gbc);

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
                new Font(Font.SANS_SERIF, Font.BOLD, 36), 
                StandardColor.Green.getColorMode());
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        gbc.weighty = 0.2;
        panel.add(resultLabel, gbc);

        // Amount withdrawn 
        JLabel amountLabel = createStyledLabel(
                String.format("HK$ %d has been withdrawn", selectedAmount),
                new Font(Font.SANS_SERIF, Font.PLAIN, 28), 
                StandardColor.GreyHighest.getOppositeColorMode());
        amountLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        gbc.weighty = 0.1;
        panel.add(amountLabel, gbc);

        // Banknote breakdown 
        cashDispenser.precheckNumberOfAmountType(selectedAmount);
        int thousands = cashDispenser.getNumberOfOneThousand();
        int fiveHundreds = cashDispenser.getNumberOfFiveHundred();
        int hundreds = cashDispenser.getNumberOfOneHundred();
        
        JLabel breakdownLabel = createStyledLabel(
                String.format("Banknotes dispensed:\nHK$1000:%d   \nHK$500:%d   \nHK$100: %d",
                        thousands, fiveHundreds, hundreds),
                new Font(Font.SANS_SERIF, Font.PLAIN, 22), 
                StandardColor.Blue.getColorMode());
        breakdownLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 2;
        gbc.weighty = 0.2;
        panel.add(breakdownLabel, gbc);

        // Remaining balance 
        double remainingBalance = bankDatabase.getAvailableBalance(currentAccountNumber);
        JLabel balanceLabel = createStyledLabel(
                String.format("Remaining Balance: HK$ %.2f", remainingBalance),
                new Font(Font.SANS_SERIF, Font.PLAIN, 24), 
                StandardColor.Blue.getColorMode());
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 3;
        gbc.weighty = 0.1;
        panel.add(balanceLabel, gbc);

        // Instruction 
        JLabel instructionLabel = createStyledLabel("Please take your cash now.",
                new Font(Font.SANS_SERIF, Font.PLAIN, 22), 
                StandardColor.Orange.getColorMode());
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 4;
        gbc.weighty = 0.1;
        panel.add(instructionLabel, gbc);

        // Continue button 
        RoundedButton continueButton = createActionButton("Continue",
                StandardColor.Blue.getColorMode());
        continueButton.addActionListener(e -> {
            resetToInitialState();
            showCard(CARD_MENU);
        });

        gbc.gridy = 5;
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
                new Font(Font.SANS_SERIF, Font.BOLD, 22), 
                new Font(Font.SANS_SERIF, Font.BOLD, 22), 
                true, 180, 60); 

        button.addActionListener(e -> {
            selectedAmount = amount;
            showConfirmationCard();
        });

        return button;
    }

    private void processCustomAmount() {
        try {
            String amountText = customAmountField.getText().trim();
            if (amountText.isEmpty()) {
                statusLabel.setText("Please enter an amount");
                statusLabel.setForeground(StandardColor.Red.getColorMode()); 
                return;
            }

            int amount = Integer.parseInt(amountText);

            // Check if amount is multiples of 100, 500, or 1000
            if (!isMultiplesCondition(amount)) {
                statusLabel.setText("Amount must be multiples of HK$ 100, 500, or 1000 - WITHDRAWAL FAILED");
                statusLabel.setForeground(StandardColor.Red.getColorMode()); 
                return;
            }

            // Check if amount is positive
            if (amount <= 0) {
                statusLabel.setText("Amount must be greater than 0");
                statusLabel.setForeground(StandardColor.Red.getColorMode()); 
                return;
            }

            // Check account balance
            double availableBalance = bankDatabase.getAvailableBalance(currentAccountNumber);
            if (amount > availableBalance) {
                statusLabel.setText("Insufficient funds in your account");
                statusLabel.setForeground(StandardColor.Red.getColorMode()); 
                return;
            }

            // Check cash dispenser
            if (!cashDispenser.isSufficientCashAvailable(amount)) {
                statusLabel.setText("Insufficient cash available in ATM");
                statusLabel.setForeground(StandardColor.Red.getColorMode()); 
                return;
            }

            // Check account limit
            if (withdrawal.isLimitAccountConditionCheckerHappen(amount, "withdrawal")) {
                statusLabel.setText("Transaction exceeds account limit");
                statusLabel.setForeground(StandardColor.Red.getColorMode()); 
                return;
            }

            selectedAmount = amount;
            customAmountField.setText("");
            statusLabel.setText("");
            showConfirmationCard();

        } catch (NumberFormatException ex) {
            statusLabel.setText("Please enter a valid number");
            statusLabel.setForeground(StandardColor.Red.getColorMode()); 
        }
    }

    private boolean isMultiplesCondition(int amount) {
        if (amount <= 0)
            return false;
        return amount % 100 == 0 || amount % 500 == 0 || amount % 1000 == 0;
    }

    private void executeWithdrawal() {
        // Perform the withdrawal
        boolean success = withdrawal.performWithdrawal(selectedAmount);
        
        if (success) {
            // Record transaction history
            new TransactionHistory(1, currentAccountNumber, 0, 0, 0, (double) selectedAmount);
            showResultCard();
        } else {
            // Show error message
            statusLabel.setText("Withdrawal failed - please try again");
            statusLabel.setForeground(StandardColor.Red.getColorMode());
            showCard(CARD_CUSTOM);
        }
    }

    private void showCard(String cardName) {
        cardLayout.show(cardPanel, cardName);
    }


    private void showConfirmationCard() {

        Component[] components = cardPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel && CARD_CONFIRMATION.equals(((JPanel) comp).getName())) {
                cardPanel.remove(comp);
                break;
            }
        }
        
        JPanel confirmationCard = createConfirmationCard();
        confirmationCard.setName(CARD_CONFIRMATION);
        cardPanel.add(confirmationCard, CARD_CONFIRMATION);
        
        cardLayout.show(cardPanel, CARD_CONFIRMATION);
    }


    private void showResultCard() {
        Component[] components = cardPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel && CARD_RESULT.equals(((JPanel) comp).getName())) {
                cardPanel.remove(comp);
                break;
            }
        }
        
        JPanel resultCard = createResultCard();
        resultCard.setName(CARD_RESULT);
        cardPanel.add(resultCard, CARD_RESULT);
        
        cardLayout.show(cardPanel, CARD_RESULT);
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
                new Font(Font.SANS_SERIF, Font.PLAIN, 20), 
                new Font(Font.SANS_SERIF, Font.PLAIN, 20), 
                true, 180, 50); 
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