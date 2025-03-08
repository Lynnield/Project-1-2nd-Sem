package Main;

import java.util.Scanner;

import Bank.Bank;

public class Main {

    private static final Scanner input = new Scanner(System.in);
    /**
     * Option field used when selection options during menu prompts. Do not create a different
     * option variable in menus. Just use this instead.
     * 
     * @see #prompt(String, boolean)
     * @see #setOption() How Field objects are used.
     */
    public static Field<Integer, Integer> option = new Field<>("Option",
            Integer.class, -1, new Field.IntegerFieldValidator());

    // Global launcher objects for managing banks and current bank session.
    private static BankLauncher bankLauncher = new BankLauncher();
    private static Bank currentBank = null;

    public static void main(String[] args) {

        while (true) {
            showMenuHeader("Main Menu");
            System.out.println("[1] Accounts Login");
            System.out.println("[2] Bank Login");
            System.out.println("[3] Create New Bank");
            System.out.println("[4] Create New Account");
            System.out.println("[5] Exit");
            System.out.print("Select an option: ");
            int mainChoice = input.nextInt();
            input.nextLine(); // consume newline

            if (mainChoice == 1) {
                if (currentBank == null) {
                    System.out.println("No bank is currently logged in. Please login to a bank first.");
                } else {
                    accountLoginMenu();
                }
            } else if (mainChoice == 2) {
                bankLoginProcess();
            } else if (mainChoice == 3) {
                createNewBankProcess();
            } else if (mainChoice == 4) {
                createAccountProcess();
            } else if (mainChoice == 5) {
                System.out.println("Exiting. Thank you for banking!");
                break;
            } else {
                System.out.println("Invalid option!");
            }
        }
    }

    /**
     * Processes bank login.
     */
    private static void bankLoginProcess() {
        showMenuHeader("Bank Login");
        System.out.print("Enter Bank ID or Name: ");
        String bankIdOrName = input.nextLine();
        bankLauncher.bankLogin(bankIdOrName);
        currentBank = bankLauncher.getLoggedBank();
        if (currentBank != null) {
            System.out.println("Bank logged in: " + currentBank.getBankName());
        } else {
            System.out.println("Bank not found.");
        }
    }

    /**
     * Processes new bank creation.
     */
    private static void createNewBankProcess() {
        showMenuHeader("Create New Bank");
        System.out.print("Enter new Bank ID: ");
        String id = input.nextLine();
        System.out.print("Enter new Bank Name: ");
        String name = input.nextLine();
        bankLauncher.createNewBank(id, name);
        System.out.println("New bank created: " + name);
    }

    /**
     * Processes creation of a new account in the currently logged-in bank.
     */
    private static void createAccountProcess() {
        if (currentBank == null) {
            System.out.println("No bank is currently logged in. Please login to a bank first.");
            return;
        }
        showMenuHeader("Create New Account");
        System.out.println("[1] Savings Account");
        System.out.println("[2] Credit Account");
        System.out.print("Select account type: ");
        int type = input.nextInt();
        input.nextLine(); // consume newline
        String accountType = "";
        if (type == 1) {
            accountType = "savings";
        } else if (type == 2) {
            accountType = "credit";
        } else {
            System.out.println("Invalid account type selected.");
            return;
        }
        System.out.print("Enter new account number: ");
        String accNum = input.nextLine();
        System.out.print("Enter owner name: ");
        String ownerName = input.nextLine();
        System.out.print("Enter initial balance (or credit limit): ");
        double initialValue = input.nextDouble();
        input.nextLine(); // consume newline
        System.out.print("Enter account PIN: ");
        String pin = input.nextLine();
        bankLauncher.newAccounts(accountType, accNum, ownerName, initialValue, pin);
    }

    /**
     * Presents a submenu for account login and then directs to account operations.
     */
    private static void accountLoginMenu() {
        showMenuHeader("Account Login Menu");
        System.out.println("[1] Savings Account");
        System.out.println("[2] Credit Account");
        System.out.println("[3] Go Back");
        System.out.print("Select an option: ");
        int accChoice = input.nextInt();
        input.nextLine(); // consume newline

        if (accChoice == 1) {
            // Savings Account Login
            SavingsAccountLauncher saLauncher = new SavingsAccountLauncher();
            // Associate the current bank with the launcher.
            saLauncher.setLogSession(currentBank);
            System.out.print("Enter Savings Account Number: ");
            String accNum = input.nextLine();
            System.out.print("Enter PIN: ");
            String pin = input.nextLine();
            saLauncher.accountLogin(accNum, pin);
            if (saLauncher.isLoggedIn()) {
                savingsOperations(saLauncher);
            }
        } else if (accChoice == 2) {
            // Credit Account Login
            CreditAccountLauncher caLauncher = new CreditAccountLauncher();
            // Associate the current bank with the launcher.
            caLauncher.setLogSession(currentBank);
            System.out.print("Enter Credit Account Number: ");
            String accNum = input.nextLine();
            System.out.print("Enter PIN: ");
            String pin = input.nextLine();
            caLauncher.accountLogin(accNum, pin);
            if (caLauncher.isLoggedIn()) {
                creditOperations(caLauncher);
            }
        } else if (accChoice == 3) {
            // Go back to main menu
            return;
        } else {
            System.out.println("Invalid option.");
        }
    }

