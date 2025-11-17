import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Scanner;

public class TransferUI extends Transfer {
    private static CardLayout cardLayout;
    private static JPanel cardPanel;
    private static JPanel mainPanel;
    private Boolean receiverAccountTextFieldChecker = false;
    private Boolean amountTextFieldChecker = false;
    private int r;
    private double a;

    BankDatabase bankDatabase = getBankDatabase();

    public TransferUI(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase,
                      Keypad atmKeypad, CashDispenser atmCashDispenser){
        super(userAccountNumber,atmScreen,atmBankDatabase,
                atmKeypad,atmCashDispenser);
    }

//    public RoundedButton getButton(JPanel panel){
//        return panel
//    }

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

        selectionOneBtn.addActionListener(e -> showCard(NextPage));
        selectionTwoBtn.addActionListener(e -> System.exit(0)); // 退出

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
                r = accountNumber;

                if (bankDatabase.isAccountNumberExist(accountNumber) == -1) {
                    receiverAccountTextField.warning();
                    receiverLabel.setText(receiver + " | The receiver account does not exist");
                } else if (super.getAccountNumber() == accountNumber) {
                    receiverAccountTextField.warning();
                    receiverLabel.setText(receiver + " | The send account and receiver account can not be same.");
                } else {
                    setReceiverAccounts(accountNumber);
                    receiverAccountTextFieldChecker = true;
                    receiverLabel.setText(receiver);
                }
            } catch (NumberFormatException ex) {
                receiverAccountTextField.warning();
                receiverLabel.setText(receiver + " | Please enter a valid account number");
            }

            String amountText = amountTextField.getContent().trim();
            if (amountText.isEmpty()) {
                amountTextField.warning();
                amountLabel.setText(amount + " | Please enter amount");
                return;
            }

            try {
                double amountValue = Double.parseDouble(amountText);
                a = amountValue;

                if (!isTwoDecimalOnly(amountValue)) {
                    amountTextField.warning();
                    amountLabel.setText(amount + " | Only two decimal amount is maximum allowed");
                } else {
                    setAmount(amountValue);
                    amountTextFieldChecker = true;
                    amountLabel.setText(amount); // 清除错误信息
                }
            } catch (NumberFormatException ex) {
                amountTextField.warning();
                amountLabel.setText(amount + " | Please enter a valid amount");
            }

            // 所有验证通过后跳转
            if (receiverAccountTextFieldChecker && amountTextFieldChecker) {
                showCard("CONFIRMATION");
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

        String previous = "The system is going to transfer " + a + " to account number: " + r;
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

        gbc.gridy = 0;
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

    private  void showCard(String cardName) {
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

        JPanel infoCard = receiveTransferInformation("Currently asset in your account HKD$"+bankDatabase.getAvailableBalance(getAccountNumber()));

        JPanel confirmationCard = confirmationStep(
                "Transfer Confirmation Operation:", "1- Confirm the transfer", "2-Cancel the transfer", 40,
                new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        JPanel afterTransactionCard = afterTransaction("Total HK$ 33.00 transfers to account 21111.",
                new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        cardPanel.add(menuCard, "MENU");
        cardPanel.add(infoCard, "INFO");
        cardPanel.add(confirmationCard, "CONFIRMATION");
        cardPanel.add(afterTransactionCard, "AFTER_TRANSACTION");

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