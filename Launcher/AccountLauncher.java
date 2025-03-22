package Launcher;

import Accounts.Account;
import Bank.Bank;

/**
 * A base launcher class for handling bank/account sessions.
 * 
 * UML Methods:
 *   + isLoggedIn(): boolean
 *   + accountLogin(String, String): void
 *   + selectBank(String, BankLauncher): Bank
 *   + setLogSession(Bank): void
 *   + destroyLogSession(): void
 *   + checkCredentials(String, String): boolean
 *   + getLoggedAccount(): Account
 *   + logout(): void  // Added for convenience.
 */
public abstract class AccountLauncher {

    protected Account loggedAccount;  // The currently logged-in account.
    protected Bank assocBank;         // The associated bank for this launcher.

    /**
     * Checks if an account is currently logged in.
     * @return true if loggedAccount is not null.
     */
    public boolean isLoggedIn() {
        return (loggedAccount != null);
    }

    /**
     * Attempts to log in to an account within the associated bank using
     * an account number and a PIN.
     * @param accountNumber the account number to log in with.
     * @param pin the PIN provided by the user.
     */
    public void accountLogin(String accountNumber, String pin) {
        if (assocBank == null) {
            System.out.println("No bank selected! Cannot log in to an account.");
            return;
        }
        // Attempt to find the account in the associated bank.
        Account acc = assocBank.getBankAccount(accountNumber);
        if (acc == null) {
            System.out.println("Account not found!");
            return;
        }
        // Verify credentials using the stored PIN.
        if (!checkCredentials(pin, acc.getPin())) {
            System.out.println("Incorrect PIN!");
            return;
        }
        loggedAccount = acc;
        System.out.println("Account login successful -> " + accountNumber);
    }

    /**
     * Selects a bank from a BankLauncher instance.
     * @param bankIdOrName the bank ID or name.
     * @param bankLauncher the BankLauncher instance to use.
     * @return the selected Bank object.
     */
    public Bank selectBank(String bankIdOrName, BankLauncher bankLauncher) {
        bankLauncher.bankLogin(bankIdOrName);
        assocBank = bankLauncher.getLoggedBank();
        return assocBank;
    }

    /**
     * Sets the associated bank session explicitly.
     * @param bank the Bank object to set as the current session.
     */
    public void setLogSession(Bank bank) {
        assocBank = bank;
        if (bank != null) {
            System.out.println("Session set to bank: " + bank.getBankName());
        } else {
            System.out.println("No bank selected.");
        }
    }

    /**
     * Destroys the current bank/account session.
     */
    public void destroyLogSession() {
        assocBank = null;
        loggedAccount = null;
        System.out.println("Session destroyed. No bank or account is logged in.");
    }

    /**
     * Convenience method to log out by destroying the current session.
     */
    public void logout() {
        destroyLogSession();
    }

    /**
     * Checks credentials by comparing the input PIN with the stored PIN.
     * @param inputPin the PIN provided by the user.
     * @param storedPin the stored PIN for the account.
     * @return true if they match, false otherwise.
     */
    public boolean checkCredentials(String inputPin, String storedPin) {
        if (storedPin == null) {
            // If the account doesn't use a PIN, allow login.
            return true;
        }
        return inputPin.equals(storedPin);
    }

    /**
     * Returns the currently logged-in account.
     * @return the Account object that is logged in.
     */
    public Account getLoggedAccount() {
        return loggedAccount;
    }
    
    /**
     * Returns the associated bank.
     * @return the Bank object associated with this launcher.
     */
    public Bank getAssocBank() {
        return assocBank;
    }
}
