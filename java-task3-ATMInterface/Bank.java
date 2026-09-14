import java.util.HashMap;
public class Bank 
{
    private HashMap<String,Account>accountDatabase;
    public Bank()
    {
        accountDatabase=new HashMap<String,Account>();
        initializeSampleAccounts();
    }
    private void initializeSampleAccounts()
    {
        accountDatabase.put("1001", new Account("1001","1001","Megha",150000.0));
        accountDatabase.put("1002", new Account("1002","2002","Anu",100000.0));
        accountDatabase.put("1003", new Account("1003","3003","Devi",45000.0));
        accountDatabase.put("1004", new Account("1004","4004","Saanvi",80000.0));
    }
    public Account getAccount(String userId)
    {
        return accountDatabase.get(userId);
    }
    public boolean accountExists(String userId)
    {
        return accountDatabase.containsKey(userId);
    }
    public void addAccount(Account account)
    {
        accountDatabase.put(account.getUserId(),account);
    }
    public int getTotalAccounts()
    {
        return accountDatabase.size();
    }
}