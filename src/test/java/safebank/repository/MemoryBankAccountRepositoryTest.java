package safebank.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import safebank.domain.BankAccount;
import safebank.service.BankAccountService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemoryBankAccountRepositoryTest {

    private BankAccount account;
    private BankAccountRepository bankRepo;
    private BankAccountService bankService;

    @BeforeEach
    void setUp(){
        account = new BankAccount("AC-DE-2026-01");
        bankRepo = new MemoryBankAccountRepository();
        bankService = new BankAccountService(bankRepo);

        bankRepo.save(account);
    }

    @Test
    public void findById_returnAccount_whenExists(){
        Optional<BankAccount> result = bankRepo.findById("AC-DE-2026-01");

        assertTrue(result.isPresent());
        assertEquals("AC-DE-2026-01", result.get().getId());
    }

    @Test
    public void findById_returnEmpty_whenAccountNotExists(){
        Optional<BankAccount> result = bankRepo.findById("AC-DE-2026-02");

        assertTrue(result.isEmpty());
    }

    @Test
    public void save_returnTrue_whenExists(){
        BankAccount testAccount = new BankAccount("AC-DE-2026-02");
        bankRepo.save(testAccount);
        assertTrue(bankRepo.existsByID("AC-DE-2026-02"));
    }

    @Test
    public void existsByID_returnTrue_whenExists(){
        assertTrue(bankRepo.existsByID("AC-DE-2026-01"));
    }

    @Test
    public void existsByID_returnFalse_whenAccountNotExists(){
        assertFalse(bankRepo.existsByID("AC-DE-2026-03"));
    }
}