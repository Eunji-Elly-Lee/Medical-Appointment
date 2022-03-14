package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.Account;
import models.Administrator;
import models.AppointmentType;
import models.Availability;
import models.Doctor;
import models.Patient;
import service.AccountService;
import service.AdministratorService;
import service.AppointmentTypeService;
import service.AvailabilityService;
import service.DoctorService;
import service.PatientService;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class BookAppointmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_name = (String)session.getAttribute("user_name");
        String appointment_date = request.getParameter("appointment_date");
        
        if (user_name == null || user_name.equals("")) {
            response.sendRedirect("welcome");
            return;
        } else {
            if (appointment_date == null || appointment_date.equals("")) {
                request.setAttribute("step", "1");
            }
            
            displayInformation(request, user_name);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/bookAppointment.jsp").forward(request, response);
        return;
    }
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_name = (String) session.getAttribute("user_name");       
        
        List<Availability> availabilities = displayInformation(request, user_name);
        String appointment_date = request.getParameter("appointment_date");
        String action = request.getParameter("action");
        
        switch (action) {
                case "select_date":
                    String end_date_time = null;
                    String duration = null;
                    
                    for (Availability availability: availabilities) {
                        if (availability.getStart_date_time().startsWith(appointment_date)) {                            
                            end_date_time = availability.getStart_date_time().substring(0, 19);
                            duration = availability.getDuration() + "";                            
                        }
                    }
                    
                    LocalTime time = LocalTime.of(Integer.parseInt(end_date_time.substring(11, 13)),
                            Integer.parseInt(end_date_time.substring(14, 16)), Integer.parseInt(end_date_time.substring(17)));
                    
                    time = time.plusMinutes(Integer.parseInt(duration));
                    
                    end_date_time = end_date_time.substring(0, 11) + time;
                    
//                    System.out.println("appointment_date: " + appointment_date);
//                    System.out.println("end_date_time: " + end_date_time);
                    
                    request.setAttribute("appointment_date", appointment_date);
                    break;
                
                case "book_appointment":
                    request.setAttribute("step", "1");
                    break;
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/bookAppointment.jsp").forward(request, response);
        return;
    }
    
    private List<Availability> displayInformation(HttpServletRequest request, String user_name) {
        AccountService accountService = new AccountService();
        AdministratorService administratorService = new AdministratorService();
        DoctorService doctorService = new DoctorService();
        PatientService patientService = new PatientService();
        AppointmentTypeService appointmentTypeService = new AppointmentTypeService();
        AvailabilityService availabilityService = new AvailabilityService();
        
        List<Availability> availabilities = new ArrayList<>();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        // for test version!
        tomorrow = tomorrow.withMonth(1);
        

        try {
            Account account = accountService.get(user_name);
            List<AppointmentType> types = new ArrayList<>();
            
            request.setAttribute("account", account);
                
            if (account.getProfile().equals("DOCTOR")) {
                Doctor doctor = doctorService.get(account.getAccount_id());
                request.setAttribute("user", doctor);
            } else if (account.getProfile().equals("ADMIN") || account.getProfile().equals("SYSADMIN")) {                
                Administrator administrator = administratorService.get(account.getAccount_id());
                request.setAttribute("user", administrator);
            } else if (account.getProfile().equals("PATIENT")) {                
                Patient patient = patientService.get(account.getAccount_id());    
                
                if (patient.getDoctor_id() == 0) {
                    types.add(appointmentTypeService.get(4));
                } else {
                    Doctor doctor = doctorService.getByDoctorID(patient.getDoctor_id());                   
                    request.setAttribute("doctor", doctor);
                    types.add(appointmentTypeService.get(1));
                    types.add(appointmentTypeService.get(2));   
                    
                    availabilities =
                            availabilityService.getAllByDoctorDate(doctor.getDoctor_id(), tomorrow.toString());
                    
                    if (!availabilities.isEmpty()) {
                        List<String> available_dates = new ArrayList<>();

                        for (Availability availability: availabilities) {
                            available_dates.add(availability.getStart_date_time().substring(0, 11));
                        }
                        
                        request.setAttribute("available_dates", available_dates);
//                        for (String date: available_dates) {
//                            System.out.println("date: " + date); 
//                        }
                    }
                }
                
                request.setAttribute("user", patient);
//                request.setAttribute("message", "You new");
            }
            
            request.setAttribute("types", types);
        } catch (Exception ex) {
                Logger.getLogger(WelcomeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return availabilities;
    }
}
