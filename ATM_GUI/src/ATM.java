
// ATM.java
// Represents an automated teller machine
import javax.swing.*;
import java.awt.*;

public class ATM extends JFrame {
   private boolean userAuthenticated; // whether user is authenticated
   private int currentAccountNumber; // current user's account number
   private Screen screen; // ATM's screen
   private Keypad keypad; // ATM's keypad
   private CashDispenser cashDispenser; // ATM's cash dispenser

   private JFrame mainFrame;
   private CardLayout cardLayout;
   private JPanel functionPanel;
   private JPanel keypadPanel;

   // private DepositSlot depositSlot; // ATM's deposit slot
   private BankDatabase bankDatabase; // account information database

   // constants corresponding to main menu options
   private static final int BALANCE_INQUIRY = 1;
   private static final int WITHDRAWAL = 2;
   private static final int TRANSFER = 3;
   private static final int EXIT = 5;
   private static final int RECORD = 4;
   // private static final int DEPOSIT = 3;

   // no-argument ATM constructor initializes instance variables
   public ATM() {
      userAuthenticated = false; // user is not authenticated to start
      currentAccountNumber = 0; // no current account number to start
      screen = new Screen(); // create screen
      keypad = new Keypad(); // create keypad
      cashDispenser = new CashDispenser(); // create cash dispenser
      // depositSlot = new DepositSlot(); // create deposit slot
      bankDatabase = new BankDatabase(); // create acct info database

      mainFrame = new JFrame("ATM GUI implement");
      // ATM GUI implements to initial the mainFrame setting.
      initializeGUI();
   } // end no-argument ATM constructor

   private void initializeGUI() {
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // setting icon for window
      ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("resources/atm-machine.png"));
      setIconImage(icon.getImage());

      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      final double rate = 0.9;
      int screenWidth = (int) (screenSize.width * rate);
      int screenHeight = (int) (screenSize.height * rate);

      setSize(screenWidth, screenHeight);
      setLocationRelativeTo(null);

      setLayout(new BorderLayout());

      Transaction currentTransaction = createTransaction(3);

      functionPanel = currentTransaction.getPanelUI();
      // functionPanel =
      // createFunctionPanel((int)(screenWidth*0.7),screenHeight,"keypad");
      keypadPanel = createkeypadPanel((int) (screenWidth * 0.3), screenHeight, "keypad");

      add(functionPanel, BorderLayout.CENTER);
      add(keypadPanel, BorderLayout.EAST);

