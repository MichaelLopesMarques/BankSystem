import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    // Deposit Test-Scenario
    @Test
    public void deposit_increase_balance(){
        BankAccount account1 = new BankAccount();
        account1.deposit(100);
        assertEquals(100 , account1.getBalance());
    }

    @Test
    public void deposit_zero_throws_exception(){
        BankAccount account1 = new BankAccount();
        assertThrows(IllegalArgumentException.class, () -> {
            account1.deposit(0);
        });
    }

    @Test
    public void deposit_negative_throws_exception(){
        BankAccount account1 = new BankAccount();
        assertThrows(IllegalArgumentException.class, () -> {
            account1.deposit(-100);
        });
    }

//------------------------------------------------------------------------------------------------------

    // Withdraw Test-Scenario
    @Test
    public void withdraw_reduce_balance(){
        BankAccount account1 = new BankAccount();
        account1.deposit(100);
        account1.withdraw(50);
        assertEquals(50 , account1.getBalance());
    }

    @Test
    public void withdraw_zero_throws_exception(){
        BankAccount account1 = new BankAccount();
        account1.deposit(100);
        assertThrows(IllegalArgumentException.class, () -> {
            account1.withdraw(0);
        });
    }

    @Test
    public void withdraw_negative_throws_exception(){
        BankAccount account1 = new BankAccount();
        account1.deposit(100);
        assertThrows(IllegalArgumentException.class, () -> {
            account1.withdraw(-50);
        });
    }

    @Test
    public void withdraw_more_than_balance_throws_exception(){
        BankAccount account1 = new BankAccount();
        account1.deposit(50);
        assertThrows(IllegalArgumentException.class, () -> {
            account1.withdraw(100);
        });
    }

    @Test
    public void withdraw_lockStatus_throws_exception(){
        BankAccount account1 = new BankAccount();
        account1.deposit(100);
        account1.lock();
        assertThrows(IllegalStateException.class, () -> {
            account1.withdraw(50);
        });

        account1.unlock();
        account1.withdraw(50);
        assertEquals(50, account1.getBalance());

    }

//------------------------------------------------------------------------------------------------------

    // TransactionHistory Test-Scenario
    @Test
    public void transactionHistory_contains_all_transactions(){
        BankAccount account1 = new BankAccount();
        account1.deposit(100);
        account1.withdraw(50);
        account1.deposit(80);
        account1.withdraw(100);
        List<Transaction> history = account1.getTransactionHistory();
        assertEquals(4, history.size());
    }

    @Test
    public void transactionHistory_NotNull(){
        BankAccount account1 = new BankAccount();
        List<Transaction> history = account1.getTransactionHistory();
        assertNotNull(history);
    }

    @Test
    public void transactionHistory_isEmpty(){
        BankAccount account1 = new BankAccount();
        List<Transaction> history = account1.getTransactionHistory();
        assertTrue(history.isEmpty());
    }

    @Test
    public void transactionHistory_Type(){
        BankAccount account1 = new BankAccount();
        account1.deposit(50);
        Transaction typeTest = account1.getTransactionHistory().get(0);
        assertEquals("DEPOSIT", typeTest.getType());
    }

    @Test
    public void transactionHistory_InvalidChange(){
        BankAccount account1 = new BankAccount();
        List<Transaction> history = account1.getTransactionHistory();
        assertThrows(UnsupportedOperationException.class, () -> {
           history.add(new Transaction("DEPOSIT", 10, 10));
        });
    }

}

// assertEquals()
// assertNotEquals()
// assertTrue()
// assertFalse()
// assertNull()
// assertNotNull()
// assertThrows()

