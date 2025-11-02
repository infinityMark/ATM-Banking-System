// Transfer.java
// Represents a transfer ATM transaction
import java.math.BigDecimal;
import java.util.InputMismatchException;
import javax.swing.*;
import java.awt.*;

public class Transfer extends Transaction {
    private double amount; // amount to transfer
    private Keypad keypad; // reference to keypad
    private int receiverAccount;
    private final static int CANCELED = 2;

    private JFrame mainFrame;
    private JPanel functionPanel;
    private JPanel keypadPanel;

    public Transfer(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase,
            Keypad atmKeypad, CashDispenser atmCashDispenser) {
        // initialize superclass variables
        super(userAccountNumber, atmScreen, atmBankDatabase);
        // initialize references to keypad and cash dispenser
        keypad = atmKeypad;
    } // end Transfer constructor

    @Override
    public void execute() {
        boolean transferEnd = false; // cash was not dispensed yet
        double availableBalance; // amount available for transfer
        int userSelection; // function of user's selection
        int[] remarkSelection = new int[2];

        // get references to bank database and screen
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        // loop until cash is dispensed or the user cancels
        do {
            // obtain a chosen transfer function from the user
            userSelection = displayMenu(
                    "\nTransfer Menu:", "1 - Input receiver account number", "2 - Exit",
                    "\nChoose a function: ", "\nInvalid selection. Try again.", "", "");

            // check whether user chose a transferor canceled
            if (userSelection == CANCELED) {
                screen.displayMessageLine("\nCanceling transaction...");
                return; // return to main menu because user canceled
            } // end else

            screen.displayCurrentRemaining(bankDatabase.getAvailableBalance(getAccountNumber()));

            // get available balance of account involved
            availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());
            int accountVarify = getReceiverAccount();

            // Since the getReceiverAccount() return
            if (accountVarify == -1 || accountVarify == 0) {
                keypad.clearBuffer();
                // The loop will skip once time, to let user enters value again.
                continue;
            }

            // check whether the user has enough money in the account
            if (amount > availableBalance) {
                screen.displayMessageLine(
                        "\nInsufficient amounts in your account.\n" + "\n\nPlease choose a smaller amount.\n");
                continue;
            }
            // If limit condition happen by limit account type, then the system will pop up
            // warning and return to transfer menu

            if (isLimitAccountConditionCheckerHappen(amount, "transfer"))
                continue;

            // System shows the receiver account and transfer amount, and let user confirm
            // to transfer or not
            System.out.printf("\nThe system is going to transfer %.2f to account number: %d\n", amount,
                    receiverAccount);
            if (displayMenu(
                    "\nTransfer Confirmation Operation:", "1 - Confirm the transfer", "2 - Cancel the transfer",
                    "\nPlease choose the action: ", "\nInvalid selection. Try again.", "\nThe transfer continue.",
                    "\nThe transfer suspend.") != 1)
                continue;

            // System hold transfer action
            bankDatabase.transfer(getAccountNumber(), receiverAccount, amount);

            // Indicate transfer detail, the receiver and amount of transfer.
            screen.displayTransferMessageLine(amount, receiverAccount);
            screen.displayMessageLine("\nThe transfer action ends.\n");
            transferEnd = true;

            // Ask whether user want to make a remark of this transfer for user's help
            screen.displayMessageLine("Do you want to make a remark for the transfer?");
            screen.displayMessageLine("Input 1 for yes, input any number on the keypad for not.\n");

            int remark = keypad.getSelection();

            // If user choose not to make a remark, show user remain account amount, then
            // return to ATM menu
            if (remark != 1) {
                // Show current account asset.
                screen.displayMessageLine(screen.amountsDisplay(getAccountNumber(),
                        bankDatabase.getAvailableBalance(getAccountNumber())));

                // Write the transfer action into transaction history.
                new TransactionHistory(0, getAccountNumber(), receiverAccount, remarkSelection[0], remarkSelection[1],
                        amount);
                screen.displayMessageLine("\nReturning to ATM menu.");
                continue;
            }

            // System show available remark option.
            TransferRemark.transferRemarkFullOption();
            screen.displayMessage("Please select major category that you want: ");
            remark = keypad.getSelection();

            if (remark >= 1 && remark <= 9) {
                remarkSelection[0] = remark;

                screen.displayMessageLine("\n");

                // Show detail remark serious under major remark type
                TransferRemark.transferRemarkOptionDetail(remark);
                screen.displayMessage("Select sub category that you want: ");
                remark = keypad.getSelection();

                // Limit user's input
                if (remark >= 1 && remark <= 3)
                    remarkSelection[1] = remark;
                else
                    screen.displayMessageLine("Out of selection, system will return to ATM menu");
            } else {
                screen.displayMessageLine("Out of selection, system will return to ATM menu");
            }

            // Write the transfer action into transaction history.
            new TransactionHistory(0, getAccountNumber(), receiverAccount, remarkSelection[0], remarkSelection[1],
                    amount);

            if (transferEnd)
                screen.displayMessageLine(screen.amountsDisplay(getAccountNumber(),
                        bankDatabase.getAvailableBalance(getAccountNumber())));

            if (remarkSelection[0] != 0)
                TransferRemark.displayRemarkSelection("Selected Remark:", remarkSelection);

        } while (!transferEnd);
    } // end method execute

    // Add
    // To check whether the transfer account is same with receiver account or not
    public boolean isSameAccount(int aimTransferAccount) {
        return super.getAccountNumber() == aimTransferAccount;
    }

    // Add
    // To check whether user input correct format of account.
    private double varifyAmount(Screen screen) {
        // This try-catch function aim to obtain user's input of amount, to do verify
        // checking
        try {
            screen.displayMessage("\nPlease enter transfer amount: ");
            return keypad.getDoubleInput();
        } catch (InputMismatchException e) {
            screen.displayMessageLine("Invalid input! Please enter a valid amount.");
            return -1;
        }
    }

    // Add
    private boolean isTwoDecimalOnly(double predictAmount) {
        // This try-catch function aim to cope when user do not input correct amount
        // format, like ...10
        try {
            BigDecimal amount = BigDecimal.valueOf(predictAmount);
            String amountStr = amount.toPlainString();
            int decimalIndex = amountStr.indexOf('.');

            if (decimalIndex == -1)
                return true;

            int decimalPlaces = amountStr.length() - decimalIndex - 1;
            return decimalPlaces <= 2;
        } catch (Exception e) {
            return false;
        }
    }

    // Add
    public int getReceiverAccount() {
        Screen screen = getScreen();

        // The variable will temporarily store the number of receiver account number.
        int receiverAccountNumber;

        // Let user inputs the receiver account, with a valid check.
        receiverAccountNumber = keypad.getReceiverAccountNumber(screen, "\nPlease enter receiver account number: ",
                "\nInvalid input! Please enter a valid account number.");

        // Let user inputs the transfer amount, with a valid check.
        amount = varifyAmount(screen);

        // If amount value become -1, it indicates user input invalid value such as
        // 990.. .
        if (!isTwoDecimalOnly(amount)) {
            screen.displayMessageLine("Only two decimal amount is allowed");
            return -1;
        }

        if (amount == -1)
            return -1;

        // Even user input valid amount format, it will still check whether the amount
        // is greater than 0 or not.
        if (amount <= 0) {
            screen.displayMessageLine("The amount can not be below HK$1.");
            return -1;
        }

        // Request to get the bank database.
        BankDatabase bankDatabase = getBankDatabase();

        // Check whether user entered receiver account is same currently account number.
        if (isSameAccount(receiverAccountNumber)) {
            screen.displayMessageLine("The send account and receiver account can not be same.");
            return -1;
        }

        // Check whether the account number really exists or not.
        if (bankDatabase.isAccountNumberExist(receiverAccountNumber) != -1) {
            receiverAccount = receiverAccountNumber;
            return 1;
        } else {
            screen.displayMessageLine("The account is not does not exist.\n");
            return 0;
        }
    }

    private int displayMenu(String line1, String line2, String line3, String line4, String ErrorMessage,
            String innerMessage1, String innerMessage2) {
        int userChoice = 0; // local variable to store return value
        Screen screen = getScreen(); // get screen reference
        // loop while no valid choice has been made
        while (userChoice == 0) {
            // display the menu
            screen.displayMessageLine(line1);
            screen.displayMessageLine(line2);
            screen.displayMessageLine(line3);
            screen.displayMessage(line4);

            int input = keypad.getSelection(); // get user input through keypad

            // determine how to proceed based on the input value
            if (input == 1) {
                userChoice = input;
                System.out.print(innerMessage1);
            } else if (input == CANCELED) {
                userChoice = CANCELED; // save user's choice
                System.out.print(innerMessage2);
            } else
                screen.displayMessageLine(ErrorMessage);
        } // end while
        return userChoice; // return transfer or CANCELED
    } // end method displayMenuOfAmounts
} // end class Transfer