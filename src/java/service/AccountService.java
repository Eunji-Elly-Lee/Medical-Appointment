package service;

import dataaccess.AccountDB;
import java.util.List;
import models.Account;

public class AccountService {
    public Account login(String user_name, String password) {
        AccountDB accountDB = new AccountDB();
        
        try {
            Account account = get(user_name);
            
            if(password.equals(account.getPassword())) {
                return account;
            }
        } catch(Exception e) {}
        
        return null;
    }
    
    public List<Account> getAll() throws Exception {
        AccountDB accountDB = new AccountDB();
        List<Account> accounts = accountDB.getAll();
        return accounts;
    }
    
    public Account get(String user_name) throws Exception {
        AccountDB accountDB = new AccountDB();
        Account account = accountDB.get(user_name);
        return account;
    }
    
    public void insert(int account_id, String user_name, String password, String profile) throws Exception {
        Account account = new Account(account_id, user_name, password, profile);
        AccountDB accountDB = new AccountDB();
        accountDB.insert(account);
    }
    
    public void update(int account_id, String user_name, String password, String profile) throws Exception {
        Account account = new Account(account_id, user_name, password, profile);
        AccountDB accountDB = new AccountDB();
        accountDB.update(account);
    }
    
    public void delete(String user_name) throws Exception {
        Account account = get(user_name);
        AccountDB accountDB = new AccountDB();
        accountDB.delete(account);
    }
}