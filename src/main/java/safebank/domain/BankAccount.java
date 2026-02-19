package safebank.domain;

import safebank.exception.AccountLockedException;
import safebank.exception.InsufficientBalanceException;
import safebank.exception.InvalidAmountException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private List<Transaction> transactions = new ArrayList<>();
    private final String id;
    private String owner;
    private BigDecimal balance;
    private boolean lockStatus = false;

    public BankAccount(String id, String owner){
        this.id = id;
        this.owner = owner;
        this.balance = BigDecimal.ZERO;
    }

    public BankAccount(String id, String owner, BigDecimal balance) {
        this.id = id;
        this.owner = owner;
        this.balance = balance;
    }

    public void deposit(BigDecimal amount){
        if (lockStatus){
            throw new AccountLockedException("Account is locked");
        }
        if(balance.compareTo(amount) < 0) {
            balance = balance.add(amount);
            transactions.add(Transaction.deposit(amount, balance));
        } else {
            throw new InvalidAmountException("Amount: " + amount + " must be above 0 ");
        }
    }

    public void withdraw(BigDecimal amount) {
        if (lockStatus) {
            throw new AccountLockedException("Account is locked");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Amount must be positive: " + amount);
        }
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException(
                    "Error! Balance: " + balance + ", Amount: " + amount);
        }
        balance = balance.subtract(amount);
        transactions.add(Transaction.withdraw(amount, balance));
    }

    public void lock(){
        lockStatus = true;
    }

    public void unlock(){
        lockStatus = false;
    }

    public boolean isLocked(){
        return lockStatus;
    }

    public String getId(){
        return id;
    }

    public String getOwner(){ return owner; }

    public BigDecimal getBalance(){
        return balance;
    }

    public List<Transaction> getTransactionHistory(){
        return List.copyOf(transactions);
    }
}
