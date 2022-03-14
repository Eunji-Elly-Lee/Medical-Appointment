package service;

import dataaccess.AvailabilityDB;
import java.util.List;
import models.Availability;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class AvailabilityService {
    public List<Availability> getAll() throws Exception {
        AvailabilityDB availabilityDB = new AvailabilityDB();
        List<Availability> availabilities = availabilityDB.getAll();
        return availabilities;
    }
    
    public List<Availability> getAllByDoctorDate(int doctor_id, String start_date_time) throws Exception {
        AvailabilityDB availabilityDB = new AvailabilityDB();
        List<Availability> availabilities = availabilityDB.getAllByDoctorDate(doctor_id, start_date_time);
        return availabilities;
    }
    
    public Availability get(int doctor_id) throws Exception {
        AvailabilityDB availabilityDB = new AvailabilityDB();
        Availability availability = availabilityDB.get(doctor_id);
        return availability;
    }
    
    public void insert(int doctor_id, String start_date_time, int duration) throws Exception {
        Availability availability = new Availability(doctor_id , start_date_time, duration);
        AvailabilityDB availabilityDB = new AvailabilityDB();
        availabilityDB.insert(availability);
    }
    
    public void update(int doctor_id, String start_date_time, int duration) throws Exception {
        Availability availability = new Availability(doctor_id , start_date_time, duration);
        AvailabilityDB availabilityDB = new AvailabilityDB();
        availabilityDB.update(availability);
    }
    
    public void delete(int doctor_id) throws Exception {
        Availability availability = get(doctor_id);
        AvailabilityDB availabilityDB = new AvailabilityDB();
        availabilityDB.delete(availability);
    }
}