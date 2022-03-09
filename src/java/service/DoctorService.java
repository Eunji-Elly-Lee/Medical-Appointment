package service;

import dataaccess.DoctorDB;
import java.util.List;
import models.Doctor;

public class DoctorService {
    public List<Doctor> getAll() throws Exception {
        DoctorDB doctorDB = new DoctorDB();
        List<Doctor> doctors = doctorDB.getAll();
        return doctors;
    }
    
    public Doctor get(int account_id) throws Exception {
        DoctorDB doctorDB = new DoctorDB();
        Doctor doctor = doctorDB.get(account_id);
        return doctor;
    }
    
    public void insert(int doctor_id, String first_name, String last_name, String email, String mobile_phone,
            String alt_phone, String pref_contact_type, int account_id, String gender, String birth_date,
            String street_address, String city, String province, String postal_code) throws Exception {
        DoctorDB doctorDB = new DoctorDB();
        Doctor doctor = new Doctor(doctor_id, first_name, last_name, email, mobile_phone, alt_phone,
                pref_contact_type, account_id, gender, birth_date, street_address, city, province, postal_code);
        doctorDB.insert(doctor);
    }
    
    public void update(int doctor_id, String first_name, String last_name, String email, String mobile_phone,
            String alt_phone, String pref_contact_type, int account_id, String gender, String birth_date,
            String street_address, String city, String province, String postal_code) throws Exception {
        DoctorDB doctorDB = new DoctorDB();
        Doctor doctor = new Doctor(doctor_id, first_name, last_name, email, mobile_phone, alt_phone,
                pref_contact_type, account_id, gender, birth_date, street_address, city, province, postal_code);
        doctorDB.update(doctor);
    }
    
    public void delete(int account_id) throws Exception {
        Doctor doctor = get(account_id);
        DoctorDB doctorDB = new DoctorDB();
        doctorDB.delete(doctor);
    }
}