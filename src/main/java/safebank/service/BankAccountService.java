package safebank.service;

import safebank.domain.BankAccount;
import safebank.repository.BankAccountRepository;

public class BankAccountService {

    private final BankAccountRepository repository;

    public BankAccountService(BankAccountRepository repository) {
        this.repository = repository;
    }

    public void createAccount(String accountId){
        if (repository.existsByID(accountId)){
            throw new IllegalStateException("Account already exists!");
        }
        repository.save(new BankAccount(accountId));
    }

    public void deposit(String accountId, int amount){
        BankAccount account = repository.findbyId(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));
        account.deposit(amount);
        repository.save(account);
    }

    public void withdraw(String accountId, int amount){
        BankAccount account = repository.findbyId(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));
        account.withdraw(amount);
        repository.save(account);
    }

    public int getBalance(String accountId){
        return repository.findbyId(accountId)
                .map(BankAccount::getBalance)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));
    }
}
