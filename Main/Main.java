package Main;

import Bank.Bank;
import Launcher.BankLauncher;
import Launcher.CreditAccountLauncher;
import Launcher.SavingsAccountLauncher;
import Launcher.StudentAccountLauncher;
import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);
    private static BankLauncher bankLauncher = new BankLauncher();
    private static Bank currentBank = null;
    private static Field<Object, Object> option;

    public static void main(String[] args) {
        bankLauncher.createNewBank("001", "Alpha Bank");

        while (true) {
            showMenuHeader("Main Menu");
            System.out.println("[1] Accounts Login");
            System.out.println("[2] Bank Login");
            System.out.println("[3] Create New Bank");
            System.out.println("[4] Exit");
            System.out.print("Select an option: ");
            int mainChoice = input.nextInt();
            input.nextLine(); // consume newline

            switch (mainChoice) {
                case 1:
                    if (currentBank == null) {
                        System.out.println("No bank is currently logged in. Please login to a bank first.");
                    } else {
                        accountLoginMenu();
                    }
                    break;
                case 2:
                    bankLoginMenu();
                    break;
                case 3:
                    createNewBankProcess();
                    break;
                case 4:
                    System.out.println("Exiting. Thank you for banking!");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private static void bankLoginMenu() {
        boolean exit = false;
        while (!exit) {
            showMenuHeader("Bank Login Menu");
            System.out.println("[1] Login to Bank");
            System.out.println("[2] Show Banks");
            System.out.println("[3] Go Back");
            System.out.print("Select an option: ");
            int choice = input.nextInt();
            input.nextLine(); // consume newline

            if (choice == 1) {
                bankLoginProcess();
                exit = true;
            } else if (choice == 2) {
                bankLauncher.showBanksMenu();
            } else if (choice == 3) {
                exit = true;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    private static void bankLoginProcess() {
        showMenuHeader("Bank Login");
        System.out.print("Enter Bank ID or Name: ");
        String bankIdOrName = input.nextLine();
        bankLauncher.bankLogin(bankIdOrName);
        currentBank = bankLauncher.getLoggedBank();
        if (currentBank != null) {
            System.out.println("Bank logged in: " + currentBank.getBankName());
            bankMenu();
        } else {
            System.out.println("Bank not found.");
        }
    }

    private static void createNewBankProcess() {
        showMenuHeader("Create New Bank");
        System.out.print("Enter new Bank ID: ");
        String id = input.nextLine();
        System.out.print("Enter new Bank Name: ");
        String name = input.nextLine();
        bankLauncher.createNewBank(id, name);
        System.out.println("New bank created: " + name);
    }

    private static void bankMenu() {
        boolean exit = false;
        while (!exit) {
            showMenuHeader(currentBank.getBankName() + " Menu");
            System.out.println("[1] Show Accounts");
            System.out.println("[2] Create New Account");
            System.out.println("[3] Accounts login");
            System.out.println("[4] Log Out");
            System.out.print("Select an option: ");
            int choice = input.nextInt();
            input.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    bankLauncher.showAccounts();
                    break;
                case 2:
                    createAccountProcess();
                    break;
                case 3:
                    accountLoginMenu();
                    break;
                case 4:
                    currentBank = null;
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void createAccountProcess() {
        if (currentBank == null) {
            System.out.println("No bank is currently logged in. Please login to a bank first.");
            return;
        }
        showMenuHeader("Create New Account");
        System.out.println("[1] Savings Account");
        System.out.println("[2] Credit Account");
        System.out.println("[3] Student Account");
        System.out.print("Select account type: ");
        int type = input.nextInt();
        input.nextLine(); // consume newline
        String accountType = "";
        switch (type) {
            case 1:
                accountType = "savings";
                break;
            case 2:
                accountType = "credit";
                break;
            case 3:
                accountType = "student";
                break;
            default:
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

    private static void accountLoginMenu() {
        showMenuHeader("Account Login Menu");
        System.out.println("[1] Savings Account");
        System.out.println("[2] Credit Account");
        System.out.println("[3] Student Account");
        System.out.println("[4] Go Back");
        System.out.print("Select an option: ");
        int accChoice = input.nextInt();
        input.nextLine(); // consume newline

        if (accChoice == 1) {
            SavingsAccountLauncher saLauncher = new SavingsAccountLauncher();
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
            CreditAccountLauncher caLauncher = new CreditAccountLauncher();
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
            StudentAccountLauncher saLauncher = new StudentAccountLauncher();
            saLauncher.setLogSession(currentBank);
            System.out.print("Enter Student Account Number: ");
            String accNum = input.nextLine();
            System.out.print("Enter PIN: ");
            String pin = input.nextLine();
            saLauncher.accountLogin(accNum, pin);
            if (saLauncher.isLoggedIn()) {
                studentOperations(saLauncher);
            }
        } else if (accChoice == 4) {
            return;
        } else {
            System.out.println("Invalid option.");
        }
    }

    private static void savingsOperations(SavingsAccountLauncher saLauncher) {
        boolean exit = false;
        while (!exit) {
            showMenuHeader("Savings Account Menu");
            System.out.println("[1] Deposit");
            System.out.println("[2] Withdraw");
            System.out.println("[3] Fund Transfer");
            System.out.println("[4] Show Balance");
            System.out.println("[5] Show Transactions");
            System.out.println("[6] Logout");
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
                    saLauncher.showTransactions();
                    break;
                case 6:
                    saLauncher.logout();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void studentOperations(StudentAccountLauncher saLauncher) {
        boolean exit = false;
        while (!exit) {
            showMenuHeader("Student's Account Menu");
            System.out.println("[1] Deposit");
            System.out.println("[2] Withdraw");
            System.out.println("[3] Fund Transfer");
            System.out.println("[4] Show Balance");
            System.out.println("[5] Show Transactions");
            System.out.println("[6] Logout");
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
                    saLauncher.showTransactions();
                    break;
                case 6:
                    saLauncher.logout();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void creditOperations(CreditAccountLauncher caLauncher) {
        boolean exit = false;
        while (!exit) {
            showMenuHeader("Credit Account Menu");
            System.out.println("[1] Charge Account");
            System.out.println("[2] Payment (from Savings Account)");
            System.out.println("[3] Recompense");
            System.out.println("[4] Show Available Credit and Current Debt");
            System.out.println("[5] Show Transactions");
            System.out.println("[6] Logout");
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
                    caLauncher.showTransactions();
                    break;
                case 6:
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
        return (Integer) Main.option.getFieldValue();
    }

    public static void showMenuHeader(String menuTitle) {
        System.out.printf("<---- %s ----->\n", menuTitle);
    }
}
