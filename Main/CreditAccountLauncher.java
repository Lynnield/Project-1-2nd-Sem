package Main;

import Accounts.CreditAccount;
import Accounts.Account;
import Accounts.SavingsAccount;
import Accounts.IllegalAccountType;

/**
 * A launcher specifically for CreditAccount operations.
 * 
 * UML Methods:
 *   + paymentProcess(String, double): void
 *   + recompenseProcess(double): void
 *   + getLoggedAccount(): CreditAccount
 *   + logout(): void
 */
public class CreditAccountLauncher extends AccountLauncher {

    /**
     * Processes a payment for the logged-in CreditAccount using a SavingsAccount.
     * @param savingsAccNum the account number of the SavingsAccount making the payment.
     * @param amount the payment amount.
     */
    public void paymentProcess(String savingsAccNum, double amount) {
        if (loggedAccount == null) {
            System.out.println("No Credit Account logged in!");
            return;
        }
        if (!(loggedAccount instanceof CreditAccount)) {
            System.out.println("Logged account is not a CreditAccount!");
            return;
        }
        CreditAccount ca = (CreditAccount) loggedAccount;
        if (assocBank == null) {
            System.out.println("No bank selected!");
            return;
        }
        Account source = assocBank.getBankAccount(savingsAccNum);
        if (source == null || !(source instanceof SavingsAccount)) {
            System.out.println("Source Savings Account not found!");
            return;
        }
        try {
            ca.pay(source, amount);
        } catch (IllegalAccountType e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Processes a recompense operation for the logged-in CreditAccount.
     * @param amount the recompense amount.
     */
    public void recompenseProcess(double amount) {
        if (loggedAccount == null) {
            System.out.println("No Credit Account logged in!");
            return;
        }
        if (!(loggedAccount instanceof CreditAccount)) {
            System.out.println("Logged account is not a CreditAccount!");
            return;
        }
        CreditAccount ca = (CreditAccount) loggedAccount;
        ca.recompense(amount);
    }

    /**
     * Logs out of the current CreditAccount session.
     */
    @Override
    public void logout() {
        destroyLogSession();
    }

    /**
     * Returns the currently logged-in CreditAccount.
     * @return the CreditAccount object if logged in, else null.
     */
    public CreditAccount getLoggedAccount() {
        if (loggedAccount instanceof CreditAccount) {
            return (CreditAccount) loggedAccount;
        }
        return null;
    }
}
