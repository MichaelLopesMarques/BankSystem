package safebank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import safebank.domain.BankAccount;
import safebank.exception.InsufficientBalanceException;
import safebank.exception.InvalidAmountException;
import safebank.repository.BankAccountRepository;
import safebank.repository.MemoryBankAccountRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountServiceTest {

    private BankAccount account;
    private BankAccountRepository bankRepo;
    private BankAccountService bankService;

    @BeforeEach
    void setUp(){
        account = new BankAccount("AC-DE-2026-01", "Michael Marques");
        bankRepo = new MemoryBankAccountRepository();
        bankService = new BankAccountService(bankRepo);

        bankRepo.save(account);
    }

    @Test
    public void createAccount_test(){
        bankService.createAccount("AC-DE-2026-02", "Michael Marques");
        assertTrue(() -> bankRepo.existsById("AC-DE-2026-02"));
    }

    @Test
    public void createAccount_throw_exception(){
        assertThrows(IllegalStateException.class, () -> bankService.createAccount("AC-DE-2026-01", "Michael Marques"));
    }

    @Test
    public void deposit_returnRightBalance(){
        bankService.deposit("AC-DE-2026-01", BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(100), account.getBalance());
    }

    @Test
    public void deposit_negativ_amount_throw_exception(){
        assertThrows(InvalidAmountException.class,
                () -> bankService.deposit("AC-DE-2026-01", BigDecimal.valueOf(-100)));
    }

    @Test
    public void deposit_not_existing_account_throw_exception(){
        assertThrows(IllegalArgumentException.class,
                () -> bankService.deposit("AC-DE-2026-03", BigDecimal.valueOf(100)));
    }

    @Test
    public void withdraw_returnRightBalance(){
        bankService.deposit("AC-DE-2026-01", BigDecimal.valueOf(100));
        bankService.withdraw("AC-DE-2026-01", BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(50), account.getBalance());
    }

    @Test
    public void withdraw_negativ_amount_throw_exception(){
        assertThrows(InvalidAmountException.class,
                () -> bankService.withdraw("AC-DE-2026-01", BigDecimal.valueOf(-100)));
    }

    @Test
    public void withdraw_insufficient_balance_throw_exception(){
        assertThrows(InsufficientBalanceException.class,
                () -> bankService.withdraw("AC-DE-2026-01", BigDecimal.valueOf(100)));
    }

    @Test
    public void withdraw_not_existing_account_throw_exception(){
        assertThrows(IllegalArgumentException.class,
                () -> bankService.withdraw("AC-DE-2026-03", BigDecimal.valueOf(100)));
    }

    @Test
    public void getBalance_test(){
        assertEquals(BigDecimal.valueOf(0), bankService.getBalance("AC-DE-2026-01"));
    }

    @Test
    public void getBalance_throws_exception(){
        assertThrows(IllegalArgumentException.class,
                () -> bankService.getBalance("AC-DE-2026-03"));
    }

}