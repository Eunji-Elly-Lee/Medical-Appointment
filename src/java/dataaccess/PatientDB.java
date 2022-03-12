package dataaccess;

import java.sql.*;
import models.Patient;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class PatientDB {
    public Patient get(int account_id) throws Exception {
        Patient patient = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM patient WHERE account_id = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, account_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                int patient_id = rs.getInt(1);
                String healthcare_id = rs.getString(2);
                String first_name = rs.getString(3);
                String last_name = rs.getString(4);
                String email = rs.getString(5);
                String mobile_phone = rs.getString(6);
                String alt_phone = rs.getString(7);
                String pref_contact_type = rs.getString(8);
                int doctor_id = rs.getInt(9);                
                String gender = rs.getString(11);
                String birth_date = rs.getString(12);
                String street_address = rs.getString(13);
                String city = rs.getString(14);
                String province = rs.getString(15);
                String postal_code = rs.getString(16);
                patient = new Patient(patient_id, healthcare_id, first_name, last_name,
                        email, mobile_phone, alt_phone, pref_contact_type, doctor_id, account_id,
                        gender, birth_date, street_address, city, province, postal_code);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return patient;
    }
    
    public Patient get(String email) throws Exception {
        Patient patient = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM patient WHERE email = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                int patient_id = rs.getInt(1);
                String healthcare_id = rs.getString(2);
                String first_name = rs.getString(3);
                String last_name = rs.getString(4);                
                String mobile_phone = rs.getString(6);
                String alt_phone = rs.getString(7);
                String pref_contact_type = rs.getString(8);
                int doctor_id = rs.getInt(9);
                int account_id = rs.getInt(10);
                String gender = rs.getString(11);
                String birth_date = rs.getString(12);
                String street_address = rs.getString(13);
                String city = rs.getString(14);
                String province = rs.getString(15);
                String postal_code = rs.getString(16);
                patient = new Patient(patient_id, healthcare_id, first_name, last_name,
                        email, mobile_phone, alt_phone, pref_contact_type, doctor_id, account_id,
                        gender, birth_date, street_address, city, province, postal_code);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return patient;
    }
}
