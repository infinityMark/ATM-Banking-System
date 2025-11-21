import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Transfer User Interface - Handles the GUI for money transfer operations
 * Provides a multistep form for transferring funds between accounts
 */
public class TransferUI extends JPanel {
    // Constants for card names
    public static final String CARD_MENU = "MENU";
    public static final String CARD_INFO = "INFO";
    public static final String CARD_CONFIRMATION = "CONFIRMATION";
    public static final String CARD_AFTER_TRANSACTION = "AFTER_TRANSACTION";

    // Constants for button names
    public static final String BUTTON_FIRST = "FIRST_BUTTON";
    public static final String BUTTON_SECOND = "SECOND_BUTTON";
    public static final String AMOUNT_LABEL = "AMOUNT";
    public static final String RECEIVER_LABEL = "RECEIVER";
    public static final String AMOUNT_TEXTFIELD = "RECEIVER_TEXTFIELDA";
    public static final String RECEIVER_TEXTFIELD = "RECEIVER_TEXTFIELD";
    public static final String amountText = "Amount";
    public static final String receiverText = "Receiver account";

    // Layout components
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel mainPanel;

    private int userAccountNumberInUI;

    private Transfer transfer;

    // Validation states
    private Boolean isReceiverValid = false;
    private Boolean isAmountValid = false;

    // keypad integration
    private boolean isReceiverFieldActive = true;
    private TextFields receiverAccountTextField;
    private TextFields amountTextField;
    private JLabel receiverLabel;
    private JLabel amountLabel;

    ATMUIController controller;

    Screen screen = new Screen();
    BankDatabase bankDatabase = BankDatabase.getInstance();

    public TransferUI(int userAccountNumber
        ,BankDatabase atmBankDatabase, ATMUIController controller) {
        super();
        userAccountNumberInUI = userAccountNumber;
        // JPanel panel = this.transferLayout();
        this.controller = controller;
        bankDatabase = atmBankDatabase;
        transfer = new Transfer(userAccountNumber, screen,atmBankDatabase);
        initializeUI();
    }

