import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * ATM Transfer Record Class
 * Used to record detailed information for each ATM transfer transaction.
 */
public class TransactionHistory {
    // Static array to store transaction history records, with a maximum capacity of
    // 21,000,000 entries
    private static TransactionHistory[] transactionHistory = new TransactionHistory[21000000];
    // Static counter to track the current number of recorded transactions
    private static int transactionCount = 0; // Record the current transaction quantity

    // Unique identifier for the transaction
    private final String transactionId;
    // Code representing the type of transaction (e.g., 0 for transfer, 1 for
    // withdrawal)
    private final int action;
    // Account number of the transaction initiator/owner
    private final int ownAccount;
    // Target account number involved in the transaction (e.g., recipient in a
    // transfer)
    private final int targetAccount;
    // Primary tag/category code for the transaction
    private final int tag;
    // Sub-tag/sub-category code for more specific transaction classification
    private final int subTag;
    // Monetary amount involved in the transaction
    private final double amount;
    // Date and time when the transaction was created
    private final LocalDateTime transactionDate;

    // Static array to map transaction action codes to their corresponding string
    // descriptions
    private static final String[] transactionMission = new String[2];

    /**
     * Constructs a new TransactionHistory object with the specified transaction
     * details.
     * Automatically generates a transaction ID, sets the current timestamp, defines
     * transaction type names,
     * and adds the new record to the static transaction history array.
     *
     * @param action        The action code indicating the transaction type (0 for
     *                      transfer, 1 for withdrawal)
     * @param ownAccount    The account number of the transaction initiator
     * @param targetAccount The target account number involved in the transaction
     * @param tag           The primary tag/category code for the transaction
     * @param subTag        The sub-tag code for more specific classification
     * @param amounts       The monetary amount of the transaction
     */
    public TransactionHistory(int action, int ownAccount, int targetAccount, int tag, int subTag, double amounts) {
        this.action = action;
        this.transactionId = generateTransactionId(); // Generate a unique transaction ID
        this.ownAccount = ownAccount;
        this.targetAccount = targetAccount;
        this.tag = tag;
        this.subTag = subTag;
        this.transactionDate = LocalDateTime.now(); // Set transaction time to current moment
        this.amount = amounts;

        // Set descriptive names for transaction types (indexes correspond to action
        // codes)
        this.transactionMission[0] = "Transfer";
        this.transactionMission[1] = "Withdrawal";

        // Add the newly created transaction to the static history array
        addToHistory(this);
    }

    // Add transaction records to history
    private static void addToHistory(TransactionHistory history) {
        if (transactionCount < transactionHistory.length) {
            transactionHistory[transactionCount] = history;
            transactionCount++;
        } else {
            System.out.println("The transaction history is full and new records cannot be added.");
        }
    }

    /**
     * Searches and displays all transaction history records associated with a
     * specific account.
     * Iterates through stored transactions to find those where the account is the
     * owner/initiator,
     * and prints each matching record using the printHistory method. If no records
     * are found,
     * it displays a message indicating the absence of transaction history.
     *
     * @param account The account number to search transaction history for
     */
    public static void checkHistory(int account) {
        boolean found = false;
        // Iterate through all recorded transactions
        for (int i = 0; i < transactionCount; i++) {
            // Check if the current transaction belongs to the target account
            if (transactionHistory[i].ownAccount == account) {
                printHistory(i); // Print details of the matching transaction
                found = true; // Show that at least one record was found
            }
        }

        // If no transactions were found for the account, notify the user
        if (!found) {
            System.out.println("No any record yet");
        }
    }

    // Prints the transaction history details for a specific transaction.
    // If the user has transaction records, this method displays relevant
    // information.
    private static void printHistory(int index) {
        TransactionHistory history = transactionHistory[index];
        System.out.printf("\nTransaction Type: %s\n", transactionMission[history.getAction()]);
        System.out.printf("Transaction Id: %s\n", history.getTransactionId());

        // For transactions of action type 0 (e.g., transfers), display sender and
        // recipient accounts
        if (history.getAction() == 0)
            System.out.printf("From: %d -> To: %d\n", history.getOwnAccount(), history.getTargetAccount());

        System.out.printf("%s amount: HK$ %.2f\n", transactionMission[history.getAction()], history.getAmount());
        System.out.printf("Date: %s\n", history.getFormattedTransactionDate());

        // If the transaction has a non-zero tag, display its associated remarks
        if (history.getTag() != 0) {
            int temporary[] = new int[2];
            temporary[0] = history.getTag();
            temporary[1] = history.getSubTag();
            TransferRemark.displayRemarkSelection("Remark:", temporary);
        }
    }

    public static void clearHistory() {
        for (int i = 0; i < transactionCount; i++) {
            transactionHistory[i] = null;
        }
        transactionCount = 0;
    }

    /**
     * Generates a unique transaction ID using UUID.
     * The generated ID is converted to uppercase with hyphens removed for
     * consistency.
     * 
     * @return A unique string representing the transaction ID
     */
    private String generateTransactionId() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * Returns the transaction ID associated with this transaction.
     * 
     * @return The transaction ID string
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Returns the account number of the owner/initiator of the transaction.
     * 
     * @return The owner's account number
     */
    public int getOwnAccount() {
        return ownAccount;
    }

    /**
     * Returns the target account number involved in the transaction (e.g.,
     * recipient in a transfer).
     * 
     * @return The target account number
     */
    public int getTargetAccount() {
        return targetAccount;
    }

    /**
     * Returns the primary tag/category code for the transaction.
     * 
     * @return The primary tag integer
     */
    public int getTag() {
        return tag;
    }

    /**
     * Returns the sub-tag/sub-category code for the transaction, providing more
     * specific classification.
     * 
     * @return The sub-tag integer
     */
    public int getSubTag() {
        return subTag;
    }

    /**
     * Returns the action code representing the type of transaction (e.g., deposit,
     * withdrawal, transfer).
     * 
     * @return The action code integer
     */
    public int getAction() {
        return action;
    }

    /**
     * Returns the formatted string representation of the transaction date and time.
     * Uses the pattern "yyyy-MM-dd HH:mm:ss" for consistent formatting.
     * 
     * @return The formatted transaction date and time string
     */
    public String getFormattedTransactionDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return transactionDate.format(formatter);
    }

    /**
     * Returns the monetary amount involved in the transaction.
     * 
     * @return The transaction amount as a double
     */
    public double getAmount() {
        return amount;
    }

    public static String getHistoryAsString(int accountNumber) {
        StringBuilder sb = new StringBuilder();
        boolean f = false;

        for (int i = 0; i < transactionCount; i++) {
            TransactionHistory history = transactionHistory[i];
            if (history.getOwnAccount() == accountNumber) {
                f = true;

                sb.append("Transaction Type: ")
                        .append(transactionMission[history.getAction()])
                        .append("\n");

                sb.append("Transaction Id: ")
                        .append(history.getTransactionId())
                        .append("\n");

                if (history.getAction() == 0) {
                    sb.append("From: ")
                            .append(history.getOwnAccount())
                            .append(" -> To: ")
                            .append(history.getTargetAccount())
                            .append("\n");
                }

                sb.append(transactionMission[history.getAction()])
                        .append(" amount: HK$ ")
                        .append(String.format("%.2f", history.getAmount()))
                        .append("\n");

                sb.append("Date: ")
                        .append(history.getFormattedTransactionDate())
                        .append("\n\n");
            }
        }

        if (!f) {
            sb.append("No any record yet\n");
        }

        return sb.toString();
    }
}