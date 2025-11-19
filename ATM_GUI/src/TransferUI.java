import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Transfer User Interface - Handles the GUI for money transfer operations
 * Provides a multi-step form for transferring funds between accounts
 */
public class TransferUI extends Transfer {
    // Constants for card names
    private static final String CARD_MENU = "MENU";
    private static final String CARD_INFO = "INFO";
    private static final String CARD_CONFIRMATION = "CONFIRMATION";
    private static final String CARD_AFTER_TRANSACTION = "AFTER_TRANSACTION";

    // Constants for button names
    private static final String BUTTON_FIRST = "FIRST_BUTTON";
    private static final String BUTTON_SECOND = "SECOND_BUTTON";
    private static final String AMOUNT_LABEL = "AMOUNT";
    private static final String RECEIVER_LABEL = "RECEIVER";
    private static final String AMOUNT_TEXTFIELD = "RECEIVER_TEXTFIELDA";
    private static final String amountText = "Amount";
    private static final String receiverText = "Receiver account";

//    private TextFields receiverAccountTextField;
//    private TextFields amountTextField;
//    private JLabel receiverLabel;
//    private JLabel amountLabel;

    // Layout components
    private static CardLayout cardLayout;
    private static JPanel cardPanel;
    private static JPanel mainPanel;

    // Validation states
    private Boolean isReceiverValid = false;
    private Boolean isAmountValid = false;

    BankDatabase bankDatabase = getBankDatabase();

    public TransferUI(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase,
                      Keypad atmKeypad, CashDispenser atmCashDispenser) {
        super(userAccountNumber, atmScreen, atmBankDatabase, atmKeypad, atmCashDispenser);
    }