    /**
     * Recursively searches for a button by name within a panel hierarchy
     */
    public RoundedButton getButtonByName(JPanel panel, String buttonName) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                RoundedButton found = getButtonByName((JPanel) comp, buttonName);
                if (found != null)
                    return found;
            } else if (comp instanceof RoundedButton && buttonName.equals(comp.getName())) {
                return (RoundedButton) comp;
            }
        }
        return null;
    }


    /**
     * Navigates back to the main menu panel
     */
    public void goBackToMainPanel() {
        controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
    }

    // Keypad integration methods
    public void handleNumberInput(String input) {
        if (isReceiverFieldActive && receiverAccountTextField != null) {
            // For receiver account, only allow numbers (no decimals)
            handleReceiverAccountInput(input);
        } else if (!isReceiverFieldActive && amountTextField != null) {
            // For amount field, allow numbers, decimal point, and "00"
            handleAmountInput(input);
        }
    }

    private void handleReceiverAccountInput(String input) {
        String currentText = receiverAccountTextField.getText();

        if (input.equals(".")) {
            // "." button - append .
            receiverAccountTextField.setText(currentText + ".");
            return;
        } else if (input.equals("00")) {
            // "00" button - append two zeros
            receiverAccountTextField.setText(currentText + "00");
        } else if (input.matches("[0-9]")) {
            // Regular numbers
            receiverAccountTextField.setText(currentText + input);
        }
    }

    private void handleAmountInput(String input) {
        String currentText = amountTextField.getText();

        if (input.equals(".")) {
            // Only allow one decimal point
            if (!currentText.contains(".")) {
                // If text is empty, add "0." first
                if (currentText.isEmpty()) {
                    amountTextField.setText("0.");
                } else {
                    amountTextField.setText(currentText + input);
                }
            }
        } else if (input.equals("00")) {
            if (currentText.isEmpty() || currentText.equals("0")) {
                amountTextField.setText("0");
            } else if (currentText.contains(".")) {
                // If there's a decimal point, add zeros after it
                String[] parts = currentText.split("\\.");
                if (parts.length > 1 && parts[1].length() < 2) {
                    // Add zeros but respect the 2 decimal limit
                    int zerosToAdd = Math.min(2 - parts[1].length(), 2);
                    amountTextField.setText(currentText + "0".repeat(zerosToAdd));
                }
            } else {
                // If no decimal point, append "00"
                amountTextField.setText(currentText + "00");
            }
        } else if (input.matches("[0-9]")) {
            // Handle regular numbers
            if (currentText.equals("0")) {
                amountTextField.setText(input); // Replace "0" with the new number
            } else if (currentText.contains(".")) {
                // Check if we already have 2 decimal places
                String[] parts = currentText.split("\\.");
                if (parts.length > 1 && parts[1].length() < 2) {
                    amountTextField.setText(currentText + input);
                } else if (parts.length > 1 && parts[1].length() >= 2) {
                    // Already have 2 decimal places, don't add more
                    return;
                } else {
                    amountTextField.setText(currentText + input);
                }
            } else {
                amountTextField.setText(currentText + input);
            }
        }
    }

    public void handleDelete() {
        if (isReceiverFieldActive && receiverAccountTextField != null) {
            String currentText = receiverAccountTextField.getText();
            if (!currentText.isEmpty()) {
                receiverAccountTextField.setText(currentText.substring(0, currentText.length() - 1));
            }
        } else if (!isReceiverFieldActive && amountTextField != null) {
            String currentText = amountTextField.getText();
            if (!currentText.isEmpty()) {
                amountTextField.setText(currentText.substring(0, currentText.length() - 1));
            }
        }
    }

    public void handleClear() {
        if (isReceiverFieldActive && receiverAccountTextField != null) {
            receiverAccountTextField.setText("");
        } else if (!isReceiverFieldActive && amountTextField != null) {
            amountTextField.setText("");
        }
    }

    public void handleConfirm() {
        if (isReceiverFieldActive) {
            // Validate receiver account and switch to amount field
            if (validateReceiverAccount(receiverAccountTextField.getContent().trim(), receiverAccountTextField,
                    receiverLabel)) {
                isReceiverFieldActive = false;
                highlightActiveField();
            }
        } else {
            // Validate amount and proceed to confirmation
            if (validateAmount(amountTextField.getContent().trim(), amountTextField, amountLabel)) {
                if (!transfer.isLimitAccountConditionCheckerHappen(transfer.getAmount(), "Transfer")) {
                    showCard(CARD_CONFIRMATION);
                } else {
                    showValidationError(amountTextField, amountLabel,
                            String.format("Cheque Account can not %s over HK$50000 once time.", "Transfer"));
                }
            }
        }
    }

    private void highlightActiveField() {
        if (receiverAccountTextField != null && amountTextField != null) {
            if (isReceiverFieldActive) {
                receiverAccountTextField.setBorderColor(StandardColor.Blue.getColorMode());
                amountTextField.setBorderColor(StandardColor.GreyHighest.getColor(1));
            } else {
                receiverAccountTextField.setBorderColor(StandardColor.GreyHighest.getColor(1));
                amountTextField.setBorderColor(StandardColor.Blue.getColorMode());
            }
        }
    }

    public JPanel createSelectionMenu(String title, String firstSelection, String secondSelection,
            int fontSize, Font font, String nextPageForButtonOne, String nextPageForButtonTwo,
            GridBagConstraints gridBagConstraints) {

        setCurrentCardName(CARD_MENU);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = gridBagConstraints.insets;

        // Create selection buttons
        RoundedButton selectionOneBtn = createMenuButton(firstSelection, StandardColor.Green.getColorMode(), fontSize);
        RoundedButton selectionTwoBtn = createMenuButton(secondSelection, StandardColor.Yellow.getColorMode(),
                fontSize);

        selectionOneBtn.setName(BUTTON_FIRST);
        selectionTwoBtn.setName(BUTTON_SECOND);

        selectionOneBtn.addActionListener(e -> showCard(nextPageForButtonOne));
        selectionTwoBtn.addActionListener(e -> {
            showCard(nextPageForButtonTwo);
            if (nextPageForButtonTwo.equals("mainMenu")) {
                showCard(CARD_MENU);
            }
            goBackToMainPanel();
        });

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add title
        JLabel menuTitle = createStyledLabel(title, font, StandardColor.GreyHighest.getOppositeColorMode());
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        panel.add(menuTitle, gbc);

        // Add buttons in a panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        buttonPanel.add(selectionOneBtn);
        buttonPanel.add(selectionTwoBtn);

        gbc.gridy = 1;
        gbc.weighty = 0.3;
        panel.add(buttonPanel, gbc);

        //setCurrentCardName(CARD_MENU);

        return panel;
    }

    /**
     * Creates the receiver account and amount input form
     */
    public JPanel createReceiveTransferInformation(String remainAmount) {
        //setupReceiverPageButtons();

        setCurrentCardName(CARD_INFO);


        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create UI components
        JLabel remainAmountTitle = createStyledLabel(remainAmount, ATMUI.FONT_SMALL,
                StandardColor.GreyHighest.getOppositeColorMode());
//        RoundedButton confirmationButton = ATMUI.createActionButton("Confirm", StandardColor.Green.getColorMode());
//        RoundedButton backButton = ATMUI.createActionButton("Back", StandardColor.Yellow.getColorMode());

        receiverAccountTextField = ATMUI.createInputField(40);
        receiverAccountTextField.setName(RECEIVER_TEXTFIELD);
        receiverAccountTextField.setEditable(false);

        amountTextField = ATMUI.createInputField(40);
        amountTextField.setName(AMOUNT_TEXTFIELD);
        amountTextField.setEditable(false);

        receiverLabel = createStyledLabel(receiverText, ATMUI.FONT_NORMAL, StandardColor.GreyHighest.getOppositeColorMode());
        receiverLabel.setName(RECEIVER_LABEL);
        amountLabel = createStyledLabel(amountText, ATMUI.FONT_NORMAL, StandardColor.GreyHighest.getOppositeColorMode());
        amountLabel.setName(AMOUNT_LABEL);

        setupFieldListener(receiverAccountTextField, receiverLabel, receiverText);
        setupFieldListener(amountTextField, amountLabel, amountText);

        JPanel amountDisplay = createAmountDisplayPanel(amountTextField);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Layout components
        addComponentToPanel(panel, gbc, remainAmountTitle, 0, 0.01);
        addComponentToPanel(panel, gbc, receiverLabel, 1, 0.01);
        addComponentToPanel(panel, gbc, receiverAccountTextField, 2, 0.01);
        addComponentToPanel(panel, gbc, amountLabel, 3, 0.01);
        addComponentToPanel(panel, gbc, amountDisplay, 4, 0.01);

        // Instructions for keypad usage
        JLabel instructionLabel = createStyledLabel("Use keypad to input numbers. Press Confirm to proceed.",
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                StandardColor.Blue.getColorMode());
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 5;
        gbc.weighty = 0.01;
        panel.add(instructionLabel, gbc);

//        backButton.addActionListener(e -> {
//            showCard(CARD_MENU);
//            resetToInitialState();
//        });

        gbc.gridy = 6;
        gbc.weighty = 0.01;
        gbc.insets = new Insets(30, 10, 10, 10);
        // panel.add(buttonPanel, gbc);

        // Initialize field highlighting
        isReceiverFieldActive = true;
        highlightActiveField();

        // removeAllButtonListenersCompletely();

        //setCurrentCardName(CARD_INFO);

        return panel;
    }

    /**
     * Creates the transfer confirmation screen
     */
    public JPanel createConfirmationStep(String title, String firstSelection, String secondSelection,
            int fontSize, Font font) {

        // setupConfirmationPageButtons();
        setCurrentCardName(CARD_CONFIRMATION);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();

        // Create confirmation message
        String confirmationMessage = String.format("The system is going to transfer HK$ %.2f to account number: %d",
                transfer.getAmount(), transfer.getReceiverAccounts());
        JLabel confirmationLabel = createStyledLabel(confirmationMessage, ATMUI.FONT_SMALL,
                StandardColor.GreyHighest.getOppositeColorMode());

        gbc.weighty = 0.0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 15, 10, 15);
        panel.add(confirmationLabel, gbc);

        // Create selection menu
        gbc.insets = new Insets(10, 15, 10, 15);
        JPanel selectionMenu = createSelectionMenu(title, firstSelection, secondSelection,
                fontSize, font, CARD_AFTER_TRANSACTION, CARD_MENU, gbc);
        selectionMenu.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        gbc.weighty = 0.4;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(selectionMenu, gbc);

        // Add transfer action to confirmation button
        RoundedButton confirmButton = getButtonByName(selectionMenu, BUTTON_FIRST);
        confirmButton.addActionListener(e -> {
            executeTransfer();
            updateBalanceInquiry();
        });

        RoundedButton cancelButton = getButtonByName(selectionMenu, BUTTON_SECOND);
        cancelButton.addActionListener(e -> {
            showCard(CARD_MENU);
            resetToInitialState();
        });

        //setCurrentCardName(CARD_CONFIRMATION);

        return panel;
    }

    private void updateBalanceInquiry() {
        // Update balance inquiry for current account
        // BankDatabase bankDatabase = getBankDatabase();
        double currentBalance = bankDatabase.getAvailableBalance(userAccountNumberInUI);
        double receiverBalance = bankDatabase.getAvailableBalance(transfer.getReceiverAccounts());

        System.out.printf("Transfer completed: Account %d balance: HK$ %.2f, Account %d balance: HK$ %.2f%n",
                userAccountNumberInUI, currentBalance, transfer.getReceiverAccounts(), receiverBalance);
    }

    public void resetToInitialState() {
        cardLayout.show(cardPanel, CARD_MENU);
        setCurrentCardName(CARD_MENU);
        resetValidationFlags();
        isReceiverFieldActive = true;
        if (receiverAccountTextField != null)
            receiverAccountTextField.setText("");
        if (amountTextField != null)
            amountTextField.setText("");
        if (receiverLabel != null)
            receiverLabel.setText(receiverText);
        if (amountLabel != null)
            amountLabel.setText(amountText);
    }

    /**
     * Creates the post-transfer completion screen
     */
    public JPanel createAfterTransaction(String transactionInformation, Font font) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();

        setCurrentCardName(CARD_AFTER_TRANSACTION);

        // 清理并设置按钮
        // removeAllButtonListenersCompletely();
        // setupFirstPageButtons();

        JLabel transactionInfoLabel = createStyledLabel(transactionInformation, font,
                StandardColor.GreyHighest.getOppositeColorMode());
        JLabel successLabel = createStyledLabel("The transfer successfully.", font,
                StandardColor.GreyHighest.getOppositeColorMode());

        panel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        addComponentToPanel(panel, gbc, transactionInfoLabel, 0, 0.1);
        addComponentToPanel(panel, gbc, successLabel, 1, 0.1);

        gbc.insets = new Insets(0, 0, 0, 0);
        JPanel continueMenu = createSelectionMenu("Do you want transfer to another?",
                "Yes, go back transfer", "No, go back ATM menu",
                35, font, CARD_INFO, "mainMenu", gbc);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        gbc.gridy = 2;
        gbc.weighty = 0.8;
        panel.add(continueMenu, gbc);

        //setCurrentCardName(CARD_AFTER_TRANSACTION);

        return panel;
    }

    // ============ PRIVATE HELPER METHODS ============

    /**
     * Creates a standardized GridBagConstraints object
     */
    private GridBagConstraints createDefaultGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    /**
     * Creates a styled menu button with consistent appearance
     */
    private RoundedButton createMenuButton(String text, Color backgroundColor, int fontSize) {
        RoundedButton button = new RoundedButton(text, text,
                StandardColor.GreyHighest.getColorMode(),
                backgroundColor,
                StandardColor.GreyHighest.getOppositeColorMode(),
                StandardColor.GreyHighest.getColorMode(),
                new Font(Font.SANS_SERIF, Font.PLAIN, fontSize),
                new Font(Font.SANS_SERIF, Font.PLAIN, fontSize),
                true, 200, 10);
        button.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        return button;
    }

    /**
     * Creates a styled text label
     */
    private JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    /**
     * Creates the amount display panel with currency symbol
     */
    private JPanel createAmountDisplayPanel(TextFields amountTextField) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        TextFields currencySymbol = ATMUI.createInputField(40);
        currencySymbol.setText("HK$ ");
        currencySymbol.setEnabled(false);

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weighty = 1.0;
        gbc.weightx = 0.001;
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(currencySymbol, gbc);

        gbc.weightx = 0.9;
        gbc.gridx = 1;
        panel.add(amountTextField, gbc);

        return panel;
    }

    /**
     * Helper method to add components to panel with consistent layout
     */
    private void addComponentToPanel(JPanel panel, GridBagConstraints gbc, Component component,
            int gridY, double weightY) {
        gbc.gridy = gridY;
        gbc.weighty = weightY;
        panel.add(component, gbc);
    }

    /**
     * Creates a panel containing action buttons
     */
    /*private JPanel createButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout());
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }*/

    private void setupFieldListener(TextFields textField, JLabel label, String content) {
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                label.setText(content);
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Do nothing
            }
        });
    }

    /**
     * Validates receiver account input
     */
    private boolean validateReceiverAccount(String accountText, TextFields textField, JLabel label) {
        if (accountText.isEmpty()) {
            showValidationError(textField, label, "Please enter account number");
            return false;
        }

        try {
            int accountNumber = Integer.parseInt(accountText);

            if (bankDatabase.isAccountNumberExist(accountNumber) == -1) {
                showValidationError(textField, label, "The receiver account does not exist");
                return false;
            }

            if (userAccountNumberInUI == accountNumber) {
                showValidationError(textField, label, "The send account and receiver account can not be same.");
                return false;
            }

            transfer.setReceiverAccounts(accountNumber);
            clearValidationError(label);
            return true;

        } catch (NumberFormatException ex) {
            showValidationError(textField, label, "Please enter a valid account number");
            return false;
        }
    }

    /**
     * Validates transfer amount input
     */
    private boolean validateAmount(String amountText, TextFields textField, JLabel label) {
        if (amountText.isEmpty()) {
            showValidationError(textField, label, "Please enter amount");
            return false;
        }

        try {
            double amountValue = Double.parseDouble(amountText);

            if (!transfer.isTwoDecimalOnly(amountValue)) {
                showValidationError(textField, label, "Only two decimal amount is maximum allowed");
                return false;
            }

            if (amountValue > bankDatabase.getTotalBalance(userAccountNumberInUI)) {
                showValidationError(textField, label, "Insufficient amount in your account");
                return false;
            }

            if (amountValue <= 0) {
                showValidationError(textField, label, "Amount must be greater than 0");
                return false;
            }

            transfer.setAmount(amountValue);
            clearValidationError(label);
            return true;

        } catch (NumberFormatException ex) {
            showValidationError(textField, label, "Please enter a valid amount");
            return false;
        }
    }

    /**
     * Displays validation error message
     */
    private void showValidationError(TextFields field, JLabel label, String message) {
        field.warning();
        String originalText = label.getText().split("\\|")[0].trim();
        label.setText(originalText + " | " + message);
    }

    /**
     * Clears validation error message
     */
    private void clearValidationError(JLabel label) {
        String originalText = label.getText().split("\\|")[0].trim();
        label.setText(originalText);
    }

    /**
     * Resets validation flags
     */
    private void resetValidationFlags() {
        isReceiverValid = false;
        isAmountValid = false;
    }

    /**
     * Clears input fields
     */
    /*private void clearInputFields(TextFields field1, TextFields field2) {
        if (field1 != null)
            field1.setText("");
        if (field2 != null)
            field2.setText("");
    }*/

    /**
     * Executes the actual transfer operation
     */
    protected void executeTransfer() {
        bankDatabase.transfer(transfer.getAccountNumber(), transfer.getReceiverAccounts(), transfer.getAmount());
        System.out.println(
                "Transfer executed: " + transfer.getAmount() + " from " + transfer.getAccountNumber() + " to " + transfer.getReceiverAccounts());
        System.out.println("Remaining balance: " + bankDatabase.getAvailableBalance(transfer.getAccountNumber()));
        new TransactionHistory(0, transfer.getAccountNumber(), transfer.getReceiverAccounts(), 0, 0, transfer.getAmount());
    }

    /**
     * Switches between different card views with dynamic creation
     */
    public void showCard(String cardName) {
        System.out.println("Switching to card: " + cardName);
        recreateCardIfNeeded(cardName);

        if (cardLayout != null && cardPanel != null) {
            cardLayout.show(cardPanel, cardName);
            setCurrentCardName(cardName);
        } else {
            System.err.println("CardLayout or cardPanel is null");
        }
    }

    /**
     * Recreates card panels when needed to ensure fresh state
     */
    private void recreateCardIfNeeded(String cardName) {
        removeExistingCard(cardName);

        switch (cardName) {
            case CARD_INFO:
                JPanel infoCard = createReceiveTransferInformation(
                        "Currently asset in your account HKD$"
                                + bankDatabase.getAvailableBalance(userAccountNumberInUI));
                infoCard.setName(CARD_INFO);
                cardPanel.add(infoCard, CARD_INFO);
                break;

            case CARD_CONFIRMATION:
                JPanel confirmationCard = createConfirmationStep(
                        "Transfer Confirmation Operation:", "1- Confirm the transfer", "2-Cancel the transfer", 40,
                        new Font(Font.SANS_SERIF, Font.PLAIN, 30));
                confirmationCard.setName(CARD_CONFIRMATION);
                cardPanel.add(confirmationCard, CARD_CONFIRMATION);
                break;

            case CARD_AFTER_TRANSACTION:
                String transactionInfo = String.format("Total HK$ %.2f transfers to account %d.",
                        transfer.getAmount(), transfer.getReceiverAccounts());
                JPanel afterTransactionCard = createAfterTransaction(transactionInfo,
                        new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                afterTransactionCard.setName(CARD_AFTER_TRANSACTION);
                cardPanel.add(afterTransactionCard, CARD_AFTER_TRANSACTION);
                break;
        }
    }

    /**
     * Removes existing card panel if it exists
     */
    private void removeExistingCard(String cardName) {
        Component[] components = cardPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel && cardName.equals(((JPanel) comp).getName())) {
                cardPanel.remove(comp);
                break;
            }
        }
    }

    /**
     * Creates and returns the main transfer layout panel
     */
    public void initializeUI() {
        System.out.println("=== TransferUI.initializeUI() ===");
        System.out.println("用户账户: " + userAccountNumberInUI);

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(StandardColor.GreyHighest.getColorMode());

        GridBagConstraints gbc = createDefaultGridBagConstraints();

        JLabel taskTitle = createStyledLabel("Transfer", ATMUI.FONT_TITLE_LARGE, StandardColor.Blue.getColorMode());
        taskTitle.setHorizontalAlignment(SwingConstants.LEFT);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        gbc.insets = new Insets(10, 15, 10, 15);
        JPanel menuCard = createSelectionMenu("Menu", "1 - Input receiver account number", "2 - Exit", 30,
                new Font(Font.SANS_SERIF, Font.PLAIN, 35), CARD_INFO, "mainMenu", gbc);

        cardPanel.add(menuCard, CARD_MENU);

        addComponentToPanel(mainPanel, gbc, taskTitle, 0, 0.1);
        gbc.gridy = 1;
        gbc.weighty = 0.9;
        mainPanel.add(cardPanel, gbc);

        // 关键修复：在显示卡片后立即设置当前卡片状态
        cardLayout.show(cardPanel, CARD_MENU);
        setCurrentCardName(CARD_MENU);  // 确保状态同步

        System.out.println("初始化完成，当前卡片: " + getCurrentCardName());
        System.out.println("=== TransferUI.initializeUI() 结束 ===");
    }

    private String currentCardName = CARD_MENU;

    public void setCurrentCardName(String name){
        currentCardName = name;
    }

    public String getCurrentCardName() {
        return currentCardName;
    }
   
    public JPanel getMainPanel() {
        return mainPanel;
    }
}