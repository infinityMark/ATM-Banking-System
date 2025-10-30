// Screen.java
// Represents the screen of the ATM

import java.time.LocalDate;

public class Screen {
   // displays a message without a carriage return
   public void displayMessage(String message) {
      System.out.print(message);
   } // end method displayMessage

   // display a message with a carriage return
   public void displayMessageLine(String message) {
      System.out.println(message);
   } // end method displayMessageLine

   // display a dollar amount
   public void displayDollarAmount(double amount) {
      System.out.printf("$%.2f", amount);
   } // end method displayDollarAmount

   /**
    * Generates a formatted string displaying the current account information,
    * including account number and remaining balance.
    * 
    * @param account The account number to display
    * @param amounts The remaining balance in the account
    * @return A formatted string with account details
    */
   public String amountsDisplay(int account, double amounts) {
      return String.format("\nCurrently Account Information:\nAccount number: %d\nRemain amount %.2f", account,
            amounts);
   }

   /**
    * Displays a message indicating the amount the user is about to withdraw.
    * 
    * @param amount The withdrawal amount in HKD
    */
   public void displayWithdrawMessageLine(int amount) {
      System.out.printf("You are going to withdraw HKD$%2d.\n", amount);
   }

   /**
    * Displays the current remaining balance in the user's account.
    * 
    * @param amount The current balance in HKD
    */
   public void displayCurrentRemaining(double amount) {
      System.out.printf("Currently asset in your account HKD$%.2f\n", amount);
   }

   /**
    * Displays a confirmation message for a successful transfer, including the
    * amount and recipient account.
    * 
    * @param amount   The transferred amount in HKD
    * @param receiver The account number of the recipient
    */
   public void displayTransferMessageLine(double amount, int receiver) {
      System.out.printf("Total HK$ %.2f transfers to account %d.\nThe transfer successfully.", amount, receiver);
   }

   /**
    * Displays a notification when a transaction exceeds the account's limit.
    * 
    * @param accountType The type of the account (e.g., "Cheque Account")
    * @param amount      The maximum allowed amount for the transaction
    * @param action      The type of transaction (e.g., "withdraw", "transfer")
    *                    that was restricted
    */
   public void displayLimitAccountNotification(String accountType, double amount, String action) {
      System.out.printf(
            "Sorry, since the limit of your account (%s),\nYou are not allowed to %s over %.2f amount at once time.\n",
            accountType, action, amount);
   }

   /**
    * Displays the breakdown of banknote denominations for a withdrawal (only shows
    * denominations with non-zero counts).
    * 
    * @param numberOfOneHundred  The number of $100 banknotes
    * @param numberOfFiveHundred The number of $500 banknotes
    * @param numberOfOneThousand The number of $1000 banknotes
    */
   public void displayWithdrawalTypeOfAmount(int numberOfOneHundred, int numberOfFiveHundred, int numberOfOneThousand) {
      System.out.println("\nNumber of money withdraw:");

      // Display each denomination only if the count is greater than 0
      if (numberOfOneThousand != 0)
         System.out.println("$1000: " + numberOfOneThousand);

      if (numberOfFiveHundred != 0)
         System.out.println("$500:  " + numberOfFiveHundred);

      if (numberOfOneHundred != 0)
         System.out.println("$100:  " + numberOfOneHundred);
   }

   /**
    * Displays a line of repeated characters (e.g., for visual separation in
    * output).
    * 
    * @param symbol The character to repeat
    * @param length The number of times to repeat the character
    */
   public void displaySymbolicLine(char symbol, int length) {
      for (int i = 0; i < length; i++) {
         System.out.print(symbol);
      }
      System.out.println();
   }

   /**
    * Returns the current date as a string in the default ISO format (yyyy-MM-dd).
    * 
    * @return A string representing the current date
    */
   public String currentDay() {
      return LocalDate.now().toString();
   }
} // end class Screen

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