    /**
     * Recursively searches for a button by name within a panel hierarchy
     */
    public RoundedButton getButtonByName(JPanel panel, String buttonName) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                RoundedButton found = getButtonByName((JPanel) comp, buttonName);
                if (found != null) return found;
            } else if (comp instanceof RoundedButton && buttonName.equals(comp.getName())) {
                return (RoundedButton) comp;
            }
        }
        return null;
    }

    /**
     * Navigates back to the main menu panel
     */

    /**
     * Creates a standardized selection menu with two options
     */
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

    public JPanel createSelectionMenu(String title, String firstSelection, String secondSelection,
                                      int fontSize, Font font, String nextPage) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        // Create selection buttons
        RoundedButton selectionOneBtn = createMenuButton(firstSelection, StandardColor.Green.getColorMode(), fontSize);
        RoundedButton selectionTwoBtn = createMenuButton(secondSelection, StandardColor.Yellow.getColorMode(), fontSize);

        selectionOneBtn.setName(BUTTON_FIRST);
        selectionTwoBtn.setName(BUTTON_SECOND);

        selectionOneBtn.addActionListener(e -> showCard(nextPage));
        selectionTwoBtn.addActionListener(e -> {
            showCard(nextPage);
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

        return panel;
    }

    /**
     * Creates the receiver account and amount input form
     */
    public JPanel createReceiveTransferInformation(String remainAmount) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create UI components
        JLabel remainAmountTitle = createStyledLabel(remainAmount, FONT_SMALL, StandardColor.GreyHighest.getOppositeColorMode());
        RoundedButton confirmationButton = createActionButton("Confirm", StandardColor.Green.getColorMode());
        RoundedButton backButton = createActionButton("Back", StandardColor.Yellow.getColorMode());

        TextFields receiverAccountTextField = createInputField(40);
        TextFields amountTextField = createInputField(40);
        amountTextField.setName(AMOUNT_TEXTFIELD);

        JLabel receiverLabel = createStyledLabel(receiverText, FONT_NORMAL, StandardColor.GreyHighest.getOppositeColorMode());
        receiverLabel.setName(RECEIVER_LABEL);
        JLabel amountLabel = createStyledLabel(amountText, FONT_NORMAL, StandardColor.GreyHighest.getOppositeColorMode());
        amountLabel.setName(AMOUNT_LABEL);

        setupFieldListener(receiverAccountTextField, receiverLabel, receiverText);
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

        // Add buttons
        JPanel buttonPanel = createButtonPanel(backButton, confirmationButton);
        setupConfirmationButtonListener(confirmationButton, receiverAccountTextField, amountTextField,
                receiverLabel, amountLabel);
        backButton.addActionListener(e -> showCard(CARD_MENU));

        gbc.gridy = 5;
        gbc.weighty = 0.01;
        gbc.insets = new Insets(30, 10, 10, 10);
        panel.add(buttonPanel, gbc);

        return panel;
    }

    /**
     * Creates the transfer confirmation screen
     */
    public JPanel createConfirmationStep(String title, String firstSelection, String secondSelection,
                                         int fontSize, Font font) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();

        // Create confirmation message
        String confirmationMessage = String.format("The system is going to transfer %.2f to account number: %d",
                getAmount(), getReceiverAccounts());
        JLabel confirmationLabel = createStyledLabel(confirmationMessage, FONT_SMALL,
                StandardColor.GreyHighest.getOppositeColorMode());

        gbc.weighty = 0.0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 15, 10, 15);
        panel.add(confirmationLabel, gbc);

        // Create selection menu
        JPanel selectionMenu = createSelectionMenu(title, firstSelection, secondSelection,
                fontSize, font, CARD_AFTER_TRANSACTION);
        selectionMenu.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        gbc.weighty = 0.4;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(selectionMenu, gbc);

        // Add transfer action to confirmation button
        RoundedButton confirmButton = getButtonByName(selectionMenu, BUTTON_FIRST);
        confirmButton.addActionListener(e -> executeTransfer());

        return panel;
    }

    public void resetToInitialState() {
        cardLayout.show(cardPanel, "MENU");
        resetValidationFlags();
    }
    /**
     * Creates the post-transfer completion screen
     */
    public JPanel createAfterTransaction(String transactionInformation, Font font) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();

        JLabel transactionInfoLabel = createStyledLabel(transactionInformation, font,
                StandardColor.GreyHighest.getOppositeColorMode());
        JLabel successLabel = createStyledLabel("The transfer successfully.", font,
                StandardColor.GreyHighest.getOppositeColorMode());

        panel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        addComponentToPanel(panel, gbc, transactionInfoLabel, 0, 0.1);
        addComponentToPanel(panel, gbc, successLabel, 1, 0.1);

        JPanel continueMenu = createSelectionMenu("Do you want transfer to another?",
                "Yes, go back transfer", "No, go back ATM menu",
                30, font, CARD_INFO);
        gbc.gridy = 2;
        gbc.weighty = 0.8;
        panel.add(continueMenu, gbc);
        resetToInitialState();

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
     * Creates a styled action button (Confirm, Back, etc.)
     */
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
     * Creates an input text field with consistent styling
     */
    private TextFields createInputField(int fontSize) {
        return new TextFields(200, 30,
                StandardColor.GreyHighest.getColor(0),
                StandardColor.Blue.getColor(0),
                StandardColor.GreyHighest.getColor(1),
                new Font(Font.SANS_SERIF, Font.BOLD, fontSize));
    }

    /**
     * Creates the amount display panel with currency symbol
     */
    private JPanel createAmountDisplayPanel(TextFields amountTextField) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        TextFields currencySymbol = createInputField(40);
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
    private JPanel createButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout());
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }

    private void setupFieldListener(TextFields textField, JLabel label, String content){
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                label.setText(content);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
    }
    /**
     * Sets up the confirmation button validation and action logic
     */
    private void setupConfirmationButtonListener(RoundedButton confirmationButton,
                                                 TextFields receiverAccountTextField,
                                                 TextFields amountTextField,
                                                 JLabel receiverLabel,
                                                 JLabel amountLabel) {
        confirmationButton.addActionListener(e -> {
            resetValidationFlags();

            boolean isReceiverValid = validateReceiverAccount(
                    receiverAccountTextField.getContent().trim(), receiverAccountTextField, receiverLabel);
            boolean isAmountValid = validateAmount(
                    amountTextField.getContent().trim(), amountTextField, amountLabel);

            if (super.isLimitAccountConditionCheckerHappen(getAmount(),"Transfer")){
                showValidationError(amountTextField, amountLabel, String.format("Sorry, You are not allowed to %s over HK$50000 at once time.","Transfer"));
            }

            if (isReceiverValid && isAmountValid) {
                showCard(CARD_CONFIRMATION);
                clearInputFields(receiverAccountTextField, amountTextField);
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

            if (super.getAccountNumber() == accountNumber) {
                showValidationError(textField, label, "The send account and receiver account can not be same.");
                return false;
            }

            setReceiverAccounts(accountNumber);
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

            if (!isTwoDecimalOnly(amountValue)) {
                showValidationError(textField, label, "Only two decimal amount is maximum allowed");
                return false;
            }

            setAmount(amountValue);
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
    private void clearInputFields(TextFields field1, TextFields field2) {
        if (field1 != null) field1.setText("");
        if (field2 != null) field2.setText("");
    }

    /**
     * Executes the actual transfer operation
     */
    private void executeTransfer() {
        bankDatabase.transfer(getAccountNumber(), getReceiverAccounts(), getAmount());
        System.out.println("Remaining balance: " + bankDatabase.getAvailableBalance(getAccountNumber()));
    }

    /**
     * Switches between different card views with dynamic creation
     */
    private void showCard(String cardName) {
        recreateCardIfNeeded(cardName);
        cardLayout.show(cardPanel, cardName);
    }

    /**
     * Recreates card panels when needed to ensure fresh state
     */
    private void recreateCardIfNeeded(String cardName) {
        removeExistingCard(cardName);

        switch (cardName) {
            case CARD_INFO:
                JPanel infoCard = createReceiveTransferInformation(
                        "Currently asset in your account HKD$" + bankDatabase.getAvailableBalance(getAccountNumber()));
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
                        getAmount(), getReceiverAccounts());
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
    public JPanel transferLayout() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(StandardColor.GreyHighest.getColorMode());

        GridBagConstraints gbc = createDefaultGridBagConstraints();

        JLabel taskTitle = createStyledLabel("Transfer", FONT_TITLE_LARGE, StandardColor.Blue.getColorMode());
        taskTitle.setHorizontalAlignment(SwingConstants.LEFT);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel menuCard = createSelectionMenu("Menu", "1 - Input receiver account number", "2 - Exit", 30,
                new Font(Font.SANS_SERIF, Font.PLAIN, 30), CARD_INFO);

        cardPanel.add(menuCard, CARD_MENU);

        addComponentToPanel(mainPanel, gbc, taskTitle, 0, 0.1);
        gbc.gridy = 1;
        gbc.weighty = 0.9;
        mainPanel.add(cardPanel, gbc);

        cardLayout.show(cardPanel, CARD_MENU);
        return mainPanel;
    }
}