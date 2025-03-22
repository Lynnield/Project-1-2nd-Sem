package Bank;

import java.util.ArrayList;
import java.util.List;
import Accounts.Account;
import Accounts.SavingsAccount;
import Accounts.StudentAccount;
import Accounts.CreditAccount;

/**
 * A Bank class that stores a list of Account objects.
 */
public class Bank {
    private String bankId;
    private String bankName;
    private String passcode;  // Optional field for bank credentials
    private List<Account> accounts;

    /**
     * Basic constructor to initialize a bank with an ID and name.
     * Sets a default empty passcode.
     * @param bankId   The bank's unique ID.
     * @param bankName The name of the bank.
     */
    public Bank(String bankId, String bankName) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.passcode = "";  // Default passcode, can be set later via a setter if needed.
        this.accounts = new ArrayList<>();
    }

    /**
     * Full constructor allowing a custom passcode.
     * @param bankId   The bank's unique ID.
     * @param bankName The name of the bank.
     * @param passcode The bank's passcode.
     */
    public Bank(String bankId, String bankName, String passcode) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.passcode = passcode;
        this.accounts = new ArrayList<>();
    }

    /**
     * Getter for the bank's ID.
     * @return The bank's ID.
     */
    public String getBankId() {
        return bankId;
    }

    /**
     * Getter for the bank's name.
     * @return The bank's name.
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * Getter for the bank's passcode.
     * @return The bank's passcode.
     */
    public String getPasscode() {
        return passcode;
    }

    /**
     * Getter for the list of all accounts in this bank.
     * @return A List of Account objects.
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Searches for an account in this bank by its account number.
     * @param accountNumber The account number to search for.
     * @return The Account object if found, or null if not found.
     */
    public Account getBankAccount(String accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        return null;
    }

    /**
     * Creates a new SavingsAccount, adds it to the bank's accounts list,
     * and returns the newly created account.
     * @param accountNumber The unique account number for the new account.
     * @param ownerName     The owner's name.
     * @param initialValue  The initial balance.
     * @param pin           The PIN/password for the account.
     * @return The created SavingsAccount object.
     */
    public SavingsAccount createNewSavingsAccount(String accountNumber, String ownerName, double initialValue, String pin) {
        SavingsAccount sa = new SavingsAccount(this, accountNumber, ownerName, initialValue, pin);
        accounts.add(sa);
        return sa;
    }

    /**
     * Creates a new CreditAccount, adds it to the bank's accounts list,
     * and returns the newly created account.
     * @param accountNumber The unique account number for the new account.
     * @param ownerName     The owner's name.
     * @param initialValue  The credit limit for the account.
     * @param pin           The PIN/password for the account.
     * @return The created CreditAccount object.
     */
    public CreditAccount createNewCreditAccount(String accountNumber, String ownerName, double initialValue, String pin) {
        CreditAccount ca = new CreditAccount(this, accountNumber, ownerName, initialValue, pin);
        accounts.add(ca);
        return ca;
    }

     public StudentAccount createNewStudentAccount(String accountNumber, String ownerName, double initialValue, String pin) {
        StudentAccount sa = new StudentAccount(this, accountNumber, ownerName, initialValue, pin);
        accounts.add(sa);
        return sa;
    }


    @Override
    public String toString() {
        return String.format("Bank[ID=%s, Name=%s, Passcode=%s, TotalAccounts=%d]",
                bankId, bankName, passcode, accounts.size());
    }
}
