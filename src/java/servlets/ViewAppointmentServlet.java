package servlets;

import java.io.IOException;
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
        
        if (request.getParameter("logout") != null) {
            session.invalidate();
            session = request.getSession();
        } else {
            if (user_name != null && !user_name.equals("")) {
                AccountService accountService = new AccountService();

                try {
                    Account account = accountService.get(user_name);
                    request.setAttribute("account", account);
                    
                    if (account.getProfile().equals("DOCTOR")) {
                        DoctorService doctorService = new DoctorService();
                        Doctor doctor = doctorService.get(account.getAccount_id());
                        request.setAttribute("user", doctor);                        
                        AppointmentService as = new AppointmentService();
                        List <Appointment> appointments = as.getByDoctorID(doctor.getDoctor_id());
                        ArrayList<Appointment> arrlAppointments = new ArrayList();
                        
                        for(int i =0;i<appointments.size();i++){
                            arrlAppointments.add(appointments.get(i));
                        }
                        
                        request.setAttribute("arrlAppointments",arrlAppointments );                        
                        PatientService patientService = new PatientService();
                        ArrayList<Patient> arrlPatients = new ArrayList();
                        
                        for(int i =0;i<appointments.size();i++){
                            Patient patient = patientService.getByPatientId(appointments.get(i).getPatient_id());
                            arrlPatients.add(patient);
                        }
                        
                        request.setAttribute("arrlPatients",arrlPatients );                        
                        ArrayList<String> arrType= new ArrayList(); 
                        
                        for (int i =0; i < appointments.size();i++){
                            arrType.add(appointments.get(i).getType_toString(i));
                        }
                        
                        request.setAttribute("arrType", arrType);     
                    } else if (account.getProfile().equals("ADMIN") || account.getProfile().equals("SYSADMIN")) {
                        AdministratorService administratorService = new AdministratorService();
                        Administrator administrator = administratorService.get(account.getAccount_id());
                        request.setAttribute("user", administrator);
                    } else if (account.getProfile().equals("PATIENT")) {
                        PatientService patientService = new PatientService();
                        Patient patient = patientService.get(account.getAccount_id());
                        request.setAttribute("user", patient);
                        AppointmentService as = new AppointmentService();
                        List<Appointment> appointments = as.getByPatientID(patient.getPatient_id());
                        ArrayList<String> arrType= new ArrayList(); 
                        
                        for (int i =0; i < appointments.size();i++){
                            arrType.add(appointments.get(i).getType_toString(i));
                        }
                        
                        request.setAttribute("arrType", arrType);
                        
                        ArrayList<String> arrTime= new ArrayList(); 
                        
                        for (int i =0; i < appointments.size();i++){
                            arrTime.add(appointments.get(i).getStart_date_time());
                        }
                        
                        request.setAttribute("arrTime", arrTime);
                        DoctorService ds = new DoctorService();
                        Doctor doctor = ds.getByDoctorID(patient.getDoctor_id());
                        request.setAttribute("doctorFirstName", doctor.getFirst_name());
                        request.setAttribute("doctorLastName", doctor.getLast_name());
                    }
                } catch (Exception ex) {
                    Logger.getLogger(WelcomeServlet.class.getName()).log(Level.SEVERE, null, ex);
                }   
            }        
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/viewAppointment.jsp").forward(request, response);
          return;
    }
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  
        
    }
}
