package servlets;

import java.io.IOException;
import java.text.*;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.*;
import service.*;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class DoctorScheduleServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setAttribute("step", "1");

        String user_name = (String) session.getAttribute("user_name");
        ArrayList<String> datelist = new ArrayList<>();
        AccountService accountService = new AccountService();
        DoctorService doctorService = new DoctorService();

        for (int i = 1; i < 20; i++) {
            if (!(LocalDate.now().plusDays(i).getDayOfWeek().toString().equals("SATURDAY")
                    || LocalDate.now().plusDays(i).getDayOfWeek().toString().equals("SUNDAY"))) {
                datelist.add(LocalDate.now().plusDays(i).toString());
            }
        }
        
        request.setAttribute("dateList", datelist);

        try {
            Account account = accountService.get(user_name);
            Doctor doctor = doctorService.get(account.getAccount_id());
            request.setAttribute("user", doctor);
        } catch (Exception ex) {
            Logger.getLogger(DoctorScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/doctorSchedule.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String user_name = (String) session.getAttribute("user_name");
        String action = request.getParameter("action");
        String schedule_date = request.getParameter("schedule_date");

        AccountService accountService = new AccountService();
        DoctorService doctorService = new DoctorService();
        AvailabilityService availabilityService = new AvailabilityService();
        Doctor doctor = null;
        Account account = null;
        String seleted_schedule_date[] = request.getParameterValues("seleted_schedule_date");
        String seleted_start_time[] = request.getParameterValues("start_time");
        String seleted_end_time[] = request.getParameterValues("end_time");
        String duraion[];
        Date beginDate;
        Date endDate;
        long du_time;
        DateFormat df = new SimpleDateFormat("HH:mm");        
        
        List<Availability> availabilities = new ArrayList<>();

        try {
            account = accountService.get(user_name);
            doctor = doctorService.get(account.getAccount_id());
            availabilities = availabilityService.getAllByDoctorId(doctor.getDoctor_id());
        } catch (Exception ex) {
            Logger.getLogger(DoctorScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        switch (action) {
            case "select_date":
                request.setAttribute("step", "0");  
                break;
                
            case "doctor_schedule":
                List<String> cleand_start_time = new ArrayList<>();
                List<String> cleand_end_time = new ArrayList<>();

                for (int i = 0; i < seleted_start_time.length; i++) {
                    if (!seleted_start_time[i].equals("0") && !seleted_end_time[i].equals("0")) {
                        cleand_start_time.add(seleted_start_time[i]);
                        cleand_end_time.add(seleted_end_time[i]);
                    }
                }

                for (int i = 0; i < seleted_schedule_date.length; i++) {
                    try {
                        beginDate = df.parse(cleand_start_time.get(i));
                        endDate = df.parse(cleand_end_time.get(i));
                        du_time = (endDate.getTime() - beginDate.getTime()) / (60 * 1000);
                        Long longtime = du_time;

                        int du_time_time = longtime.intValue();
                        
                        for (Availability availability: availabilities) {
                            if ( availability.getStart_date_time().substring(0, 10).equals(seleted_schedule_date[i])){
                                availabilityService.deleteBySchedule(doctor.getDoctor_id(), availability.getStart_date_time());
                            }
                        }                       
                        
                        availabilityService.insert(doctor.getDoctor_id(),
                                seleted_schedule_date[i] + " " + cleand_start_time.get(i) + ":00.0", du_time_time);                   
                    } catch (Exception ex) {
                        Logger.getLogger(DoctorScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                break;
        }
        
        // after select date  
        ArrayList<String> datelist = new ArrayList<>();
        ArrayList<String> timetable = new ArrayList<>();

        int k = 0;
        for (int i = 1; i < 20; i++) {
            if ((LocalDate.now().plusDays(i).toString().equals(schedule_date))) {
                k = i - 1;
            }
        }

        for (int i = 1; i < 20; i++) {
            if (!(LocalDate.now().plusDays(k + i).getDayOfWeek().toString().equals("SATURDAY")
                    || LocalDate.now().plusDays(k + i).getDayOfWeek().toString().equals("SUNDAY"))) {
                datelist.add(LocalDate.now().plusDays(k + i).toString());
            }
        }

        request.setAttribute("dateList", datelist);

        int time = 7;
        for (int i = 0; i < 19; i++) {
            if (i % 2 == 0) {
                time = time + 1;
                timetable.add(Integer.toString(time) + ":" + "00");
            } else {
                timetable.add(Integer.toString(time) + ":" + "30");
            }
        }
        
        request.setAttribute("timetable", timetable);

        getServletContext().getRequestDispatcher("/WEB-INF/doctorSchedule.jsp").forward(request, response);
        return;
    }
}
