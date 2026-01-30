package safebank.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    // Transaction deposit() Tests
    @Test
    public void deposit_Transaction_return(){
        Transaction transaction = Transaction.deposit(50, 450);
        assertAll("Transaction fields",
                () -> assertEquals("DEPOSIT", transaction.getType()),
                () -> assertEquals(50, transaction.getAmount()),
                () -> assertEquals(450, transaction.getBalanceAfter())
        );
    }

    @Test
    public void deposit_trackingOfTransactionhistory(){
        BankAccount account = new BankAccount("AC-DE-2026-01");

        account.deposit(100);
        List<Transaction> history = account.getTransactionHistory();

        assertEquals(1, history.size());
        Transaction lastTransaction = history.get(0);

        assertAll("Transaction fields",
                () -> assertEquals("DEPOSIT", lastTransaction.getType()),
                () -> assertEquals(100, lastTransaction.getAmount()),
                () -> assertEquals(100, lastTransaction.getBalanceAfter())
        );

    }

//------------------------------------------------------------------------------------------------------

    // Transaction withdraw() Tests
    @Test
    public void withdraw_Transaction_return(){
        Transaction transaction = Transaction.withdraw(50, 450);
        assertAll("Transaction fields",
                () -> assertEquals("WITHDRAW", transaction.getType()),
                () -> assertEquals(50, transaction.getAmount()),
                () -> assertEquals(450, transaction.getBalanceAfter())
        );
    }

    @Test
    public void withdraw_trackingOfTransactionhistory(){
        BankAccount account = new BankAccount("AC-DE-2026-01");

        account.deposit(200);
        account.withdraw(100);
        List<Transaction> history = account.getTransactionHistory();

        assertEquals(2, history.size());
        Transaction lastTransaction = history.get(1);

        assertAll("Transaction fields",
                () -> assertEquals("WITHDRAW", lastTransaction.getType()),
                () -> assertEquals(100, lastTransaction.getAmount()),
                () -> assertEquals(100, lastTransaction.getBalanceAfter())
        );

    }
}