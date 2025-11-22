import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Transfer User Interface - Handles the GUI for money transfer operations.
 * Provides a multistep form for transferring funds between accounts with validation and confirmation steps.
 * Supports keypad input integration and dynamic card-based navigation.
 */
public class TransferUI extends JPanel {
    // ============ CARD NAVIGATION CONSTANTS ============
    private String currentCardName = CARD_MENU; // Tracks currently displayed card
    public static final String CARD_MENU = "MENU"; // Initial menu card
    public static final String CARD_INFO = "INFO"; // Account/amount input card
    public static final String CARD_CONFIRMATION = "CONFIRMATION"; // Transfer confirmation card
    public static final String CARD_AFTER_TRANSACTION = "AFTER_TRANSACTION"; // Post-transfer summary card

    // ============ COMPONENT IDENTIFIER CONSTANTS ============
    public static final String BUTTON_FIRST = "FIRST_BUTTON"; // Identifier for primary action button
    public static final String BUTTON_SECOND = "SECOND_BUTTON"; // Identifier for secondary action button
    public static final String AMOUNT_LABEL = "AMOUNT"; // Amount field label identifier
    public static final String RECEIVER_LABEL = "RECEIVER"; // Receiver field label identifier
    public static final String AMOUNT_TEXTFIELD = "AMOUNT_TEXTFIELD"; // Amount input field identifier
    public static final String RECEIVER_TEXTFIELD = "RECEIVER_TEXTFIELD"; // Receiver account input field identifier
    public static final String amountText = "Amount"; // Default amount label text
    public static final String receiverText = "Receiver account"; // Default receiver label text

    // ============ UI LAYOUT COMPONENTS ============
    private CardLayout cardLayout; // Manages card-based navigation
    private JPanel cardPanel; // Container for different UI cards
    private JPanel mainPanel; // Main container panel

    private int userAccountNumberInUI; // Current authenticated user's account number
    private Transfer transfer; // Business logic handler for transfers

    // ============ VALIDATION STATE FLAGS ============
    private Boolean isReceiverValid = false; // Tracks receiver account validation status
    private Boolean isAmountValid = false; // Tracks amount validation status

    // ============ KEYBOARD INPUT INTEGRATION ============
    private boolean isReceiverFieldActive = true; // Indicates active input field (receiver vs amount)
    private TextFields receiverAccountTextField; // Input field for receiver account number
    private TextFields amountTextField; // Input field for transfer amount
    private JLabel receiverLabel; // Label for receiver account field
    private JLabel amountLabel; // Label for amount field

    // ============ DEPENDENCIES ============
    ATMUIController controller; // Main application controller
    Screen screen = new Screen(); // Output display handler
    BankDatabase bankDatabase = BankDatabase.getInstance(); // Singleton database instance

    /**
     * Constructs a new TransferUI instance with specified parameters.
     *
     * @param userAccountNumber    The authenticated user's account number
     * @param atmBankDatabase      Database instance for account operations
     * @param controller           Application controller for navigation
     */
    public TransferUI(int userAccountNumber, BankDatabase atmBankDatabase, ATMUIController controller) {
        super();
        userAccountNumberInUI = userAccountNumber;
        this.controller = controller;
        bankDatabase = atmBankDatabase;
        transfer = new Transfer(userAccountNumber, screen, atmBankDatabase);
        initializeUI(); // Build initial UI components
    }

    /**
     * Sets the currently displayed card name.
     *
     * @param name The card identifier constant (e.g., CARD_MENU, CARD_INFO)
     */
    public void setCurrentCardName(String name) {
        currentCardName = name;
    }

    /**
     * Gets the currently displayed card name.
     *
     * @return Current card identifier constant
     */
    public String getCurrentCardName() {
        return currentCardName;
    }

    /**
     * Gets the main panel containing the UI components.
     *
     * @return Main UI panel
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Recursively searches for a button by name within a panel hierarchy.
     *
     * @param panel      Root panel to search
     * @param buttonName Target button's name identifier
     * @return Found button or null if not found
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
     * Navigates back to the main ATM menu panel.
     */
    public void goBackToMainPanel() {
        controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
    }

    // ============ KEYBOARD INPUT HANDLERS ============

