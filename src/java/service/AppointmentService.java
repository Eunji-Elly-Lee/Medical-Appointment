package service;

import dataaccess.AppointmentDB;
import java.util.List;
import models.Appointment;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class AppointmentService {
    public List<Appointment> getAll() throws Exception {
        AppointmentDB appointmentDB = new AppointmentDB();
        List<Appointment> appointments = appointmentDB.getAll();
        return appointments;
    }
    
    public Appointment getByDoctorID(int doctor_id) throws Exception {
        AppointmentDB appointmentDB = new AppointmentDB();
        Appointment appointment = appointmentDB.getByDoctorID(doctor_id);
        return appointment;
    }
    
    public Appointment getByPatientID(int patient_id) throws Exception {
        AppointmentDB appointmentDB = new AppointmentDB();
        Appointment appointment = appointmentDB.getByPatientID(patient_id);
        return appointment;
    }
    
    public Appointment getByDate(String start_date_time) throws Exception {
        AppointmentDB appointmentDB = new AppointmentDB();
        Appointment appointment = appointmentDB.getByDate(start_date_time);
        return appointment;
    }
    
    public void insert(int doctor_id, String start_date_time, int patient_id, int duration,
            int type, String reason, boolean patient_attended) throws Exception {
        Appointment appointment =
                new Appointment(doctor_id, start_date_time, patient_id, duration, type, reason, patient_attended);
        AppointmentDB appointmentDB = new AppointmentDB();
        appointmentDB.insert(appointment);
    }
    
    public void update(int doctor_id, String start_date_time, int patient_id, int duration,
            int type, String reason, boolean patient_attended) throws Exception {
        Appointment appointment =
                new Appointment(doctor_id, start_date_time, patient_id, duration, type, reason, patient_attended);
        AppointmentDB appointmentDB = new AppointmentDB();
        appointmentDB.update(appointment);
    }
    
    public void deleteByDoctorID(int doctor_id) throws Exception {
        Appointment appointment = getByDoctorID(doctor_id);
        AppointmentDB appointmentDB = new AppointmentDB();
        appointmentDB.delete(appointment);
    }
    
    public void deleteByPatientID(int patient_id) throws Exception {
        Appointment appointment = getByPatientID(patient_id);
        AppointmentDB appointmentDB = new AppointmentDB();
        appointmentDB.delete(appointment);
    }
}