package service;

import dataaccess.AccountDB;
import java.util.List;
import models.Account;

public class AccountService {
    public List<Account> getAll() throws Exception {
        AccountDB accountDB = new AccountDB();
        List<Account> accounts = accountDB.getAll();
        return accounts;
    }
    
    public Account get(int account_id) throws Exception {
        AccountDB accountDB = new AccountDB();
        Account account = accountDB.get(account_id);
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
    
    public void delete(int account_id) throws Exception {
        Account account = get(account_id);
        AccountDB accountDB = new AccountDB();
        accountDB.delete(account);
    }
}