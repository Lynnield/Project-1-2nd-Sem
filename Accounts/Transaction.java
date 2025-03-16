package Accounts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class containing Transaction enums.
 */
public class Transaction {

    private String name;
    private String type;
    private double amount;
    private String date;

    public enum Transactions {
        Deposit,
        Withdraw,
        FundTransfer,
        Payment,
        Recompense,
        Charge
    }

    /**
     * Account number that triggered this transaction.
     */
    public String accountNumber;
    /**
     * Type of transaction that was triggered.
     */
    public Transactions transactionType;
    /**
     * Description of the transaction.
     */
    public String description;

    public Transaction(String accountNumber, Transactions transactionType, String description, String name, String type, double amount) {
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.description = description;
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return String.format("%s %s %.2f. %s", name, type, amount, date);
    }
}