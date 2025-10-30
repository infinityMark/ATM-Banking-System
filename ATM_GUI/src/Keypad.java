
// Keypad.java
// Represents the keypad of the ATM
import java.util.InputMismatchException;
import java.util.Scanner; // program uses Scanner to obtain user input

public class Keypad {
   private Scanner input; // reads data from the command line

   // no-argument constructor initializes the Scanner
   public Keypad() {
      input = new Scanner(System.in);
   } // end no-argument Keypad constructor

   // return an integer value entered by user
   public int getInput() {
      return input.nextInt(); // we assume that user enters an integer
   } // end method getInput

   /**
    * Retrieves a double-precision floating-point number input from the user.
    * 
    * @return The double value entered by the user
    */
   public double getDoubleInput() {
      return input.nextDouble();
   }

   /**
    * Retrieves an integer selection input from the user.
    * Handles InputMismatchException by clearing the input buffer and returning -1
    * if the input is invalid.
    * 
    * @return The integer selection entered by the user, or -1 if the input is not
    *         a valid integer
    */
   public int getSelection() {
      try {
         return input.nextInt();
      } catch (InputMismatchException e) {
         input.nextLine();
         return -1;
      }
   }

   /**
    * Clears the input buffer to ensure subsequent user inputs are processed
    * correctly.
    * This method helps prevent residual input data from interfering with future
    * input operations.
    */
   public void clearBuffer() {
      input.nextLine();
   }

   /**
    * Prompts the user to enter a receiver's account number and retrieves the
    * input.
    * Displays a specified prompt message and handles invalid input by showing an
    * error message and returning -1.
    * 
    * @param screen         The Screen object used to display messages to the user
    * @param promptForInput The message prompting the user to enter the account
    *                       number
    * @param errorMessage   The message displayed if the input is invalid (not an
    *                       integer)
    * @return The receiver's account number entered by the user, or -1 if the input
    *         is invalid
    */
   public int getReceiverAccountNumber(Screen screen, String promptForInput, String errorMessage) {
      try {
         screen.displayMessage(promptForInput);
         return input.nextInt();
      } catch (InputMismatchException e) {
         screen.displayMessageLine(errorMessage);
         return -1;
      }
   }
} // end class Keypad

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