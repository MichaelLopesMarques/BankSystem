package safebank.domain;

import safebank.exception.AccountLockedException;
import safebank.exception.InsufficientBalanceException;
import safebank.exception.InvalidAmountException;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private List<Transaction> transactions = new ArrayList<>();
    private final String id;
    private int balance = 0;
    private boolean lockStatus = false;

    public BankAccount(String id){
        this.id = id;
    }

    public void deposit(int amount){
        if (lockStatus){
            throw new AccountLockedException("Account is locked");
        }
        if(amount > 0) {
            balance += amount;
            transactions.add(Transaction.deposit(amount, balance));
        } else {
            throw new InvalidAmountException("Amount: " + amount + " must be above 0");
        }
    }

    public void withdraw(int amount){
        if (lockStatus){
            throw new AccountLockedException("Account is locked");
        }
        if (amount <= 0){
            throw new InvalidAmountException("Amount: " + amount + " is not positive");
        }
        if(amount <= balance) {
            balance -= amount;
            transactions.add(Transaction.withdraw(amount, balance));
        } else{
            throw new InsufficientBalanceException("Error! Balance: " + balance + ", Requested: " + amount);
        }
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

    public int getBalance(){
        return balance;
    }

    public List<Transaction> getTransactionHistory(){
        return List.copyOf(transactions);
    }
}
