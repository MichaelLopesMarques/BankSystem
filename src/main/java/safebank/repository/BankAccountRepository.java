package safebank.repository;

import safebank.domain.BankAccount;

import java.util.Optional;

public interface BankAccountRepository {

    Optional<BankAccount> findbyId(String accountId);

    void save(BankAccount account);

    boolean existsByID(String accountId);
}
