package Accounts;

import Bank.Bank;
import Transactions.Deposit;
import Transactions.FundTransfer;
import Transactions.Transaction;
import Transactions.Withdrawal;

public class StudentAccount extends Account implements Deposit, Withdrawal, FundTransfer {
    private double balance;

    /**
     * Constructor for a StudentAccount, including an initial balance.
     */
    public StudentAccount(Bank bank, String accountNumber, String ownerName, double initialBalance, String pin) {
        super(bank, accountNumber, ownerName, pin);
        this.balance = initialBalance;
    }

    /**
     * Deposits an amount of money into this account.
     */
    @Override
    public boolean cashDeposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit must be positive.");
            return false;
        }
        balance += amount;
        addNewTransaction(accountNumber, Transaction.Transactions.Deposit, "Deposited " + amount);
        return true;
    }

    /**
     * Withdraws an amount of money from this account.
     */
    @Override
    public boolean withdrawal(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal must be positive.");
            return false;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds!");
            return false;
        }
        balance -= amount;
        addNewTransaction(accountNumber, Transaction.Transactions.Withdraw, "Withdrew " + amount);
        return true;
    }

    /**
     * Transfers an amount to another SavingsAccount (same bank or cross-bank).
     */
    @Override
    public boolean transfer(Account target, double amount) throws IllegalAccountType {
        if (!(target instanceof SavingsAccount)) {
            throw new IllegalAccountType("Cannot transfer to a non-SavingsAccount!");
        }
        if (amount <= 0) {
            System.out.println("Transfer amount must be positive.");
            return false;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds to transfer!");
            return false;
        }
        // Deduct from this account
        balance -= amount;
        addNewTransaction(accountNumber, Transaction.Transactions.FundTransfer,
                String.format("Transferred %.2f to %s", amount, target.getAccountNumber()));

        // Deposit into the target account
        ((SavingsAccount) target).cashDeposit(amount);
        return true;
    }

    /**
     * Overloaded method for cross-bank transfer if needed.
     */
    @Override
    public boolean transfer(Bank otherBank, Account target, double amount) throws IllegalAccountType {
        // For cross-bank logic, you might charge a fee or check if otherBank != this.bank.
        return transfer(target, amount);
    }

    /**
     * Getter for the current balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Returns the logged account.
     * @return the logged account.
     */
    protected Account getLoggedAccount() {
        // Assuming this method should return the current instance of the account
        return this;
    }

    @Override
    public String toString() {
        return String.format("StudentAccount[%s, balance=%.2f]", super.toString(), balance);
    }
}
