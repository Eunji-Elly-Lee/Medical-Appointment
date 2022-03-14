package dataaccess;

import java.sql.*;
import java.util.*;
import models.Calendar;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class CalendarDB {
    public List<Calendar> getAll() throws Exception {
        List<Calendar> calendars = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        String sql = "SELECT * FROM calendar";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                String date_time = rs.getString(1);
                String clinic_open = rs.getString(2);             
                
                Calendar calendar = new Calendar(date_time , clinic_open);
                calendars.add(calendar);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return calendars;
    }
    
    public Calendar get(String date_time) throws Exception {
        Calendar calendar = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM calendar WHERE date_time = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, date_time);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String clinic_open = rs.getString(2);
                
                calendar = new Calendar(date_time , clinic_open);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return calendar;
    }
    
    public void insert(Calendar calendar) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO calendar (date_time , clinic_open) VALUES (?, ?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, calendar.getDate_time());
            ps.setString(2, calendar.getClinic_open());
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }
    
    public void update(Calendar calendar) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE calendar SET date_time = ?, clinic_open = ? WHERE date_time = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, calendar.getDate_time());
            ps.setString(2, calendar.getClinic_open());
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }
    
    public void delete(Calendar calendar) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM calendar WHERE date_time = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, calendar.getDate_time());
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }
}
