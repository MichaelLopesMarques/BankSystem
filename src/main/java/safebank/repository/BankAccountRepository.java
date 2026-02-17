package safebank.repository;

import safebank.domain.BankAccount;

import java.util.Optional;

public interface BankAccountRepository {

    Optional<BankAccount> findById(String accountId);

    void save(BankAccount account);

    boolean existsByID(String accountId);
}
