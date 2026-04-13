import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Helper class used to record transaction history.
 */
class Transaction {
    public enum Type { DEPOSIT, WITHDRAWAL }
    private final Type type;
    private final double amount;
    private final double newBalance;

    public Transaction(Type type, double amount, double newBalance) {
        this.type = type;
        this.amount = amount;
        this.newBalance = newBalance;
    }

    public Type getType() { return type; }
    public double getAmount() { return amount; }
    public double getNewBalance() { return newBalance; }
}

/**
 * Bank account class developed iteratively using TDD,
 * implementing requirements R1–R5 and transaction history.
 */
public class BankAccountManagementSystem_24039837 {
    private final String accountId; // R1.1/R1.3
    private double balance;
    private final List<Transaction> history; // Implicit requirement: transaction history

    // R1.1: account creation operation (with initial balance)
    public BankAccountManagementSystem_24039837(String accountId, double initialBalance) {
        // R1.2: validate that the initial balance must be positive (Iteration 1)
        if (initialBalance <= 0) {
            throw new IllegalArgumentException("R1.2: Initial balance must be a positive numeric value.");
        }
        this.accountId = accountId;
        this.balance = initialBalance;
        this.history = new ArrayList<>();
    }

    // R5.1: balance inquiry operation
    public double getBalance() {
        return balance;
    }

    // Accessor for transaction history
    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(history);
    }

    // R2.1: deposit operation
    public void deposit(double amount) {
        // R2.2: validate that the deposit amount must be positive (Iteration 2)
        if (amount <= 0) {
            throw new IllegalArgumentException("R2.2: Deposit amount must be a positive numeric value.");
        }

        // R2.1: perform the deposit
        balance += amount;

        // Record transaction in history (Iteration 4)
        history.add(new Transaction(Transaction.Type.DEPOSIT, amount, balance));
    }

    // R3.1: withdrawal operation
    public void withdraw(double amount) {
        // R3.2: validate that the withdrawal amount must be positive (Iteration 3)
        if (amount <= 0) {
            throw new IllegalArgumentException("R3.2: Withdrawal amount must be a positive numeric value.");
        }

        // R4.2/R4.1: overdraft protection (Iteration 3)
        if (amount > balance) {
            throw new IllegalArgumentException("R4.2: Insufficient funds. Withdrawal exceeds available balance.");
        }

        // R3.1: perform the withdrawal
        balance -= amount;

        // Record transaction in history (Iteration 4)
        history.add(new Transaction(Transaction.Type.WITHDRAWAL, amount, balance));
    }
}
