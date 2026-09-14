import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ATM 
{

    private Bank bank;
    private Account currentAccount;
    private Scanner scanner;
    public ATM(Bank bank) 
    {
        this.bank = bank;
        this.scanner = new Scanner(System.in);
    }
    public void start()
    {
        System.out.println("=========================================");
        System.out.println("          CAMPUS BANKING ATM             ");
        System.out.println("=========================================");
        int attempts = 0;
        boolean loginSuccess = false;
        while (attempts < 3) {
            System.out.print("Enter User ID: ");
            String id = scanner.nextLine().trim();
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine().trim();
            Account account = bank.getAccount(id);
            if (account != null && account.validatePin(pin)) {
                currentAccount = account;
                loginSuccess = true;
                System.out.println();
                System.out.println("Login successful!");
                System.out.println("Welcome, " + currentAccount.getName() + "!");
                break;
            } else {
                attempts++;
                System.out.println("Invalid User ID or PIN.");
                System.out.println("Attempts used: " + attempts + "/3");
            }
        }
        if (loginSuccess) {
            displayMainMenu();
        } else {
            System.out.println();
            System.out.println("Three incorrect attempts.");
            System.out.println("Account blocked for this session.");
        }
    }

    // Main ATM menu
    private void displayMainMenu() {

        boolean exit = false;

        while (!exit) {

            System.out.println();
            System.out.println("=========================================");
            System.out.println("                MAIN MENU                ");
            System.out.println("=========================================");
            System.out.println("Current Balance : Rs." + currentAccount.getBalance());

            double remainingLimit =
                    currentAccount.getDailyLimit()
                    - currentAccount.getDailyWithdrawAmount();

            System.out.println("Daily Limit Left: Rs." + remainingLimit);

            System.out.println("-----------------------------------------");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw Cash");
            System.out.println("3. Deposit Cash");
            System.out.println("4. Transfer Money");
            System.out.println("5. Print Mini Statement");
            System.out.println("6. Change PIN");
            System.out.println("7. Logout");
            System.out.println("-----------------------------------------");

            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    viewHistory();
                    break;

                case "2":
                    handleWithdrawal();
                    break;

                case "3":
                    handleDeposit();
                    break;

                case "4":
                    handleTransfer();
                    break;

                case "5":
                    handleReceiptExport();
                    break;

                case "6":
                    changePin();
                    break;

                case "7":
                    System.out.println();
                    System.out.println("Thank you for using Campus Banking ATM.");
                    System.out.println("Goodbye!");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1 to 7.");
            }
        }
    }

    // Displays transaction history
    private void viewHistory() {

        System.out.println();
        System.out.println("----------- TRANSACTION HISTORY -----------");

        ArrayList<Transaction> transactions =
                currentAccount.getTransactionHistory();

        if (transactions.isEmpty()) {

            System.out.println("No transactions found.");

        } else {

            for (int i = 0; i < transactions.size(); i++) {

                System.out.println(
                        transactions.get(i).getReceiptLine()
                );
            }
        }

        System.out.println("-------------------------------------------");
        pauseBeforeMenu();
    }

    // Handles withdrawal
    private void handleWithdrawal() {

        System.out.print("Enter amount to withdraw: Rs.");

        double amount = getValidNumberInput();

        if (amount <= 0) {
            return;
        }

        // ATM accepts only multiples of ₹100
        if (amount % 100 != 0) {

            System.out.println(
                    "Please enter an amount in multiples of ₹100."
            );
            pauseBeforeMenu();

            return;
        }

        if (currentAccount.withdraw(amount)) {

            System.out.println("Withdrawal successful.");
            System.out.println("Please collect your cash.");
            System.out.println("----------------------------------------");
            System.out.println("Amount Withdrawn:Rs."+ amount);
            System.out.println("New Balance:₹"+currentAccount.getBalance());
            System.out.println("----------------------------------------");
            // Calculate number of notes
            int remaining = (int) amount;

            int notes500 = remaining / 500;
            remaining = remaining % 500;

            int notes200 = remaining / 200;
            remaining = remaining % 200;

            int notes100 = remaining / 100;

            System.out.println();
            System.out.println("Cash breakdown:");

            if (notes500 > 0) {
                System.out.println("Rs.500 notes : " + notes500);
            }

            if (notes200 > 0) {
                System.out.println("Rs.₹200 notes : " + notes200);
            }

            if (notes100 > 0) {
                System.out.println("Rs.100 notes : " + notes100);
            }

            showLowBalanceAlert();
        }
        pauseBeforeMenu();
    }

    // Handles deposit
    private void handleDeposit() {

    System.out.print("Enter deposit amount: Rs.");

    double amount = getValidNumberInput();

    if (amount <= 0) {
        return;
    }

    currentAccount.deposit(amount);

    System.out.println();
    System.out.println("✓ Deposit Successful");
    System.out.println("-----------------------------------------");
    System.out.println("Amount Deposited : Rs." + amount);
    System.out.println("New Balance      : Rs."
            + currentAccount.getBalance());
    System.out.println("-----------------------------------------");

    pauseBeforeMenu();
}

    // Handles money transfer
    private void handleTransfer() {

        System.out.print("Enter receiver User ID: ");

        String receiverId = scanner.nextLine().trim();

        // Prevent transfer to yourself
        if (receiverId.equals(currentAccount.getUserId())) {

            System.out.println(
                    "You cannot transfer money to yourself."
            );

            return;
        }

        // Check receiver exists
        Account receiver = bank.getAccount(receiverId);

        if (receiver == null) {

            System.out.println("Receiver account not found.");

            return;
        }

        System.out.println("Receiver account found.");

        System.out.print("Enter amount to transfer: Rs.");

        double amount = getValidNumberInput();

        if (amount <= 0) {
            return;
        }

        if (currentAccount.transferTo(receiver, amount)) {

    System.out.println();
    System.out.println("✓ Transfer Successful");
    System.out.println("-----------------------------------------");
    System.out.println("Amount      : Rs." + amount);
    System.out.println("Receiver ID : " + receiverId);
    System.out.println("New Balance : Rs."
            + currentAccount.getBalance());
    System.out.println("-----------------------------------------");

    showLowBalanceAlert();

    pauseBeforeMenu();
}
    }

    // Change PIN
    private void changePin() {

        System.out.print("Enter current PIN: ");

        String currentPin = scanner.nextLine().trim();

        if (!currentAccount.validatePin(currentPin)) {

            System.out.println("Incorrect current PIN.");

            return;
        }

        System.out.print("Enter new 4-digit PIN: ");

        String newPin = scanner.nextLine().trim();

        if (newPin.length() != 4 || !newPin.matches("\\d+")) {

            System.out.println(
                    "PIN must contain exactly 4 digits."
            );

            return;
        }

        System.out.print("Confirm new PIN: ");

        String confirmPin = scanner.nextLine().trim();

        if (!newPin.equals(confirmPin)) {

            System.out.println("PINs do not match.");

            return;
        }

        currentAccount.changePin(newPin);

        System.out.println("PIN changed successfully.");
        pauseBeforeMenu();
    }

    // Low balance warning
    private void showLowBalanceAlert() {

        if (currentAccount.getBalance() < 2000) {

            System.out.println();
            System.out.println("WARNING: Your balance is low.");
            System.out.println("Current Balance: Rs."
                    + currentAccount.getBalance());
        }
    }

    // Creates a text mini statement
    private void handleReceiptExport() {

        String fileName =
                "ATM_Receipt_"
                + currentAccount.getUserId()
                + ".txt";

        try {

            FileWriter writer =
                    new FileWriter(fileName);

            writer.write("=========================================\n");
            writer.write("          CAMPUS BANKING ATM             \n");
            writer.write("             MINI STATEMENT              \n");
            writer.write("=========================================\n");

            writer.write("User ID: "
                    + currentAccount.getUserId() + "\n");

            writer.write("Balance: ₹"
                    + currentAccount.getBalance() + "\n");

            writer.write("-----------------------------------------\n");

            ArrayList<Transaction> transactions =
                    currentAccount.getTransactionHistory();

            if (transactions.isEmpty()) {

                writer.write("No transactions found.\n");

            } else {

                for (int i = 0; i < transactions.size(); i++) {

                    writer.write(
                            transactions.get(i).getReceiptLine()
                            + "\n"
                    );
                }
            }

            writer.write("-----------------------------------------\n");
            writer.write("Thank you for banking with us!\n");
            writer.write("=========================================\n");

            writer.close();

            System.out.println();
            System.out.println("Mini statement created: " + fileName);
        }
        catch (Exception e) {

            System.out.println(
                    "Unable to create mini statement."
            );
        }
        pauseBeforeMenu();
    }

    // Reads and validates amount
    private double getValidNumberInput() {

        while (true) {

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("cancel")) {

                System.out.println("Operation cancelled.");

                return -1;
            }

            try {

                double amount =
                        Double.parseDouble(input);

                if (amount <= 0) {

                    System.out.println(
                            "Amount must be greater than zero."
                    );

                    System.out.print(
                            "Enter amount again or type cancel: Rs."
                    );

                    continue;
                }

                return amount;

            } catch (Exception e) {

                System.out.println(
                        "Please enter numbers only."
                );

                System.out.print(
                        "Enter amount again or type cancel: Rs."
                );
            }
        }
    }
    private void pauseBeforeMenu()
    {
        System.out.println();
        System.out.println("PRESS ENTER TO RETURN TO MAIN MENU...");
        scanner.nextLine();
    }
}