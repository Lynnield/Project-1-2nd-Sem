package Transactions;

import java.util.Date;

/**
 * A class representing a transaction in a bank account.
 */
public class Transaction {
    public enum Transactions {
        Deposit, Withdraw, FundTransfer, Payment, Recompense
    }

    private String accountNumber;
    private Transactions transactionType;
    private String description;
    private Date timestamp;

    /**
     * Constructor for a Transaction.
     * @param accountNumber The account number involved in the transaction.
     * @param transactionType The type of transaction.
     * @param description A description of the transaction.
     */
    public Transaction(String accountNumber, Transactions transactionType, String description) {
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.description = description;
        this.timestamp = new Date();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Transactions getTransactionType() {
        return transactionType;
    }

    public String getDescription() {
        return description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getFormattedDescription() {
        try {
            // Attempt to parse the description as a number
            double value = Double.parseDouble(description);
            return String.format("Amount: %.2f", value);
        } catch (NumberFormatException e) {
            // If parsing fails, treat it as a non-numeric description
            return "Description: " + description;
        }
    }

    @Override
    public String toString() {
        String[] parts = description.split(" ");
        double amount = Double.parseDouble(parts[1]);
        return String.format("%s %s's %.2f. %s", accountNumber, transactionType, amount, timestamp);
    }
}