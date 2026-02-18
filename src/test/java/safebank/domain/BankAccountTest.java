package safebank.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import safebank.exception.AccountLockedException;
import safebank.exception.InsufficientBalanceException;
import safebank.exception.InvalidAmountException;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp(){
        account = new BankAccount("AC-DE-2026-01", "Michael Marques");
    }

    // deposit Tests
    @Test
    public void deposit_increase_balance(){
        account.deposit(BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(100), account.getBalance());
    }

    @Test
    public void deposit_zero_throws_exception(){
        assertThrows(InvalidAmountException.class, () -> account.deposit(BigDecimal.valueOf(0)));
    }

    @Test
    public void deposit_negative_throws_exception(){
        assertThrows(InvalidAmountException.class, () -> account.deposit(BigDecimal.valueOf(-50)));
    }

    @Test
    public void deposit_lockedAccount_throws_exception(){
        account.lock();
        assertThrows(AccountLockedException.class, () -> account.deposit(BigDecimal.valueOf(50)));
    }

    @Test
    public void deposit_lockedAccount_switchToUnlock_transaction(){
        account.lock();
        account.unlock();
        account.deposit(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(50), account.getBalance());
    }

//------------------------------------------------------------------------------------------------------

    // withdraw Tests
    @Test
    public void withdraw_reduce_balance(){
        account.deposit(BigDecimal.valueOf(100));
        account.withdraw(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(50), account.getBalance());
    }

    @Test
    public void withdraw_zero_throws_exception(){
        assertThrows(InvalidAmountException.class, () -> account.withdraw(BigDecimal.valueOf(0)));
    }

    @Test
    public void withdraw_negative_throws_exception(){
        assertThrows(InvalidAmountException.class, () -> account.withdraw(BigDecimal.valueOf(-50)));
    }

    @Test
    public void withdraw_more_than_balance(){
        account.deposit(BigDecimal.valueOf(100));
        assertThrows(InsufficientBalanceException.class, () -> account.withdraw(BigDecimal.valueOf(200)));
    }

    @Test
    public void withdraw_lockedAccount_throws_exception(){
        account.lock();
        assertThrows(AccountLockedException.class, () -> account.withdraw(BigDecimal.valueOf(200)));
    }

    @Test
    public void withdraw_lockedAccount_switchToUnlock_transaction(){
        account.lock();
        account.unlock();
        account.deposit(BigDecimal.valueOf(50));
        account.withdraw(BigDecimal.valueOf(25));
        assertEquals(BigDecimal.valueOf(25), account.getBalance());
    }

//------------------------------------------------------------------------------------------------------

    // isLocked Tests
    @Test
    public void isLocked_locked_status(){
        account.lock();
        assertTrue(account.isLocked());
    }

    @Test
    public void isLocked_unlocked_status(){
        account.lock();
        account.unlock();
        assertFalse(account.isLocked());
    }

//------------------------------------------------------------------------------------------------------

    // transactionHistory Tests
    @Test
    public void transactionHistory_contains_all_transactions(){
        account.deposit(BigDecimal.valueOf(100));
        account.withdraw(BigDecimal.valueOf(50));
        account.deposit(BigDecimal.valueOf(80));
        account.withdraw(BigDecimal.valueOf(100));
        List<Transaction> history = account.getTransactionHistory();
        assertEquals(4, history.size());
    }

    @Test
    public void transactionHistory_NotNull(){
        List<Transaction> history = account.getTransactionHistory();
        assertNotNull(history);
    }

    @Test
    public void transactionHistory_isEmpty(){
        List<Transaction> history = account.getTransactionHistory();
        assertTrue(history.isEmpty());
    }

    @Test
    public void transactionHistory_Type(){
        account.deposit(BigDecimal.valueOf(50));
        Transaction typeTest = account.getTransactionHistory().get(0);
        assertEquals("DEPOSIT", typeTest.getType());
    }

    @Test
    public void transactionHistory_InvalidChange(){
        List<Transaction> history = account.getTransactionHistory();
        assertThrows(UnsupportedOperationException.class, () ->
                history.add(new Transaction("DEPOSIT", BigDecimal.valueOf(10), BigDecimal.valueOf(10))));
    }



}