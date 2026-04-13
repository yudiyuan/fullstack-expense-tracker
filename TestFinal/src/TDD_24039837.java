import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

// Helper manager class used to simulate R1.3 (duplicate account check)
class AccountManager {
    // Static list used to simulate storage of existing account IDs
    private static final List<String> existingAccountIds = new ArrayList<>();

    public static BankAccountManagementSystem_24039837 createAccount(String accountId, double initialBalance) {
        // R1.3 (green phase): check whether the ID already exists
        if (existingAccountIds.contains(accountId)) {
            throw new IllegalArgumentException("Account ID already exists. R1.3: Creation of duplicate accounts prevented.");
        }

        // Successfully create a new BankAccountManagementSystem_24039837 instance
        BankAccountManagementSystem_24039837 account =
                new BankAccountManagementSystem_24039837(accountId, initialBalance);
        existingAccountIds.add(accountId);
        return account;
    }

    // UTILITY: reset manager state between tests
    public static void resetManager() {
        existingAccountIds.clear();
    }
}

public class TDD_24039837 {

    // Helper method: directly create a BankAccountManagementSystem_24039837 (for testing internal logic)
    private BankAccountManagementSystem_24039837 setupAccount(String id, double initialBalance) {
        return new BankAccountManagementSystem_24039837(id, initialBalance);
    }

    // --- Iteration 1: Account creation & balance (R1.1, R1.2, R5.1) ---

    @Test
    void testAccountCreationSuccessful() {
        // R1.1/R5.1: ensure an account can be created and its balance can be queried.
        BankAccountManagementSystem_24039837 account = setupAccount("1001", 50.0);
        assertEquals(50.0, account.getBalance(), "R1.1/R5.1: Account balance should match the initial balance.");
    }

    @Test
    void testInitialBalanceIsPositiveFails() {
        // R1.2: ensure that the initial balance must be positive.
        // Red: constructor does not validate the initial balance. Green: implement the R1.2 check.

        // Test zero initial balance
        assertThrows(IllegalArgumentException.class, () -> {
            new BankAccountManagementSystem_24039837("1002", 0.0);
        }, "R1.2: An initial balance of 0.0 should be rejected.");

        // Test negative initial balance
        assertThrows(IllegalArgumentException.class, () -> {
            new BankAccountManagementSystem_24039837("1003", -10.0);
        }, "R1.2: A negative initial balance should be rejected.");
    }

    // --- Iteration 2: Deposit (R2.1, R2.2) ---

    @Test
    void testDepositSuccessful() {
        // R2.1: ensure that a valid deposit succeeds.
        BankAccountManagementSystem_24039837 account = setupAccount("2001", 100.0);
        account.deposit(50.0);
        assertEquals(150.0, account.getBalance(), "R2.1: Balance should increase after a deposit.");
    }

    @Test
    void testDepositNonPositiveAmountFails() {
        // R2.2: ensure that the deposit amount must be positive.
        // Red: deposit() does not validate the amount. Green: implement the R2.2 check.
        BankAccountManagementSystem_24039837 account = setupAccount("2002", 100.0);

        // Test deposit of 0.0
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(0.0);
        }, "R2.2: A deposit of 0.0 should be rejected.");

        // Balance should remain unchanged after a failed deposit
        assertEquals(100.0, account.getBalance(), "R2.2: A failed deposit must not change the balance.");
    }

    // --- Iteration 3: Withdrawal and overdraft (R3, R4) ---

    @Test
    void testWithdrawSuccessful() {
        // R3.1: ensure that a valid withdrawal succeeds.
        BankAccountManagementSystem_24039837 account = setupAccount("3001", 200.0);
        account.withdraw(75.0);
        assertEquals(125.0, account.getBalance(), "R3.1: Balance should decrease after a successful withdrawal.");
    }

    @Test
    void testWithdrawInsufficientFundsFails() {
        // R4.2/R4.1: ensure that overdrawing is rejected and overdrafts are prevented.
        // Red: withdraw() does not enforce R4. Green: implement R4.2/R4.1 checks.
        BankAccountManagementSystem_24039837 account = setupAccount("3002", 100.0);

        // Attempt to withdraw 150.00
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(150.0);
        }, "R4.2/R4.1: Withdrawal should be rejected when funds are insufficient.");

        // Balance should remain unchanged after a failed withdrawal
        assertEquals(100.0, account.getBalance(), "R4.2: A failed withdrawal must not change the balance.");
    }

    @Test
    void testWithdrawNonPositiveAmountFails() {
        // R3.2: ensure that the withdrawal amount must be positive.
        // Red: withdraw() does not validate the amount. Green: implement the R3.2 check.
        BankAccountManagementSystem_24039837 account = setupAccount("3003", 100.0);

        // Test withdrawal of 0.0
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(0.0);
        }, "R3.2: A withdrawal of 0.0 should be rejected.");

        // Balance should remain unchanged after a failed withdrawal
        assertEquals(100.0, account.getBalance(), "R3.2: A failed withdrawal must not change the balance.");
    }

    // --- Iteration 4: Transaction history (implicit requirement) ---

    @Test
    void testDepositRecordsTransaction() {
        // Ensure that a deposit operation is recorded in the transaction history.
        // Red: history mechanism not implemented. Green: implement Transaction class and recording logic.
        BankAccountManagementSystem_24039837 account = setupAccount("4001", 100.0);
        account.deposit(50.0);

        List<Transaction> history = account.getTransactionHistory();
        assertEquals(1, history.size(), "A deposit should result in one transaction record.");
        Transaction t = history.get(0);
        assertEquals(Transaction.Type.DEPOSIT, t.getType(), "The transaction type must be DEPOSIT.");
        assertEquals(150.0, t.getNewBalance(), "The recorded balance should equal the final balance after the deposit.");
    }

    @Test
    void testWithdrawalRecordsTransaction() {
        // Ensure that a withdrawal operation is recorded in the transaction history.
        BankAccountManagementSystem_24039837 account = setupAccount("4002", 200.0);
        account.withdraw(50.0);

        List<Transaction> history = account.getTransactionHistory();
        assertEquals(1, history.size(), "A withdrawal should result in one transaction record.");
        Transaction t = history.get(0);
        assertEquals(Transaction.Type.WITHDRAWAL, t.getType(), "The transaction type must be WITHDRAWAL.");
        assertEquals(150.0, t.getNewBalance(), "The recorded balance should equal the final balance after the withdrawal.");
    }

    // --- Iteration 5: Duplicate account check (R1.3) ---

    @Test
    void testDuplicateAccountCreationFails() {
        // R1.3: simulate an account manager that rejects duplicate account IDs.
        // Red: AccountManager does not enforce ID uniqueness. Green: implement ID uniqueness check.
        AccountManager.resetManager(); // Reset state between tests

        // First creation should succeed
        AccountManager.createAccount("5001", 100.0);

        // Second creation with the same ID should fail
        assertThrows(IllegalArgumentException.class, () -> {
            AccountManager.createAccount("5001", 200.0);
        }, "R1.3: Creating an account with a duplicate ID should throw an exception.");
    }
}
