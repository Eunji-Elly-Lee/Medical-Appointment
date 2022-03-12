package service;

import dataaccess.PatientDB;
import models.Patient;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class PatientService {
    public Patient get(int account_id) throws Exception {
        PatientDB patientDB = new PatientDB();
        Patient patient = patientDB.get(account_id);
        return patient;
    }
}
