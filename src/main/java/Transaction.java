public class Transaction {

    private final String type;
    private final int amount;
    private final int balanceAfter;

    public Transaction(String type, int amount, int balanceAfter){
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    public String getType(){
        return type;
    }

    public int getAmount(){
        return amount;
    }

    public int getBalanceAfter(){
        return balanceAfter;
    }
}
