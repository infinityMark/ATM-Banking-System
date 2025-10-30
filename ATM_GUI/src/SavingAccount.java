
//SavingAccount.java
//Saving Account extends Account
import java.time.LocalDate;

public class SavingAccount extends Account {
    private static double InterestRate = 0.25 / 100;
    // the variable of interest rate (0.25% per year)

    // the constructor of Saving Account
    public SavingAccount(int theAccountNumber, int thePIN, double theAvailableBalance, double theTotalBalance) {
        // calling the variable which in Account.java
        super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance, "Saving Account");
        // set an interestrate to 0.25% because the group project has default
        SetInterestRate(InterestRate);
    }// end constructor

    // setting the interest rate
    public static void SetInterestRate(double theInterestRate) {
        if (theInterestRate > 0.0 && theInterestRate < 1.0)
            InterestRate = theInterestRate;
        else
            throw new IllegalArgumentException("Interest rate must be > 0.0 and < 1.0");
    }// end method for setting interest rate

    // getting the interest rate
    public double getInterestRate() {
        return InterestRate;
    } // end method for getting interest rate

    public String expectedDay(int days) {
        return LocalDate.now().plusDays(days).toString();
    }

    public double calculateInterest(int annum, Screen screen) {
        double originalBalance = getTotalBalance();
        double rate = getInterestRate();
        String predictedDay = expectedDay(annum * 365);

        double futureValue = originalBalance * Math.pow((1 + rate), annum);

        screen.displayMessageLine(
                "At " + predictedDay + ", your Saving Account will have: $" + String.format("%.2f", futureValue));
        return futureValue;
    } // end method for calculating interest
}