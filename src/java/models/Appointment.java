package models;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class Appointment {
   private int doctor_id;
    private String date;
    private String time;
    private int patient_id;
    private int duration;
    private int type;
    private String reason;

    public Appointment(int doctor_id, String date, String time, int patient_id, int duration, int type, String reason)
    {
        this.doctor_id = doctor_id;
        this.date = date;
        this.time = time;
        this.patient_id = patient_id;
        this.duration = duration;
        this.type = type;
        this.reason = reason;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
