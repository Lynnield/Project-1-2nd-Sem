package Launcher;

import Accounts.StudentAccount;
import Accounts.Account;
import Accounts.IllegalAccountType;
import Bank.Bank;

/**
 * A launcher specifically for StudentAccount operations.
 * 
 * UML Methods:
 *   + depositProcess(double): void
 *   + withdrawProcess(double): void
 *   + fundTransferProcess(String, double): void
 *   + getLoggedAccount(): StudentAccount
 *   + logout(): void
 */
public class StudentAccountLauncher extends AccountLauncher {

    public static void main(String[] args) {
        // Assuming the Bank constructor requires bankId and bankName
        Bank bank = new Bank("001", "MyBank");
        StudentAccount studentAccount = new StudentAccount(bank, "123456", "John Doe", 1000.0, "1234");

        // Perform some operations
        System.out.println("Initial Balance: " + studentAccount.getBalance());

        // Deposit
        studentAccount.cashDeposit(500.0);
        System.out.println("Balance after deposit: " + studentAccount.getBalance());

        // Withdrawal
        studentAccount.withdrawal(200.0);
        System.out.println("Balance after withdrawal: " + studentAccount.getBalance());

        // Transfer to another account
        StudentAccount anotherAccount = new StudentAccount(bank, "654321", "Jane Doe", 500.0, "4321");
        try {
            studentAccount.transfer(anotherAccount, 300.0);
            System.out.println("Balance after transfer: " + studentAccount.getBalance());
            System.out.println("Another account balance after transfer: " + anotherAccount.getBalance());
        } catch (IllegalAccountType e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Processes a deposit for the logged-in StudentAccount.
     * @param amount the deposit amount.
     */
    public void depositProcess(double amount) {
        if (loggedAccount == null) {
            System.out.println("No Student Account logged in!");
            return;
        }
        if (!(loggedAccount instanceof StudentAccount)) {
            System.out.println("Logged account is not a StudentAccount!");
            return;
        }
        StudentAccount sa = (StudentAccount) loggedAccount;
        sa.cashDeposit(amount);
    }

    /**
     * Processes a withdrawal for the logged-in StudentAccount.
     * @param amount the withdrawal amount.
     */
    public void withdrawProcess(double amount) {
        if (loggedAccount == null) {
            System.out.println("No Student Account logged in!");
            return;
        }
        if (!(loggedAccount instanceof StudentAccount)) {
            System.out.println("Logged account is not a StudentAccount!");
            return;
        }
        StudentAccount sa = (StudentAccount) loggedAccount;
        sa.withdrawal(amount);
    }

    /**
     * Processes a fund transfer for the logged-in StudentAccount.
     * @param targetAccNum the recipient account number.
     * @param amount the transfer amount.
     */
    public void fundTransferProcess(String targetAccNum, double amount) {
        if (loggedAccount == null) {
            System.out.println("No Student Account logged in!");
            return;
        }
        if (!(loggedAccount instanceof StudentAccount)) {
            System.out.println("Logged account is not a StudentAccount!");
            return;
        }
        StudentAccount sa = (StudentAccount) loggedAccount;
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
     * Shows transactions for the logged-in StudentAccount.
     */
    public void showTransactions() {
        if (loggedAccount == null) {
            System.out.println("No Student Account logged in!");
            return;
        }
        if (!(loggedAccount instanceof StudentAccount)) {
            System.out.println("Logged account is not a StudentAccount!");
            return;
        }
        StudentAccount sa = (StudentAccount) loggedAccount;
        System.out.println("Transactions for account " + sa.getAccountNumber() + ":");
        sa.getTransactions().forEach(transaction -> {
            System.out.println(transaction.getFormattedDescription());
        });
    }

    /**
     * Logs out of the current StudentAccount session.
     */
    @Override
    public void logout() {
        destroyLogSession();
    }

    /**
     * Returns the currently logged-in StudentAccount.
     * @return the StudentAccount object if logged in, else null.
     */
    public StudentAccount getLoggedAccount() {
        if (loggedAccount instanceof StudentAccount) {
            return (StudentAccount) loggedAccount;
        }
        return null;
    }
}
