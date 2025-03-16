package Accounts;

import Bank.Bank;
import java.util.ArrayList;
import java.util.List;

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
    private Bank bank;
    private String accountNumber;
    private String ownerName;
    private String pin;
    private List<Transaction> transactions;
    

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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Adds a new transaction to this account's transaction list.
     * @param type The type of transaction (Deposit, Withdraw, etc.).
     * @param amount The amount of the transaction.
     */
    public void addTransaction(String type, double amount) {
        transactions.add(new Transaction(accountNumber, Transaction.Transactions.valueOf(type), type, ownerName, type, amount));
    }

    /**
     * Abstract method that returns the current balance (or available credit)
     * of the account. Subclasses must implement this method.
     * @return the current balance.
     */
    public abstract double getBalance();

    @Override
    public String toString() {
        return String.format("Account[%s, %s]", accountNumber, ownerName);
    }
}
