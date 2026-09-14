import java.util.ArrayList;
public class Account
{
    private String userId;
    private String pin;
    private double balance;
    private String name;
    private ArrayList<Transaction>transactionHistory;
    private double dailyWithdrawAmount = 0.0;
    private final double DailyLimit = 20000.0;
    public Account(String userId, String pin, String name, double initialBalance)
    {
        this.userId=userId;
        this.pin=pin;
        this.name=name;
        this.balance=initialBalance;
        this.transactionHistory=new ArrayList<Transaction>();
    }
    public String getUserId()
    {
        return userId;
    }
    public double getBalance()
    {
        return balance;
    }
    public String getName()
    {
        return name;
    }
    public double getDailyWithdrawAmount()
    {
        return dailyWithdrawAmount;
    }
    public double getDailyLimit()
    {
        return DailyLimit;
    }
    public ArrayList<Transaction>getTransactionHistory()
    {
        return transactionHistory;
    }
    public boolean validatePin(String inputPin)
    {
        return this.pin.equals(inputPin);
    }
    public void deposit(double amount)
    {
        balance=balance+amount;
        transactionHistory.add(new Transaction("Deposit",amount,"CASH DEPOSIT SUCCESSFUL"));
    }
    public boolean withdraw(double amount)
    {
        if(amount>balance)
        {
            System.out.println("INSUFFICIENT FUNDS!");
            return false;
        }
        if(dailyWithdrawAmount + amount > DailyLimit)
        {
            System.out.println("DAILY LIMIT EXCEEDED!");
            return false;
        }
        balance = balance-amount;
        dailyWithdrawAmount=dailyWithdrawAmount+amount;
        transactionHistory.add(new Transaction("WITHDRAWAL",amount,"CASH WITHDRAWAL SUCCESSFUL"));
        return true;
    }
    public boolean transferTo(Account recipient, double amount)
    {
        if(amount>balance)
        {
            System.out.println("INSUFFICIENT FUNDS!");
            return false;
        }
        balance=balance-amount;
        recipient.balance=recipient.balance+amount;
        transactionHistory.add(new Transaction("TRANSFER OUT",amount,"Sent to Account:"+recipient.getUserId()));
        recipient.transactionHistory.add(new Transaction("TRANSFER IN",amount,"Received from Account:"+this.userId));
        return true;
    }
    public void changePin(String newPin)
    {
        this.pin=newPin;
    }
}