package Accounts;

import Bank.Bank;
import Transactions.Payment;
import Transactions.Recompense;
import Transactions.Transaction;

/**
 * Concrete CreditAccount implementation.
 * Implements Payment and Recompense interfaces.
 * 
 * This class uses:
 *  - loanAmount: current debt
 *  - creditLimit: maximum allowed debt
 * 
 * It defines methods:
 *  - charge(double amount): boolean
 *  - getBalance(): double (available credit = creditLimit - loanAmount)
 *  - getCurrentDebt(): double
 *  - pay(Account fromAccount, double amount): boolean
 *  - recompense(double amount): boolean
 */
public class CreditAccount extends Account implements Payment, Recompense {
    private double loanAmount;   // current debt
    private double creditLimit;  // maximum allowed debt

    /**
     * Constructor for CreditAccount.
     * @param bank The associated Bank object.
     * @param accountNumber Unique account number.
     * @param ownerName The account owner's name.
     * @param creditLimit The maximum credit limit.
     * @param pin The account PIN.
     */
    public CreditAccount(Bank bank, String accountNumber, String ownerName, double creditLimit, String pin) {
        super(bank, accountNumber, ownerName, pin);
        this.loanAmount = 0;  // initially no debt
        this.creditLimit = creditLimit;
    }

    /**
     * Returns the available credit for this account.
     * (Credit limit minus current debt.)
     * @return the available credit.
     */
    @Override
    public double getBalance() {
        return creditLimit - loanAmount;
    }

    /**
     * Returns the current debt (loan amount) for this account.
     * @return the current debt.
     */
    public double getCurrentDebt() {
        return loanAmount;
    }

    /**
     * Checks if the specified amount can be charged without exceeding the credit limit.
     * @param amount The amount to charge.
     * @return true if allowed, false otherwise.
     */
    public boolean canCredit(double amount) {
        return (loanAmount + amount) <= creditLimit;
    }

    /**
     * Charges the credit account by increasing the debt.
     * @param amount The charge amount.
     * @return true if the charge was successful; false otherwise.
     */
    public boolean charge(double amount) {
        if (amount <= 0) {
            System.out.println("Charge must be positive.");
            return false;
        }
        if (!canCredit(amount)) {
            System.out.println("Charge exceeds credit limit!");
            return false;
        }
        loanAmount += amount;
        addNewTransaction(accountNumber, Transaction.Transactions.Withdraw, "Charged " + amount);
        return true;
    }

    /**
     * Processes a payment from a SavingsAccount to reduce this account's debt.
     * @param fromAccount The SavingsAccount making the payment.
     * @param amount The payment amount.
     * @return true if payment successful; false otherwise.
     * @throws IllegalAccountType if the fromAccount is not a SavingsAccount.
     */
    @Override
    public boolean pay(Account fromAccount, double amount) throws IllegalAccountType {
        if (!(fromAccount instanceof SavingsAccount)) {
            throw new IllegalAccountType("Payment must come from a SavingsAccount!");
        }
        SavingsAccount saver = (SavingsAccount) fromAccount;
        if (amount <= 0) {
            System.out.println("Payment must be positive.");
            return false;
        }
        if (amount > saver.getBalance()) {
            System.out.println("Insufficient funds in source SavingsAccount!");
            return false;
        }
        // Deduct from the SavingsAccount.
        saver.withdrawal(amount);
        double oldLoan = loanAmount;
        loanAmount = Math.max(0, loanAmount - amount);
        addNewTransaction(accountNumber, Transaction.Transactions.Payment,
                String.format("Payment of %.2f from %s (old debt=%.2f, new debt=%.2f)",
                        amount, saver.getAccountNumber(), oldLoan, loanAmount));
        return true;
    }

    /**
     * Recompenses (compensates) to reduce the current debt.
     * @param amount The recompense amount.
     * @return true if successful; false otherwise.
     */
    @Override
    public boolean recompense(double amount) {
        if (amount <= 0) {
            System.out.println("Recompense must be positive.");
            return false;
        }
        if (amount > loanAmount) {
            System.out.println("Cannot recompense more than current debt!");
            return false;
        }
        loanAmount -= amount;
        addNewTransaction(accountNumber, Transaction.Transactions.Recompense, "Recompensed " + amount);
        return true;
    }

    /**
     * Returns the logged account.
     * @return the logged account.
     */
    protected Account getLoggedAccount() {
        // Return the current instance of CreditAccount
        return this;
    }

    @Override
    public String toString() {
        return String.format("CreditAccount[%s, loan=%.2f/%.2f, available=%.2f]", 
                super.toString(), loanAmount, creditLimit, getBalance());
    }
}
