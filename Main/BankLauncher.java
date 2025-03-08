package Main;

import Accounts.Account;
import Bank.Bank;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * BankLauncher class that manages multiple Bank objects
 * and handles basic operations like creating/logging in to banks,
 * showing accounts, creating new accounts, etc.
 */
public class BankLauncher {
    
    private ArrayList<Bank> BANKS = new ArrayList<>();
    private Bank loggedBank = null;

    public boolean isLogged() {
        return loggedBank != null;
    }

    public void bankInit() {
        // Example: create default banks
        createNewBank("001", "Alpha Bank");
        createNewBank("002", "Beta Bank");
    }

    public void showAccounts() {
        if (!isLogged()) {
            System.out.println("No bank is currently logged in.");
            return;
        }
        List<Account> accounts = loggedBank.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts in " + loggedBank.getBankName());
        } else {
            System.out.println("Accounts in " + loggedBank.getBankName() + ":");
            for (Account acc : accounts) {
                System.out.println(" - " + acc);
            }
        }
    }

    public void newAccounts(String accountType, String accountNumber, String ownerName, double initialValue, String pin) {
        if (!isLogged()) {
            System.out.println("No bank is currently logged in.");
            return;
        }
        if ("savings".equalsIgnoreCase(accountType)) {
            loggedBank.createNewSavingsAccount(accountNumber, ownerName, initialValue, pin);
            System.out.println("Created new SavingsAccount: " + accountNumber);
        } else if ("credit".equalsIgnoreCase(accountType)) {
            loggedBank.createNewCreditAccount(accountNumber, ownerName, initialValue, pin);
            System.out.println("Created new CreditAccount: " + accountNumber);
        } else {
            System.out.println("Unknown account type: " + accountType);
        }
    }

    /**
     * Log in to an existing bank by bank ID or bank name.
     */
    public void bankLogin(String bankIdOrName) {
        Bank found = null;
        for (Bank b : BANKS) {
            if (b.getBankId().equalsIgnoreCase(bankIdOrName) 
                || b.getBankName().equalsIgnoreCase(bankIdOrName)) {
                found = b;
                break;
            }
        }
        if (found != null) {
            loggedBank = found;
            System.out.println("Logged in to bank: " + found.getBankName());
        } else {
            System.out.println("Bank not found with ID or name: " + bankIdOrName);
        }
    }

    /**
     * Sets the current logged-in bank session explicitly.
     */
    public void setLogSession(Bank b) {
        this.loggedBank = b;
        if (b != null) {
            System.out.println("Session set to bank: " + b.getBankName());
        } else {
            System.out.println("No bank selected.");
        }
    }

    /**
     * Creates a new bank and adds it to the list.
     */
    public void createNewBank(String bankId, String bankName) {
        Bank newBank = new Bank(bankId, bankName);
        BANKS.add(newBank);
        System.out.println("Created new bank: " + bankName + " (ID=" + bankId + ")");
    }

    /**
     * Show a simple menu of all banks.
     */
    public void showBanksMenu() {
        if (BANKS.isEmpty()) {
            System.out.println("No banks available.");
            return;
        }
        System.out.println("List of Banks:");
        for (int i = 0; i < BANKS.size(); i++) {
            Bank b = BANKS.get(i);
            System.out.printf("[%d] %s (ID=%s)\n", i + 1, b.getBankName(), b.getBankId());
        }
    }

    /**
     * Retrieves the currently logged-in bank.
     * This method fixes the "undefined for the type banklauncher" error.
     */
    public Bank getLoggedBank() {
        return loggedBank;
    }

    public Bank getBank(Comparator<Bank> comparator, Bank bank) {
        for (Bank b : BANKS) {
            if (comparator.compare(b, bank) == 0) {
                return b;
            }
        }
        return null;
    }

    public Accounts.Account findAccount(String num) {
        if (!isLogged()) {
            System.out.println("No bank is currently logged in.");
            return null;
        }
        for (Account acc : loggedBank.getAccounts()) {
            if (acc.getAccountNumber().equals(num)) {
                return acc;
            }
        }
        return null;
    }

    public int bankSize() {
        return BANKS.size();
    }
}
