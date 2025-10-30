// ChequeAccount.java
// Cheque Account has a specific attribute – limit per cheque (with default value HK$50,000).
public class ChequeAccount extends Account {
    private static double cLimit; // limit

    public ChequeAccount(int theAccountNumber, int thePIN, double theAvailableBalance, double theTotalBalance) {
        super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance, "Cheque Account");
        setcLimit(50000); // default and setter
    }

    // setter
    public static void setcLimit(double c2Limit) {
        // cheque need to > 0
        if (c2Limit > 0)
            cLimit = c2Limit;
        else
            throw new IllegalArgumentException("Cheque limit must be > 0");
    }

    // getter
    public static double getcLimit() {
        return cLimit;
    }
}
