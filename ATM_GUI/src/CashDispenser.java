// CashDispenser.java
// Represents the cash dispenser of the ATM

public class CashDispenser {
   // the default initial number of bills in the cash dispenser
   private final static int[] INITIAL_COUNT = { 500, 500, 500 };
   private int[] count = new int[3]; // number of $20 bills remaining.
   private final int[] DENOMINATIONS = { 1000, 500, 100 };
   private int[] require = { 0, 0, 0 }; // Correspond amount: 1000, 500, 100.

   // no-argument CashDispenser constructor initializes count to default
   public CashDispenser() {
      count[0] = INITIAL_COUNT[0]; // set count attribute to default
      count[1] = INITIAL_COUNT[1]; // set count attribute to default
      count[2] = INITIAL_COUNT[2]; // set count attribute to default
   } // end CashDispenser constructor

   /**
    * Simulates the dispensing of cash by reducing the ATM's banknote counts based
    * on the required AMOUNT.
    * Assumes the system has already verified sufficient funds are available before
    * calling this method.
    */
   public void dispenseCash() {
      // Reduce the count of each banknote denomination by the required number for the
      // transaction
      for (int i = 0; i < 3; i++) {
         count[i] -= require[i];
      }
   } // end method dispenseCash

   /**
    * Prepares the required number of each ba nknote denomination needed to fulfill
    * the specified amount.
    * This method calculates how many of each denomination are required to make up
    * the total amount.
    *
    * @param amount The total cash amount to be dispensed (in the smallest currency
    *               unit, e.g., cents)
    */
   public boolean precheckNumberOfAmountType(int amount) {

      require[0] = 0;
      require[1] = 0;
      require[2] = 0;

      int totalAmount = amount;
      // Calculate and update the required count for each denomination, adjusting the
      // remaining amount iteratively
      for (int i = 0; i < 3; i++)
         totalAmount -= numberOfAmountType(totalAmount, i);

      return totalAmount == 0;
   }

   /**
    * Calculates the number of banknotes of a specific denomination needed to
    * contribute to the remaining amount.
    * Updates the required count for that denomination and returns the total value
    * covered by those banknotes.
    *
    * @param amount The remaining amount to be covered by this denomination
    * @param index  The index of the denomination in the AMOUNT array (e.g., 0 for
    *               highest, 2 for lowest)
    * @return The total value of the banknotes of this denomination used (count ×
    *         denomination value)
    */
   public int numberOfAmountType(int amount, int index) {
      // Calculate how many bills of the current denomination (AMOUNT[index]) fit into
      // the remaining amount
      if (count[index] == 0)
         return 0;

      int billsRequired = amount / DENOMINATIONS[index];

      if (billsRequired >= count[index]) {
         require[index] = count[index]; // Store the required number of bills for this denomination
         return count[index] * DENOMINATIONS[index];
      }

      require[index] = billsRequired; // Store the required number of bills for this denomination
      return billsRequired * DENOMINATIONS[index]; // Return the total value covered by these bills
   }

   public boolean isEmptyInATM() {
      return count[0] == 0 && count[1] == 0 && count[2] == 0;
   }

   /**
    * Checks if the ATM has a sufficient number of each banknote denomination to
    * dispense the specified amount.
    * First calculates the required denominations via precheckNumberOfAmountType(),
    * then verifies against available counts.
    *
    * @param amount The total cash amount requested (in the smallest currency unit,
    *               e.g., cents)
    * @return true if the ATM has enough of each required denomination; false
    *         otherwise
    */
   public boolean isSufficientCashAvailable(int amount) {
      if (isEmptyInATM())
         return false;

      return precheckNumberOfAmountType(amount);

      // if (!precheckNumberOfAmountType(amount))
      // return false;
      // Calculate the required number of each denomination
      // Check if the available count of each denomination is at least the required
      // count
      // for (int i = 0; i < 3; i++) {
      // // If any denomination has fewer bills than required, return false
      // if (count[i] < require[i])
      // return false;
      // }
      // return true; // All AMOUNT meet the required count
   } // end method isSufficientCashAvailable

   // return the number of HK$1000 require.
   public int getNumberOfOneThousand() {
      return require[0];
   }

   // return the number of HK$500 require.
   public int getNumberOfFiveHundred() {
      return require[1];
   }

   // return the number of HK$100 require.
   public int getNumberOfOneHundred() {
      return require[2];
   }
} // end class CashDispenser

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