    /**
     * Provides operations for a logged-in Savings Account.
     */
    private static void savingsOperations(SavingsAccountLauncher saLauncher) {
        boolean exit = false;
        while (!exit) {
            showMenuHeader("Savings Account Menu");
            System.out.println("[1] Deposit");
            System.out.println("[2] Withdraw");
            System.out.println("[3] Fund Transfer");
            System.out.println("[4] Show Balance");
            System.out.println("[5] Logout");
            System.out.print("Select an option: ");
            int choice = input.nextInt();
            input.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    System.out.print("Enter deposit amount: ");
                    double deposit = input.nextDouble();
                    input.nextLine();
                    saLauncher.depositProcess(deposit);
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    double withdraw = input.nextDouble();
                    input.nextLine();
                    saLauncher.withdrawProcess(withdraw);
                    break;
                case 3:
                    System.out.print("Enter recipient account number: ");
                    String targetAcc = input.nextLine();
                    System.out.print("Enter transfer amount: ");
                    double transfer = input.nextDouble();
                    input.nextLine();
                    saLauncher.fundTransferProcess(targetAcc, transfer);
                    break;
                case 4:
                    System.out.println("Current Balance: " + saLauncher.getLoggedAccount().getBalance());
                    break;
                case 5:
                    saLauncher.logout();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Provides operations for a logged-in Credit Account.
     */
    private static void creditOperations(CreditAccountLauncher caLauncher) {
        boolean exit = false;
        while (!exit) {
            showMenuHeader("Credit Account Menu");
            System.out.println("[1] Charge Account");
            System.out.println("[2] Payment (from Savings Account)");
            System.out.println("[3] Recompense");
            System.out.println("[4] Show Available Credit and Current Debt");
            System.out.println("[5] Logout");
            System.out.print("Select an option: ");
            int choice = input.nextInt();
            input.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    System.out.print("Enter charge amount: ");
                    double charge = input.nextDouble();
                    input.nextLine();
                    caLauncher.getLoggedAccount().charge(charge);
                    break;
                case 2:
                    System.out.print("Enter Savings Account Number for payment: ");
                    String savAcc = input.nextLine();
                    System.out.print("Enter payment amount: ");
                    double payAmount = input.nextDouble();
                    input.nextLine();
                    caLauncher.paymentProcess(savAcc, payAmount);
                    break;
                case 3:
                    System.out.print("Enter recompense amount: ");
                    double recompense = input.nextDouble();
                    input.nextLine();
                    caLauncher.recompenseProcess(recompense);
                    break;
                case 4:
                    System.out.println("Available Credit: " + caLauncher.getLoggedAccount().getBalance());
                    System.out.println("Current Debt: " + caLauncher.getLoggedAccount().getCurrentDebt());
                    break;
                case 5:
                    caLauncher.logout();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    public static void showMenu(int menuIdx) {
        showMenu(menuIdx, 1);
    }

    public static void showMenu(int menuIdx, int inlineTexts) {
        String[] menu = Menu.getMenuOptions(menuIdx);
        if (menu == null) {
            System.out.println("Invalid menu index given!");
        } else {
            String space = inlineTexts == 0 ? "" : "%-20s";
            String fmt = "[%d] " + space;
            int count = 0;
            for (String s : menu) {
                count++;
                System.out.printf(fmt, count, s);
                if (count % inlineTexts == 0) {
                    System.out.println();
                }
            }
        }
    }

    public static String prompt(String phrase, boolean inlineInput) {
        System.out.print(phrase);
        if (inlineInput) {
            String val = input.next();
            input.nextLine();
            return val;
        }
        return input.nextLine();
    }

    public static void setOption() throws NumberFormatException {
        option.setFieldValue("Select an option: ");
    }

    public static int getOption() {
        return Main.option.getFieldValue();
    }

    public static void showMenuHeader(String menuTitle) {
        System.out.printf("<---- %s ----->\n", menuTitle);
    }
}
