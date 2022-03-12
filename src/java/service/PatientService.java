package service;

import dataaccess.PatientDB;
import java.util.List;
import models.Patient;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class PatientService {
    public List<Patient> getAll() throws Exception {
        PatientDB patientDB = new PatientDB();
        List<Patient> patients = patientDB.getAll();
        return patients;
    }
    
    public Patient get(int account_id) throws Exception {
        PatientDB patientDB = new PatientDB();
        Patient patient = patientDB.get(account_id);
        return patient;
    }
    
    public void insert(int patient_id, String healthcare_id, String first_name, String last_name, String email,
            String mobile_phone, String alt_phone, String pref_contact_type, int doctor_id, int account_id, String gender,
            String birth_date, String street_address, String city, String province, String postal_code) throws Exception {
        PatientDB patientDB = new PatientDB();
        Patient patient = new Patient(patient_id, healthcare_id, first_name, last_name, email, mobile_phone, alt_phone,
                pref_contact_type, doctor_id, account_id, gender, birth_date, street_address, city, province, postal_code);
        patientDB.insert(patient);
    }
    
    public void update(int patient_id, String healthcare_id, String first_name, String last_name, String email,
            String mobile_phone, String alt_phone, String pref_contact_type, int doctor_id, int account_id, String gender,
            String birth_date, String street_address, String city, String province, String postal_code) throws Exception {
        PatientDB patientDB = new PatientDB();
        Patient patient = new Patient(patient_id, healthcare_id, first_name, last_name, email, mobile_phone, alt_phone,
                pref_contact_type, doctor_id, account_id, gender, birth_date, street_address, city, province, postal_code);
        patientDB.update(patient);
    }
    
    public void delete(int account_id) throws Exception {
        Patient patient = get(account_id);
        PatientDB patientDB = new PatientDB();
        patientDB.delete(patient);
    }
}
