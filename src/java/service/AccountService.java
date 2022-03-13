package service;

import dataaccess.*;
import java.util.*;
import java.util.logging.*;
import models.*;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class AccountService {
    public Account login(String user_name, String password) {        
        try {
            Account account = get(user_name);
            String salt = account.getSalt();
            String userPassword = account.getPassword();
            
            if ((salt == null && password.equals(userPassword)) ||
                    (salt != null && userPassword.equals(PasswordUtil.hashAndSaltPassword(password, salt)))) {
                return account;
            }
        } catch (Exception e) {}
        
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
        String salt = PasswordUtil.getSalt();
        Account account =
                new Account(account_id, user_name, PasswordUtil.hashAndSaltPassword(password, salt), profile, null, salt);
        AccountDB accountDB = new AccountDB();
        accountDB.insert(account);
    }
    
    public void update(int account_id, String user_name, String password, String profile) throws Exception {
        String salt = PasswordUtil.getSalt();
        Account account =
                new Account(account_id, user_name, PasswordUtil.hashAndSaltPassword(password, salt), profile, null, salt);
        AccountDB accountDB = new AccountDB();
        accountDB.update(account);
    }
    
    public void delete(String user_name) throws Exception {
        Account account = get(user_name);
        AccountDB accountDB = new AccountDB();
        accountDB.delete(account);
    }
    
    public Account resetPassword(String email, String path, String url) {
        String uuid = UUID.randomUUID().toString();
        String to = email;
        String subject = "Surpass Health Clinic Reset Password";
        String template = path + "/email_templates/reset_password.html";
        String link = url + "?uuid=" + uuid;
        AccountDB accountDB = new AccountDB();
        Account account = null;
        
        try {
            account = accountDB.getByEmail(email);

            if (account.getProfile().equals("DOCTOR")) {
                DoctorDB doctorDB = new DoctorDB();
                Doctor doctor = doctorDB.get(account.getAccount_id());
                
                account.setReset_password_uuid(uuid);
                accountDB.update(account);

                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", doctor.getFirst_name());
                tags.put("lastname", doctor.getLast_name());
                tags.put("link", link);
                tags.put("date", (new java.util.Date()).toString());

                GmailService.sendMail(to, subject, template, tags);
            } else if (account.getProfile().equals("ADMIN") || account.getProfile().equals("SYSADMIN")) {
                AdministratorDB administratorDB = new AdministratorDB();
                Administrator administrator = administratorDB.get(account.getAccount_id());

                account.setReset_password_uuid(uuid);
                accountDB.update(account);

                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", administrator.getFirst_name());
                tags.put("lastname", administrator.getLast_name());
                tags.put("link", link);
                tags.put("date", (new java.util.Date()).toString());

                GmailService.sendMail(to, subject, template, tags);
            } else if (account.getProfile().equals("PATIENT")) {
                PatientDB patientDB = new PatientDB();
                Patient patient = patientDB.get(account.getAccount_id());

                account.setReset_password_uuid(uuid);
                accountDB.update(account);

                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", patient.getFirst_name());
                tags.put("lastname", patient.getLast_name());
                tags.put("link", link);
                tags.put("date", (new java.util.Date()).toString());

                GmailService.sendMail(to, subject, template, tags);
            }

            //GmailService.sendMail(email, "NotesKeeper Password", "Hello, To reset your password, please click this link : ", false);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return account;
    }
    
    public boolean changePassword(String uuid, String password) {
        AccountDB accountDB = new AccountDB();
        
        try {
            Account account = accountDB.getByUUID(uuid);
            String salt = account.getSalt();
            String encryptedPassword = PasswordUtil.hashAndSaltPassword(password, salt);
            account.setPassword(encryptedPassword);
            account.setReset_password_uuid(null);
            accountDB.update(account);
            
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public Account findAccount(String firstName, String lastName, String email) throws Exception {
        AccountDB accountDB = new AccountDB();
        DoctorDB doctorDB = new DoctorDB();
        PatientDB patientDB = new PatientDB();
        AdministratorDB administratorDB = new AdministratorDB();
        Account account = accountDB.getByEmail(email);
        
        if (account.getProfile().equals("DOCTOR")) {
            Doctor doctor = doctorDB.get(email);
            
            if (doctor.getFirst_name().equals(firstName) && doctor.getLast_name().equals(lastName)) {
                return account;
            }
        } else if (account.getProfile().equals("PATIENT")) {
            Patient patient = patientDB.get(email);
            
            if (patient.getFirst_name().equals(firstName) && patient.getLast_name().equals(lastName)) {
                return account;
            }
        } else if (account.getProfile().equals("ADMIN") || account.getProfile().equals("SYSADMIN")) {
            Administrator administrator = administratorDB.get(email);
            
            if (administrator.getFirst_name().equals(firstName) && administrator.getLast_name().equals(lastName)) {
                return account;
            }
        }
        
        return null;
    }
    
    public Account sendAccount(String email, String firstName, String LastName, String path) throws Exception {
        String to = email;
        String subject = "Your ID From Surpass Health Clinic";
        String template = path + "/email_templates/find_account.html";
        String url2 = "http://localhost:8084/capstoneUpdate/";
        AccountDB accountDB = new AccountDB();        
        Account account = null;
        
        try {
            account = accountDB.getByEmail(email);

            if (account.getProfile().equals("DOCTOR")) {
                DoctorDB doctorDB = new DoctorDB();
                Doctor doctor = doctorDB.get(account.getAccount_id());

                if (doctor.getFirst_name().equals(firstName) &&
                        doctor.getLast_name().equals(LastName) && doctor.getEmail().equals(email)) {      
                    HashMap<String, String> tags = new HashMap<>();
                    tags.put("firstname", doctor.getFirst_name());
                    tags.put("lastname", doctor.getLast_name());
                    tags.put("id", account.getUser_name());
                    tags.put("link", url2);
                    tags.put("date", (new java.util.Date()).toString());

                    GmailService.sendMail(to, subject, template, tags);
                } else {
                    return null;
                }

            } else if (account.getProfile().equals("ADMIN") || account.getProfile().equals("SYSADMIN")) {
                AdministratorDB administratorDB = new AdministratorDB();
                Administrator administrator = administratorDB.get(account.getAccount_id());

                if (administrator.getFirst_name().equals(firstName) &&
                        administrator.getLast_name().equals(LastName) && administrator.getEmail().equals(email)) {
                    HashMap<String, String> tags = new HashMap<>();
                    tags.put("firstname", administrator.getFirst_name());
                    tags.put("lastname", administrator.getLast_name());
                    tags.put("id", account.getUser_name());
                    tags.put("link", url2);
                    tags.put("date", (new java.util.Date()).toString());

                    GmailService.sendMail(to, subject, template, tags);
                } else {
                    return null;
                }
            } else if (account.getProfile().equals("PATIENT")) {
                PatientDB patientDB = new PatientDB();
                Patient patient = patientDB.get(account.getAccount_id());

                if (patient.getFirst_name().equals(firstName) &&
                        patient.getLast_name().equals(LastName) && patient.getEmail().equals(email)) {
                    HashMap<String, String> tags = new HashMap<>();
                    tags.put("firstname", patient.getFirst_name());
                    tags.put("lastname", patient.getLast_name());
                    tags.put("id", account.getUser_name());
                    tags.put("link", url2);
                    tags.put("date", (new java.util.Date()).toString());

                    GmailService.sendMail(to, subject, template, tags);
                } else {
                    return null;
                }
            }

            //GmailService.sendMail(email, "NotesKeeper Password", "Hello, To reset your password, please click this link : ", false);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return account;
    }
}
