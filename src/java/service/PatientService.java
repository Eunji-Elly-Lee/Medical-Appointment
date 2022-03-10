/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dataaccess.PatientDB;
import models.Patient;

/**
 *
 * @author ADMIN
 */
public class PatientService {
    public Patient get(int account_id) throws Exception {
        PatientDB patientDB = new PatientDB();
        Patient patient = patientDB.get(account_id);
        return patient;
    }
}
