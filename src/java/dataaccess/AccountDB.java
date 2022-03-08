package dataaccess;

import java.sql.*;
import java.util.*;
import models.Account;

public class AccountDB {
    public List<Account> getAll() throws Exception {
        List<Account> accounts = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        String sql = "SELECT * FROM account";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                int account_id = rs.getInt(1);
                String user_name = rs.getString(2);
                String password = rs.getString(3);
                String profile = rs.getString(4);                
                
                Account account = new Account(account_id, user_name, password, profile);
                accounts.add(account);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return accounts;
    }
    
    public Account get(int account_id) throws Exception {
        Account account = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM acount WHERE account_id = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, account_id);
            rs = ps.executeQuery();
            
            if(rs.next()) {
                String user_name = rs.getString(2);
                String password = rs.getString(3);
                String profile = rs.getString(4);  
                
                account = new Account(account_id, user_name, password, profile);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return account;
    }
    
    public void insert(Account account) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO account "
                + "(account_id, user_name, password, profile) "
                + "VALUES (?, ?, ?, ?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, account.getAccount_id());
            ps.setString(2, account.getUser_name());
            ps.setString(3, account.getPassword());
            ps.setString(4, account.getProfile());           
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }
    
    public void update(Account account) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE account SET account_id = ?, user_name = ?, password = ?, profile = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, account.getAccount_id());
            ps.setString(2, account.getUser_name());
            ps.setString(3, account.getPassword());
            ps.setString(4, account.getProfile());
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }
    
    public void delete(Account account) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM account WHERE account_id = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, account.getAccount_id());
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }
}