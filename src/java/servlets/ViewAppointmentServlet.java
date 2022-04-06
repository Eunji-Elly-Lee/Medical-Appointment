package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class ViewAppointmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_name = (String) session.getAttribute("user_name");
        
        AccountService accountService = new AccountService();
        DoctorService doctorService = new DoctorService();
        PatientService patientService = new PatientService();
        AppointmentService as = new AppointmentService();           
        LocalDate today = LocalDate.now();

        if (request.getParameter("delete") != null) {
            String time = request.getParameter("delete");           
            
            try {
                Appointment appointment = as.getByDate(time);
                as.delete(time, appointment.getDoctor_id(), appointment.getPatient_id());
            } catch (Exception ex) {
                Logger.getLogger(ViewAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            request.setAttribute("message", "Appointment is deleted successfully.");
        }

        if (user_name != null && !user_name.equals("")) {
            try {
                Account account = accountService.get(user_name);
                request.setAttribute("account", account);
                
                List<Appointment> appointments = as.getAllFutures(today + "");
                
                if (account.getProfile().equals("DOCTOR")) {                    
                    Doctor doctor = doctorService.get(account.getAccount_id());
                    request.setAttribute("user", doctor);
                    
                    ArrayList<Appointment> arrlAppointments = new ArrayList();
                    ArrayList<Patient> arrlPatients = new ArrayList();
                    
                    for (int i = 0; i < appointments.size(); i++) {
                        if (appointments.get(i).getDoctor_id() == doctor.getDoctor_id()) {
                            arrlAppointments.add(appointments.get(i));
                        }
                    }

                    for (int i = 0; i < arrlAppointments.size(); i++) {
                        Patient patient = patientService.getByPatientId(arrlAppointments.get(i).getPatient_id());
                        arrlPatients.add(patient);
                    }
                    
                    request.setAttribute("arrlAppointments", arrlAppointments);
                    request.setAttribute("arrlPatients", arrlPatients);
                } else if (account.getProfile().equals("ADMIN")) {
                    AdministratorService administratorService = new AdministratorService();
                    Administrator administrator = administratorService.get(account.getAccount_id());
                    request.setAttribute("user", administrator);
                   
                    ArrayList<Appointment> arrlAppointments = (ArrayList<Appointment>) appointments;
                    ArrayList<Patient> arrlPatients = new ArrayList();
                    ArrayList<Doctor> arrlDoctors = new ArrayList();
                        
                    for (int i = 0; i < arrlAppointments.size(); i++) {
                        Patient patient = patientService.getByPatientId(arrlAppointments.get(i).getPatient_id());
                        Doctor doctor = doctorService.getByDoctorID(arrlAppointments.get(i).getDoctor_id());
                        arrlPatients.add(patient);
                        arrlDoctors.add(doctor);
                    }
                       
                    request.setAttribute("arrlAppointments", arrlAppointments);
                    request.setAttribute("arrlPatients", arrlPatients);
                    request.setAttribute("arrlDoctors", arrlDoctors);
                } else if (account.getProfile().equals("PATIENT")) {
                    Patient patient = patientService.get(account.getAccount_id());
                    request.setAttribute("user", patient);
                    //add jihoon
                    request.setAttribute("step", 1);
                    //end jihoon
                    
                    List<Appointment> allAppointments = as.getByPatientID(patient.getPatient_id());
                    //change type    jihoon                    
                    // add  jihoon
                    ArrayList<Appointment> pastAppointments = new ArrayList();
                    ArrayList<Appointment> futuerAppointments = new ArrayList();

                    for (int i = 0; i < allAppointments.size(); i++) {
                        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate beginDate = LocalDate.parse(allAppointments.get(i).getStart_date_time().substring(0, 10), df);
                         
                        if (beginDate.isBefore(today)) {
                            //jihoon  for history
                            pastAppointments.add(allAppointments.get(i));
                        } else {
                            futuerAppointments.add(allAppointments.get(i));
                        }
                    }
                    //end  jihoon
                    
                    request.setAttribute("pastAppointments", pastAppointments);
                    request.setAttribute("futuerAppointments", futuerAppointments);

                    Doctor doctor = doctorService.getByDoctorID(patient.getDoctor_id());
                    request.setAttribute("doctorName", doctor.getFirst_name() + " " + doctor.getLast_name());
                } else {
                    response.sendRedirect("welcome");
                    return;
                }
            } catch (Exception ex) {
                Logger.getLogger(WelcomeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            response.sendRedirect("welcome");
            return;
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/viewAppointment.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_name = (String) session.getAttribute("user_name");
        
        AccountService as = new AccountService();
        PatientService ps = new PatientService();
        DoctorService ds = new DoctorService();
        AppointmentService aps = new AppointmentService();
        
        try {            
            String time = request.getParameter("date_time"); 
            Appointment appointment = aps.getByDate(time);            
            session.setAttribute("appointmentSessionObj", appointment);                      

            //add jihoon
            String action = request.getParameter("action");          
            boolean active = false;
            
            switch (action) {
                case "history_appointment":
                    Account account = as.get(user_name);
                    request.setAttribute("account", account);
                    request.setAttribute("step", 0);                    
                    
                    Patient patient = ps.get(account.getAccount_id());
                    request.setAttribute("user", patient);
                    
                    List<Appointment> allAppointments = aps.getByPatientID(patient.getPatient_id());
                    ArrayList<Appointment> pastAppointments = new ArrayList();
                    ArrayList<Appointment> futuerAppointments = new ArrayList();
                    LocalDate today = LocalDate.now();
                    
                    for (int i = 0; i < allAppointments.size(); i++) {
                        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate beginDate = LocalDate.parse(allAppointments.get(i).getStart_date_time().substring(0, 10), df);
                         
                        if (beginDate.isBefore(today)) {
                            pastAppointments.add(allAppointments.get(i));
                        } else {
                            futuerAppointments.add(allAppointments.get(i));
                        }
                    }
                    
                    request.setAttribute("pastAppointments", pastAppointments);
                    request.setAttribute("futuerAppointments", futuerAppointments);
                    
                    Doctor doctor = ds.getByDoctorID(patient.getDoctor_id());
                    request.setAttribute("doctorName", doctor.getFirst_name() + " " + doctor.getLast_name());
                    active = true;        
                    break;                
            }
            
            if (active){   
                getServletContext().getRequestDispatcher("/WEB-INF/viewAppointment.jsp").forward(request, response);
                return; 
            }      
            //end jihoon
           
            getServletContext().getRequestDispatcher("/WEB-INF/editAppointment.jsp").forward(request, response);
            return;
        } catch (Exception ex) {
            Logger.getLogger(EditAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
