// BankDatabase.java
// Represents the bank account information database 

public class BankDatabase {
   private Account accounts[]; // array of Accounts

   // no-argument BankDatabase constructor initializes accounts
   public BankDatabase() {
      accounts = new Account[9]; // 8 accounts for testing
      accounts[0] = new SavingAccount(11111, 11111, 1000.0, 1000.0);
      accounts[1] = new ChequeAccount(21111, 21111, 55000.0, 55000.0);
      accounts[2] = new SavingAccount(12222, 12222, 50200.0, 50200.0);
      accounts[3] = new ChequeAccount(22222, 22222, 2000.0, 2000.0);
      accounts[4] = new SavingAccount(13333, 33333, 10000.0, 10000.0);
      accounts[5] = new ChequeAccount(23333, 23333, 151000.0, 151000.0);
      accounts[6] = new SavingAccount(14444, 14444, 7999999, 7999999);
      accounts[7] = new ChequeAccount(24444, 24444, 7999999, 7999999);
      accounts[8] = new SavingAccount(24985, 24985, 800000, 800000);
   } // end no-argument BankDatabase constructor

   // retrieve Account object containing specified account number
   Account getAccount(int accountNumber) {
      // loop through accounts searching for matching account number
      for (Account currentAccount : accounts) {
         // return current account if match found
         if (currentAccount.getAccountNumber() == accountNumber)
            return currentAccount;
      } // end for

      return null; // if no matching account was found, return null
   } // end method getAccount

   // determine whether user-specified account number and PIN match
   // those of an account in the database
   public boolean authenticateUser(int userAccountNumber, int userPIN) {
      // attempt to retrieve the account with the account number
      Account userAccount = getAccount(userAccountNumber);

      // if account exists, return result of Account method validatePIN
      if (userAccount == null)
         return false;// account number not found, so return false

      return userAccount.validatePIN(userPIN);

   } // end method authenticateUser

   // return available balance of Account with specified account number
   public double getAvailableBalance(int userAccountNumber) {
      return getAccount(userAccountNumber).getAvailableBalance();
   } // end method getAvailableBalance

   // return total balance of Account with specified account number
   public double getTotalBalance(int userAccountNumber) {
      return getAccount(userAccountNumber).getTotalBalance();
   } // end method getTotalBalance

   // credit an amount to Account with specified account number
   public void credit(int userAccountNumber, double amount) {
      getAccount(userAccountNumber).credit(amount);
   } // end method credit

   // debit an amount from of Account with specified account number
   public void debit(int userAccountNumber, double amount) {
      getAccount(userAccountNumber).debit(amount);
   } // end method debit

   /**
    * Checks if an account with the specified account number exists in the system.
    * 
    * @param userAccountNumber The account number to verify
    * @return The account number if it exists; -1 if no account is found with the
    *         given number
    */
   public int isAccountNumberExist(int userAccountNumber) {
      Account account = getAccount(userAccountNumber);

      if (account == null)
         return -1;

      return account.getAccountNumber();

   } // end method isAccountNumberExist

   /**
    * Processes a fund transfer between two accounts: debits the sender's account
    * and credits the receiver's account.
    * Assumes validation (e.g., sufficient funds, account existence) is performed
    * before calling this method.
    * 
    * @param senderAccounts   The account number of the sender (source of funds)
    * @param receiverAccounts The account number of the receiver (destination of
    *                         funds)
    * @param amount           The amount to be transferred
    */
   public void transfer(int senderAccounts, int receiverAccounts, double amount) {
      Account receiverAccount = getAccount(receiverAccounts);
      Account senderAccount = getAccount(senderAccounts);

      senderAccount.debit(amount); // Deduct the amount from the sender's account
      receiverAccount.credit(amount); // Add the amount to the receiver's account
   }

   /**
    * Checks if the specified account is a limit-restricted account (specifically a
    * ChequeAccount).
    * 
    * @param accountNumber The account number to check
    * @return true if the account is an instance of ChequeAccount; false otherwise
    */
   public boolean isLimitAccountConditionChecker(int accountNumber) {
      Account examinAccount = getAccount(accountNumber);
      return examinAccount instanceof ChequeAccount;
   }

   /**
    * Retrieves the type of account associated with the specified account number.
    * 
    * @param userAccountNumber The account number to check
    * @return A string representing the account type (e.g., "ChequeAccount",
    *         "SavingsAccount")
    */
   public String getAccountType(int userAccountNumber) {
      return getAccount(userAccountNumber).getAccountType();
   } // end method getAccountType

   // Add
   // return interest rate / cheque limit of Account with specified account number
   public double getRateOrLimit(int userAccountNumber) {
      Account account = getAccount(userAccountNumber);

      if (account instanceof SavingAccount) // check account is Saving account
         return ((SavingAccount) account).getInterestRate();
      else if (account instanceof ChequeAccount) // check account is Cheque account
         return ChequeAccount.getcLimit();

      return 0.0;
   } // end method getRateOrLimit
} // end class BankDatabase

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