    /**
     * Processes numeric keypad input based on active field.
     *
     * @param input Keypad input value (digit, "00", or ".")
     */
    public void handleNumberInput(String input) {
        if (isReceiverFieldActive && receiverAccountTextField != null) {
            handleReceiverAccountInput(input); // Handle receiver account input
        } else if (!isReceiverFieldActive && amountTextField != null) {
            handleAmountInput(input); // Handle transfer amount input
        }
    }

    /**
     * Processes input for receiver account field (numeric only).
     *
     * @param input Keypad input value
     */
    private void handleReceiverAccountInput(String input) {
        String currentText = receiverAccountTextField.getText();

        // Special handling for input types
        if (input.equals(".")) {
            receiverAccountTextField.setText(currentText + ".");
        } else if (input.equals("00")) {
            receiverAccountTextField.setText(currentText + "00");
        } else if (input.matches("[0-9]")) {
            receiverAccountTextField.setText(currentText + input);
        }
    }

    /**
     * Processes input for amount field with decimal constraints.
     *
     * @param input Keypad input value
     */
    private void handleAmountInput(String input) {
        String currentText = amountTextField.getText();

        if (input.equals(".")) {
                amountTextField.setText(currentText + ".");
        } else if (input.equals("00")) {
            // Handle double-zero input with decimal constraints
                amountTextField.setText(currentText + "00");
            } else if (input.matches("[0-9]")) {
                amountTextField.setText(currentText + input);
            }
    }

    /**
     * Handles delete operation for active input field.
     */
    public void handleDelete() {
        if (isReceiverFieldActive && receiverAccountTextField != null) {
            String currentText = receiverAccountTextField.getText();
            receiverAccountTextField.setText(!currentText.isEmpty() ? currentText.substring(0, currentText.length() - 1) : "");
        } else if (!isReceiverFieldActive && amountTextField != null) {
            String currentText = amountTextField.getText();
            amountTextField.setText(!currentText.isEmpty() ? currentText.substring(0, currentText.length() - 1) : "");
        }
    }

    /**
     * Clears the active input field completely.
     */
    public void handleClear() {
        if (isReceiverFieldActive && receiverAccountTextField != null) {
            receiverAccountTextField.setText("");
        } else if (!isReceiverFieldActive && amountTextField != null) {
            amountTextField.setText("");
        }
    }

    /**
     * Processes confirmation action based on active field.
     * Validates input and progresses to next step.
     */
    public void handleConfirm() {
        if (isReceiverFieldActive) {
            // Validate receiver account and switch to amount field
            if (validateReceiverAccount(receiverAccountTextField.getContent().trim(), receiverAccountTextField, receiverLabel)) {
                isReceiverFieldActive = false;
                highlightActiveField(); // Update UI highlighting
            }
        } else {
            // Validate amount and proceed to confirmation
            if (validateAmount(amountTextField.getContent().trim(), amountTextField, amountLabel)) {
                if (!transfer.isLimitAccountConditionCheckerHappen(transfer.getAmount(), "Transfer")) {
                    showCard(CARD_CONFIRMATION); // Show confirmation screen
                } else {
                    showValidationError(amountTextField, amountLabel,
                            "Cheque Account cannot transfer over HK$50,000 at once.");
                }
            }
        }
    }

    /**
     * Updates visual highlighting for active input field.
     */
    private void highlightActiveField() {
        if (receiverAccountTextField != null && amountTextField != null) {
            receiverAccountTextField.setBorderColor(isReceiverFieldActive ?
                    StandardColor.Blue.getColorMode() :
                    StandardColor.GreyHighest.getColor(1));

            amountTextField.setBorderColor(!isReceiverFieldActive ?
                    StandardColor.Blue.getColorMode() :
                    StandardColor.GreyHighest.getColor(1));
        }
    }

    // ============ UTILITY METHODS ============

