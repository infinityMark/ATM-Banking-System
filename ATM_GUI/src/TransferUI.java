import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransferUI extends Transfer {
    private static CardLayout cardLayout;
    private static JPanel cardPanel;
    private static JPanel mainPanel;
    private Boolean receiverAccountTextFieldChecker = false;
    private Boolean amountTextFieldChecker = false;

    BankDatabase bankDatabase = getBankDatabase();

    public TransferUI(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase,
                      Keypad atmKeypad, CashDispenser atmCashDispenser){
        super(userAccountNumber,atmScreen,atmBankDatabase,
                atmKeypad,atmCashDispenser);
    }

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

    public JPanel getSelectionMenu(String title, String firstSelection, String secondSelection, int fontSize, Font font, String NextPage){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 15, 10, 15);

        RoundedButton selectionOneBtn = new RoundedButton(firstSelection, firstSelection,
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.Green.getColorMode(),
                StandardColor.GreyHighest.getOppositeColorMode(),
                StandardColor.GreyHighest.getColorMode(),
                new Font(Font.SANS_SERIF, Font.PLAIN, fontSize),
                new Font(Font.SANS_SERIF, Font.PLAIN, fontSize),
                true, 200, 10);
        selectionOneBtn.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        selectionOneBtn.setHorizontalAlignment(SwingConstants.LEFT);

        RoundedButton selectionTwoBtn = new RoundedButton(secondSelection, secondSelection,
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.Yellow.getColorMode(),
                StandardColor.GreyHighest.getOppositeColorMode(),
                StandardColor.GreyHighest.getOppositeColorMode(),
                new Font(Font.SANS_SERIF, Font.PLAIN, fontSize),
                new Font(Font.SANS_SERIF, Font.PLAIN, fontSize),
                true, 200, 10);
        selectionTwoBtn.setHorizontalAlignment(SwingConstants.LEFT);

        selectionOneBtn.setName("FIRST_BUTTON");
        selectionTwoBtn.setName("SECOND_BUTTON");

        selectionOneBtn.addActionListener(e -> showCard(NextPage));
        selectionTwoBtn.addActionListener(e -> goBackToMainPanel());

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel menuTitle = new JLabel(title);
        menuTitle.setFont(font);
        menuTitle.setForeground(StandardColor.GreyHighest.getOppositeColorMode());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(10, 15, 10, 15);
        panel.add(menuTitle, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        buttonPanel.add(selectionOneBtn);
        buttonPanel.add(selectionTwoBtn);

        gbc.gridy = 1;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private void clearInputFields(TextFields a, TextFields b) {
        if (a != null) {
            a.setText("");
        }
        if (b != null) {
            b.setText("");
        }
    }

    public JPanel receiveTransferInformation(String remainAmount){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.BOTH;

        JLabel remainAmountTitle = new JLabel(remainAmount);
        remainAmountTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        remainAmountTitle.setForeground(StandardColor.GreyHighest.getOppositeColorMode());

        RoundedButton confirmationButton = new RoundedButton("Confirm", "Confirm",
                StandardColor.Green.getColorMode(),
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.GreyHighest.getOppositeColorMode(),
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                true, 200, 10);

        RoundedButton backButton = new RoundedButton("Back", "Back",
                StandardColor.Yellow.getColorMode(),
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.GreyHighest.getOppositeColorMode(),
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                true, 200, 10);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        TextFields receiverAccountTextField = new TextFields(200, 30,
                StandardColor.GreyHighest.getColor(0),
                StandardColor.Blue.getColor(0),
                StandardColor.GreyHighest.getColor(1),new Font(Font.SANS_SERIF, Font.BOLD, 40));

        TextFields amountTextField = new TextFields(200, 30,
                StandardColor.GreyHighest.getColor(0),
                StandardColor.Blue.getColor(0),
                StandardColor.GreyHighest.getColor(1),new Font(Font.SANS_SERIF, Font.BOLD, 40));

        String receiver = "Receiver account";
        JLabel receiverLabel = new JLabel(receiver);
        receiverLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

        String amount = "Amount";
        JLabel amountLabel = new JLabel(amount);
        amountLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

        TextFields amountSy = new TextFields(200, 30,
                StandardColor.GreyHighest.getColor(0),
                StandardColor.Blue.getColor(0),
                StandardColor.GreyHighest.getColor(1),new Font(Font.SANS_SERIF, Font.BOLD, 40));
        amountSy.setText("HK$ ");
        amountSy.setEnabled(false);

        JPanel amountDisplay = new JPanel(new GridBagLayout());
        GridBagConstraints gbcAmountDisplay = new GridBagConstraints();
        gbcAmountDisplay.gridy = 0;
        gbcAmountDisplay.gridx = 0;
        gbcAmountDisplay.weighty = 1.0;
        gbcAmountDisplay.weightx = 0.001;
        gbcAmountDisplay.insets = new Insets(0,5,0,5);
        gbcAmountDisplay.fill = GridBagConstraints.BOTH;
        amountDisplay.add(amountSy,gbcAmountDisplay);
        gbcAmountDisplay.weightx = 0.9;
        gbcAmountDisplay.gridx = 1;
        amountDisplay.add(amountTextField,gbcAmountDisplay);

        gbc.gridy = 0;
        gbc.weighty = 0.01;
        panel.add(remainAmountTitle,gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.01;
        gbc.insets = new Insets(0,10,0,10);
        panel.add(receiverLabel,gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.01;
        gbc.insets = new Insets(0,10,10,10);
        panel.add(receiverAccountTextField,gbc);

        gbc.gridy = 3;
        gbc.weighty = 0.01;
        gbc.insets = new Insets(0,10,0,10);
        panel.add(amountLabel,gbc);

        gbc.gridy = 4;
        gbc.weighty = 0.01;
        gbc.insets = new Insets(0,10,10,10);
        panel.add(amountDisplay,gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(backButton);
        buttonPanel.add(confirmationButton);

        confirmationButton.addActionListener(e -> {
            receiverAccountTextFieldChecker = false;
            amountTextFieldChecker = false;

            String accountText = receiverAccountTextField.getContent().trim();
            if (accountText.isEmpty()) {
                receiverAccountTextField.warning();
                receiverLabel.setText(receiver + " | Please enter account number");
                return;
            }

            try {
                int accountNumber = Integer.parseInt(accountText);

                if (bankDatabase.isAccountNumberExist(accountNumber) == -1) {
                    receiverAccountTextField.warning();
                    receiverLabel.setText(receiver + " | The receiver account does not exist");
                    receiverAccountTextFieldChecker = false;
                } else if (super.getAccountNumber() == accountNumber) {
                    receiverAccountTextField.warning();
                    receiverLabel.setText(receiver + " | The send account and receiver account can not be same.");
                    receiverAccountTextFieldChecker = false;
                } else {
                    setReceiverAccounts(accountNumber);
                    receiverAccountTextFieldChecker = true;
                    setReceiverAccounts(accountNumber);
                    receiverLabel.setText(receiver);
                }
            } catch (NumberFormatException ex) {
                receiverAccountTextField.warning();
                receiverLabel.setText(receiver + " | Please enter a valid account number");
                receiverAccountTextFieldChecker = false;
            }

            String amountText = amountTextField.getContent().trim();
            if (amountText.isEmpty()) {
                amountTextField.warning();
                amountLabel.setText(amount + " | Please enter amount");
                return;
            }

            try {
                double amountValue = Double.parseDouble(amountText);

                if (!isTwoDecimalOnly(amountValue)) {
                    amountTextField.warning();
                    amountLabel.setText(amount + " | Only two decimal amount is maximum allowed");
                    amountTextFieldChecker = false;
                } else {
                    setAmount(amountValue);
                    amountTextFieldChecker = true;
                    amountLabel.setText(amount);
                }
            } catch (NumberFormatException ex) {
                amountTextField.warning();
                amountLabel.setText(amount + " | Please enter a valid amount");
                amountTextFieldChecker = false;
            }

            if (receiverAccountTextFieldChecker && amountTextFieldChecker) {
                showCard("CONFIRMATION");
                clearInputFields(receiverAccountTextField,amountTextField);
            }
        });

        backButton.addActionListener(e -> showCard("MENU"));

        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 0.01;
        gbc.insets = new Insets(30,10,10,10);
        panel.add(buttonPanel, gbc);

        return panel;
    }

    public JPanel confirmationStep(String title, String firstSelection, String secondSelection, int fontSize, Font font){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;

        String previous = "The system is going to transfer " + getAmount() + " to account number: " + getReceiverAccounts();
        JLabel previousLabel = new JLabel();
        previousLabel.setText(previous);
        previousLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        previousLabel.setForeground(StandardColor.GreyHighest.getOppositeColorMode());

        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 15, 10, 15);
        panel.add(previousLabel, gbc);

        gbc.weighty = 0.4;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        JPanel selectionMenu = getSelectionMenu(title,firstSelection,secondSelection,fontSize,font,"AFTER_TRANSACTION");
        selectionMenu.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        panel.add(selectionMenu, gbc);

        RoundedButton firstButton = getButtonByName(selectionMenu, "FIRST_BUTTON");
        firstButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bankDatabase.transfer(getAccountNumber(), getReceiverAccounts(), getAmount());
                System.out.println(bankDatabase.getAvailableBalance(getAccountNumber()));
            }
        });

        return panel;
    }

    public JPanel afterTransaction(String transactionInformation, Font font){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        JLabel transactionInformationLabel = new JLabel(transactionInformation);
        transactionInformationLabel.setFont(font);
        transactionInformationLabel.setForeground(StandardColor.Green.getColorMode());
        transactionInformationLabel.setForeground(StandardColor.GreyHighest.getOppositeColorMode());

        JLabel endTransaction = new JLabel("The transfer successfully.");
        endTransaction.setFont(font);
        endTransaction.setForeground(StandardColor.GreyHighest.getOppositeColorMode());

        panel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        gbc.weighty = 0.1;
        panel.add(transactionInformationLabel, gbc);
        gbc.gridy = 1;
        gbc.weighty = 0.1;
        panel.add(endTransaction, gbc);
        gbc.gridy = 2;
        gbc.weighty = 0.8;
        panel.add(getSelectionMenu("Do you want transfer to another?","Yes, go back transfer","No, go back ATM menu", 30, font, "INFO"), gbc);
        return panel;
    }

    private void showCard(String cardName) {
        if ("INFO".equals(cardName)) {
            Component[] components = cardPanel.getComponents();
            for (Component comp : components) {
                if ("INFO".equals(((JPanel)comp).getName())) {
                    cardPanel.remove(comp);
                    break;
                }
            }

            JPanel infoCard = receiveTransferInformation("Currently asset in your account HKD$"+bankDatabase.getAvailableBalance(getAccountNumber()));
            infoCard.setName("INFO");
            cardPanel.add(infoCard, "INFO");
        }

        if ("CONFIRMATION".equals(cardName)) {
            Component[] components = cardPanel.getComponents();
            for (Component comp : components) {
                if ("CONFIRMATION".equals(((JPanel)comp).getName())) {
                    cardPanel.remove(comp);
                    break;
                }
            }

            JPanel confirmationCard = confirmationStep(
                    "Transfer Confirmation Operation:", "1- Confirm the transfer", "2-Cancel the transfer", 40,
                    new Font(Font.SANS_SERIF, Font.PLAIN, 30));
            confirmationCard.setName("CONFIRMATION");
            cardPanel.add(confirmationCard, "CONFIRMATION");
        }

        if ("AFTER_TRANSACTION".equals(cardName)) {
            Component[] components = cardPanel.getComponents();
            for (Component comp : components) {
                if ("AFTER_TRANSACTION".equals(((JPanel)comp).getName())) {
                    cardPanel.remove(comp);
                    break;
                }
            }

            String transactionInfo = "Total HK$ " + getAmount() + " transfers to account " + getReceiverAccounts() + ".";
            JPanel afterTransactionCard = afterTransaction(transactionInfo,
                    new Font(Font.SANS_SERIF, Font.PLAIN, 30));
            afterTransactionCard.setName("AFTER_TRANSACTION");
            cardPanel.add(afterTransactionCard, "AFTER_TRANSACTION");
        }

        cardLayout.show(cardPanel, cardName);
    }

    public JPanel transferLayout(){
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(StandardColor.GreyHighest.getColorMode());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;

        JLabel taskTitle = new JLabel("Transfer");
        taskTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        taskTitle.setForeground(StandardColor.Blue.getColorMode());
        taskTitle.setHorizontalAlignment(SwingConstants.LEFT);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel menuCard = getSelectionMenu("Menu", "1 - Input receiver account number", "2 - Exit", 30,
                new Font(Font.SANS_SERIF, Font.PLAIN, 30), "INFO");

        cardPanel.add(menuCard, "MENU");

        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        mainPanel.add(taskTitle, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.9;
        mainPanel.add(cardPanel, gbc);

        return mainPanel;
    }
}