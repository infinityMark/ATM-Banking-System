// Withdrawal.java
// Represents a withdrawal ATM transaction

import java.util.InputMismatchException;

public class Withdrawal extends Transaction {
   private int amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private CashDispenser cashDispenser; // reference to cash dispenser

   // constant corresponding to menu option to cancel
   private final static int CANCELED = 6;

   // Withdrawal constructor
   public Withdrawal(int userAccountNumber, Screen atmScreen,
         BankDatabase atmBankDatabase, Keypad atmKeypad,
         CashDispenser atmCashDispenser) {
      // initialize superclass variables
      super(userAccountNumber, atmScreen, atmBankDatabase);

      // initialize references to keypad and cash dispenser
      keypad = atmKeypad;
      cashDispenser = atmCashDispenser;
   } // end Withdrawal constructor

   // perform transaction
   // Adjustment
   @Override
   public void execute() {
      boolean cashDispensed = false; // cash was not dispensed yet
      double availableBalance; // amount available for withdrawal

      // get references to bank database and screen
      BankDatabase bankDatabase = getBankDatabase();
      Screen screen = getScreen();

      // loop until cash is dispensed or the user cancels
      do {
         // obtain a chosen withdrawal amount from the user
         amount = displayMenuOfAmounts();

         // check whether user chose a withdrawal amount or canceled
         if (amount == CANCELED) {
            screen.displayMessageLine("\nCanceling transaction...");
            return; // return to main menu because user canceled
         }
         // get available balance of account involved
         availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());

         // When user choose to withdraw multiples of HKD$ 100 or 500 or 1000.
         if (amount == 1) {
            try {
               screen.displayMessage("Please input amount: ");
               amount = keypad.getInput();
            } catch (InputMismatchException e) {
               // When user are not input multiples of HKD$ 100 or 500 or 1000, then continue
               // will rerun the do-while loop
               screen.displayMessageLine("Only integer are allowed");
               screen.displayMessageLine("The amount must be the multiples of HKD$ 100 or 500 or 1000.\n");
               continue;
            } finally {
               // Clear input buffer to avoid unwanted value
               keypad.clearBuffer();
            }
         }

         // If user's input amount is not multiples of HKD$ 100 or 500 or 1000.
         if (isMultiplesCondition(amount))
            screen.displayWithdrawMessageLine(amount);
         else {
            screen.displayMessageLine("The value is not the multiples of HKD$ 100 or 500 or 1000");
            continue;
         }

         // check whether the user has enough money in the account
         if (amount <= availableBalance) {
            if (isLimitAccountConditionCheckerHappen(amount, "withdrawal"))
               continue;

            // check whether the cash dispenser has enough money
            if (cashDispenser.isSufficientCashAvailable(amount)) {
               // update the account involved to reflect withdrawal
               bankDatabase.debit(getAccountNumber(), amount);

               cashDispenser.dispenseCash(); // dispense cash
               screen.displayWithdrawalTypeOfAmount(cashDispenser.getNumberOfOneHundred(),
                     cashDispenser.getNumberOfFiveHundred(), cashDispenser.getNumberOfOneThousand());
               cashDispensed = true; // cash was dispensed

               // Write the withdrawal action into transaction history
               new TransactionHistory(1, getAccountNumber(), 0, 0, 0, (double) amount);

               // instruct user to take cash
               screen.displayMessageLine("\nPlease take your cash now.");
               screen.displayMessageLine("Remaining amount:");
               Transaction remainAmount = new BalanceInquiry(getAccountNumber(), screen, bankDatabase);
               remainAmount.execute();
            } // end if
            else // cash dispenser does not have enough cash
               screen.displayMessageLine(
                     "\nInsufficient cash available in the ATM." + "\n\nPlease choose a smaller amount.");
         } // end if
         else { // not enough money available in user's account
            screen.displayMessageLine("\nInsufficient funds in your account." + "\n\nPlease choose a smaller amount.");
         } // end else
      } while (!cashDispensed);
   } // end method execute

   // Add
   public boolean isMultiplesCondition(int amount) {
      // Check whether the amount is the multiples of HKD$ 100 or 500 or 1000.
      if (amount <= 0)
         return false;
      return amount % 100 == 0 || amount % 500 == 0 || amount % 1000 == 0;
   }

   // display a menu of withdrawal amounts and the option to cancel;
   // return the chosen amount or 0 if the user chooses to cancel
   // Adjustment
   private int displayMenuOfAmounts() {
      int userChoice = 0; // local variable to store return value
      Screen screen = getScreen(); // get screen reference
      // array of amounts to correspond to menu numbers
      int amounts[] = { 0, 200, 400, 800, 1000 };

      // loop while no valid choice has been made
      while (userChoice == 0) {
         // display the menu
         screen.displayMessageLine("\nWithdrawal Menu:");
         screen.displayMessageLine("1 - $200");
         screen.displayMessageLine("2 - $400");
         screen.displayMessageLine("3 - $800");
         screen.displayMessageLine("4 - $1000");
         screen.displayMessageLine("5 - Amount of multiples of HK$ 100 or 500 or 1000");
         screen.displayMessageLine("6 - Cancel transaction");
         screen.displayMessage("\nChoose a withdrawal amount: ");

         int input = keypad.getSelection(); // get user input through keypad

         // determine how to proceed based on the input value
         switch (input) {
            case 1: // if the user chose a withdrawal amount
            case 2: // (i.e., chose option 1, 2, 3), return the
            case 3: // corresponding amount from amounts array
            case 4:
               userChoice = amounts[input]; // save user's choice
               break;
            case 5: // The amount must be the multiples of HKD$ 100 or 500 or 1000.
               userChoice = 1; // save user's choice
               break;
            case CANCELED: // the user chose to cancel
               userChoice = CANCELED; // save user's choice
               break;
            default: // the user did not enter a value from 1-5
               screen.displayMessageLine("\nInvalid selection. Try again.");
         } // end switch
      } // end while

      return userChoice; // return withdrawal amount or CANCELED
   } // end method displayMenuOfAmounts
} // end class Withdrawal

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