    /**
     * Creates default GridBagConstraints for consistent layout.
     *
     * @return Configured GridBagConstraints instance
     */
    private GridBagConstraints createDefaultGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    /**
     * Creates a consistently styled menu button.
     *
     * @param text           Button display text
     * @param backgroundColor Button background color
     * @param fontSize       Text font size
     * @return Configured RoundedButton instance
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
     * Creates a consistently styled text label.
     *
     * @param text   Display text
     * @param font   Text font
     * @param color  Text color
     * @return Configured JLabel instance
     */
    private JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    /**
     * Adds component to panel with standardized layout constraints.
     *
     * @param panel      Target panel
     * @param gbc        Layout constraints
     * @param component  Component to add
     * @param gridY      Vertical grid position
     * @param weightY    Vertical weight distribution
     */
    private void addComponentToPanel(JPanel panel, GridBagConstraints gbc, Component component,
                                     int gridY, double weightY) {
        gbc.gridy = gridY;
        gbc.weighty = weightY;
        panel.add(component, gbc);
    }

    /**
     * Sets up focus listeners for input fields to update placeholder text.
     *
     * @param textField Input field component
     * @param label     Associated label
     * @param content   Placeholder text
     */
    private void setupFieldListener(TextFields textField, JLabel label, String content) {
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                label.setText(content); // Restore original label text
            }

