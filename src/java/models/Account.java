package models;

public class Account {
    private int account_id;
    private String user_name;
    private String password;
    private String profile;
    private String resetPasswordUuid;
    private String salt;
    
    public Account(int account_id, String user_name, String password, String profile, String resetPasswordUuid, String salt) {
        this.account_id = account_id;
        this.user_name = user_name;
        this.password = password;
        this.profile = profile;
        this.resetPasswordUuid = resetPasswordUuid;
        this.salt = salt;
    }
    
    public int getAccount_id() {
        return account_id;
    }
    
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
    
    public String getResetPasswordUuid() {
        return resetPasswordUuid;
    }

    public void setResetPasswordUuid(String resetPasswordUuid) {
        this.resetPasswordUuid = resetPasswordUuid;
    }
    
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + account_id +
                ", user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", profile='" + profile + '\'' +
                '}';
    }
}