      setVisible(true);
   }

   // Temporary for test
   private JPanel createkeypadPanel(int width, int height, String methodName) {
      JPanel panel = new JPanel();
      panel.setBackground(StandardColor.Red.getColor(1));
      panel.setPreferredSize(new Dimension(width, height));

      return panel;
   }

   // Temporary for test
   private JPanel createFunctionPanel(int width, int height, String methodName) {
      JPanel panel = new JPanel();
      panel.setBackground(Color.PINK);
      panel.setPreferredSize(new Dimension(width, height));

      JLabel label = new JLabel(methodName);
      label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
      panel.add(label);

      return panel;
   }

   // start ATM
   public void run() {
      // welcome and authenticate user; perform transactions
      while (true) {
         // loop while user is not yet authenticated
         while (!userAuthenticated) {
            screen.displayMessageLine("\nWelcome!");
            authenticateUser(); // authenticate user
         } // end while

         performTransactions(); // user is now authenticated
         userAuthenticated = false; // reset before next ATM session
         currentAccountNumber = 0; // reset before next ATM session
         screen.displayMessageLine("\nThank you! Goodbye!");
      } // end while
   } // end method run

   // attempts to authenticate user against database
   private void authenticateUser() {
      screen.displayMessage("\nPlease enter your account number: ");
      int accountNumber = keypad.getInput(); // input account number
      screen.displayMessage("\nEnter your PIN: "); // prompt for PIN
      int pin = keypad.getInput(); // input PIN

      // set userAuthenticated to boolean value returned by database
      userAuthenticated = bankDatabase.authenticateUser(accountNumber, pin);

      // check whether authentication succeeded
      if (!userAuthenticated) {
         screen.displayMessageLine("Invalid account number or PIN. Please try again.");
      } // end if

      currentAccountNumber = accountNumber; // save user's account #
   } // end method authenticateUser

   // display the main menu and perform transactions
   private void performTransactions() {
      // local variable to store transaction currently being processed
      Transaction currentTransaction;
      boolean userExited = false; // user has not chosen to exit

      // loop while user has not chosen option to exit system
      while (!userExited) {
         // show main menu and get user selection
         int mainMenuSelection = displayMainMenu();

         // decide how to proceed based on user's menu selection
         switch (mainMenuSelection) {
            // user chose to perform one of three transaction types
            case BALANCE_INQUIRY:
            case WITHDRAWAL:
            case TRANSFER:
               // case DEPOSIT:
               // initialize as new object of chosen type
               currentTransaction = createTransaction(mainMenuSelection);
               currentTransaction.execute();
               // functionPanel = currentTransaction.getPanelUI(); // execute transaction
               break;
            case RECORD:
               TransactionHistory.checkHistory(currentAccountNumber);
               break;
            case EXIT: // user chose to terminate session
               screen.displayMessageLine("\nExiting the system...");
               userExited = true; // this ATM session should end
               break;
            default: // user did not enter an integer from 1-4
               screen.displayMessageLine("\nYou did not enter a valid selection. Try again.");
               break;
         } // end switch
      } // end while
   } // end method performTransactions

   // display the main menu and return an input selection
   private int displayMainMenu() {
      // get an account type for the account involved
      String accountType = bankDatabase.getAccountType(currentAccountNumber);

      screen.displaySymbolicLine('-', 40);
      screen.displayMessageLine("Today is " + screen.currentDay());
      screen.displayMessageLine("\nMain menu:");

      // display the Account type and it's interest rate or cheque limit
      screen.displayMessageLine(accountType);

      if (accountType.equals("Saving Account")) {
         double interestRate = bankDatabase.getRateOrLimit(currentAccountNumber);
         screen.displayMessageLine("Interest Rate: " + String.format("%.2f%%",
               interestRate * 100) + " per annum");
      }

      if (accountType.equals("Cheque Account")) {
         double chequeLimit = bankDatabase.getRateOrLimit(currentAccountNumber);
         screen.displayMessageLine("Limit per Cheque: " + String.format("%.2f",
               chequeLimit));
      }

      screen.displayMessageLine("\n1 - View my balance");
      screen.displayMessageLine("2 - Withdraw cash");
      screen.displayMessageLine("3 - Transfer funds");
      screen.displayMessageLine("4 - Show transaction history");
      screen.displayMessageLine("5 - Exit\n");
      screen.displaySymbolicLine('-', 40);
      screen.displayMessage("Enter a choice: ");
      return keypad.getInput(); // return user's selection
   } // end method displayMainMenu

   // return object of specified Transaction subclass
   private Transaction createTransaction(int type) {
      Transaction temp = null; // temporary Transaction variable

      // determine which type of Transaction to create
      switch (type) {
         case BALANCE_INQUIRY: // create new BalanceInquiry transaction
            temp = new BalanceInquiry(currentAccountNumber, screen, bankDatabase);
            break;
         case WITHDRAWAL: // create new Withdrawal transaction
            temp = new Withdrawal(currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);
            break;
         case TRANSFER:
            temp = new Transfer(currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);
            break;
      } // end switch
      return temp; // return the newly created object
   } // end method createTransaction
} // end class ATM

/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and *
 * Pearson Education, Inc. All Rights Reserved. *
 * *
 * DISCLAIMER: The authors and publisher of this book have used their *
 * best efforts in preparing the book. These efforts include the *
 * development, research, and testing of the theories and programs *
 * to determine their effectiveness. The authors and publisher make *
 * no warranty of any kind, expressed or implied, with regard to these *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or *
 * consequential damages in connection with, or arising out of, the *
 * furnishing, performance, or use of these programs. *
 *************************************************************************/