            @Override
            public void focusLost(FocusEvent e) {
                // No action needed on focus loss
            }
        });
    }

    /**
     * Displays validation error on input field and label.
     *
     * @param field   Input field to highlight
     * @param label   Associated label to update
     * @param message Error message to display
     */
    private void showValidationError(TextFields field, JLabel label, String message) {
        field.warning(); // Visual error state
        String originalText = label.getText().split("\\|")[0].trim();
        label.setText(originalText + " | " + message); // Append error to label
    }

    /**
     * Clears validation error state from field and label.
     *
     * @param label     Label to reset
     * @param textFields Field to reset
     */
    private void clearValidationError(JLabel label, TextFields textFields) {
        String originalText = label.getText().split("\\|")[0].trim();
        label.setText(originalText); // Restore original text
        textFields.normal(); // Reset visual state
    }

    /**
     * Resets all validation flags to default state.
     */
    private void resetValidationFlags() {
        isReceiverValid = false;
        isAmountValid = false;
    }

    // ============ BUSINESS LOGIC METHODS ============

    /**
     * Executes the actual fund transfer operation.
     * Updates database and creates transaction history record.
     */
    protected void executeTransfer() {
        bankDatabase.transfer(transfer.getAccountNumber(), transfer.getReceiverAccounts(), transfer.getAmount());
        System.out.println("Transfer executed: " + transfer.getAmount() +
                " from " + transfer.getAccountNumber() +
                " to " + transfer.getReceiverAccounts());
        System.out.println("Remaining balance: " +
                bankDatabase.getAvailableBalance(transfer.getAccountNumber()));
        new TransactionHistory(0, transfer.getAccountNumber(),
                transfer.getReceiverAccounts(), 0, 0, transfer.getAmount());
    }

    /**
     * Validates receiver account input.
     *
     * @param accountText Input text to validate
     * @param textField   Input field component
     * @param label       Associated label component
     * @return true if valid, false otherwise
     */
    private boolean validateReceiverAccount(String accountText, TextFields textField, JLabel label) {
        if (accountText.isEmpty()) {
            showValidationError(textField, label, "Please enter account number");
            return false;
        }

        try {
            int accountNumber = Integer.parseInt(accountText);

            // Check account existence
            if (bankDatabase.isAccountNumberExist(accountNumber) == -1) {
                showValidationError(textField, label, "Receiver account does not exist");
                return false;
            }

            // Prevent self-transfer
            if (userAccountNumberInUI == accountNumber) {
                showValidationError(textField, label, "Sender and receiver cannot be the same account");
                return false;
            }

            transfer.setReceiverAccounts(accountNumber);
            clearValidationError(label, textField);
            return true;

        } catch (NumberFormatException ex) {
            showValidationError(textField, label, "Please enter a valid account number");
            return false;
        }
    }

    /**
     * Validates transfer amount input.
     *
     * @param amountText Input text to validate
     * @param textField  Input field component
     * @param label      Associated label component
     * @return true if valid, false otherwise
     */
    private boolean validateAmount(String amountText, TextFields textField, JLabel label) {
        if (amountText.isEmpty()) {
            showValidationError(textField, label, "Please enter amount");
            return false;
        }

        try {
            double amountValue = Double.parseDouble(amountText);

            // Decimal precision check
            if (!transfer.isTwoDecimalOnly(amountValue)) {
                showValidationError(textField, label, "Maximum two decimal places allowed");
                return false;
            }

            // Sufficient funds check
            if (amountValue > bankDatabase.getTotalBalance(userAccountNumberInUI)) {
                showValidationError(textField, label, "Insufficient funds in your account");
                return false;
            }

            // Positive amount check
            if (amountValue <= 0) {
                showValidationError(textField, label, "Amount must be greater than 0");
                return false;
            }

            transfer.setAmount(amountValue);
            clearValidationError(label, textField);
            return true;

        } catch (NumberFormatException ex) {
            showValidationError(textField, label, "Please enter a valid amount");
            return false;
        }
    }

    // ============ UI NAVIGATION METHODS ============

    /**
     * Switches to specified card view, recreating dynamic content if needed.
     *
     * @param cardName Target card identifier constant
     */
    public void showCard(String cardName) {
        System.out.println("Switching to card: " + cardName);
        recreateCardIfNeeded(cardName); // Rebuild dynamic cards

        if (cardLayout != null && cardPanel != null) {
            cardLayout.show(cardPanel, cardName);
            setCurrentCardName(cardName);
        } else {
            System.err.println("CardLayout or cardPanel is null");
        }
    }

    /**
     * Resets UI to initial state (menu card with cleared fields).
     */
    public void resetToInitialState() {
        cardLayout.show(cardPanel, CARD_MENU);
        setCurrentCardName(CARD_MENU);
        resetValidationFlags();
        isReceiverFieldActive = true;
        if (receiverAccountTextField != null) receiverAccountTextField.setText("");
        if (amountTextField != null) amountTextField.setText("");
        if (receiverLabel != null) receiverLabel.setText(receiverText);
        if (amountLabel != null) amountLabel.setText(amountText);
    }

    /**
     * Recreates card panels when needed to ensure fresh state.
     *
     * @param cardName Target card identifier constant
     */
    private void recreateCardIfNeeded(String cardName) {
        removeExistingCard(cardName); // Remove existing instance if present

        switch (cardName) {
            case CARD_INFO:
                // Create dynamic info card with current balance
                JPanel infoCard = createReceiveTransferInformation(
                        "Current account balance: HKD$" +
                                bankDatabase.getAvailableBalance(userAccountNumberInUI));
                infoCard.setName(CARD_INFO);
                cardPanel.add(infoCard, CARD_INFO);
                break;

            case CARD_CONFIRMATION:
                // Create confirmation card with transfer details
                JPanel confirmationCard = createConfirmationStep(
                        "Transfer Confirmation",
                        "Confirm transfer",
                        "Cancel transfer",
                        40,
                        new Font(Font.SANS_SERIF, Font.PLAIN, 30));
                confirmationCard.setName(CARD_CONFIRMATION);
                cardPanel.add(confirmationCard, CARD_CONFIRMATION);
                break;

            case CARD_AFTER_TRANSACTION:
                // Create post-transaction card with summary
                String transactionInfo = String.format("Transferred HK$ %.2f to account %d.",
                        transfer.getAmount(), transfer.getReceiverAccounts());
                JPanel afterTransactionCard = createAfterTransaction(transactionInfo,
                        new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                afterTransactionCard.setName(CARD_AFTER_TRANSACTION);
                cardPanel.add(afterTransactionCard, CARD_AFTER_TRANSACTION);
                break;
        }
    }

    /**
     * Removes existing card panel if it exists.
     *
     * @param cardName Target card identifier constant
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
     * Updates balance display and resets UI after transaction.
     *
     * @param user Account number to update
     */
    protected void updateBalanceInquiry(int user) {
        user = userAccountNumberInUI;
        double currentBalance = bankDatabase.getAvailableBalance(user);
        showCard(CARD_MENU); // Return to menu card
    }

    // ============ UI CREATION METHODS ============

    /**
     * Initializes the main UI structure with card layout system.
     */
    public void initializeUI() {
        System.out.println("=== TransferUI.initializeUI() started ===");

        // Main container setup
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(StandardColor.GreyHighest.getColorMode());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        // Title label
        JLabel taskTitle = createStyledLabel("Transfer", ATMUI.FONT_TITLE_LARGE, StandardColor.Blue.getColorMode());
        taskTitle.setHorizontalAlignment(SwingConstants.LEFT);

        // Card layout system
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create initial menu card
        gbc.insets = new Insets(10, 15, 10, 15);
        JPanel menuCard = createSelectionMenu("Transfer Menu",
                "Enter receiver details",
                "Exit",
                40,
                new Font(Font.SANS_SERIF, Font.PLAIN, 35),
                CARD_INFO,
                "mainMenu",
                gbc);
        cardPanel.add(menuCard, CARD_MENU);

        // Assemble main layout
        addComponentToPanel(mainPanel, gbc, taskTitle, 0, 0.1);
        gbc.gridy = 1;
        gbc.weighty = 0.9;
        mainPanel.add(cardPanel, gbc);

        // Initial state setup
        cardLayout.show(cardPanel, CARD_MENU);
        setCurrentCardName(CARD_MENU);

        System.out.println("UI initialization complete. Current card: " + getCurrentCardName());
        System.out.println("=== TransferUI.initializeUI() completed ===");
    }

    /**
     * Creates a selection menu card with two action buttons.
     *
     * @param title               Menu title
     * @param firstSelection      Primary button text
     * @param secondSelection     Secondary button text
     * @param fontSize            Button font size
     * @param font                Title font
     * @param nextPageForButtonOne Target card for primary button
     * @param nextPageForButtonTwo Target action for secondary button
     * @param gridBagConstraints   Layout constraints
     * @return Configured menu panel
     */
    public JPanel createSelectionMenu(String title, String firstSelection, String secondSelection,
                                      int fontSize, Font font, String nextPageForButtonOne, String nextPageForButtonTwo,
                                      GridBagConstraints gridBagConstraints) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = gridBagConstraints.insets;
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create action buttons
        RoundedButton selectionOneBtn = createMenuButton(firstSelection, StandardColor.Green.getColorMode(), fontSize);
        RoundedButton selectionTwoBtn = createMenuButton(secondSelection, StandardColor.Yellow.getColorMode(), fontSize);
        selectionOneBtn.setName(BUTTON_FIRST);
        selectionTwoBtn.setName(BUTTON_SECOND);

        // Configure button actions
        selectionOneBtn.addActionListener(e -> showCard(nextPageForButtonOne));
        selectionTwoBtn.addActionListener(e -> {
            if ("mainMenu".equals(nextPageForButtonTwo)) {
                goBackToMainPanel();
            } else {
                showCard(nextPageForButtonTwo);
            }
        });

        // Title label
        JLabel menuTitle = createStyledLabel(title, font, StandardColor.GreyHighest.getOppositeColorMode());
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        panel.add(menuTitle, gbc);

        // Button container
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        buttonPanel.add(selectionOneBtn);
        buttonPanel.add(selectionTwoBtn);

        gbc.gridy = 1;
        gbc.weighty = 0.3;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    /**
     * Creates a panel combining currency symbol with amount input field.
     *
     * @param amountTextField Amount input field component
     * @return Configured currency display panel
     */
    private JPanel createAmountDisplayPanel(TextFields amountTextField) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Currency symbol field (disabled)
        TextFields currencySymbol = ATMUI.createInputField(40);
        currencySymbol.setText("HK$ ");
        currencySymbol.setEnabled(false);

        // Layout constraints
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
     * Creates the input form for receiver account and transfer amount.
     *
     * @param remainAmount Current account balance display text
     * @return Configured input form panel
     */
    public JPanel createReceiveTransferInformation(String remainAmount) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Balance display
        JLabel remainAmountTitle = createStyledLabel(remainAmount, ATMUI.FONT_SMALL,
                StandardColor.GreyHighest.getOppositeColorMode());

        // Input fields creation
        receiverAccountTextField = ATMUI.createInputField(40);
        receiverAccountTextField.setName(RECEIVER_TEXTFIELD);
        receiverAccountTextField.setEditable(false); // Keypad-controlled input

        amountTextField = ATMUI.createInputField(40);
        amountTextField.setName(AMOUNT_TEXTFIELD);
        amountTextField.setEditable(false); // Keypad-controlled input

        // Field labels
        receiverLabel = createStyledLabel(receiverText, ATMUI.FONT_NORMAL, StandardColor.GreyHighest.getOppositeColorMode());
        receiverLabel.setName(RECEIVER_LABEL);
        amountLabel = createStyledLabel(amountText, ATMUI.FONT_NORMAL, StandardColor.GreyHighest.getOppositeColorMode());
        amountLabel.setName(AMOUNT_LABEL);

        // Focus listeners for placeholder text
        setupFieldListener(receiverAccountTextField, receiverLabel, receiverText);
        setupFieldListener(amountTextField, amountLabel, amountText);

        // Amount field with currency symbol
        JPanel amountDisplay = createAmountDisplayPanel(amountTextField);

        // Assemble form layout
        addComponentToPanel(panel, gbc, remainAmountTitle, 0, 0.01);
        addComponentToPanel(panel, gbc, receiverLabel, 1, 0.01);
        addComponentToPanel(panel, gbc, receiverAccountTextField, 2, 0.01);
        addComponentToPanel(panel, gbc, amountLabel, 3, 0.01);
        addComponentToPanel(panel, gbc, amountDisplay, 4, 0.01);

        // Keypad instructions
        JLabel instructionLabel = createStyledLabel("Use keypad to input numbers. Press Confirm to proceed.",
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                StandardColor.Blue.getColorMode());
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 5;
        gbc.weighty = 0.01;
        panel.add(instructionLabel, gbc);

        // Initialize field highlighting
        isReceiverFieldActive = true;
        highlightActiveField();

        return panel;
    }

    /**
     * Creates the transfer confirmation screen with transaction details.
     *
     * @param title        Screen title
     * @param firstSelection Confirm button text
     * @param secondSelection Cancel button text
     * @param fontSize     Button font size
     * @param font         Title font
     * @return Configured confirmation panel
     */
    public JPanel createConfirmationStep(String title, String firstSelection, String secondSelection,
                                         int fontSize, Font font) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        // Transaction details display
        String confirmationMessage = String.format("Transfer HK$ %.2f to account: %d",
                transfer.getAmount(), transfer.getReceiverAccounts());
        JLabel confirmationLabel = createStyledLabel(confirmationMessage, ATMUI.FONT_SMALL,
                StandardColor.GreyHighest.getOppositeColorMode());
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        panel.add(confirmationLabel, gbc);

        // Action buttons
        JPanel selectionMenu = createSelectionMenu(title, firstSelection, secondSelection,
                fontSize, font, CARD_AFTER_TRANSACTION, CARD_MENU, gbc);
        selectionMenu.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        gbc.gridy = 1;
        gbc.weighty = 0.4;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(selectionMenu, gbc);

        // Configure button actions
        RoundedButton confirmButton = getButtonByName(selectionMenu, BUTTON_FIRST);
        confirmButton.addActionListener(e -> {
            executeTransfer();
            updateBalanceInquiry(userAccountNumberInUI);
        });

        RoundedButton cancelButton = getButtonByName(selectionMenu, BUTTON_SECOND);
        cancelButton.addActionListener(e -> {
            showCard(CARD_MENU);
            resetToInitialState();
        });

        return panel;
    }
    /**
     * Creates the post-transaction summary screen.
     *
     * @param transactionInformation Transaction summary text
     * @param font                   Text font
     * @return Configured summary panel
     */
    public JPanel createAfterTransaction(String transactionInformation, Font font) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        // Transaction result display
        JLabel transactionInfoLabel = createStyledLabel(transactionInformation, font,
                StandardColor.GreyHighest.getOppositeColorMode());
        JLabel successLabel = createStyledLabel("Transfer completed successfully.", font,
                StandardColor.GreyHighest.getOppositeColorMode());

        addComponentToPanel(panel, gbc, transactionInfoLabel, 0, 0.1);
        addComponentToPanel(panel, gbc, successLabel, 1, 0.1);

        // Post-transaction options
        gbc.insets = new Insets(0, 0, 0, 0);
        JPanel continueMenu = createSelectionMenu("Next Action",
                "Make another transfer",
                "Return to main menu",
                35, font, CARD_INFO, "mainMenu", gbc);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        gbc.gridy = 2;
        gbc.weighty = 0.8;
        panel.add(continueMenu, gbc);

        return panel;
    }
}