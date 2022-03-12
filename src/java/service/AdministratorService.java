package service;

import dataaccess.AdministratorDB;
import java.util.List;
import models.Administrator;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class AdministratorService {
    public List<Administrator> getAll() throws Exception {
        AdministratorDB administratorDB = new AdministratorDB();
        List<Administrator> administrators = administratorDB.getAll();
        return administrators;
    }
    
    public Administrator get(int account_id) throws Exception {
        AdministratorDB administratorDB = new AdministratorDB();
        Administrator administrator = administratorDB.get(account_id);
        return administrator;
    }
    
    public void insert(int doctor_id, String first_name, String last_name, String email, String mobile_phone,
            String alt_phone, String pref_contact_type, int account_id, String gender, String birth_date,
            String street_address, String city, String province, String postal_code) throws Exception {
        AdministratorDB administratorDB = new AdministratorDB();
        Administrator administrator = new Administrator(doctor_id, first_name, last_name, email, mobile_phone, alt_phone,
                pref_contact_type, account_id, gender, birth_date, street_address, city, province, postal_code);
        administratorDB.insert(administrator);
    }
    
    public void update(int doctor_id, String first_name, String last_name, String email, String mobile_phone,
            String alt_phone, String pref_contact_type, int account_id, String gender, String birth_date,
            String street_address, String city, String province, String postal_code) throws Exception {
        AdministratorDB administratorDB = new AdministratorDB();
        Administrator administrator = new Administrator(doctor_id, first_name, last_name, email, mobile_phone, alt_phone,
                pref_contact_type, account_id, gender, birth_date, street_address, city, province, postal_code);
        administratorDB.update(administrator);
    }
    
    public void delete(int account_id) throws Exception {
        Administrator administrator = get(account_id);
        AdministratorDB administratorDB = new AdministratorDB();
        administratorDB.delete(administrator);
    }
}
