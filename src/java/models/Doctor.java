package models;

public class Doctor {
    private int doctor_id;
    private String first_name;
    private String last_name;
    private String email;
    private String mobile_phone;
    private String alt_phone;
    private String pref_contact_type;
    private int account_id;

    public Doctor(int doctor_id, String first_name, String last_name, String email,
            String mobile_phone,String alt_phone, String pref_contact_type, int account_id) {
        this.doctor_id = doctor_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.mobile_phone = mobile_phone;
        this.alt_phone = alt_phone;
        this.pref_contact_type = pref_contact_type;
        this.account_id = account_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getAlt_phone() {
        return alt_phone;
    }

    public void setAlt_phone(String alt_phone) {
        this.alt_phone = alt_phone;
    }

    public String getPref_contact_type() {
        return pref_contact_type;
    }

    public void setPref_contact_type(String pref_contact_type) {
        this.pref_contact_type = pref_contact_type;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctor_id=" + doctor_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", mobile_phone='" + mobile_phone + '\'' +
                ", alt_phone='" + alt_phone + '\'' +
                ", pref_contact_type='" + pref_contact_type + '\'' +
                ", account_id=" + account_id +
                '}';
    }
}