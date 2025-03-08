package Accounts;

import java.util.ArrayList;

import Bank.Bank;

/**
 * An abstract Account class based on the UML:
 *  - bank: Bank
 *  - accountNumber: String
 *  - ownerName: String
 *  - pin: String
 *  - transactions: ArrayList<Transaction>
 * 
 *  + getBank()
 *  + getAccountNumber()
 *  + getOwnerName()
 *  + getPin()
 *  + addNewTransaction(...)
 *  + toString()
 *  + getBalance()   // abstract method for subclasses to implement
 */
public abstract class Account {
    protected Bank bank;
    protected String accountNumber;
    protected String ownerName;
    protected String pin;
    protected ArrayList<Transaction> transactions;

    /**
     * Constructor that sets the basic fields for an account.
     */
    public Account(Bank bank, String accountNumber, String ownerName, String pin) {
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.pin = pin;
        this.transactions = new ArrayList<>();
    }

    public Bank getBank() {
        return bank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getPin() {
        return pin;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Adds a new transaction to this account's transaction list.
     * @param accNum The account number that triggered this transaction.
     * @param type The type of transaction (Deposit, Withdraw, etc.).
     * @param description Description or details of the transaction.
     */
    public void addNewTransaction(String accNum, Transaction.Transactions type, String description) {
        Transaction t = new Transaction(accNum, type, description);
        transactions.add(t);
    }

    /**
     * Abstract method that returns the current balance (or available credit)
     * of the account. Subclasses must implement this method.
     * @return the current balance.
     */
    public abstract double getBalance();

    @Override
    public String toString() {
        String bankName = (bank != null) ? bank.getBankName() : "NoBank";
        return String.format("%s@%s [Owner: %s]", accountNumber, bankName, ownerName);
    }
}
