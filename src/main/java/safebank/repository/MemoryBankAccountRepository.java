package safebank.repository;

import safebank.domain.BankAccount;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryBankAccountRepository implements BankAccountRepository{

    private final Map<String, BankAccount> storage = new HashMap<>();

    @Override
    public Optional<BankAccount> findById(String accountId) {
        return Optional.ofNullable(storage.get(accountId));
    }

    @Override
    public void save(BankAccount account) {
        storage.put(account.getId(), account);
    }

    @Override
    public boolean existsByID(String accountId) {
        return storage.containsKey(accountId);
    }
}
