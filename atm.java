import java.util.ArrayList;
import java.util.Scanner;

class User {
    private String userId;
    private String encryptedPin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public User(String userId, String encryptedPin) {
        this.userId = userId;
        this.encryptedPin = encryptedPin;
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getEncryptedPin() {
        return encryptedPin;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: " + amount);
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrawn: " + amount);
        }
    }

    public void transfer(User receiver, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            receiver.deposit(amount);
            transactionHistory.add("Transferred: " + amount + " to " + receiver.getUserId());
        }
    }
}

public class atm {
    private static ArrayList<User> users = new ArrayList<>(); 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        users.add(new User("123456", "1234")); 

        System.out.println("Welcome to the ATM");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String enteredPin = scanner.nextLine();

        User currentUser = authenticateUser(userId, enteredPin);

        if (currentUser != null) {
            System.out.println("Authentication Successful");
            performTransactions(currentUser, scanner);
        } else {
            System.out.println("Authentication Failed. Exiting...");
        }
    }

    public static User authenticateUser(String userId, String enteredPin) {
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getEncryptedPin().equals(hashPin(enteredPin))) {
                return user;
            }
        }
        return null;
    }

    public static String hashPin(String pin) {
        
        return pin;
    }

    public static void performTransactions(User user, Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\nChoose an operation:");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Transaction History");
            System.out.println("6. Quit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Current Balance: " + user.getBalance());
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    user.deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    user.withdraw(withdrawalAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient's account number: ");
                    String recipientUserId = scanner.next();
                    User recipient = findUser(recipientUserId);
                    if (recipient != null) {
                        System.out.print("Enter transfer amount: ");
                        double transferAmount = scanner.nextDouble();
                        user.transfer(recipient, transferAmount);
                    } else {
                        System.out.println("Recipient's account not found.");
                    }
                    break;
                case 5:
                    System.out.println("Transaction History:");
                    for (String transaction : user.getTransactionHistory()) {
                        System.out.println(transaction);
                    }
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Thank you for using the ATM. Have a nice day!");
    }

    public static User findUser(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}
