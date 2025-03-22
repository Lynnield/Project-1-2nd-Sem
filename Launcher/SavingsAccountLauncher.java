package Launcher;

import Accounts.SavingsAccount;
import Accounts.Account;
import Accounts.IllegalAccountType;

/**
 * A launcher specifically for SavingsAccount operations.
 * 
 * UML Methods:
 *   + depositProcess(double): void
 *   + withdrawProcess(double): void
 *   + fundTransferProcess(String, double): void
 *   + getLoggedAccount(): SavingsAccount
 *   + logout(): void
 */
public class SavingsAccountLauncher extends AccountLauncher {

    /**
     * Processes a deposit for the logged-in SavingsAccount.
     * @param amount the deposit amount.
     */
    public void depositProcess(double amount) {
        if (loggedAccount == null) {
            System.out.println("No Savings Account logged in!");
            return;
        }
        if (!(loggedAccount instanceof SavingsAccount)) {
            System.out.println("Logged account is not a SavingsAccount!");
            return;
        }
        SavingsAccount sa = (SavingsAccount) loggedAccount;
        sa.cashDeposit(amount);
    }

    /**
     * Processes a withdrawal for the logged-in SavingsAccount.
     * @param amount the withdrawal amount.
     */
    public void withdrawProcess(double amount) {
        if (loggedAccount == null) {
            System.out.println("No Savings Account logged in!");
            return;
        }
        if (!(loggedAccount instanceof SavingsAccount)) {
            System.out.println("Logged account is not a SavingsAccount!");
            return;
        }
        SavingsAccount sa = (SavingsAccount) loggedAccount;
        sa.withdrawal(amount);
    }

    /**
     * Processes a fund transfer for the logged-in SavingsAccount.
     * @param targetAccNum the recipient account number.
     * @param amount the transfer amount.
     */
    public void fundTransferProcess(String targetAccNum, double amount) {
        if (loggedAccount == null) {
            System.out.println("No Savings Account logged in!");
            return;
        }
        if (!(loggedAccount instanceof SavingsAccount)) {
            System.out.println("Logged account is not a SavingsAccount!");
            return;
        }
        SavingsAccount sa = (SavingsAccount) loggedAccount;
        if (assocBank == null) {
            System.out.println("No bank selected!");
            return;
        }
        Account target = assocBank.getBankAccount(targetAccNum);
        if (target == null) {
            System.out.println("Recipient account not found!");
            return;
        }
        try {
            sa.transfer(target, amount);
        } catch (IllegalAccountType e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Shows transactions for the logged-in SavingsAccount.
     */
    public void showTransactions() {
        if (loggedAccount == null) {
            System.out.println("No Savings Account logged in!");
            return;
        }
        if (!(loggedAccount instanceof SavingsAccount)) {
            System.out.println("Logged account is not a SavingsAccount!");
            return;
        }
        SavingsAccount sa = (SavingsAccount) loggedAccount;
        System.out.println("Transactions for account " + sa.getAccountNumber() + ":");
        sa.getTransactions().forEach(transaction -> {
            System.out.println(transaction.getFormattedDescription());
        });
    }

    /**
     * Logs out of the current SavingsAccount session.
     */
    @Override
    public void logout() {
        destroyLogSession();
    }

    /**
     * Returns the currently logged-in SavingsAccount.
     * @return the SavingsAccount object if logged in, else null.
     */
    public SavingsAccount getLoggedAccount() {
        if (loggedAccount instanceof SavingsAccount) {
            return (SavingsAccount) loggedAccount;
        }
        return null;
    }
}
