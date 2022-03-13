package models;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class Calendar {
    private String date;
    private String time;
    private String clinic_open;


    public Calendar(String date, String time, String clinic_open) {
        this.date = date;
        this.time = time;
        this.clinic_open = clinic_open;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
    
    public String getClinic_open() {
        return clinic_open;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    public void setClinic_open(String clinic_open) {
        this.clinic_open = clinic_open;
    }
}
