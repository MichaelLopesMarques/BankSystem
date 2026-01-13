import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private List<Transaction> transactions = new ArrayList<>();
    private int balance = 0;
    private boolean lockStatus = false;

    public void deposit(int amount){
        if(amount > 0) {
            balance += amount;
            transactions.add(new Transaction("DEPOSIT", amount, balance));
        } else {
            throw new IllegalArgumentException("Amount must be positive and above 0");
        }
    }

    public void withdraw(int amount){
        if (lockStatus){
            throw new IllegalStateException("Account is locked");
        }
        if (amount <= 0){
            throw new IllegalArgumentException("Amount must be positive");
        }
        if(amount <= balance) {
            balance -= amount;
            transactions.add(new Transaction("WITHDRAW", amount, balance));
        } else{
            throw new IllegalArgumentException("Not enough Money to withdraw");
        }
    }

    public int getBalance(){
        return balance;
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

    public List<Transaction> getTransactionHistory(){
        return List.copyOf(transactions);
    }
}
